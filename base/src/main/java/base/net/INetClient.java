package base.net;


import java.util.Map;

public interface INetClient {

    /**
     * get请求  无tag
     * @param url   请求地址
     * @param listener  网络请求回调
     */
    public void doGet(String url, INetListener listener);

    /**
     * get请求  带tag
     * @param url   请求地址
     * @param tag   tag标签
     * @param listener  网络请求回调
     */
    public void doGet(String url, Object tag, INetListener listener);

    /**
     * post请求 参数为json串  无tag
     * @param url   请求地址
     * @param parasJson json参数
     * @param listener  网络请求回调
     */
    public void doPost(String url, String parasJson, INetListener listener);

    /**
     *  post请求 参数为json串 有tag
     * @param url   请求地址
     * @param tag   tag标签
     * @param parasJson json参数
     * @param listener  网络请求回调
     */
    public void doPost(String url, Object tag, String parasJson, INetListener listener);

    /**
     * post请求 参数为表单  无tag
     * @param url   请求地址
     * @param paras 表单参数
     * @param listener  网络请求回调
     */
    public void doPost(String url, Map<String, String> paras, INetListener listener);

    /**
     * post请求 参数为表单  有tag
     * @param url   请求地址
     * @param tag   tag标签
     * @param paras 表单参数
     * @param listener  网络请求回调
     */
    public void doPost(String url, Object tag, Map<String, String> paras, INetListener listener);

    /**
     * 根据tag取消所有请求
     * @param tag
     */
    public void cancelCall(Object tag);

}
