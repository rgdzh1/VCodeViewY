package com.yey.vcodevy.widget;

import android.text.method.ReplacementTransformationMethod;

/**
 * `EditTextView`接收的内容为`A`,但希望展示出来的是`♠`,当获取`EditTextView`中内容依旧为'A'.
 */
public class YPeachCharTransform extends ReplacementTransformationMethod {
    @Override
    protected char[] getOriginal() {
        char[] ori = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '0','1','2','3','4','5','6','7','8','9',
        };
        return ori;
    }

    @Override
    protected char[] getReplacement() {
        char[] dis = {'♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠',
                '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠',
                '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠', '♠',
        };
        return dis;
    }
}
