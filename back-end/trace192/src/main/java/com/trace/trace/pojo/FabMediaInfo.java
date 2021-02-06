package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.pojo
 * @Description
 * @create 2021-02-03-16:12
 */
public class FabMediaInfo {
    @Expose
    @SerializedName("type")
    int type;

    @Expose
    @SerializedName("filename")
    String filename;

    @Expose
    @SerializedName("md5code")
    String md5code;

    @Expose
    @SerializedName("checkTime")
    String checkTime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMd5code() {
        return md5code;
    }

    public void setMd5code(String md5code) {
        this.md5code = md5code;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public String toString() {
        return "FabMediaInfo{"
               + "type=" + type
               + ", filename='" + filename + '\''
               + ", md5code='" + md5code + '\''
               + ", checkTime='" + checkTime + '\''
               + '}';
    }
}
