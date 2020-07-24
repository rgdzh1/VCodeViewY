package com.yey.demo;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class YPasswordTransformation extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new YPasswordCharSequence(source);
    }
}