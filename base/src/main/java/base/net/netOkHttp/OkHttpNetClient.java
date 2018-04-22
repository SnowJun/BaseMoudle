package base.net.netOkHttp;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import base.net.INetClient;
import base.net.INetListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpNetClient implements INetClient {

    private OkHttpClient client;

    private static OkHttpNetClient netClient;

    private OkHttpNetClient() {
        client = new OkHttpClient();
    }

    public static OkHttpNetClient getOkHttpNetClient(){
        if (null == netClient){
            netClient = new OkHttpNetClient();
        }
        return netClient;
    }


    @Override
    public void doGet(String url, final INetListener listener) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.netFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.netSuccess(response.body().string());
            }
        });
    }

    @Override
    public void doGet(String url, Object tag, final INetListener listener) {
        Request request = new Request.Builder().url(url).tag(tag).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.netFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.netSuccess(response.body().string());
            }
        });
    }

    @Override
    public void doPost(String url, String parasJson, final INetListener listener) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, parasJson);
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.netFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.netSuccess(response.body().string());
            }
        });
    }

    @Override
    public void doPost(String url, Object tag, String parasJson, final INetListener listener) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, parasJson);
        Request request = new Request.Builder().url(url).tag(tag).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.netFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.netSuccess(response.body().string());
            }
        });
    }

    @Override
    public void doPost(String url, Map<String, String> paras, final INetListener listener) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != paras){
            Set<String> keys = paras.keySet();
            for (String key:keys) {
                builder.add(key,paras.get(key));
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.netFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.netSuccess(response.body().string());
            }
        });
    }

    @Override
    public void doPost(String url, Object tag, Map<String, String> paras, final INetListener listener) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != paras){
            Set<String> keys = paras.keySet();
            for (String key:keys) {
                builder.add(key,paras.get(key));
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).tag(tag).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.netFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.netSuccess(response.body().string());
            }
        });
    }


    /**
     * 根据tag取消请求
     * @param tag
     */
    @Override
    public void cancelCall(Object tag){
        if (null == tag){
            return;
        }
        List<Call> callsQuene = client.dispatcher().queuedCalls();
        for (Call call:callsQuene) {
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
        List<Call> callsRunning = client.dispatcher().runningCalls();
        for (Call call:callsRunning) {
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
    }

}
