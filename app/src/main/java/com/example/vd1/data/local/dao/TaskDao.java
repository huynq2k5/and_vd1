package com.example.vd1.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction; // Nhớ import cái này

import com.example.vd1.data.model.Task;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    LiveData<List<Task>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    // 1. Thêm hàm xóa toàn bộ bảng
    @Query("DELETE FROM tasks")
    void deleteAll();

    // 2. Thêm hàm Đồng bộ (Transaction)
    // "default" cho phép viết logic ngay trong interface (yêu cầu Java 8 trở lên)
    @Transaction
    default void syncData(List<Task> serverData) {
        deleteAll();            // Bước 1: Xóa sạch dữ liệu cũ trong máy
        insertAll(serverData);  // Bước 2: Nạp dữ liệu mới từ Server vào
    }
}