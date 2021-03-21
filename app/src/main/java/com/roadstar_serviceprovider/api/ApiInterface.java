package com.roadstar_serviceprovider.api;


import com.roadstar_serviceprovider.models.BaseResponse;
import com.roadstar_serviceprovider.models.LoadSizeResponse;
import com.roadstar_serviceprovider.models.RegisterCompany.RegisterResponse;
import com.roadstar_serviceprovider.models.city.CityResponse;
import com.roadstar_serviceprovider.models.state.StatesResponse;
import com.roadstar_serviceprovider.models.document.DocumentResponse;
import com.roadstar_serviceprovider.models.forgotPasswordOtp.ForgotPasswordOTPResponse;
import com.roadstar_serviceprovider.models.verfiyPasswordOTP.VerifyForgotPasswordOTPResponse;
import com.roadstar_serviceprovider.models.login.LoginResponse;
import com.roadstar_serviceprovider.models.otp.OTPResponse;
import com.roadstar_serviceprovider.models.serviceMedium.ServiceMediumResponse;
import com.roadstar_serviceprovider.models.serviceType.ServiceTypeResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("provide-otp")
    Call<OTPResponse> getOTP(@Field("mobile") String mobile,
                             @Field("country_phone_code") String countryPhoneCode,
                             @Field("country_name") String countryName,
                             @Field("country_code") String countryCode);

    @FormUrlEncoded
    @POST("provide-otp-verification")
    Call<BaseResponse> verifyOTP(@Field("mobile") String mobile,
                                 @Field("country_phone_code") String countryPhoneCode,
                                 @Field("otp") String otp);

    @FormUrlEncoded
    @POST("all-states")
    Call<StatesResponse> getStates(@Field("country_id") String countryId);

    @FormUrlEncoded
    @POST("all-cities")
    Call<CityResponse> getCity(@Field("state_id") String stateId);



    @GET("company-mandatory-document")
    Call<DocumentResponse> getCompanyDocument();

    @GET("driver-mandatory-document")
    Call<DocumentResponse> getDriverDocument();

    @GET("individual-mandatory-document")
    Call<DocumentResponse> getIndividualDocument();

    @GET("service-type")
    Call<ServiceTypeResponse> getServiceType();

    @GET("load-size")
    Call<LoadSizeResponse> getLoadSize();

    @FormUrlEncoded
    @POST("service-medium")
    Call<ServiceMediumResponse> getMediumType(@Field("service_id") String serviceId);

    @FormUrlEncoded
    @POST("provider-login")
    Call<LoginResponse> Login(@Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("provider-forgot-password-otp")
    Call<ForgotPasswordOTPResponse> getForgotPasswordOTP(@Field("email") String email);

    @FormUrlEncoded
    @POST("verify-provider-forgot-password-otp")
    Call<VerifyForgotPasswordOTPResponse> verifyPasswordOTP(@Field("mobile") String mobile,
                                                            @Field("country_phone_code") String countryPhoneCode,
                                                            @Field("otp") String otp);

    @FormUrlEncoded
    @POST("update-provider-password")
    Call<VerifyForgotPasswordOTPResponse> updatePassword(@Field("mobile") String mobile,
                                                         @Field("country_phone_code") String countryPhoneCode,
                                                         @Field("password") String password,
                                                         @Field("confirm_password") String confirmPassword);


    @Multipart
    @POST("add-company")
    Call<RegisterResponse> registerCompany(
            @Part("registration_no") RequestBody registrationNo,
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("mobile") RequestBody mobile,
            @Part("country_phone_code") RequestBody countryPhoneCode,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("no_of_vehicle") RequestBody noOfVehicle,
            @Part("state_id") RequestBody stateId,
            @Part("city_id") RequestBody cityId,
            @Part("doc_ids") RequestBody ids,
            @Part List<MultipartBody.Part> file);


    @Multipart
    @POST("add-driver")
    Call<RegisterResponse> registerDriver(
            @Part("first_name") RequestBody firstName,
            @Part("last_name") RequestBody lastName,
            @Part("address") RequestBody address,
            @Part("mobile") RequestBody mobile,
            @Part("country_phone_code") RequestBody countryPhoneCode,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("transport_id") RequestBody transportId,
            @Part("state_id") RequestBody stateId,
            @Part("city_id") RequestBody cityId,
            @Part("vehicle_id") RequestBody vehicleId,
            @Part("doc_ids") RequestBody ids,
            @Part List<MultipartBody.Part> file);


    @Multipart
    @POST("add-individual")
    Call<RegisterResponse> registerProvider(
            @Part("first_name") RequestBody firstName,
            @Part("last_name") RequestBody lastName,
            @Part("address") RequestBody address,
            @Part("mobile") RequestBody mobile,
            @Part("country_phone_code") RequestBody countryPhoneCode,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("state_id") RequestBody stateId,
            @Part("city_id") RequestBody cityId,
            @Part("journy_medium") RequestBody journyMedium,
            @Part("practical_detail") RequestBody practicalDetail,
            @Part("size_of_load") RequestBody sizeOfLoad,
            @Part("departure_from") RequestBody departureFrom,
            @Part("arrival_to") RequestBody arrivalTo,
            @Part("doc_ids") RequestBody ids,
            @Part List<MultipartBody.Part> file);


}
