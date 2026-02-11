package com.example.vd1.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    // Lưu ý: Base URL trong Retrofit luôn phải kết thúc bằng dấu "/"
    private static final String BASE_URL = "https://curved-juline-huy01-d62d7a12.koyeb.app/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}