package com.example.vd1.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.vd1.data.model.Task;
import com.example.vd1.data.remote.ApiService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskRepository {
    private ApiService apiService;

    public TaskRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itchy-leola-huycompany-8ab116e9.koyeb.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<Task>> getTasksFromApi() {
        MutableLiveData<List<Task>> data = new MutableLiveData<>();

        apiService.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}