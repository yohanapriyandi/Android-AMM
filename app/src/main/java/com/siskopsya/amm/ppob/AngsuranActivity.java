package com.siskopsya.amm.ppob;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.siskopsya.amm.MainActivity;
import com.siskopsya.amm.R;
import com.siskopsya.amm.additional.PPOBActivity;

public class AngsuranActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angsuran);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pembayaran Angsuran");
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AngsuranActivity.this, PPOBActivity.class);
        startActivity(intent);
        finish();
    }
}
