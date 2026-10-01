package com.systemtuner.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

public class SecureSettingsHelper {

    public static boolean hasSecurePermission(Context context) {
        return context.checkSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS)
                == PackageManager.PERMISSION_GRANTED;
    }

    /** Returns true on success, false if the permission isn't granted or the write failed. */
    public static boolean setAnimationScales(Context context, float scale) {
        if (!hasSecurePermission(context)) return false;
        try {
            Settings.Global.putFloat(context.getContentResolver(), "window_animation_scale", scale);
            Settings.Global.putFloat(context.getContentResolver(), "transition_animation_scale", scale);
            Settings.Global.putFloat(context.getContentResolver(), "animator_duration_scale", scale);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAdbEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) == 1;
    }

    public static boolean setAdbEnabled(Context context, boolean enabled) {
        if (!hasSecurePermission(context)) return false;
        try {
            Settings.Global.putInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, enabled ? 1 : 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isStayAwakeEnabled(Context context) {
        int value = Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.STAY_ON_WHILE_PLUGGED_IN, 0);
        return value != 0;
    }

    public static boolean setStayAwake(Context context, boolean enabled) {
        if (!hasSecurePermission(context)) return false;
        try {
            // 3 = plugged in via USB (1) + AC (2); a broad, commonly-used "always" setting.
            Settings.Global.putInt(context.getContentResolver(),
                    Settings.Global.STAY_ON_WHILE_PLUGGED_IN, enabled ? 3 : 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}