package com.example.vd1.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tasks") // Đánh dấu đây là bảng tên 'tasks'
public class Task {

    @PrimaryKey(autoGenerate = true) // ID tự tăng (nếu muốn server quản lý ID thì bỏ autoGenerate = true)
    @SerializedName("id") // Map với field 'id' từ JSON
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("isCompleted") // Map với field JSON (lưu ý tên field json của bạn phải chuẩn)
    private boolean isCompleted;

    // Constructor rỗng cho Room (Bắt buộc)
    public Task() {
    }

    public Task(int id, String title, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.isCompleted = isCompleted;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}