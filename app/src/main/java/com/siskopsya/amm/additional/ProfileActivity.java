package com.siskopsya.amm.additional;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.siskopsya.amm.MainActivity;
import com.siskopsya.amm.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView tnamaP, tAlamatP;
    private CardView btn_pass;
    SharedPreferences sharedpreferences;
    private  String nama, alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        tnamaP = findViewById(R.id.nama_profile);
        tAlamatP = findViewById(R.id.alamat_profile);
        btn_pass = findViewById(R.id.ganti_password);
        sharedpreferences = getSharedPreferences("siskopsya", Context.MODE_PRIVATE);
        nama = sharedpreferences.getString("nama_lengkap", null);
        alamat = sharedpreferences.getString("alamat", null);

        tnamaP.setText(nama);
        tAlamatP.setText(alamat);
        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
