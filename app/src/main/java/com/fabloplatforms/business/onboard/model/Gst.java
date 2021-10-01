package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gst {

    @SerializedName("is_available")
    @Expose
    private Boolean isAvailable;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("file_type")
    @Expose
    private String fileType;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
