package com.yey.demo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yey.demo.databinding.ActivitySofteKeyboardBinding;


public class SofteKeyboardActivity extends AppCompatActivity {
    private final static String TAG = SofteKeyboardActivity.class.getName();
    private ActivitySofteKeyboardBinding dataBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = (ActivitySofteKeyboardBinding) DataBindingUtil.setContentView(this, R.layout.activity_softe_keyboard);
        dataBinding.setLifecycleOwner(this);
        initLister();
    }

    private void initLister() {
        dataBinding.btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBinding.vcv.inputContent("1");
            }
        });
        dataBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBinding.vcv.deleteContent();
            }
        });
    }
}
