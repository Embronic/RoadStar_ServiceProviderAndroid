package com.roadstar_serviceprovider.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.callback.DialogCallback;

public class DialogUtil {

    /**
     * get a blocking progress dialog.
     *
     * @param context context of current activity/fragment
     * @return create of {@link ProgressDialog}
     */
    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            progressDialog = new ProgressDialog(context, R.style.ProgressTheme);
        else
            progressDialog = new ProgressDialog(context, R.style.AlertDialog_Holo);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static void showToast(@NonNull Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@NonNull Context context, int msg) {
        Toast.makeText(context.getApplicationContext(), context.getResources().getString(msg), Toast.LENGTH_SHORT).show();
    }

    public static void showCancelRequestAlertDialog(Context activity, FragmentManager manager, DialogCallback mCallback)//, final
    {
        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.cancel_request_dialog);

        RelativeLayout submit_btRel = dialog.findViewById(R.id.submit_btRel);
        RelativeLayout cancel_btRel = dialog.findViewById(R.id.cancel_btRel);

        submit_btRel.setOnClickListener(v -> {
           /* if (mCallback != null) {
                mCallback.onPersonalInfoUpdate();
            }*/
            dialog.dismiss();
        });

        cancel_btRel.setOnClickListener(v -> {
            /*if (mCallback != null) {
                mCallback.cancelPressed();
            }*/
            dialog.dismiss();
        });

        dialog.show();
    }

    public static void showChooseCameraAlertDialog(Context activity, FragmentManager manager, DialogCallback mCallback) {
        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.choose_picture_dialog);

        RelativeLayout submit_btRel = dialog.findViewById(R.id.submit_btRel);
        RelativeLayout cancel_btRel = dialog.findViewById(R.id.cancel_btRel);

        submit_btRel.setOnClickListener(v -> {
           /* if (mCallback != null) {
                mCallback.onPersonalInfoUpdate();
            }*/
            dialog.dismiss();
        });

        cancel_btRel.setOnClickListener(v -> {
            /*if (mCallback != null) {
                mCallback.cancelPressed();
            }*/
            dialog.dismiss();
        });

        dialog.show();
    }


    public static void showLogoutAlertDialog(Context activity, FragmentManager manager, DialogCallback mCallback)//, final
    {
        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.logout_dialog);

        RelativeLayout submit_btRel = dialog.findViewById(R.id.submit_btRel);
        RelativeLayout cancel_btRel = dialog.findViewById(R.id.cancel_btRel);

        submit_btRel.setOnClickListener(v -> {
           /* if (mCallback != null) {
                mCallback.onPersonalInfoUpdate();
            }*/
            dialog.dismiss();
        });

        cancel_btRel.setOnClickListener(v -> {
            /*if (mCallback != null) {
                mCallback.cancelPressed();
            }*/
            dialog.dismiss();
        });

        dialog.show();
    }


}
