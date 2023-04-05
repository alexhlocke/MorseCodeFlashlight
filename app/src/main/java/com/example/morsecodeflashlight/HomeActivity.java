package com.example.morsecodeflashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.morsecodeflashlight.ui.main.SectionsPagerAdapter;
import com.example.morsecodeflashlight.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    public boolean isLightOn = false;

    private ActivityHomeBinding binding;
    private CameraManager cameraManager;
    private String cameraID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Camera management
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraID = cameraManager.getCameraIdList()[0]; //back camera
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton pulse = binding.pulse;

        pulse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!isLightOn) {
                            turnOnLight();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(HomeActivity.this, "ACTION_UP", Toast.LENGTH_SHORT).show();
                        if (isLightOn) {
                            turnOffLight();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void turnOnLight() {
        try {
            cameraManager.setTorchMode(cameraID,true);
            isLightOn = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffLight() {
        try {
            cameraManager.setTorchMode(cameraID,false);
            isLightOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}