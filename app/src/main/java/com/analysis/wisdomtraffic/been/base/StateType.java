package com.analysis.wisdomtraffic.been.base;

import com.analysis.wisdomtraffic.R;

/**
 * @Author hejunfeng
 * @Date 10:51 2021/3/20 0020
 * @Description com.analysis.wisdomtraffic.been.base
 **/
public enum StateType {
    CALLSTATE(10001,"(已报警)","接警",R.color.a1_orange_color),
    ORDERSTATE(10002,"(已接警)","处置",R.color.yichzhi_color),
    HANDLESTATE(10003,"(已处置)","",R.color.upgrade_green),
    FINISHSTATE(10004,"(已结案)","",R.color.yiwancheng_color);


    private int code;
    private String msg;
    private String act;
    private int color;

    StateType(int code, String msg, String act, int color) {
        this.code = code;
        this.msg = msg;
        this.act = act;
        this.color = color;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}
