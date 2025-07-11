package apw.android.myvault.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import apw.android.myvault.R;
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

    protected void setAddClickButtonListener(OnAddClickListener mListener){
        this.mListener = mListener;
    }

}