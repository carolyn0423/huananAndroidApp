package com.hamels.huanan.Repository;

import com.hamels.huanan.Repository.Model.BaseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbsApiCallback<T> implements Callback<T> {
    @Override
    public abstract void onResponse(Call<T> call, Response<T> response);

    public abstract void onApiSuccess(T response);

    public abstract void onApiFail(int errorCode, BaseModel failBaseModel);

    public abstract void onTokenExpired();

    @Override
    public abstract void onFailure(Call<T> call, Throwable t);

    public abstract void onApiFlowFinish();
}
