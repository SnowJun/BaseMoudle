package base.net;

public interface INetListener {

    /**
     * 网络请求成功
     * @param info
     */
    public void netSuccess(String info);

    /**
     * 网络请求失败
     * @param failInfo
     */
    public void netFail(String failInfo);

}
