package com.siskopsya.amm.APIppob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopupResult {
    @SerializedName("ref_id")
    @Expose
    private String refId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("hp")
    @Expose
    private String hp;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("tr_id")
    @Expose
    private Integer trId;
    @SerializedName("rc")
    @Expose
    private String rc;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public TopupResult withRefId(String refId) {
        this.refId = refId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TopupResult withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TopupResult withCode(String code) {
        this.code = code;
        return this;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public TopupResult withHp(String hp) {
        this.hp = hp;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public TopupResult withPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TopupResult withMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public TopupResult withBalance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public Integer getTrId() {
        return trId;
    }

    public void setTrId(Integer trId) {
        this.trId = trId;
    }

    public TopupResult withTrId(Integer trId) {
        this.trId = trId;
        return this;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public TopupResult withRc(String rc) {
        this.rc = rc;
        return this;
    }

}
