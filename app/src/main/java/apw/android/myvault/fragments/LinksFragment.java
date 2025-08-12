package apw.android.myvault.fragments;

import android.annotation.SuppressLint;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import apw.android.myvault.R;
import apw.android.myvault.components.LinksRecyclerViewAdapter;
import apw.android.myvault.database.LinksDAO;
import apw.android.myvault.databinding.LinksFragmentBinding;
import apw.android.myvault.enums.LinkEntry;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class LinksFragment extends Fragment {

    private LinksFragmentBinding binding;
    private LinksRecyclerViewAdapter adapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = LinksFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LinksRecyclerViewAdapter(requireContext(), new LinksRecyclerViewAdapter.LinkClickListener() {
            @Override
            public void onClick(LinkEntry links) {
                showBottomSheet(requireContext(), links);
            }
            @Override
            public void onLongClick(LinkEntry links, View view) {
                showPopupMenu(view, links);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
        LinksDAO dao = new LinksDAO(requireContext());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String sortOrder = prefs.getString("links_order", "newer");
        List<LinkEntry> links = dao.getUrlList(sortOrder);
        adapter.setLinksList(links);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                toggleEmptyView();
            }
        });
    }

    private void toggleEmptyView() {
        if (adapter.getItemCount() == 0 || adapter.getItemCount() == -1) {
            binding.recyclerView.setVisibility(View.GONE);
            binding.notFound.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.notFound.setVisibility(View.GONE);
        }
    }

    private void showPopupMenu(View view, LinkEntry link) {
        Context context = requireContext();
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.link_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_open) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getURL()));
                context.startActivity(intent);
                return true;
            } else if (id == R.id.menu_delete) {
                LinksDAO dao = new LinksDAO(view.getContext());
                dao.removeEncryptedLink(link.getID());
                refresh();
                return true;
            }
            return false;
        });

        popup.show();
    }

    @SuppressLint("SetTextI18n")
    public void showBottomSheet(Context context, LinkEntry link) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.url_info, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        TextView url = view.findViewById(R.id.readonlyUrl);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        TextView username = view.findViewById(R.id.readonlyUsername);
        username.setVisibility(View.GONE);
        MaterialButton delete = view.findViewById(R.id.deleteBtn);
        MaterialButton openUrl = view.findViewById(R.id.openLinkBtn);
        MaterialButton copyUrl = view.findViewById(R.id.copyUrl);
        dialogTitle.setText("URL Details");
        delete.setOnClickListener(v -> {
            LinksDAO dao = new LinksDAO(view.getContext());
            dao.removeEncryptedLink(link.getID());
            refresh();
            bottomSheetDialog.dismiss();
        });
        copyUrl.setOnClickListener(v -> {
            try {
                ClipboardManager clipBoard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("My Vault", link.getURL());
                if (clipBoard != null) {
                    clipBoard.setPrimaryClip(clipData);
                }
                Toast.makeText(requireContext(), "URL copied to clipboard", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
        openUrl.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getURL()));
            context.startActivity(intent);
            bottomSheetDialog.dismiss();
        });
        url.setText(link.getURL());
        bottomSheetDialog.show();
    }

    public void refresh() {
        LinksDAO dao = new LinksDAO(requireContext());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String sortOrder = prefs.getString("links_order", "newer");
        List<LinkEntry> updatedLinks = dao.getUrlList(sortOrder);
        adapter.setLinksList(updatedLinks);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
