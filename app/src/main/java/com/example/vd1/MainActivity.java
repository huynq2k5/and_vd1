package com.example.vd1;


import android.os.Bundle;


import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;

import androidx.core.view.ViewCompat;

import androidx.core.view.WindowInsetsCompat;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;

import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;

import androidx.navigation.ui.NavigationUI;


import com.example.vd1.ui.add.AddFragment;

import com.example.vd1.ui.home.HomeFragment;

import com.example.vd1.ui.library.LibraryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import com.mancj.materialsearchbar.MaterialSearchBar;


public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment;
    private Fragment addFragment;
    private Fragment libraryFragment;
    private Fragment activeFragment;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ChipNavigationBar navView = findViewById(R.id.bottomNavigation);

        // 2. Khởi tạo Fragment (Chỉ new 1 lần duy nhất)
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            libraryFragment = new LibraryFragment(); // Tạo file này nếu chưa có
            addFragment = new AddFragment();
            // Mặc định Home là active
            activeFragment = homeFragment;

            // 3. Add tất cả vào nhưng Hide những cái phụ đi
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, libraryFragment, "3").hide(libraryFragment)
                    .add(R.id.fragment_container, addFragment, "2").hide(addFragment)
                    .add(R.id.fragment_container, homeFragment, "1") // Home add sau cùng để hiện lên trên
                    .commit();
        } else {
            // Nếu xoay màn hình, lấy lại từ Manager để không tạo mới
            homeFragment = getSupportFragmentManager().findFragmentByTag("1");
            libraryFragment = getSupportFragmentManager().findFragmentByTag("3");
            addFragment = getSupportFragmentManager().findFragmentByTag("2");
            // Logic tìm activeFragment (nâng cao, tạm thời để home)
            activeFragment = homeFragment;
        }

        // Set mặc định chọn Home trên menu
        navView.setItemSelected(R.id.navigation_home, true);

        // 4. Xử lý sự kiện chuyển tab (Hide/Show)
        navView.setOnItemSelectedListener(id -> {
            if (id == R.id.navigation_home) {
                showFragment(homeFragment);
            } else if (id == R.id.navigation_library) { // Id trong menu của bạn
                showFragment(libraryFragment);
            } else if (id == R.id.navigation_add){
                showFragment(addFragment);
            }
            // Thêm các case khác tương tự
        });
    }

    // Hàm chuyển đổi Fragment
    private void showFragment(Fragment targetFragment) {
        if (targetFragment == null) return;

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .hide(activeFragment) // Ẩn cái đang hiện
                .show(targetFragment) // Hiện cái mình chọn
                .commit();

        activeFragment = targetFragment; // Cập nhật biến theo dõi
    }

}