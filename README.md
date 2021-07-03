### YBoxsVerify 验证码和密码输入框 
> 借用了原项目的思想, 我自己重新写的, 已用在自己多个项目中.
#### 属性介绍
| 属性 |   解释   |
| --- | -------- |
|box_bum|输入框数量|
|box_margin|输入框间隔距离|
|box_width|输入框宽|
|box_height|输入框高|
|box_text_size|输入框文字大小|
|box_text_color|输入框文字颜色|
|box_focus|输入框获取焦点时候的边框|
|box_not_focus|输入框失去焦点时候的边框|
|box_pwd_model|输入框是否是密码模式 true 密文, false 明文|
|box_input_type|输入框可以输入的是数字还是文本|
|box_pwd_dot_size|密文下小黑点是大还是小|
|box_text_style|明文下字体是否为粗体|
#### 依赖
```groovy
// 1.
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
// 2.
dependencies {
    implementation 'com.github.rgdzh1:YBoxsVerify:0.1.8'
}
```
#### 两种不同场景的使用
##### 系统键盘输入场景下使用
1. XML布局
```xml
<com.yey.vcodevy.YBoxsVerify
    android:id="@+id/vcv"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:layout_marginTop="100dp"
    app:box_bum="5"
    app:box_height="40dp"
    app:box_input_type="text"
    app:box_margin="5dp"
    app:box_pwd_dot_size="large"
    app:box_pwd_model="false"
    app:box_text_color="@color/colorPrimary"
    app:box_text_size="18sp"
    app:box_text_style="true"
    app:box_width="40dp" />
```
2. 代码
```java
dataBinding.vcv.setInputCallback(new IVCodeBack() {
    @Override
    public void inputComplete(String contentComplete) {
        // 输入完成的回调
        Log.e(TAG, contentComplete);
    }
    @Override
    public void inputing(String contentInputing, int index) {
        // YBoxsVerify控件上的内容
        Log.e(TAG, contentInputing);
        // 当前输入的索引
        Log.e(TAG, "当前输入索引 " + index);
    }
});
dataBinding.clear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // 清空YBoxsVerify控件上的内容
        dataBinding.vcv.clearAllContent();
    }
});
dataBinding.show.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // YBoxsVerify明文密文切换
        dataBinding.vcv.changeModel();
    }
});
dataBinding.fill.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // 一次性向YBoxsVerify控件中填充数据
        dataBinding.vcv.fillAllContent("12345");
    }
});
```
##### 自定义键盘输入场景下使用
1. XML 布局
```xml
<com.yey.vcodevy.YBoxsCustomInput
    android:id="@+id/vcv"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    app:box_bum="5"
    app:box_height="50dp"
    app:box_margin="5dp"
    app:box_pwd_model="true"
    app:box_text_color="@color/colorPrimary"
    app:box_text_size="14sp"
    app:box_width="50dp" />
```
2. 代码
```java
/**
 * 监听输入按键和删除按键
 */
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
    // YBoxsCustomInput 控件删除内容的方法
    mDataBinding.vcv.deleteContent();
    String mCodeStr = mDataBinding.vcv.getContent();
    Toast.makeText(this, mCodeStr, Toast.LENGTH_LONG).show();
}
/**
 * 输入内容
 */
private void inputNumber(int content) {
    // YBoxsCustomInput 控件输入内容的方法
    mDataBinding.vcv.inputContent(String.valueOf(content));
    String mCodeStr = mDataBinding.vcv.getContent();
    Toast.makeText(this, mCodeStr, Toast.LENGTH_LONG).show();
}
```
##### DEMO下载
<img src="下载.png" style="zoom:50%">
