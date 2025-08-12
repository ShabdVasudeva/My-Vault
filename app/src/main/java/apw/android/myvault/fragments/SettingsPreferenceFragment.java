package apw.android.myvault.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import apw.android.myvault.R;

public class SettingsPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle bundle, @Nullable String s) {
        setPreferencesFromResource(R.xml.settings, s);

        PreferenceCategory passwords = findPreference("passwords");
        ListPreference passwordOrder = findPreference("password_order");
        ListPreference linksOrder = findPreference("links_order");
        PreferenceCategory links = findPreference("links");
        PreferenceCategory about = findPreference("about");
        Preference app_version = findPreference("app_version");
        Preference dev_name = findPreference("dev_name");
        Preference source_code = findPreference("source_code");

        about.setIconSpaceReserved(false);
        app_version.setIconSpaceReserved(false);
        dev_name.setIconSpaceReserved(false);
        source_code.setIconSpaceReserved(false);

        source_code.setOnPreferenceClickListener((preference -> {
            String url = "https://github.com/ShabdVasudeva/My-Vault";
            Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getActivity().startActivity(browseIntent);
            return false;
        }));

        dev_name.setOnPreferenceClickListener((preference -> {
            String url = "https://github.com/ShabdVasudeva";
            Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getActivity().startActivity(browseIntent);
            return false;
        }));


        if (passwords != null) passwords.setIconSpaceReserved(false);
        if (passwordOrder != null) {
            passwordOrder.setIconSpaceReserved(false);
            passwordOrder.setOnPreferenceChangeListener((preference, newValue) -> true);
        }

        if (links != null) links.setIconSpaceReserved(false);
        if (linksOrder != null) {
            linksOrder.setIconSpaceReserved(false);
            linksOrder.setOnPreferenceChangeListener((preference, newValue) -> true);
        }
    }
}
