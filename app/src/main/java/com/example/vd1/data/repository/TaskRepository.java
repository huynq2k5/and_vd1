package com.example.vd1.data.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.vd1.data.local.AppDatabase;
import com.example.vd1.data.local.dao.TaskDao;
import com.example.vd1.data.model.Task;
import com.example.vd1.data.remote.ApiService;
import com.example.vd1.data.remote.RetrofitClient; // Nhớ import file mới này

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {
    private TaskDao taskDao;
    private ApiService apiService;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        // 1. Khởi tạo DB Local
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();

        // 2. Khởi tạo API Service (GỌN HƠN RẤT NHIỀU)
        // Thay vì viết cả cụm Builder, giờ chỉ cần gọi:
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    public LiveData<List<Task>> getTasks() {
        refreshTasksFromApi(null);
        return allTasks;
    }

    public void refreshTasksFromApi(DataCallback callback) {
        apiService.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        // Gọi hàm syncData (Xóa cũ nạp mới)
                        taskDao.syncData(response.body());

                        // Báo về UI là đã xong (để tắt vòng xoay)
                        if (callback != null) {
                            callback.onDataLoaded();
                        }
                    });
                } else {
                    if (callback != null) callback.onError("Lỗi server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                if (callback != null) callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }
}