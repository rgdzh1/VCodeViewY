package com.yey.demo;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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

//        dataBinding.et.setTransformationMethod(new UpperTransform());
//        dataBinding.et.setTransformationMethod(new MyPasswordTransformationMethod());
        dataBinding.et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        dataBinding.tv1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        dataBinding.tv2.setTransformationMethod(new YPasswordTransformation());
        dataBinding.tv3.setTransformationMethod(new YPasswordTransformation());
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
