package apw.android.myvault.fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import apw.android.myvault.R;
import apw.android.myvault.components.LinksRecyclerViewAdapter;
import apw.android.myvault.components.PasswordsRecyclerViewAdapter;
import apw.android.myvault.database.LinksDAO;
import apw.android.myvault.database.PasswordsDAO;
import apw.android.myvault.databinding.LinksFragmentBinding;
import apw.android.myvault.databinding.PasswordsFragmentBinding;
import apw.android.myvault.enums.LinkEntry;
import apw.android.myvault.enums.PasswordEntry;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PasswordsFragment extends Fragment {

    private static final int WINDOW_FLAG = WindowManager.LayoutParams.FLAG_SECURE;
    private PasswordsFragmentBinding binding;
    private PasswordsRecyclerViewAdapter adapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = PasswordsFragmentBinding.inflate(inflater, container, false);
        getActivity().getWindow().setFlags(
                WINDOW_FLAG,
                WINDOW_FLAG
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PasswordsRecyclerViewAdapter(requireContext(), new PasswordsRecyclerViewAdapter.PasswordClickListener() {
            @Override
            public void onClick(PasswordEntry password) {
                showBottomSheet(requireContext(), password);
            }
            @Override
            public void onLongClick(PasswordEntry password, View view) {
                showPopupMenu(view, password);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
        PasswordsDAO dao = new PasswordsDAO(requireContext());
        List<PasswordEntry> passwords = dao.getPasswords();
        adapter.setPasswordsList(passwords);
    }

    private void showPopupMenu(View view, PasswordEntry passwordEntry) {
        Context context = requireContext();
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.link_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_open) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(passwordEntry.getPASSWORD()));
                context.startActivity(intent);
                return true;
            } else if (id == R.id.menu_delete) {
                PasswordsDAO dao = new PasswordsDAO(view.getContext());
                dao.removeEncryptedPassword(passwordEntry.getID());
                refresh();
                return true;
            }
            return false;
        });

        popup.show();
    }

    @SuppressLint("SetTextI18n")
    public void showBottomSheet(Context context, PasswordEntry passwordEntry) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.url_info, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        TextView password = view.findViewById(R.id.readonlyUrl);
        TextView username = view.findViewById(R.id.readonlyUsername);
        username.setVisibility(View.VISIBLE);
        MaterialButton delete = view.findViewById(R.id.deleteBtn);
        MaterialButton openUrl = view.findViewById(R.id.openLinkBtn);
        MaterialButton copyUrl = view.findViewById(R.id.copyUrl);
        dialogTitle.setText("Password Details");
        delete.setOnClickListener(v -> {
            PasswordsDAO dao = new PasswordsDAO(view.getContext());
            dao.removeEncryptedPassword(passwordEntry.getID());
            refresh();
            bottomSheetDialog.dismiss();
        });
        copyUrl.setOnClickListener(v -> {
            try {
                ClipboardManager clipBoard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText(passwordEntry.getTITLE(), passwordEntry.getPASSWORD());
                if (clipBoard != null) {
                    clipBoard.setPrimaryClip(clipData);
                }
                Toast.makeText(requireContext(), "Password copied to clipboard", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(requireContext(), "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        username.setText(passwordEntry.getUSERNAME());
        password.setText(passwordEntry.getPASSWORD());
        bottomSheetDialog.show();
    }

    public void refresh() {
        PasswordsDAO dao = new PasswordsDAO(requireContext());
        List<PasswordEntry> updatedLinks = dao.getPasswords();
        adapter.setPasswordsList(updatedLinks);
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
