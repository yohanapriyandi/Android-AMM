package com.siskopsya.amm.APIppob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResult {
    @SerializedName("pulsa_code")
    @Expose
    private String pulsaCode;
    @SerializedName("pulsa_op")
    @Expose
    private String pulsaOp;
    @SerializedName("pulsa_nominal")
    @Expose
    private String pulsaNominal;
    @SerializedName("pulsa_price")
    @Expose
    private Integer pulsaPrice;
    @SerializedName("pulsa_type")
    @Expose
    private String pulsaType;
    @SerializedName("masaaktif")
    @Expose
    private String masaaktif;
    @SerializedName("status")
    String status;
    public String getPulsaCode() {
        return pulsaCode;
    }

    public void setPulsaCode(String pulsaCode) {
        this.pulsaCode = pulsaCode;
    }

    public String getPulsaOp() {
        return pulsaOp;
    }

    public void setPulsaOp(String pulsaOp) {
        this.pulsaOp = pulsaOp;
    }

    public String getPulsaNominal() {
        return pulsaNominal;
    }

    public void setPulsaNominal(String pulsaNominal) {
        this.pulsaNominal = pulsaNominal;
    }

    public Integer getPulsaPrice() {
        return pulsaPrice;
    }

    public void setPulsaPrice(Integer pulsaPrice) {
        this.pulsaPrice = pulsaPrice;
    }

    public String getPulsaType() {
        return pulsaType;
    }

    public void setPulsaType(String pulsaType) {
        this.pulsaType = pulsaType;
    }

    public String getMasaaktif() {
        return masaaktif;
    }

    public void setMasaaktif(String masaaktif) {
        this.masaaktif = masaaktif;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
