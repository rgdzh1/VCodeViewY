package com.yey.demo;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yey.demo.databinding.ActivitySofteKeyboardBinding;


public class SofteKeyboardActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = SofteKeyboardActivity.class.getName();
    private ActivitySofteKeyboardBinding mDataBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = (ActivitySofteKeyboardBinding) DataBindingUtil.setContentView(this, R.layout.activity_softe_keyboard);
        mDataBinding.setLifecycleOwner(this);
        initLister();
    }

    private void initLister() {
        mDataBinding.keyBoard.keypad0.setOnClickListener(this);
        mDataBinding.keyBoard.keypad1.setOnClickListener(this);
        mDataBinding.keyBoard.keypad2.setOnClickListener(this);
        mDataBinding.keyBoard.keypad3.setOnClickListener(this);
        mDataBinding.keyBoard.keypad4.setOnClickListener(this);
        mDataBinding.keyBoard.keypad5.setOnClickListener(this);
        mDataBinding.keyBoard.keypad6.setOnClickListener(this);
        mDataBinding.keyBoard.keypad7.setOnClickListener(this);
        mDataBinding.keyBoard.keypad8.setOnClickListener(this);
        mDataBinding.keyBoard.keypad9.setOnClickListener(this);
        mDataBinding.keyBoard.containerDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.keypad0:
                inputNumber(0);
                break;
            case R.id.keypad1:
                inputNumber(1);
                break;
            case R.id.keypad2:
                inputNumber(2);
                break;
            case R.id.keypad3:
                inputNumber(3);
                break;
            case R.id.keypad4:
                inputNumber(4);
                break;
            case R.id.keypad5:
                inputNumber(5);
                break;
            case R.id.keypad6:
                inputNumber(6);
                break;
            case R.id.keypad7:
                inputNumber(7);
                break;
            case R.id.keypad8:
                inputNumber(8);
                break;
            case R.id.keypad9:
                inputNumber(9);
                break;
            case R.id.container_delete:
                deleteNumber();
                break;
        }
    }

    /**
     * 删除内容
     */
    private void deleteNumber() {
        mDataBinding.vcv.deleteContent();
        String mCodeStr = mDataBinding.vcv.getContent();
        Toast.makeText(this, mCodeStr, Toast.LENGTH_LONG);
    }

    /**
     * 输入内容
     */
    private void inputNumber(int content) {
        mDataBinding.vcv.inputContent(String.valueOf(content));
        String mCodeStr = mDataBinding.vcv.getContent();
        Toast.makeText(this, mCodeStr, Toast.LENGTH_LONG);
    }
}
