package com.example.vd1.data.repository;

public interface DataCallback {
    void onDataLoaded();
    void onError(String message);
}