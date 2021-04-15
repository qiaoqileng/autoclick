
package com.qql.autokick.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    private static Toast sToast;

    private ToastHelper() {
    }

    public static void showToast(Context context, String text) {
        showToastInner(context, text, 0);
    }

    public static void showToast(Context context, int stringId) {
        showToastInner(context, context.getString(stringId), 0);
    }

    public static void showToastLong(Context context, String text) {
        showToastInner(context, text, 1);
    }

    public static void showToastLong(Context context, int stringId) {
        showToastInner(context, context.getString(stringId), 1);
    }

    private static void showToastInner(Context context, String text, int duration) {
        ensureToast(context);
        sToast.setText(text);
        sToast.setDuration(duration);
        sToast.show();
    }

    @SuppressLint({"ShowToast"})
    private static void ensureToast(Context context) {
        if (sToast == null) {
            Class var1 = ToastHelper.class;
            synchronized(ToastHelper.class) {
                if (sToast == null) {
                    sToast = Toast.makeText(context.getApplicationContext(), " ", 0);
                }
            }
        }
    }
}
