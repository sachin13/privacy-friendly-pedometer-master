/*
    Privacy Friendly Pedometer is licensed under the GPLv3.
    Copyright (C) 2017  Tobias Neidig

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package pedometer.step.com.m_step.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import org.secuso.privacyfriendlyactivitytracker.R;

import pedometer.step.com.m_step.utils.StepDetectionServiceHelper;

/**
 * Receives the broadcast if the own package is replaced and
 * starts the step detection and it's required services if
 * step detection is enabled.
 */

public class OnPackageReplacedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // init preferences
        PreferenceManager.setDefaultValues(context,   R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(context,   R.xml.pref_notification, false);

        // start all services
        StepDetectionServiceHelper.startAllIfEnabled(context);
    }
}
