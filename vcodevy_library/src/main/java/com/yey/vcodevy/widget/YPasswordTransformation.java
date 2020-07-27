package com.yey.vcodevy.widget;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * `EditTextView`设置为密文模式的时候, 那个黑色的小圆点总是觉得很小,下面这种可以改变黑色小圆点大小(TextView同样适用).
 */
public class YPasswordTransformation extends PasswordTransformationMethod {
    private static YPasswordTransformation sInstance;

    public static YPasswordTransformation getInstance() {
        if (sInstance != null)
            return sInstance;

        sInstance = new YPasswordTransformation();
        return sInstance;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new YPasswordCharSequence(source);
    }
}