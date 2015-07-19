package cl.monsoon.s1next.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;

import javax.inject.Inject;

import cl.monsoon.s1next.App;
import cl.monsoon.s1next.R;
import cl.monsoon.s1next.data.pref.GeneralPreferencesManager;
import cl.monsoon.s1next.data.pref.ThemeManager;
import cl.monsoon.s1next.event.FontSizeChangeEvent;
import cl.monsoon.s1next.event.ThemeChangeEvent;
import cl.monsoon.s1next.singleton.BusProvider;
import cl.monsoon.s1next.util.DeviceUtil;
import cl.monsoon.s1next.util.ResourceUtil;
import cl.monsoon.s1next.view.activity.SettingsActivity;

/**
 * An Activity includes general settings that allow users
 * to modify general features and behaviors such as theme
 * and font size.
 */
public final class GeneralPreferenceFragment extends BasePreferenceFragment
        implements Preference.OnPreferenceClickListener {

    public static final String PREF_KEY_THEME = "pref_key_theme";
    public static final String PREF_KEY_FONT_SIZE = "pref_key_font_size";
    public static final String PREF_KEY_SIGNATURE = "pref_key_signature";

    private static final String PREF_KEY_DOWNLOADS = "pref_key_downloads";

    @Inject
    GeneralPreferencesManager mGeneralPreferencesManager;

    @Inject
    ThemeManager mThemeManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_general);
        App.getAppComponent(getActivity()).inject(this);

        findPreference(PREF_KEY_DOWNLOADS).setOnPreferenceClickListener(this);
        findPreference(PREF_KEY_SIGNATURE).setSummary(DeviceUtil.getSignature());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case PREF_KEY_THEME:
                mThemeManager.invalidateTheme();
                BusProvider.get().post(new ThemeChangeEvent());

                break;
            case PREF_KEY_FONT_SIZE:
                mGeneralPreferencesManager.invalidateTextScale();
                // change scaling factor for fonts
                ResourceUtil.setScaledDensity(getResources(),
                        mGeneralPreferencesManager.getTextScale());
                BusProvider.get().post(new FontSizeChangeEvent());

                break;
            case PREF_KEY_SIGNATURE:
                mGeneralPreferencesManager.invalidateSignatureEnabled();

                break;
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case PREF_KEY_DOWNLOADS:
                SettingsActivity.startDownloadSettingsActivity(preference.getContext());

                break;
        }

        return true;
    }
}