package com.siskopsya.amm.APIppob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopupResultList {
    @SerializedName("data")
    @Expose
    private TopupResult data;

    public TopupResult getData() {
        return data;
    }

    public void setData(TopupResult data) {
        this.data = data;
    }

    public TopupResultList withData(TopupResult data) {
        this.data = data;
        return this;
    }
}
