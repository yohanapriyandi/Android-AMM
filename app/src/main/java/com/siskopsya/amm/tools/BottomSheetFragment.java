package com.siskopsya.amm.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.siskopsya.amm.R;
import com.siskopsya.amm.additional.PPOBActivity;
import com.siskopsya.amm.additional.ProfileActivity;
import com.siskopsya.amm.additional.SettingActivity;
import com.siskopsya.amm.additional.TransaksiActivity;
import com.siskopsya.amm.additional.WithdrawActivity;

public class BottomSheetFragment extends BottomSheetDialogFragment{

    LinearLayout lypr, lywd,lytr, lyst, lypp;
    public static BottomSheetFragment newInstance() {
        return new BottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.bottom_sheet, container,
                false);
        lypr = v.findViewById(R.id.bst_profile);
        lywd = v.findViewById(R.id.bst_withdraw);
        lytr= v.findViewById(R.id.bst_transaksi);
        lyst = v.findViewById(R.id.bst_setting);
        lypp = v.findViewById(R.id.bst_ppob);
        // get the views and attach the listener

        lypr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik(v, "profile");
            }
        });
        lywd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik(v, "withdraw");
            }
        });
        lytr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik(v, "transaksi");
            }
        });
        lyst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik(v, "setting");
            }
        });

        lypp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik(v, "ppob");
            }
        });
        return v;

    }

    public void klik(View v, String kode){
        if(kode.equals("profile")){
            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
            startActivity(intent);
        }else if(kode.equals("withdraw")){
            Intent intent = new Intent(v.getContext(), WithdrawActivity.class);
            startActivity(intent);
        }
        else if(kode.equals("transaksi")){
            Intent intent = new Intent(v.getContext(), TransaksiActivity.class);
            startActivity(intent);
        }
        else if(kode.equals("setting")){
            Intent intent = new Intent(v.getContext(), SettingActivity.class);
            startActivity(intent);
        }
        else if(kode.equals("ppob")){
            Intent intent = new Intent(v.getContext(), PPOBActivity.class);
            startActivity(intent);
        }
    }
}