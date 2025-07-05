package apw.android.myvault.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import apw.android.myvault.R;
import com.google.android.material.appbar.MaterialToolbar;

public class TopAppBar extends MaterialToolbar {

    public TopAppBar(Context context) {
        this(context, null);
    }

    public TopAppBar(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.appcompat.R.attr.toolbarStyle);
    }

    public TopAppBar(Context context, AttributeSet attrs, int toolbarStyle) {
        super(context, attrs, toolbarStyle);
        LayoutInflater.from(context).inflate(R.layout.top_app_bar_view, this);

    }
}