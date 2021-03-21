package com.roadstar_serviceprovider.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.adapter.CitySpinnerAdapter;
import com.roadstar_serviceprovider.adapter.DocumentAdapter;
import com.roadstar_serviceprovider.adapter.StatesSpinnerAdapter;
import com.roadstar_serviceprovider.api.ApiClient;
import com.roadstar_serviceprovider.api.ApiInterface;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.callback.RecyclerItemClickListener;
import com.roadstar_serviceprovider.databinding.ActivityRegisterCompanyBinding;
import com.roadstar_serviceprovider.models.RegisterCompany.RegisterResponse;
import com.roadstar_serviceprovider.models.city.CityData;
import com.roadstar_serviceprovider.models.city.CityResponse;
import com.roadstar_serviceprovider.models.state.StatesData;
import com.roadstar_serviceprovider.models.document.DocumentData;
import com.roadstar_serviceprovider.models.document.DocumentResponse;
import com.roadstar_serviceprovider.utils.AppUtils;
import com.roadstar_serviceprovider.utils.Constants;
import com.roadstar_serviceprovider.utils.FileUtils;
import com.roadstar_serviceprovider.utils.ImageCompression;
import com.roadstar_serviceprovider.utils.Lg;
import com.roadstar_serviceprovider.utils.NetworkHandler;
import com.roadstar_serviceprovider.utils.PermissionUtils;
import com.roadstar_serviceprovider.utils.SessionManager;
import com.roadstar_serviceprovider.utils.ValidUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.roadstar_serviceprovider.utils.AppUtils.showAlertDialog;
import static com.roadstar_serviceprovider.utils.AppUtils.showToast;
import static com.roadstar_serviceprovider.utils.Constants.SELECT_CAMERA;
import static com.roadstar_serviceprovider.utils.Constants.SELECT_GALLERY;
import static com.roadstar_serviceprovider.utils.Constants.mCamRequestedUri;
import static com.roadstar_serviceprovider.utils.GeneralUtils.showPictureDialog;
import static com.roadstar_serviceprovider.utils.PermissionUtils.REQUEST_PERMISSION_SETTING;

public class RegisterCompanyActivity extends BaseActivity implements RecyclerItemClickListener<DocumentData> {

    private static final String TAG = "RegisterCompanyActivity";
    ActivityRegisterCompanyBinding mBinding;
    private List<MultipartBody.Part> fileToUpload = new ArrayList<>();
    DocumentAdapter documentAdapter = null;
    private int itemPosition = -1;
    private String mStateId = "";
    private String mCityId = "";

    String[] values = {"1", "2", "3", "4"};

    /*Start RegisterOne activity intent*/
    public static void startActivity(Context context, ArrayList<StatesData> arrayList) {
        Intent homeIntent = new Intent(context, RegisterCompanyActivity.class);
        homeIntent.putParcelableArrayListExtra(Constants.STATE_LIST, arrayList);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_company);
        initToolbar();
        initView();
        initClickListeners();
        setNoVehicleSpinnerAdapter();
    }

    private void fetchDocument() {
        if (NetworkHandler.isConnected(mThis)) {
            showHideProgressDialog(true);
            AppUtils.hideKeyboard(mThis);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<DocumentResponse> call = apiService.getCompanyDocument();

            call.enqueue(new Callback<DocumentResponse>() {
                @Override
                public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {
                    showHideProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            setDocumentAdapter(response.body().getData());
                            setStateSpinnerAdapter();

                        } else {
                            AppUtils.showToast(mThis, response.body().getMessage());

                        }

                    } else {
                        AppUtils.showToast(R.string.server_error);
                    }
                }

                @Override
                public void onFailure(Call<DocumentResponse> call, Throwable t) {
                    showHideProgressDialog(false);
                    Lg.e(TAG, t.getMessage());
                    AppUtils.showToast(R.string.server_error);
                }
            });
        }
    }

    private void setDocumentRecycler() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mThis);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.documentRv.setLayoutManager(linearLayoutManager1);

    }


    //Set Trip Journey data adapter for RecyclerView
    private void setDocumentAdapter(ArrayList<DocumentData> arrayList) {
//        mBinding.swipeRefresh.setRefreshing(false);
        if (null == documentAdapter) {
            documentAdapter = new DocumentAdapter(mThis, arrayList, this);
            mBinding.documentRv.setAdapter(documentAdapter);
        } else {
            documentAdapter.setmDoucment_itemModelList(arrayList);
            documentAdapter.notifyDataSetChanged();
        }
    }

    private void setNoVehicleSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBinding.noVehicleSpinner.setAdapter(adapter);
    }

    private void setStateSpinnerAdapter() {
        StatesSpinnerAdapter adapter = new StatesSpinnerAdapter(mThis, R.layout.spinner_dropdown_item,
                getIntent().getParcelableArrayListExtra(Constants.STATE_LIST));

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBinding.stateSpinner.setAdapter(adapter);

        mBinding.stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStateId = adapter.getId(position);
                fetchCity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCitySpinnerAdapter(ArrayList<CityData> arrayListCity) {
        CitySpinnerAdapter adapter = new CitySpinnerAdapter(mThis, R.layout.spinner_dropdown_item,
                arrayListCity);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBinding.citySpinner.setAdapter(adapter);

        mBinding.citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCityId = adapter.getId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fetchCity() {
        if (NetworkHandler.isConnected(mThis)) {
            showHideProgressDialog(true);
            AppUtils.hideKeyboard(mThis);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CityResponse> call = apiService.getCity(mStateId);

            call.enqueue(new Callback<CityResponse>() {
                @Override
                public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                    showHideProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            setCitySpinnerAdapter(response.body().getData());

                        } else {
                            AppUtils.showToast(mThis, response.body().getMessage());

                        }

                    } else {
                        AppUtils.showToast(R.string.server_error);
                    }
                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
                    showHideProgressDialog(false);
                    Lg.e(TAG, t.getMessage());
                    AppUtils.showToast(R.string.server_error);
                }
            });
        }
    }


    private void initView() {
        mBinding.screenBannerView.screenBannerImageview.setImageDrawable(getResources().getDrawable(R.drawable.sign_up_provider_bg));
        mBinding.countryPicker.setCountryForPhoneCode(Integer.parseInt(SessionManager.get().getCountryCode()));
        mBinding.etMobile.setText(SessionManager.get().getNumber());
        mBinding.etMobile.setEnabled(false);
        setDocumentRecycler();
        fetchDocument();
    }

    private void initToolbar() {
        setHeaderTitle(getString(R.string.register));
        setBackEnabled(true);
    }

    private void initClickListeners() {
        mBinding.viewSignIn.setOnClickListener(this);
        mBinding.registerBtn.actionBtnParent.setOnClickListener(this);
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.viewSignIn:
                SignInActivity.startActivity(mThis);
                finishAffinity();
                break;
            case R.id.register_btn:
                onButtonClicked();
                break;
        }
    }

    private boolean isAllFieldsValid() {
        if (ValidUtils.isBlank(mBinding.etCompanyName)) {
            AppUtils.showToast(R.string.error_blank_company_name);
            return false;
        }

        if (ValidUtils.isBlank(mBinding.etCompnayRegistrationNo)) {
            AppUtils.showToast(R.string.error_blank_company_req_no);
            return false;
        }

        if (ValidUtils.isBlank(mBinding.etEmail)) {
            AppUtils.showToast(R.string.error_blank_email);
            return false;
        }


        if (ValidUtils.isBlank(mBinding.etMobile)) {
            AppUtils.showToast(R.string.error_blank_mobile);
            return false;
        }

        if (ValidUtils.isBlank(mBinding.etAddress)) {
            AppUtils.showToast(R.string.error_blank_address);
            return false;
        }

        if (ValidUtils.isBlank(mBinding.etPassword)) {
            AppUtils.showToast(R.string.error_blank_password);
            return false;
        }

        if (fileToUpload.size() != documentAdapter.getItemCount()) {
            AppUtils.showToast(R.string.error_document);
            return false;
        }

        return true;
    }

    private void onButtonClicked() {
        if (NetworkHandler.isConnected(mThis)) {
            showHideProgressDialog(true);


            ArrayList<String> arrayListIds = new ArrayList<>();

            for (int i = 0; i < documentAdapter.getItems().size(); i++) {
                fileToUpload.add(getFile(documentAdapter.getItem(i).getName(), i));
                arrayListIds.add(documentAdapter.getItem(i).getId());
            }

            String ids = TextUtils.join(",", arrayListIds);

            RequestBody companyReqNo = RequestBody.create(MediaType.parse("text/plain"), mBinding.etCompnayRegistrationNo.getText().toString());
            RequestBody companyName = RequestBody.create(MediaType.parse("text/plain"), mBinding.etCompanyName.getText().toString());
            RequestBody address = RequestBody.create(MediaType.parse("text/plain"), mBinding.etAddress.getText().toString());
            RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), mBinding.etMobile.getText().toString());
            RequestBody countryPhoneCode = RequestBody.create(MediaType.parse("text/plain"), mBinding.countryPicker.getSelectedCountryCodeWithPlus());
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mBinding.etEmail.getText().toString());
            RequestBody password = RequestBody.create(MediaType.parse("text/plain"), mBinding.etPassword.getText().toString());
            RequestBody noVehicle = RequestBody.create(MediaType.parse("text/plain"), mBinding.noVehicleSpinner.getSelectedItem().toString());
            RequestBody stateId = RequestBody.create(MediaType.parse("text/plain"), mStateId);
            RequestBody cityId = RequestBody.create(MediaType.parse("text/plain"), mCityId);
            RequestBody docIds = RequestBody.create(MediaType.parse("text/plain"), ids);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<RegisterResponse> call = apiService.registerCompany(
                    companyReqNo,
                    companyName,
                    address,
                    mobile,
                    countryPhoneCode,
                    email,
                    password,
                    noVehicle,
                    stateId,
                    cityId,
                    docIds,
                    fileToUpload);

            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    Lg.d(TAG, "response---> : " + response.body().toString());
                    showHideProgressDialog(false);

                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            showToast(mThis, response.body().getMessage());
                            SignInActivity.startActivity(mThis);
                            finishAffinity();
                        } else {
                            showToast(mThis, response.body().getMessage());

                        }
                    } else {
                        showToast(R.string.server_error);
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Lg.e(TAG, t.getMessage());
                    showHideProgressDialog(false);
                    showToast(R.string.server_error);
                }
            });
        }
    }


    @Override
    public void onItemClick(int position, DocumentData item, View v) {
        itemPosition = position;
        openCameraGallery();
    }

    public void openCameraGallery() {
        PermissionUtils.checkDexterPermissions(mThis, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.PermissionCallback() {

            @Override
            public void onPermissionResult(boolean granted) {
                if (granted) {
                    showPictureDialog(mThis);
                } else {
                    showAlertDialog(mThis, getString(R.string.allow_permissions), getString(R.string.need_permission),
                            getString(R.string.setting),
                            getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                }
                            }, true);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (SELECT_CAMERA == requestCode) {
            if (resultCode == RESULT_OK) {
                try {
                    setFilePath(mCamRequestedUri, requestCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    Lg.e(TAG, "onActivityResult: " + e.getMessage());
                }
            }

        } else if (SELECT_GALLERY == requestCode) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                try {
                    Uri uri = data.getData();
                    setFilePath(uri, requestCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    Lg.e(TAG, "onActivityResult: " + e.getMessage());
                }
            }
        }
    }

    public void setFilePath(Uri uri, int requestCode) {
        FileUtils fileUtils = new FileUtils(mThis);

        if (SELECT_CAMERA == requestCode) {
            try {
                String filePath = fileUtils.getRealPathFromURI(uri);
                // file validation
                File file = new File(filePath);
                if (fileUtils.validateFileSize(file)) {
                    setDocumentFile(filePath);
                } else {
                    ImageCompression imageCompression = new ImageCompression(mThis);
                    String newFilePath = imageCompression.compressImage(filePath);
                    setDocumentFile(newFilePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Lg.e(TAG, "onActivityResult: " + e.getMessage());
            }

        } else if (SELECT_GALLERY == requestCode) {
            try {
                fileUtils.downloadFile(uri, 0);
            } catch (Exception e) {
                e.printStackTrace();
                Lg.e(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    public void setDocumentFile(String filePath) {
        Log.d(TAG, "file Path-> : " + filePath);
        Uri convertedURI = Uri.fromFile(new File(filePath));
        DocumentData documentData = new DocumentData();
        documentData.setName(filePath);
        documentData.setId(documentAdapter.getItem(itemPosition).getId());
        documentAdapter.updateItemDocument(itemPosition, documentData);


        //fileToUpload.add(getFile(filePath, "photo", ""));
    }

    // Convert file itno request
    private MultipartBody.Part getFile(String filePath, int value) {
        MultipartBody.Part body = AppUtils.prepareFilePart("documents[" + value + "]", filePath);
        return body;
    }
}