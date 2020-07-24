package com.yey.demo;

import androidx.annotation.NonNull;

public class YPasswordCharSequence implements CharSequence {
    @Override
    public int length() {
        return mSource.length();
    }

    @Override
    public char charAt(int index) {
        // '.'
        // '•' \u2022
        // '●' \u25cf
        // '⚫'
        return '●';
    }

    @NonNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return mSource.subSequence(start, end);
    }


    private CharSequence mSource;

    public YPasswordCharSequence(CharSequence source) {
        mSource = source; // Store char sequence
    }
}
