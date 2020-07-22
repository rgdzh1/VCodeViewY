package com.yey.vcodevy;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.yey.vcodevy.widget.PwdEditText;

import java.util.ArrayList;

public class YBooxsVerify extends FrameLayout {
    private final static String TAG = YBooxsVerify.class.getName();
    private LinearLayout mContainerText;
    private PwdEditText mPet;
    private int mBoxNum;
    private int mBoxMargin;
    private float mBoxTextSize;
    private int mBoxFocus;
    private int mBoxNotFcous;
    private boolean mBoxPwdModel;
    private ArrayList<TextView> mTextViewList;
    private int mBoxTextColor;
    private int mInputIndex;//输入索引
    private int mInputType;
    private boolean mInputComplete;
    private int mBoxHeight;
    private int mBoxWidth;
    //明文属性转为密文属性 handler
    private Handler mRefreshHandler = new Handler(Looper.getMainLooper());
    private StringBuffer mContentBuffer = new StringBuffer();

    private IVCodeBack mICodeBack;

    public YBooxsVerify(Context context) {
        this(context, null);
    }

    public YBooxsVerify(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YBooxsVerify(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initRes(context, attrs, defStyleAttr);
        creatView();
        initLister();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_vcodeviewy, this);
        mContainerText = (LinearLayout) findViewById(R.id.ll_text);
        mPet = (PwdEditText) findViewById(R.id.pet);
    }

    private void initRes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YBooxsVerify, defStyleAttr, 0);
        mBoxNum = typedArray.getInteger(R.styleable.YBooxsVerify_box_bum, 1);
        mBoxMargin = typedArray.getDimensionPixelSize(R.styleable.YBooxsVerify_box_margin, 6);
        mBoxTextSize = typedArray.getDimensionPixelSize(R.styleable.YBooxsVerify_box_text_size, 16);
        mBoxHeight = typedArray.getDimensionPixelSize(R.styleable.YBooxsVerify_box_height, 40);
        mBoxWidth = typedArray.getDimensionPixelSize(R.styleable.YBooxsVerify_box_width, 40);
        mBoxTextColor = typedArray.getColor(R.styleable.YBooxsVerify_box_text_color, getResources().getColor(R.color.vcvy_balck));
        mBoxFocus = typedArray.getResourceId(R.styleable.YBooxsVerify_box_focus, R.drawable.box_focus);
        mBoxNotFcous = typedArray.getResourceId(R.styleable.YBooxsVerify_box_not_focus, R.drawable.box_notfoucs);
        mBoxPwdModel = typedArray.getBoolean(R.styleable.YBooxsVerify_box_pwd_model, false);
        typedArray.recycle();
    }

    private void creatView() {
        initEditText();
        mTextViewList = new ArrayList<>();
        mTextViewList.clear();
        for (int i = 0; i < mBoxNum; i++) {
            //TODO 这里Text的大小与边距都没有设置, 在onLayout中去设置
            TextView mTextView = new TextView(getContext());
            mTextView.setTextColor(mBoxTextColor);
            //为Paint画笔设置大小, 不会在有适配问题
            mTextView.getPaint().setTextSize(mBoxTextSize);
            mTextView.setGravity(Gravity.CENTER);
            if (i == 0) {
                mTextView.setBackgroundResource(mBoxFocus);
            } else {
                mTextView.setBackgroundResource(mBoxNotFcous);
            }
            mTextViewList.add(mTextView);
            mContainerText.addView(mTextView);
        }
    }

    private void initEditText() {
        switch (mInputType) {
            case 0:
                mPet.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 1:
                mPet.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < mBoxNum; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mBoxWidth, mBoxHeight);
            if (i != 0) {
                layoutParams.leftMargin = mBoxMargin;
            }
            TextView textView = mTextViewList.get(i);
            textView.setWidth(mBoxWidth);
            textView.setHeight(mBoxHeight);
            textView.setLayoutParams(layoutParams);
        }
    }


    private void initLister() {
        mPet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence inputStr, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, editable.toString());
                }
                if (!TextUtils.isEmpty(editable) && mInputIndex >= 0 && mInputIndex < mBoxNum && !mInputComplete) {
                    //1.当EditText中有输入时,将该输入取出展示到mInputIndex对应的textview中
                    //将当前的textview 置为not focus
                    //2.mInputIndex加1之后对应的textview 背景置为focus
                    //3.将EditText 数据清除
                    final TextView notFouceTextView = mTextViewList.get(mInputIndex);
                    notFouceTextView.setText(editable);
                    mContentBuffer.append(editable);//添加内容
                    //输入中回调
                    if (mICodeBack != null) {
                        mICodeBack.inputing(mContentBuffer.toString(), mInputIndex);
                    }
                    notFouceTextView.setBackgroundResource(mBoxNotFcous);
                    if (mBoxPwdModel) {
                        mRefreshHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //将textview 属性改为密文属性, 延迟200毫秒从明文变为密文
                                notFouceTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                        }, 200);
                    }
                    mInputIndex++;
                    //当mIndex 值超出了索引最大值,将mInputIndex置为索引最大值,防止索引越界
                    if (mInputIndex > mBoxNum - 1) {
                        mInputIndex--;
                        //当输入完成之后, 再输入的话就不让输入了
                        mInputComplete = true;
                        //输入完成回调
                        if (mICodeBack != null) {
                            mICodeBack.inputComplete(mContentBuffer.toString());
                        }
                    }
                    TextView fouceTextView = mTextViewList.get(mInputIndex);
                    fouceTextView.setBackgroundResource(mBoxFocus);
                    mPet.setText("");
                }
                Editable text = mPet.getText();
                if (!TextUtils.isEmpty(text)) {
                    mPet.setText("");
                }
            }
        });
        mPet.setBackSpaceListener(new TInputConnection.BackspaceListener() {
            @Override
            public boolean onBackspace() {
                if (mInputIndex >= 0 && mInputIndex < mBoxNum) {
                    //删除了一个空格, 此时可以再输入内容
                    mInputComplete = false;
                    TextView mLastText = mTextViewList.get(mBoxNum - 1);
                    String mLastString = mLastText.getText().toString().trim();
                    if (!TextUtils.isEmpty(mLastString)) {
                        //此时输入完成,将最后一个textview内容删除,但是mInputIndex 不要进行减1
                        //此时最后一个textview背景还是focus
                        mLastText.setText("");
                        if (mBoxPwdModel) {
                            mLastText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    } else {
                        //当最后一个textview没有数据,此时就是没有输入完成删除
                        //要进行两步操作1.将mInputIndex对应的textview背景置为not focus
                        //              2.将mInputIndex减1后对应的textview内容删除,并且将该textview 背景置为focus
                        TextView fouceTextView = mTextViewList.get(mInputIndex);
                        fouceTextView.setBackgroundResource(mBoxNotFcous);
                        mInputIndex--;
                        //若mInputIndex此时为0, 按删除按钮的时候mInputIndex减1就变成了-1,
                        //会造成索引越界,将mInputIndex置为0,可以避免越界.
                        if (mInputIndex < 0) {
                            mInputIndex = 0;
                        }
                        TextView notFouceTextView = mTextViewList.get(mInputIndex);
                        notFouceTextView.setText("");
                        notFouceTextView.setBackgroundResource(mBoxFocus);
                        if (mBoxPwdModel) {
                            notFouceTextView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                    if (mContentBuffer.length() != 0) {
                        mContentBuffer.deleteCharAt(mInputIndex);//删除内容
                        //输入中回调
                        if (mICodeBack != null) {
                            mICodeBack.inputing(mContentBuffer.toString(), mInputIndex);
                        }
                    }
                }
                return false;
            }
        });
    }

    /**
     * 清除所有输入内容
     */
    public void clearAllContent() {
        mPet.setText("");
        mInputIndex = 0;
        mInputComplete = false;//记得此时控件是可输入状态
        for (int i = 0; i < mTextViewList.size(); i++) {
            final TextView textView = mTextViewList.get(i);
            mContentBuffer.delete(0, mContentBuffer.length());
            textView.setText("");
            if (i == 0) {
                textView.setBackgroundResource(mBoxFocus);
            } else {
                textView.setBackgroundResource(mBoxNotFcous);
            }
        }
    }

    /**
     * 向密码框中填充内容
     */
    public void fillAllContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            int length = content.length();
            if (length > mBoxNum) {
                Log.d(TAG, "fillAllContent: 填充的内容长达比输入框个数要大");
            } else {
                mPet.setText("");
                mInputIndex = length - 1;
                mInputComplete = true;//记得此时控件是可输入状态
                mContentBuffer.append(content);
                char[] chars = content.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    final TextView textView = mTextViewList.get(i);
                    if (mBoxPwdModel) {
                        textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    textView.setText(String.valueOf(chars[i]));
                    if (i == mTextViewList.size() - 1) {
                        textView.setBackgroundResource(mBoxFocus);
                    } else {
                        textView.setBackgroundResource(mBoxNotFcous);
                    }
                }
                if (mICodeBack != null) {
                    mICodeBack.inputComplete(mContentBuffer.toString());
                }
            }
        } else {
            Log.d(TAG, "fillAllContent: 填充内容不能为空");
        }
    }

    /**
     * 获取密码框中的密码
     */
    public String getContent() {
        return mContentBuffer.toString();
    }

    /**
     * 显示或者隐藏输入内容
     */
    public void changeModel() {
        mBoxPwdModel = !mBoxPwdModel;
        for (int i = 0; i < mTextViewList.size(); i++) {
            final TextView textView = mTextViewList.get(i);
            if (mBoxPwdModel) {
                textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    /**
     * @return 控件当前展示的是明文还是密文, 密文为true,明文为false
     */
    public boolean isPwdModel() {
        return mBoxPwdModel;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRefreshHandler.removeCallbacksAndMessages(null);
    }

    public void setInputCallback(IVCodeBack ivCodeBack) {
        this.mICodeBack = ivCodeBack;
    }


}
