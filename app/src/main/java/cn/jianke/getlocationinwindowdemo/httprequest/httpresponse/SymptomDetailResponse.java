package cn.jianke.getlocationinwindowdemo.httprequest.httpresponse;

import java.io.Serializable;

/**
 * @className: SymptomDetailResponse
 * @classDescription: 症状详情
 * @author: leibing
 * @createTime:
 */
public class SymptomDetailResponse extends BaseResponse implements Serializable{
    // 序列化UID 用于反序列化
    private static final long serialVersionUID = -2809110774884320927L;
    // 科室
    public String department = "";
    // 标题
    public String namecn = "";
    // 概要
    public String summarize = "";
    // 诊断
    public String diagnoses = "";
    // 食疗
    public String foodtreat = "";
    // 预防
    public String prevent = "";
    // 差别
    public String differential = "";
    // 病因
    public String pathogeny = "";
}
