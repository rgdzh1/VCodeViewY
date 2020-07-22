package com.yey.vcodevy;

/**
 * 输入完成回调接口
 */
public interface IVCodeBack {
    /**
     * 输入完成的内容
     *
     * @param contentComplete
     */
    void inputComplete(String contentComplete);

    /**
     * @param contentInputing 输入中的内容
     * @param index           当前输入的索引
     */
    void inputing(String contentInputing, int index);
}