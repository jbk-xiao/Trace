package com.trace.fabric.fabtrace;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

/**
 * @author jbk-xiao
 * @program media-operation-chaincode-java
 * @packagename com.trace.fabric.fabtrace
 * @Description 存储所需的图片或是视频信息，包括：类型（1为图片，2为视频），文件名，md5码，存储时间（校验时间）
 * @create 2021-01-29-17:04
 */

@DataType
public final class MediaInfo {

    @Property()
    private final int type;

    @Property()
    private final String filename;

    @Property()
    private final String md5code;

    @Property()
    private final long checkTime;

    public MediaInfo(@JsonProperty("type") final int type, @JsonProperty("filename") final String filename,
                     @JsonProperty("md5code") final String md5code, @JsonProperty("check_time") final long checkTime) {
        this.type = type;
        this.filename = filename;
        this.md5code = md5code;
        this.checkTime = checkTime;
    }

    public int getType() {
        return type;
    }

    public String getFilename() {
        return filename;
    }

    public String getMd5code() {
        return md5code;
    }

    public long getCheckTime() {
        return checkTime;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaInfo)) {
            return false;
        }
        MediaInfo mediaInfo = (MediaInfo) obj;
        return this.type == mediaInfo.getType()
                && this.getFilename().equals(mediaInfo.getFilename())
                && this.getMd5code().equals(mediaInfo.getMd5code());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getFilename(), getMd5code(), getCheckTime());
    }

    @Override
    public String toString() {
        return "MediaInfo{"
                + "type=" + type
                + ", filename='" + filename + '\''
                + ", md5code='" + md5code + '\''
                + ", checkTime=" + checkTime
                + '}';
    }
}
