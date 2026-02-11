package com.example.vd1.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.example.vd1.data.model.Task;
import com.example.vd1.data.repository.TaskRepository;
import java.util.List;

// Đổi thành AndroidViewModel
public class HomeViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private final LiveData<List<Task>> allTasks;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application); // Truyền application vào
        allTasks = repository.getTasks(); // Lấy LiveData từ DB
    }

    public LiveData<List<Task>> getTasks() {
        return allTasks;
    }

    public LiveData<Integer> getInProgressCount() {
        return Transformations.map(allTasks, tasks -> {
            if (tasks == null) return 0;
            int count = 0;
            for (Task task : tasks) {
                if (!task.isCompleted()) count++;
            }
            return count;
        });
    }

    public LiveData<Integer> getCompletedCount() {
        return Transformations.map(allTasks, tasks -> {
            if (tasks == null) return 0;
            int count = 0;
            for (Task task : tasks) {
                if (task.isCompleted()) count++;
            }
            return count;
        });
    }
}