package base.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseActivity extends AppCompatActivity {


    /**
     * 是否沉浸式状态栏
     */
    private boolean isImmersionStatusBar = false;
    /**
     * 是否全屏
     */
    private boolean isFullScreen = false;
    /**
     * 是否允许屏幕旋转
     */
    private boolean isAllowScreenRoate = false;
    /**
     * TAG标签
     */
    protected final String TAG = this.getClass().getSimpleName()+"-LOG";
    /**
     * toast提示
     */
    private Toast toast;
    /**
     * Log日志的显示级别
     * 234567
     * 7对应对应Log.ASSERT
     * 2对应Log.VERBOSE
     * 大于或者等于7则不显示日志
     * 正式包不显示日志
     */
    private int logLevel = BuildConfig.DEBUG?Log.VERBOSE:Log.ASSERT;
    /**
     * 是否显示toast  只在Activity在前台活跃时候显示Toast
     */
    private boolean isShowToat = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logD("onCreate");
        init();
        initParas();
        setImmerStatusBar();
        setFullScreen();
        setContentView(bindLayout());
        setScreenOrientation();
        initData();
    }

    /**
     * 初始化参数
     */
    private void initParas() {
        Bundle bundle = getIntent().getExtras();
        if (null == bundle) {
            return;
        }
        initExtraParas(bundle);
    }

    /**
     * 初始化附带信息
     * @param bundle
     */
    protected abstract void initExtraParas(Bundle bundle);

    /**
     * 初始化数据
     * 网络请求
     */
    protected abstract void initData();

    /**
     * 总体初始化
     * 网络请求类等初始化
     */
    protected abstract void init();

    /**
     * 绑定布局
     *
     * @return 布局文件id
     */
    protected abstract int bindLayout();



    /**
     * 设置屏幕方向
     */
    private void setScreenOrientation() {
        if (isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true
     * 设置沉浸式状态栏
     */
    private void setImmerStatusBar() {
        if (isImmersionStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        if (isFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }


    /**
     * Activity跳转
     *
     * @param destinationActivity
     */
    protected void toActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(this, destinationActivity);
        startActivity(intent);
    }

    /**
     * Activity跳转
     *
     * @param destinationActivity
     * @param bundle
     */
    protected void toActivity(Class<?> destinationActivity, Bundle bundle) {
        Intent intent = new Intent(this, destinationActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转
     *
     * @param destinationActivity
     * @param requesrCode
     */
    protected void toActivity(Class<?> destinationActivity, int requesrCode) {
        Intent intent = new Intent(this, destinationActivity);
        startActivityForResult(intent, requesrCode);
    }

    /**
     * Activity跳转
     *
     * @param destinationActivity
     * @param bundle
     * @param requesrCode
     */
    protected void toActivity(Class<?> destinationActivity, Bundle bundle, int requesrCode) {
        Intent intent = new Intent(this, destinationActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requesrCode);
    }


    /**
     * 调用在super.onCreate()之前
     * 设置是否沉浸式状态栏
     *
     * @param immersionStatusBar
     */
    public void setImmersionStatusBar(boolean immersionStatusBar) {
        isImmersionStatusBar = immersionStatusBar;
    }

    /**
     * 调用在super.onCreate()之前
     * 设置是否全屏
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    /**
     * 设置是否允许屏幕旋转
     *
     * @param allowScreenRoate
     */
    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

    /**
     * 显示toast
     * @param info
     */
    protected void showToast(String info){
        if (null == toast){
            toast = Toast.makeText(this,info,Toast.LENGTH_SHORT);
        }else {
            toast.setText(info);
        }
        if (isShowToat){
            toast.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        logD("onStart");
        isShowToat = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        logD("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logD("onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        logD("onStop");
        isShowToat = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logD("onDestroy");
    }


    /**
     * 打印log日志  v级别的log日志
     * @param log
     */
    protected void logV(String log){
        if (null == log){
            return;
        }
        if (logLevel > Log.VERBOSE){
            return;
        }
        for (String logCell:segmentLog(log)) {
            Log.v(TAG+"-V", logCell);
        }
    }

    /**
     * 打印log日志  d级别的日志
     * @param log
     */
    protected void logD(String log){
        if (null == log){
            return;
        }
        if (logLevel > Log.DEBUG){
            return;
        }
        for (String logCell:segmentLog(log)) {
            Log.d(TAG+"-D", logCell);
        }
    }

    /**
     * 打印log 日志  i级别的日志
     * @param log
     */
    protected void logI(String log){
        if (null == log){
            return;
        }
        if (logLevel > Log.INFO){
            return;
        }
        for (String logCell:segmentLog(log)) {
            Log.i(TAG+"-I", logCell);
        }
    }

    /**
     * 打印log 日志  w级别的日志
     * @param log
     */
    protected void logW(String log){
        if (null == log){
            return;
        }
        if (logLevel > Log.WARN){
            return;
        }
        for (String logCell:segmentLog(log)) {
            Log.w(TAG+"-W", logCell);
        }
    }

    /**
     * 打印log 日志  e级别的日志
     * @param log
     */
    protected void logE(String log){
        if (null == log){
            return;
        }
        if (logLevel > Log.ERROR){
            return;
        }
        for (String logCell:segmentLog(log)){
            Log.e(TAG+"-E",logCell);
        }
    }

    /**
     * 拆分日志长度 ，因为系统设置的日志最大长度为4*1024B  即4kb
     * 但是实际上是略小于4kb的  所以我们的每条日志长度设置为3*1024b 即3kb
     * @param log 原log日志内容
     * @return log拆分后的日志list
     */
    protected List<String> segmentLog(String log){
        int segmentSize = 3*1024; //设置拆分大小为3 kb
        if (null == log){
            return null;
        }
        List<String> logs = new ArrayList<String>();
        //能整除部分的日志list
        int count = log.length() / segmentSize;
        for (int i = 0; i < count; i++) {
            logs.add(log.substring(segmentSize*i,segmentSize*(i+1)));
        }
        //增加上不能整除的最后部分
        if (log.length() % segmentSize > 0){
            logs.add(log.substring(segmentSize*count));
        }
        return logs;
    }


}
