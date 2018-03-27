/*Copyright (C) 2015 The ResurrectionRemix Project
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
*/
package com.android.settings.rr;

import android.os.Bundle;
import android.content.Context;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;


import android.provider.SearchIndexableResource;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.android.settings.rr.utils.RRUtils;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;
import android.provider.Settings;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
@SearchIndexable
public class StatusBarWeather extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "StatusBarWeather";
    private static final String LOCATION = "status_bar_show_weather_location";
    private static final String WEATHER = "status_bar_show_weather_temp";

    private ListPreference mWeather;
    private ListPreference mLocation;
    @Override
    public int getMetricsCategory() {
        return MetricsEvent.RESURRECTED;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.statusbar_weather);
        int loc = Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.STATUS_BAR_SHOW_WEATHER_TEMP, 0);
        mWeather =
                (ListPreference) findPreference(WEATHER);
        mLocation =
                (ListPreference) findPreference(LOCATION);
        mWeather.setOnPreferenceChangeListener(this);
        updateprefs(loc);
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mWeather) {
             int value = Integer.parseInt((String) objValue);
             updateprefs(value);
             return true;
        } 
        return false;
    }

    public void updateprefs(int enabled) {
       if (enabled == 0) {
           mLocation.setEnabled(false);
       } else {
           mLocation.setEnabled(true);
       }
    }

    /**
     * For Search.
     */
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider() {
            @Override
            public List<SearchIndexableResource> getXmlResourcesToIndex(Context context, boolean enabled) {
                ArrayList<SearchIndexableResource> result =
                    new ArrayList<SearchIndexableResource>();
                    SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.statusbar_weather;
                    result.add(sir);
                    return result;
            }

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                return keys;
            }
        };
}
