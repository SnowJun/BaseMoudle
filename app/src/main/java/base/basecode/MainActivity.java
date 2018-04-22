package base.basecode;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import base.base.BaseActivity;
import base.net.INetListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {


    @BindView(R.id.txt_test)
    TextView txtTest;
    @BindView(R.id.txt_test1)
    TextView txtTest1;
    @BindView(R.id.txt_test2)
    TextView txtTest2;
    @BindView(R.id.txt_test3)
    TextView txtTest3;

    private MainManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void initExtraParas(Bundle bundle) {
        logD("initExtraParas");
    }

    @Override
    protected void initData() {
        logD("initData");
        manager.getContent(this, "https://www.jianshu.com/p/6165c9ba94ba", new INetListener() {
            @Override
            public void netSuccess(String info) {
                logD("返回内容："+info);
            }

            @Override
            public void netFail(String failInfo) {
                logD("错误内容："+failInfo);
            }
        });
    }

    @Override
    protected void init() {
        logD("init");
        manager = new MainManager();
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.txt_test, R.id.txt_test1, R.id.txt_test2, R.id.txt_test3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_test:
                logD("0");
                break;
            case R.id.txt_test1:
                logD("1");
                break;
            case R.id.txt_test2:
                logD("2");
                break;
            case R.id.txt_test3:
                logD("3");
                break;
        }
    }


}
