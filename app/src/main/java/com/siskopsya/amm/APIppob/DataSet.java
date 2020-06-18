package com.siskopsya.amm.APIppob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSet {
    @SerializedName("commands")
    String commands;
    @SerializedName("username")
    String username;
    @SerializedName("sign")
    String sign;
    @SerializedName("status")
    String status;

    public DataSet(String commands, String username, String sign, String status){
        this.commands=commands;
        this.username=username;
        this.sign=sign;
        this.status=status;
    }
}
