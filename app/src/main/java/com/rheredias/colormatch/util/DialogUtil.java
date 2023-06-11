package com.rheredias.colormatch.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.rheredias.colormatch.R;

public class DialogUtil {

  public static void showNotCancelableDialog(
      final Context context,
      final DialogInterface.OnClickListener listener,
      final String message
      ) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(message);
    builder.setNeutralButton(context.getString(R.string.accept), listener);
    AlertDialog alertDialog = builder.create();
    Activity activity = (Activity) context;
    alertDialog.setCancelable(false);

    if (!activity.isFinishing()) {
      alertDialog.show();
    }
  }

  public static AlertDialog createLoadingDialog(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("Context cannot be null");
    }
    return new AlertDialog.Builder(context, R.style.TransparentAlertDialogTheme)
        .setView(R.layout.dialog_loading)
        .setCancelable(false)
        .create();

  }
}