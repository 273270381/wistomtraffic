package com.analysis.wisdomtraffic.been;

import java.io.Serializable;

/**
 * @description 启动页数据
 * @author hejunfeng
 * @time 2020/7/17 0017
 */

public class Launcher implements Serializable {
    private boolean isAd;//是否是广告
    private String imgUrl;//图片连接
    private String href;//打开链接
    private boolean isExpired;//是否过期

    public boolean isAd() {
        return isAd;
    }

    public void setAd(boolean ad) {
        isAd = ad;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}
