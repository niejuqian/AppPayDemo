package com.cunpiao.network.callback;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *              异常回掉
 */

public interface ErrCallback<T1,T2> {
    void call(T1 t1, T2 t2);
}
