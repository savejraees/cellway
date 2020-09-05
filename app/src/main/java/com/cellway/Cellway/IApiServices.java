package com.cellway.Cellway;

import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CategoryStatusModel;
import com.cellway.Cellway.retrofitModel.AccessoriesProductModel.AccessoriesProductStatusModel;
import com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel.SubCategoryStatusModel;
import com.cellway.Cellway.retrofitModel.AddOrderModel;
import com.cellway.Cellway.retrofitModel.AddressModel.AddressDatumModel;
import com.cellway.Cellway.retrofitModel.AddressModel.AddressStatusModel;
import com.cellway.Cellway.retrofitModel.AddressModel.DeleteAddressModel;
import com.cellway.Cellway.retrofitModel.AddressModel.FillAddressModel;
import com.cellway.Cellway.retrofitModel.AddressModel.GetFillAddressModel;
import com.cellway.Cellway.retrofitModel.AddressModel.SetAddressModel;
import com.cellway.Cellway.retrofitModel.AddressModel.StateStatusModel;
import com.cellway.Cellway.retrofitModel.AddressModel.UpdateAddressModel;
import com.cellway.Cellway.retrofitModel.BookingAccessoriesModel.BookingAccessoriesStatusModel;
import com.cellway.Cellway.retrofitModel.BrandListModel.BrandStatus;
import com.cellway.Cellway.retrofitModel.CartModel.AddCartModel;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailStatusModel;
import com.cellway.Cellway.retrofitModel.CartModel.DeleteCartModel;
import com.cellway.Cellway.retrofitModel.GenuineModel.GenuineKnowMoreModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.LoginStatusModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.OtpGetModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.RegisterStatusModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.VerifyOtpModel;
import com.cellway.Cellway.retrofitModel.PasswordModel.ForgetPasswordModel;
import com.cellway.Cellway.retrofitModel.PasswordModel.PasswordNewModel;
import com.cellway.Cellway.retrofitModel.PaymentFormModel.PaymentFormStatusModel;
import com.cellway.Cellway.retrofitModel.ProductCategoryModel.ProductCategoryStatusModel;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductStatusModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingStatusModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.PincodeModel;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestStatus;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface IApiServices {
    @Multipart
    @POST("getprofile")
    Call<JsonObject> profileAPi(@Part MultipartBody.Part image, @PartMap() Map<String, RequestBody> partMap);

    @FormUrlEncoded
    @POST("getallmobile_details")
    Call<MobDestStatus> getAllMobileDetails(@Field("key") String key);

    @FormUrlEncoded
    @POST("getcontents")
    Call<GenuineKnowMoreModel> getGenKnow(@Field("key") String key, @Field("img_cat_id") String img_cat_id);

    @FormUrlEncoded
    @POST("getbrand")
    Call<BrandStatus> getBrand(@Field("key") String key, @Field("type") String type);

    @FormUrlEncoded
    @POST("getproductlist_banner")
    Call<ProductStatusModel> getProductBanner(@Field("key") String key, @Field("page") String page, @Field("category_search") String category_search, @Field("brand_search") String brand_search, @Field("orderby") String orderby);

    @FormUrlEncoded
    @POST("getproductlist_category")
    Call<ProductCategoryStatusModel> getProduct(@Field("key") String key, @Field("page") String page, @Field("category") String category,
                                                @Field("brand_id") String brand_id, @Field("series_search") String series_search,
                                                @Field("warrenty_search") String warrenty_search, @Field("orderby") String orderby);

    @FormUrlEncoded
    @POST("getphone_details")
    Call<BookingStatusModel> getBookingDetail(@Field("key") String key, @Field("phone_id") String phone_id, @Field("userid") String UserId);

    @FormUrlEncoded
    @POST("checkpincode")
    Call<PincodeModel> getPinCodeDetail(@Field("key") String key, @Field("pincode") String pincode);

    @FormUrlEncoded
    @POST("getaddress")
    Call<AddressStatusModel> getAddress(@Field("key") String key, @Field("userid") String UserId);


    @FormUrlEncoded
    @POST("addaddress")
    Call<FillAddressModel> addAddress(@Field("key") String key, @Field("userid") String UserId, @Field("office_floor") String office_floor,
                                      @Field("landmark") String landmark, @Field("locality") String locality, @Field("pincode") String pincode,
                                      @Field("city") String city, @Field("state") String state, @Field("category") String category, @Field("name") String name);

    @FormUrlEncoded
    @POST("updateaddress")
    Call<UpdateAddressModel> updateaddress(@Field("key") String key, @Field("address_id") int address_id, @Field("office_floor") String office_floor,
                                           @Field("landmark") String landmark, @Field("locality") String locality, @Field("pincode") String pincode,
                                           @Field("city") String city, @Field("state") String state, @Field("category") String category, @Field("name") String name);

    @FormUrlEncoded
    @POST("delete_address")
    Call<DeleteAddressModel> deleteAddress(@Field("key") String key, @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST("getaddress_id")
    Call<GetFillAddressModel> getFillAddress(@Field("key") String key, @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST("getstate")
    Call<StateStatusModel> getStateApi(@Field("key") String key);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<OtpGetModel> postGetOtp(@Field("key") String key, @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<VerifyOtpModel> postVerifyOtp(@Field("key") String key, @Field("mobile") String mobile,@Field("otp") String otp);

    @FormUrlEncoded
    @POST("login")
    Call<LoginStatusModel> postLogin(@Field("key") String key, @Field("mobile") String mobile, @Field("password") String password,@Field("token") String token);

    @FormUrlEncoded
    @POST("customer_forgetpassword")
    Call<ForgetPasswordModel> forgetPassword(@Field("key") String key, @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("verify_otp_with_newpassword")
    Call<PasswordNewModel> newPassword(@Field("key") String key, @Field("mobile") String mobile,@Field("otp") String otp,
                                          @Field("password") String password,@Field("confirmpassword") String confirmpassword);

    @FormUrlEncoded
    @POST("customer_registers")
    Call<RegisterStatusModel> postRegister(@Field("key") String key, @Field("username") String username, @Field("mobile") String mobile,
                                           @Field("email") String email,
                                           @Field("password") String password,@Field("token") String token);

    @FormUrlEncoded
    @POST("getaccessories_category")
    Call<CategoryStatusModel> postCategory(@Field("key") String key);

    @FormUrlEncoded
    @POST("getaccessories_subcategory")
    Call<SubCategoryStatusModel> postSubCategory(@Field("key") String key, @Field("cat_id") int id);

    @FormUrlEncoded
    @POST("getaccessories_product")
    Call<AccessoriesProductStatusModel> getAccessoriesProduct(@Field("key") String key, @Field("cat_id") int id,
                                                              @Field("subcat_id") int subcat_id,
                                                              @Field("brand_search") String brand_search,
                                                              @Field("page") int page, @Field("orderby") String orderby);

    @FormUrlEncoded
    @POST("getaccessories_product_details")
    Call<BookingAccessoriesStatusModel> getBookingAccessoriesDetail(@Field("key") String key, @Field("product_id") String product_id, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("addcart")
    Call<AddCartModel> addCart(@Field("key") String key, @Field("product_id") String product_id,
                               @Field("category") String category, @Field("qty") int qty, @Field("userid") String userid,
                               @Field("is_protection") int is_protection, @Field("is_backcover") int is_backcover, @Field("is_tempered") int is_tempered);

    @FormUrlEncoded
    @POST("getcart")
    Call<CartDetailStatusModel> getCartDetail(@Field("key") String key, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("paymentgetway_form")
    Call<PaymentFormStatusModel> getPaymentFormDetail(@Field("key") String key);

    @FormUrlEncoded
    @POST("delete_cart")
    Call<DeleteCartModel> postDelete(@Field("key") String key, @Field("cart_id") String cart_id, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("setdelivery_address")
    Call<SetAddressModel> postSetDeliveryAddress(@Field("key") String key, @Field("address_id") int address_id, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("addorder")
    Call<AddOrderModel> postAddOrder(@Field("key") String key, @Field("userid") String userid,@Field("total_qty") String total_qty,
                                     @Field("total_amount") String total_amount,@Field("address") String address,
                                     @Field("payment_mode") String payment_mode,@Field("delivery_type") String delivery_type,
                                     @Field("booking_amount") String booking_amount,@Field("shipping_charge") String shipping_charge,
                                     @Field("delivery_charge") String delivery_charge);


}
