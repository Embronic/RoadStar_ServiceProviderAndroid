package com.roadstar_serviceprovider.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.activity.RegisterCompanyActivity;
import com.roadstar_serviceprovider.activity.RegisterProviderActivity;
import com.roadstar_serviceprovider.activity.RegisterDriverActivity;
import com.roadstar_serviceprovider.app.AppController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils extends AppCompatActivity {

    private final static String TAG = "FileUtils";

    static File documentFile = null;
    static String documentName = "pdf_doc";

    private static Activity mActivity;

    private static String filePath = null;

    public FileUtils(Activity activity) {
        this.mActivity = activity;
    }

    public static void dumpDocumentMetaData(Uri uri) {
        // BEGIN_INCLUDE (dump_metadata)

        // The query, since it only applies to a single document, will only return one row.
        // no need to filter, sort, or select fields, since we want all fields for one
        // document.
        Cursor cursor = mActivity.getContentResolver()
                .query(uri, null, null, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is provider-specific, and
                // might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name: " + displayName);
                documentName = displayName;
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null.  But since an int can't be
                // null in java, the behavior is implementation-specific, which is just a fancy
                // term for "unpredictable".  So as a rule, check if it's null before assigning
                // to an int.  This will happen often:  The storage API allows for remote
                // files, whose size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString will do the
                    // conversion automatically.
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }
                Log.i(TAG, "Size: " + size);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        // END_INCLUDE (dump_metadata)
    }


    // Get file name from Uri
    public static String uri2filename(Uri uri) {
        String ret = "";
        String scheme = uri.getScheme();

        if (scheme.equals("file")) {
            ret = uri.getLastPathSegment();
        } else if (scheme.equals("content")) {
            Cursor cursor = mActivity.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                ret = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }

        return ret;
    }

    public static void downloadFile(Uri uri, int screenStatus) {

        ProgressDialog progressDialogFile = new ProgressDialog(mActivity);
        progressDialogFile.setMessage("Please wait...");

        Log.e(TAG, "uri --- > " + uri.toString());

        AsyncTask<Uri, Void, File> loadStreamAsyncTask = new AsyncTask<Uri, Void, File>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialogFile.show();
            }

            @Override
            protected File doInBackground(Uri... uris) {
                FileUtils.dumpDocumentMetaData(uris[0]);

                InputStream inputStream = getStreamFromUri(uris[0]);
                documentName = uri2filename(uri);

                File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        mActivity.getString(R.string.app_name) + "/Document/Sent/");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                documentFile = new File(
                        directory,
                        documentName);

                if (inputStream != null) {
                    OutputStream outStream = null;
                    try {
                        byte[] buffer = new byte[inputStream.available()];
                        inputStream.read(buffer);

                        outStream = new FileOutputStream(documentFile);
                        outStream.write(buffer);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return documentFile;
            }

            @Override
            protected void onPostExecute(File file) {
                filePath = file.getPath();

                if (file == null) {
                    Toast.makeText(mActivity, "File cannot be opened.", Toast.LENGTH_LONG);
                    return;
                }

                if (screenStatus == 0) {
                    ((RegisterCompanyActivity) mActivity).setDocumentFile(filePath);

                } else if (screenStatus == 1) {
                    ((RegisterProviderActivity) mActivity).setDocumentFile(filePath);

                } else {
                    ((RegisterDriverActivity) mActivity).setDocumentFile(filePath);
                }

                progressDialogFile.dismiss();

            }
        };

        loadStreamAsyncTask.execute(uri);

    }

    private static InputStream getStreamFromUri(Uri uri) {
        //ParcelFileDescriptor parcelFileDescriptor = null;
        InputStream inputStream = null;
        try {
            inputStream = mActivity.getContentResolver().openInputStream(uri);
            return inputStream;
        } catch (Exception e) {
            Log.e(TAG, "Failed to load file.", e);
            return null;
        }
    }


    public static String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        try {
            Cursor cursor = AppController.getContext().getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor == null)
                return path;
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                path = cursor.getString(column_index);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String getImagePath(final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(AppController.getContext(), uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }


    public static String getDataColumn(Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = AppController.getContext().getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static boolean validateFileSize(File file) {
        try {
            long length = 0;
            length = file.length();
            length = length / 1024;
            System.out.println("File Path : " + file.getPath() + ", File size : " + length + " KB");
            if (length > mActivity.getResources().getInteger(R.integer.max_image_size)) {
                return false;
            } else
                return true;
        } catch (Exception e) {
            System.out.println("File not found : " + e.getMessage() + e);
        }
        return false;
    }


    public static byte[] getByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmapFromUri(Uri uri, Context context) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri, "r");

            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();

            return image;
        } catch (Exception e) {
            Log.e("ByteArray", "Failed to load image.", e);
            return null;
        } finally {
            try {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ByteArray", "Error closing ParcelFile Descriptor");
            }
        }
    }
}
