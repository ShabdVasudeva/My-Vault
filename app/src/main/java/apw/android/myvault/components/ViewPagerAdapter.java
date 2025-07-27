package apw.android.myvault.components;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import apw.android.myvault.fragments.LinksFragment;
import apw.android.myvault.fragments.PasswordsFragment;
import apw.android.myvault.fragments.SettingsFragment;
import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final PasswordsFragment passwordsFragment = new PasswordsFragment();
    private final LinksFragment linksFragment = new LinksFragment();
    private final SettingsFragment settingsFragment = new SettingsFragment();

    public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int i) {
        return switch (i) {
            case 0 -> passwordsFragment;
            case 1 -> linksFragment;
            case 2 -> settingsFragment;
            default -> passwordsFragment;
        };
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public LinksFragment getLinksFragment() {
        return linksFragment;
    }
}
