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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import com.mancj.materialsearchbar.MaterialSearchBar;


public class MainActivity extends AppCompatActivity {


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


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()

                .findFragmentById(R.id.nav_host_fragment);


        if (navHostFragment != null) {

            NavController navController = navHostFragment.getNavController();


            navView.setItemSelected(R.id.navigation_home, true);


            navView.setOnItemSelectedListener(id -> {

                navController.navigate(id);

            });

        }


    }

}