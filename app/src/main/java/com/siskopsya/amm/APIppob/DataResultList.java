package com.siskopsya.amm.APIppob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataResultList {
    @SerializedName("data")
    @Expose
    private ArrayList<DataResult> data = null;

    public ArrayList<DataResult> getData() {
        return data;
    }

    public void setData(ArrayList<DataResult> data) {
        this.data = data;
    }

    public DataResultList withData(ArrayList<DataResult> data) {
        this.data = data;
        return this;
    }
}
