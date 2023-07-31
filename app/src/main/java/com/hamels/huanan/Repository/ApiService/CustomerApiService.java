package com.hamels.huanan.Repository.ApiService;

import com.hamels.huanan.Repository.Model.Address;
import com.hamels.huanan.Repository.Model.BaseModel;
import com.hamels.huanan.Repository.Model.Carousel;
import com.hamels.huanan.Repository.Model.Customer;
import com.hamels.huanan.Repository.Model.WebSetup;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerApiService {
    @POST("Customer/GetList")
    Call<BaseModel<List<Customer>>> GetCustomerList(@Body RequestBody body);
    @POST("Customer/GetDetail")
    Call<BaseModel<Customer>> GetCustomerDetail(@Body RequestBody body);
    @POST("News/GetList")
    Call<BaseModel<List<Carousel>>> GetCarouselList(@Body RequestBody body);
    @POST("Property/GetPropertyList_AddressData")
    Call<BaseModel<List<Address>>> GetPropertyList_AddressData(@Body RequestBody body);
    @POST("Shared/GetWebConfig")
    Call<BaseModel<WebSetup>> GetWebConfig(@Body RequestBody body);
}
