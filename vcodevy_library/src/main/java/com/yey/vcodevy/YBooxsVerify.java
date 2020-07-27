package com.yey.vcodevy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.yey.vcodevy.widget.PwdEditText;
import com.yey.vcodevy.widget.TInputConnection;
import com.yey.vcodevy.widget.YPasswordTransformation;

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
    private boolean isPwd;
    private ArrayList<TextView> mTextViewList;
    private int mBoxTextColor;
    private int mInputIndex;//输入索引
    private int mEditTextType;// 键盘类型 数字 或者文字
    private int mBoxPwdDotSize;// 密文模式下,黑点是大还是小
    private boolean mInputComplete;
    private boolean isTextBoldStyle;// 明文字体是否为粗体 true 粗体
    private int mBoxHeight;
    private int mBoxWidth;
    private TransformationMethod mPwdTransformationMethod;
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
        initData();
        initLister();
    }


    /**
     * 初始化View
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_vcodeviewy, this);
        mContainerText = findViewById(R.id.ll_text);
        mPet = findViewById(R.id.pet);
    }

    /**
     * 初始化资源
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
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
        isPwd = typedArray.getBoolean(R.styleable.YBooxsVerify_box_pwd_model, false);
        mEditTextType = typedArray.getInt(R.styleable.YBooxsVerify_box_input_type, 0);
        mBoxPwdDotSize = typedArray.getInt(R.styleable.YBooxsVerify_box_pwd_dot_size, 0);
        isTextBoldStyle = typedArray.getBoolean(R.styleable.YBooxsVerify_box_text_style, false);
        typedArray.recycle();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 设置输入数据类型
        setPetInputType();
        // 设置黑色点大还是小
        setBoxPwdDotSize();
        // 创建TextViews
        creatTextViews();
    }

    /**
     * 设置黑色圆点大小
     */
    private void setBoxPwdDotSize() {
        if (mBoxPwdDotSize == 0) {
            // 大
            mPwdTransformationMethod = YPasswordTransformation.getInstance();
        } else if (mBoxPwdDotSize == 1) {
            // 小
            mPwdTransformationMethod = PasswordTransformationMethod.getInstance();
        }
    }

    /**
     * 创建展示数据用的TextVie
     */
    private void creatTextViews() {
        mTextViewList = new ArrayList<>();
        mTextViewList.clear();
        for (int i = 0; i < mBoxNum; i++) {
            //TODO 这里Text的大小与边距都没有设置, 在onLayout中去设置
            TextView mTextView = new TextView(getContext());
            mTextView.setTextColor(mBoxTextColor);
            //为Paint画笔设置大小, 不会在有适配问题
            mTextView.getPaint().setTextSize(mBoxTextSize);
            if (isTextBoldStyle) {
                mTextView.getPaint().setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
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

    /**
     * 设置EditText能接收的输入数据类型
     */
    private void setPetInputType() {
        switch (mEditTextType) {
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
        setTextViewMargin();
    }

    /**
     * 设置每个TextView之间的间距
     */
    private void setTextViewMargin() {
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
        // 监听EditText的输入
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
                    if (isPwdStatus()) {
                        mRefreshHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //将textview 属性改为密文属性, 延迟200毫秒从明文变为密文
                                notFouceTextView.setTransformationMethod(mPwdTransformationMethod);
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
        // 监听键盘删除按钮
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
                        if (isPwdStatus()) {
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
                        if (isPwdStatus()) {
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
                    if (isPwdStatus()) {
                        textView.setTransformationMethod(mPwdTransformationMethod);
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
        isPwd = !isPwd;
        for (int i = 0; i < mTextViewList.size(); i++) {
            final TextView textView = mTextViewList.get(i);
            if (isPwd) {
                textView.setTransformationMethod(mPwdTransformationMethod);
            } else {
                textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    /**
     * @return 是否是密文状态 密文为true,明文为false
     */
    public boolean isPwdStatus() {
        return isPwd;
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
