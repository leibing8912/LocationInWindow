package cn.jianke.getlocationinwindowdemo.httprequest.httpresponse;

import java.io.Serializable;

/**
 * @className: BaseResponse
 * @classDescription: 网络请求返回对象公共抽象类
 * @author: leibing
 * @createTime: 2016/08/30
 */
public class BaseResponse implements Serializable {
    // 序列化UID 用于反序列化
    private static final long serialVersionUID = 234513596098152098L;
    // 是否成功
    private boolean isSuccess;
    // 数据
    public String infoData;
    // 错误消息
    public String errormsg;
    // 数据
    public String data = "";
    // 消息
    public String msg = "";

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getInfo() {
        return infoData;
    }

    public void setInfo(String info) {
        this.infoData = info;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
