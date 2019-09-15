package com.jh.wxqb.base;

import android.support.annotation.NonNull;

import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/11/11 0011.
 * 网络请求基类
 */

public class BaseBiz {
    private Retrofit stringRetrofit;//返回字符串


    public BaseBiz() {
        stringRetrofit = new Retrofit.Builder().baseUrl(ServerInterface.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getClient())
                .build();
    }

    protected Retrofit getStringRetrofit() {
        return stringRetrofit;
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request;
                        if (MyApplication.getToken() == null) {
                            request = chain.request().newBuilder()
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                    .addHeader("Authorization", "Basic UEtCQW1kaUhkc0tzNmRzREtTOlBLQktPRFVoc2pzaFNZS25jZGRz").build();
                        } else {
                            request = chain.request().newBuilder()
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                    .addHeader("Authorization", "bearer " + MyApplication.getToken().getAccess_token()).build();
                            LogUtils.e("Token=>" + "bearer " + MyApplication.getToken().getAccess_token());
                        }
                        LogUtils.e("post=> " + request.url() + GsonUtil.GsonString(request));
                        return chain.proceed(request);
                    }
                })
                .build();
    }
}
