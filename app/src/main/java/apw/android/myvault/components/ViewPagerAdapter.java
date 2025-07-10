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

    public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int i) {
        return switch (i) {
            case 0 -> new PasswordsFragment();
            case 1 -> new LinksFragment();
            case 2 -> new SettingsFragment();
            default -> new PasswordsFragment();
        };
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}