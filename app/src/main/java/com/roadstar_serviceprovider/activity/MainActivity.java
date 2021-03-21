package com.roadstar_serviceprovider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.callback.DialogCallback;
import com.roadstar_serviceprovider.databinding.ActivityMainBinding;
import com.roadstar_serviceprovider.fragment.BookingsFragment;
import com.roadstar_serviceprovider.fragment.DashboardFragment;
import com.roadstar_serviceprovider.fragment.DocumentFragment;
import com.roadstar_serviceprovider.fragment.EarningFragment;
import com.roadstar_serviceprovider.fragment.ManageVehicle;
import com.roadstar_serviceprovider.fragment.SupportFragment;
import com.roadstar_serviceprovider.utils.DialogUtil;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements DialogCallback {

    private Context context;
    ActivityMainBinding mBinding;

    /*Start home activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, MainActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        initToolbar();
        initClicklistners();
        DashBoardNavigation();

    }

    private void initClicklistners() {
        mBinding.appBarMain.layoutAppBar.ivMenu.setOnClickListener(this);
        mBinding.navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewProfileActivity.startActivity(mThis);
                closeDrawer();
            }
        });
    }

    private void initToolbar() {
        mBinding.appBarMain.layoutAppBar.ivBack.setVisibility(View.GONE);
        mBinding.appBarMain.layoutAppBar.toolbarTitleTv.setText(getString(R.string.app_name));
        mBinding.appBarMain.layoutAppBar.ivMenu.setVisibility(View.VISIBLE);
        mBinding.appBarMain.layoutAppBar.ivMenu.setOnClickListener(this);
        setUpNavigationView();
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        DashBoardNavigation();
                        closeDrawer();

                        break;
                    case R.id.navigation_earning:
                        EarningNavigation();
                        closeDrawer();

                        break;
                    case R.id.navigation_manage_vehicle:
                        ManageVehicleNavigation();
                        closeDrawer();

                        break;
                    case R.id.navigation_documents:
                        DocumentNavigation();
                        closeDrawer();

                        break;
                    case R.id.navigation_history:
                        BookingHistoryNavigation();
                        closeDrawer();

                        break;
                    case R.id.navigation_payment_method:

                        break;
                    case R.id.navigation_support:
                        SupportNavigation();
                        closeDrawer();

                        break;
                    case R.id.navigation_logout:
                        DialogUtil.showLogoutAlertDialog(mThis, getSupportFragmentManager(), MainActivity.this);
                        break;
                    default:
                }
                return true;
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void closeDrawer() {
        mBinding.drawerLayout.closeDrawer(Gravity.START);
    }

    private void SupportNavigation() {
        addFragment(mBinding.appBarMain.contentMain.framFrag.getId(),
                new SupportFragment(),
                "Support",
                "");
    }

    private void DashBoardNavigation(){
        addFragment(mBinding.appBarMain.contentMain.framFrag.getId(),
                new DashboardFragment(),
                "DashBoard",
                "");
    }


    private void ManageVehicleNavigation(){
        addFragment(mBinding.appBarMain.contentMain.framFrag.getId(),
                new ManageVehicle(),
                "ManageVehicle",
                "");
    }

    public void BookingHistoryNavigation() {
        addFragment(mBinding.appBarMain.contentMain.framFrag.getId(),
                new BookingsFragment(),
                "BookingHistory",
                "");
    }

    public void EarningNavigation() {
        addFragment(mBinding.appBarMain.contentMain.framFrag.getId(),
                new EarningFragment(),
                "Earning",
                "");
    }


    private void DocumentNavigation() {
        addFragment(mBinding.appBarMain.contentMain.framFrag.getId(),
                new DocumentFragment(),
                "DocumentFragment",
                "");
    }

    //perform onClick
    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_menu:
                if (!mBinding.drawerLayout.isDrawerOpen(Gravity.START))
                    mBinding.drawerLayout.openDrawer(Gravity.START);
                else mBinding.drawerLayout.closeDrawer(Gravity.END);
                break;
        }
    }

    @Override
    public void okPressed() {
        closeDrawer();
        SignInActivity.startActivity(MainActivity.this);
        finish();
    }

    @Override
    public void cancelPressed() {
        closeDrawer();
    }



    /*private void getToken() {
        showHideProgressDialog(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AccessTokenResponse> call = apiService.getToken("1234567890");
        call.enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccessTokenResponse> call, @NonNull Response<AccessTokenResponse> response) {
                if (response.isSuccessful()) {
                    showHideProgressDialog(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessTokenResponse> call, @NonNull Throwable t) {
                showHideProgressDialog(false);
                showToast(context, "Unable to login");
            }
        });
    }*/
}
