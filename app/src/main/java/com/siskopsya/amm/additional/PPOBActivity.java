package com.siskopsya.amm.additional;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.siskopsya.amm.MainActivity;
import com.siskopsya.amm.R;

public class PPOBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppob);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Layanan PPOB");
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PPOBActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
