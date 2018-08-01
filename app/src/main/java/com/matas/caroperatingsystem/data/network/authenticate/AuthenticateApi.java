package com.matas.caroperatingsystem.data.network.authenticate;

import com.matas.caroperatingsystem.data.network.Urls;
import com.matas.caroperatingsystem.data.network.authenticate.request.LoginRequest;
import com.matas.caroperatingsystem.data.network.authenticate.request.SignUpRequest;
import com.matas.caroperatingsystem.data.network.authenticate.response.LoginResponse;
import com.matas.caroperatingsystem.data.network.authenticate.response.SignUpResponse;

import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

@Singleton
public interface AuthenticateApi {

    @POST(Urls.Auth.LOGIN)
    Observable<LoginResponse> login(@HeaderMap Map<String, String> headers, @Body LoginRequest loginRequest);

    @POST(Urls.Auth.SIGNUP)
    Observable<SignUpResponse> signUp(@HeaderMap Map<String, String> headers, @Body SignUpRequest signUpRequest);
}
