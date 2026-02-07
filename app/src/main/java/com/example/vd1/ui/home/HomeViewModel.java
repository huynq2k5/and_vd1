package com.example.vd1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.example.vd1.data.model.Task;
import com.example.vd1.data.repository.TaskRepository;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public HomeViewModel() {
        repository = new TaskRepository();
        allTasks = repository.getTasksFromApi();
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