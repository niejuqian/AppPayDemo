package com.cunpiao.network;

import android.app.Activity;


import com.cunpiao.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-02-28 11:46
 * @DESCRIPTION:
 */

public class SubscriberManager {
    private static String TAG = SubscriberManager.class.getSimpleName();
    private static SubscriberManager subscriberManager = new SubscriberManager();
    private SubscriberManager(){}
    public static SubscriberManager getSingleton(){
        return subscriberManager;
    }

    private Map<String,List<SubscriberWrapper>> subscribers = new HashMap<>();
    private class SubscriberWrapper {
        Subscriber subscriber;
        ActivityLifecycle unsubscribeOn;

        public SubscriberWrapper(Subscriber subscriber, ActivityLifecycle unsubscribeOn) {
            this.subscriber = subscriber;
            this.unsubscribeOn = unsubscribeOn;
        }
    }
    public void addSubscriber(Activity activity,Subscriber subscriber, ActivityLifecycle unsubscribeOn) {
        if (null == activity) return;
        Logger.e(TAG,"==================开始绑定activity" + activity.getClass().getSimpleName());
        List<SubscriberWrapper> list = subscribers.get(activity.getClass().getSimpleName());
        if (null == list) {
            list = new ArrayList<>();
            subscribers.put(activity.getClass().getSimpleName(),list);
        }
        list.add(new SubscriberWrapper(subscriber, unsubscribeOn));
    }

    public void onDestory(Activity activity){
        if (activity == null || subscribers == null || subscribers.size() == 0) return;
        Logger.e(TAG,"==================开始解除绑定activity" + activity.getClass().getSimpleName());
        List<SubscriberWrapper> list = subscribers.get(activity.getClass().getSimpleName());
        if (list != null && list.size() > 0){
            Iterator<SubscriberWrapper> it = list.iterator();
            while (it.hasNext()) {
                SubscriberWrapper subscriberWrapper = it.next();
                if (subscriberWrapper.unsubscribeOn == ActivityLifecycle.OnDestroy) {
                    Logger.e(TAG, "onDestroy==============>");
                    subscriberWrapper.subscriber.unsubscribe();
                    it.remove();
                }
            }
        }
    }
}
