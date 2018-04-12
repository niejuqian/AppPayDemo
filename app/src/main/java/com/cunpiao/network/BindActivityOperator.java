package com.cunpiao.network;

import android.app.Activity;

import rx.Observable;
import rx.Subscriber;

/**
 * @anthor: 6213
 * @time: 2018/03/02
 * @desc:进行Subscriber转换，绑定Activity生命周期
 */

public class BindActivityOperator<T> implements Observable.Operator<T, T> {

    private Activity activity;
    private ActivityLifecycle unsubscribeOn;

    public BindActivityOperator(Activity activity) {
        this(activity, ActivityLifecycle.OnDestroy);
    }

    public BindActivityOperator(Activity activity, ActivityLifecycle unsubscribeOn) {
        this.activity = activity;
        this.unsubscribeOn = unsubscribeOn;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        SubscriberManager.getSingleton().addSubscriber(activity,subscriber,unsubscribeOn);
        return subscriber;
    }
}
