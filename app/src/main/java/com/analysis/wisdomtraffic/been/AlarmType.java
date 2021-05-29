package com.analysis.wisdomtraffic.been;

/**
 * @Author hejunfeng
 * @Date 15:28 2021/3/16 0016
 * @Description com.analysis.wisdomtraffic.been
 **/
public enum AlarmType {
    CALLSTATE(1,"报警"),
    ORDERSTATE(2,"接警"),
    HANDLESTATE(3,"处置"),
    FINISHSTATE(4,"完成");

    private int code;
    private String msg;

    AlarmType(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
