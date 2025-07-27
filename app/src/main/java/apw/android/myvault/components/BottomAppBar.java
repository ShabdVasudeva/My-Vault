package apw.android.myvault.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import apw.android.myvault.R;
import apw.android.myvault.enums.Tab;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class BottomAppBar extends LinearLayout {
    private ImageView iconKey, iconLink, iconSettings;
    private LinearLayout navKey, navLink, navSettings;

    public Tab currentField;

    public BottomAppBar(Context context) {
        super(context);
        init(context);
    }
    public BottomAppBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public BottomAppBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bottom_app_bar_view, this, true);

        iconKey = findViewById(R.id.iconKey);
        iconLink = findViewById(R.id.iconLink);
        iconSettings = findViewById(R.id.iconSettings);

        navKey = findViewById(R.id.navKey);
        navLink = findViewById(R.id.navLink);
        navSettings = findViewById(R.id.navSettings);
    }
    public void selectTab(@NonNull Tab field) {
        Context mContext = getContext();
        Drawable keyDrawable = getDrawable(mContext, field == Tab.PASSWORD ? R.drawable.password_filled : R.drawable.password_outlined);
        Drawable linkDrawable = getDrawable(mContext, field == Tab.LINKS ? R.drawable.link_filled : R.drawable.link_outlined);
        Drawable settingsDrawable = getDrawable(mContext, field == Tab.SETTINGS ? R.drawable.settings_filled : R.drawable.settings_outlined);

        iconLink.setImageDrawable(linkDrawable);
        iconKey.setImageDrawable(keyDrawable);
        iconSettings.setImageDrawable(settingsDrawable);
        switch (field) {
            case PASSWORD -> {
                currentField = Tab.PASSWORD;
            }
            case LINKS -> {
                currentField = Tab.LINKS;
            }
            case SETTINGS -> {
                currentField = Tab.SETTINGS;
            }
            default -> {
                currentField = null;
            }
        }
    }

    public Tab getCurrentField() {
        return currentField;
    }
    public LinearLayout getNavKey() {
        return navKey;
    }
    public LinearLayout getNavLink() {
        return navLink;
    }
    public LinearLayout getNavSettings() {
        return navSettings;
    }
}
