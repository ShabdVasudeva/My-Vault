package apw.android.myvault.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import apw.android.myvault.R;
import apw.android.myvault.enums.Tab;
import com.google.android.material.appbar.MaterialToolbar;

public class TopAppBar extends MaterialToolbar {

    public interface OnAddClickListener{
        void onClick();
    }

    private OnAddClickListener mListener;

    public TopAppBar(Context context) {
        this(context, null);
        init(context);
    }

    public TopAppBar(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.appcompat.R.attr.toolbarStyle);
        init(context);
    }

    public TopAppBar(Context context, AttributeSet attrs, int toolbarStyle) {
        super(context, attrs, toolbarStyle);
        init(context);
    }

    private void init(Context context) {
        removeAllViews();
        LayoutInflater.from(context).inflate(R.layout.top_app_bar_view, this, true);
        ImageButton addButton = findViewById(R.id.add_btn);
        addButton.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onClick();
            }
        });
    }

    public void setAddClickButtonListener(OnAddClickListener mListener){
        this.mListener = mListener;
    }

    public void updateAddButtonVisibility(Tab currentTab) {
        View addButton = findViewById(R.id.add_btn);
        if (currentTab == Tab.SETTINGS) {
            addButton.setVisibility(View.GONE);
        } else {
            addButton.setVisibility(View.VISIBLE);
        }
    }
}