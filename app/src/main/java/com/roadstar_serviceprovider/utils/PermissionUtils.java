package com.roadstar_serviceprovider.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.roadstar_serviceprovider.R;

import java.util.List;

public class PermissionUtils {

    public static final int REQUEST_PERMISSION_SETTING = 160;

    public static AlertDialog alert = null;

    public static void checkDexterPermissions(Activity activity, String[] permissions, PermissionCallback callback) {
        Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            if (callback != null)
                                callback.onPermissionResult(true);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            AlertDialog.Builder permissionDialog = new AlertDialog.Builder(activity);
                            permissionDialog.setMessage(activity.getString(R.string.allow_permissions))
                                    .setTitle(R.string.need_permission)
                                    .setCancelable(false)
                                    .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alert.dismiss();
                                        }
                                    })
                                    .setPositiveButton(activity.getString(R.string.setting),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    alert.dismiss();
                                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                                    intent.setData(uri);
                                                    activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                                }
                                            });

                            alert = permissionDialog.create();
                            alert.show();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            if (callback != null)
                                callback.onPermissionResult(false);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(activity, "Something went wrong with permissions", Toast.LENGTH_LONG).show();
            }
        }).onSameThread().check();
    }


    public static void checkDexterPermissionsWithoutDenied(Activity activity, String[] permissions, PermissionCallback callback) {
        Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            if (callback != null)
                                callback.onPermissionResult(true);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(activity, "Something went wrong with permissions", Toast.LENGTH_LONG).show();
            }
        }).onSameThread().check();
    }

    public interface PermissionCallback {
        /**
         * @param granted true if granted, false otherwise
         */
        void onPermissionResult(boolean granted);
    }
}
