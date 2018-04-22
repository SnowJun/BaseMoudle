package base.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseFragment extends Fragment {


    /**
     * TAG标签
     */
    protected final String TAG = this.getClass().getSimpleName();

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logD("onCreateView");
        return inflater.inflate(bindLayout(), container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logD("onActivityCreated");
        init();
        initParas();
        initData();
    }

    /**
     * 初始化参数
     */
    private void initParas() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (null == bundle) {
            return;
        }
        initExtraParas(bundle);
    }

    /**
     * 初始化附带信息
     *
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
     * Activity跳转
     *
     * @param destinationActivity
     */
    protected void toActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(getActivity(), destinationActivity);
        startActivity(intent);
    }

    /**
     * Activity跳转
     *
     * @param destinationActivity
     * @param bundle
     */
    protected void toActivity(Class<?> destinationActivity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), destinationActivity);
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
        Intent intent = new Intent(getActivity(), destinationActivity);
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
        Intent intent = new Intent(getActivity(), destinationActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requesrCode);
    }

    /**
     * 显示toast
     *
     * @param info
     */
    protected void showToast(String info) {
        if (null == toast) {
            toast = Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT);
        } else {
            toast.setText(info);
        }
        toast.show();
    }



    @Override
    public void onStart() {
        super.onStart();
        logD("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        logD("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        logD("onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        logD("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logD("onDestroy");
    }


    /**
     * 打印log日志  v级别的log日志
     *
     * @param log
     */
    protected void logV(String log) {
        if (null == log) {
            return;
        }
        if (logLevel > Log.VERBOSE) {
            return;
        }
        for (String logCell : segmentLog(log)) {
            Log.v(TAG, logCell);
        }
    }

    /**
     * 打印log日志  d级别的日志
     *
     * @param log
     */
    protected void logD(String log) {
        if (null == log) {
            return;
        }
        if (logLevel > Log.DEBUG) {
            return;
        }
        for (String logCell : segmentLog(log)) {
            Log.d(TAG, logCell);
        }
    }

    /**
     * 打印log 日志  i级别的日志
     *
     * @param log
     */
    protected void logI(String log) {
        if (null == log) {
            return;
        }
        if (logLevel > Log.INFO) {
            return;
        }
        for (String logCell : segmentLog(log)) {
            Log.i(TAG, logCell);
        }
    }

    /**
     * 打印log 日志  w级别的日志
     *
     * @param log
     */
    protected void logW(String log) {
        if (null == log) {
            return;
        }
        if (logLevel > Log.WARN) {
            return;
        }
        for (String logCell : segmentLog(log)) {
            Log.w(TAG, logCell);
        }
    }

    /**
     * 打印log 日志  e级别的日志
     *
     * @param log
     */
    protected void logE(String log) {
        if (null == log) {
            return;
        }
        if (logLevel > Log.ERROR) {
            return;
        }
        for (String logCell : segmentLog(log)) {
            Log.e(TAG, logCell);
        }
    }

    /**
     * 拆分日志长度 ，因为系统设置的日志最大长度为4*1024B  即4kb
     * 但是实际上是略小于4kb的  所以我们的每条日志长度设置为3*1024b 即3kb
     *
     * @param log 原log日志内容
     * @return log拆分后的日志list
     */
    protected List<String> segmentLog(String log) {
        int segmentSize = 3 * 1024; //设置拆分大小为3 kb
        if (null == log) {
            return null;
        }
        List<String> logs = new ArrayList<String>();
        //能整除部分的日志list
        int count = log.length() / segmentSize;
        for (int i = 0; i < count; i++) {
            logs.add(log.substring(segmentSize * i, segmentSize * (i * 1)));
        }
        //增加上不能整除的最后部分
        if (log.length() % segmentSize > 0) {
            logs.add(log.substring(segmentSize * count));
        }
        return logs;
    }


}
