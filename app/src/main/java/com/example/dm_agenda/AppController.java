package com.example.dm_agenda;
import android.app.Activity;

import java.util.Stack;

public class AppController {
    private static AppController instance;
    private Stack<Activity> activityStack;

    private AppController() {
        activityStack = new Stack<>();
    }

    public static synchronized AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    public Activity getActivityInStack(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }
}
