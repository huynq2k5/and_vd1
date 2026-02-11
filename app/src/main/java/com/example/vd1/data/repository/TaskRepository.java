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
        refreshTasksFromApi();
        return allTasks;
    }

    public void refreshTasksFromApi() {
        apiService.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        // SỬA ĐOẠN NÀY:
                        // Thay vì chỉ insertAll, ta gọi hàm syncData vừa viết
                        taskDao.syncData(response.body());
                    });
                    Log.d("TaskRepository", "Đã đồng bộ lại toàn bộ DB theo Server");
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("TaskRepository", "Lỗi mạng: " + t.getMessage());
            }
        });
    }
}