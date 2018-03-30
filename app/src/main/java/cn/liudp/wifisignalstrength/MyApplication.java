package cn.liudp.wifisignalstrength;

import android.app.Activity;
import android.app.Application;
import android.os.Binder;
import android.os.Bundle;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.hugo.watcher.Watcher;
import com.blankj.utilcode.util.NetworkUtils;

//import im.fir.sdk.FIR;

/**
 * @author dongpoliu on 2018-03-15.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;
    private static String mAppCacheDir;
    private int mActivityCount;
    public static String mAppName = "MyApplication";
    public final static boolean enableGuide = true;

    public static MyApplication getApplication() {
        return mApplication;
    }

    public static void setApplication(MyApplication application) {
        MyApplication.mApplication = application;
    }

    public int getActivityCount() {
        return mActivityCount;
    }

    public boolean isConnected() {
        return NetworkUtils.isConnected();
    }

    public void setActivityCount(int activityCount) {
        mActivityCount = activityCount;
    }

    public static String getAppCacheDir() {
        return mAppCacheDir;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppName = this.getPackageManager().getNameForUid(Binder.getCallingUid());
        mApplication = this;
//        CrashHandler.init(new CrashHandler(application));
        Utils.init(this);
        if (!BuildConfig.DEBUG) {
            // App crash analysis and auto-detect upgrading
//            FIR.init(this);
        } else {
            // watch the fps and used memory of the app.
            Watcher.getInstance().start(this);
            // debug bridge for Android applications, enabling the powerful Chrome Developer Tools and much more.
            Stetho.initializeWithDefaults(this);
        }
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivityCount++;
//                AppLog.d(getClass().getName(), "onActivityStarted " + mActivityCount);
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mActivityCount--;
//                AppLog.d(getClass().getName(), "onActivityStarted " + mActivityCount);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}