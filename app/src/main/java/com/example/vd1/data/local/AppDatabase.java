package com.example.vd1.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.vd1.data.local.dao.TaskDao;
import com.example.vd1.data.model.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Khai báo các Entity (Bảng) và Version
@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    private static volatile AppDatabase INSTANCE;
    // Tạo luồng phụ để ghi dữ liệu (tránh làm đơ UI)
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "task_database")
                            .fallbackToDestructiveMigration() // Xóa DB cũ nếu đổi version
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}