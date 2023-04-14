package com.flk.quiz;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ActivityHelper {
    private static ActivityHelper helper = new ActivityHelper();

    private final List<Activity> list = new ArrayList<>();

    public static ActivityHelper newInstance() {
        return helper;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void removeActivity(Activity activity) {
        list.remove(activity);
    }

    public void finishAll() {
        for (Activity activity : list) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }


}
