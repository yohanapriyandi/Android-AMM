package com.siskopsya.amm.APIppob;

import com.google.gson.annotations.SerializedName;

public class TopupSet {
    @SerializedName("commands")
    String commands;
    @SerializedName("username")
    String username;
    @SerializedName("ref_id")
    String ref_id;
    @SerializedName("hp")
    String hp;
    @SerializedName("pulsa_code")
    String pulsa_code;
    @SerializedName("sign")
    String sign;

    public TopupSet(String commands, String username, String ref_id, String hp, String pulsa_code, String sign){
        this.commands=commands;
        this.username=username;
        this.ref_id=ref_id;
        this.hp = hp;
        this.pulsa_code=pulsa_code;
        this.sign=sign;
    }
}
