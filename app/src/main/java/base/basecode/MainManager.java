package base.basecode;

import base.net.INetClient;
import base.net.INetListener;
import base.net.netOkHttp.OkHttpNetClient;

public class MainManager {

    private  INetClient client = OkHttpNetClient.getOkHttpNetClient();

    public MainManager() {
    }

    public void getContent(Object tag, String url, INetListener listener){
        client.doGet(url,tag,listener);
    }



}
