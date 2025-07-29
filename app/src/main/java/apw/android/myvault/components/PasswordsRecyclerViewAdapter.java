package apw.android.myvault.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import apw.android.myvault.R;
import apw.android.myvault.enums.PasswordEntry;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PasswordsRecyclerViewAdapter extends RecyclerView.Adapter<PasswordsRecyclerViewAdapter.PasswordsViewHolder> {
    private List<PasswordEntry> linksList = new ArrayList<>();
    static class PasswordsViewHolder extends RecyclerView.ViewHolder{
        TextView password, title;
        ImageView icon;
        public PasswordsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            password = itemView.findViewById(R.id.password);
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    private Context context;

    public interface PasswordClickListener{
        void onClick(PasswordEntry links);
        void onLongClick(PasswordEntry links, View view);
    }

    private final PasswordClickListener listener;

    @SuppressLint("NotifyDataSetChanged")
    public void setPasswordsList(List<PasswordEntry> linksList){
        this.linksList = linksList;
        notifyDataSetChanged();
    }

    public PasswordsRecyclerViewAdapter(Context context, PasswordClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public PasswordsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.password_item, viewGroup, false);
        return new PasswordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PasswordsViewHolder viewHolder, int i) {
        PasswordEntry password = linksList.get(i);
        if (password.getTITLE().toLowerCase().trim().contains("instagram") || password.getTITLE().toLowerCase().trim().contains("insta")) {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_instagram));
        } else if (password.getTITLE().toLowerCase().trim().contains("facebook") || password.getTITLE().toLowerCase().trim().contains("fb")) {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_facebook));
        } else if (password.getTITLE().toLowerCase().trim().contains("gmail") || password.getTITLE().toLowerCase().trim().contains("mail") || password.getTITLE().toLowerCase().trim().contains("email")) {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_gmail));
        } else if (password.getTITLE().toLowerCase().trim().contains("twitter") || password.getTITLE().toLowerCase().trim().equals("x")) {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_twitter));
        } else if (password.getTITLE().toLowerCase().trim().contains("github") || password.getTITLE().toLowerCase().trim().contains("git") || password.getTITLE().toLowerCase().trim().contains("code")) {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_github));
        } else if (password.getTITLE().toLowerCase().trim().contains("linkedln") || password.getTITLE().toLowerCase().trim().contains("linkedin")) {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_linkedln));
        } else if (password.getTITLE().toLowerCase().trim().contains("microsoft") || password.getTITLE().toLowerCase().trim().contains("window")) {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_microsoft));
        } else {
            viewHolder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_password));
        }
        viewHolder.password.setText(password.getPASSWORD());
        viewHolder.title.setText(password.getTITLE());
        viewHolder.itemView.setOnClickListener(v -> listener.onClick(password));
        viewHolder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(password, v);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return linksList.size();
    }
}
