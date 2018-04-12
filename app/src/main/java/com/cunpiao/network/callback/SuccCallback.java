package com.cunpiao.network.callback;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *              网络请求成功回掉
 */

public interface SuccCallback<T> {
    void call(T t);
}
