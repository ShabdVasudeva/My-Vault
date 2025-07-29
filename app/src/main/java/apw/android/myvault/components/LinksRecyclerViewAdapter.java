package apw.android.myvault.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import apw.android.myvault.R;
import apw.android.myvault.enums.LinkEntry;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class LinksRecyclerViewAdapter extends RecyclerView.Adapter<LinksRecyclerViewAdapter.LinksViewHolder> {
    private List<LinkEntry> linksList = new ArrayList<>();
    static class LinksViewHolder extends RecyclerView.ViewHolder{
        TextView url;
        public LinksViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.link_url);
        }
    }

    private Context context;

    public interface LinkClickListener{
        void onClick(LinkEntry links);
        void onLongClick(LinkEntry links, View view);
    }

    private final LinkClickListener listener;

    @SuppressLint("NotifyDataSetChanged")
    public void setLinksList(List<LinkEntry> linksList){
        this.linksList = linksList;
        notifyDataSetChanged();
    }

    public LinksRecyclerViewAdapter(Context context, LinkClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public LinksViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_link, viewGroup, false);
        return new LinksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LinksViewHolder viewHolder, int i) {
        LinkEntry link = linksList.get(i);
        viewHolder.url.setText(link.getURL());
        viewHolder.itemView.setOnClickListener(v -> listener.onClick(link));
        viewHolder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(link, v);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return linksList.size();
    }
}
