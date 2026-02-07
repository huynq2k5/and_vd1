package com.example.vd1.data.remote;

import com.example.vd1.data.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/get_tasks.php")
    Call<List<Task>> getTasks();
}
