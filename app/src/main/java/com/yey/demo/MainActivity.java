package com.yey.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yey.vcodevy.IVCodeBack;
import com.yey.vcodevy.VCodeViewY;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();
    private VCodeViewY mCodeViewY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCodeViewY = (VCodeViewY) findViewById(R.id.vcv);
        mCodeViewY.setVCodeBack(new IVCodeBack() {
            @Override
            public void inputComplete(String contentComplete) {
                Log.e(TAG, contentComplete);
            }

            @Override
            public void inputing(String contentInputing, int index) {
                Log.e(TAG, contentInputing);
                Log.e(TAG, "当前输入索引 " + index);
            }
        });
        findViewById(R.id.clear)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCodeViewY.clearAllContent();
                    }
                });
        findViewById(R.id.show)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCodeViewY.changeModel();
                    }
                });
        findViewById(R.id.fill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeViewY.fillAllContent("12345");
            }
        });
    }
}
