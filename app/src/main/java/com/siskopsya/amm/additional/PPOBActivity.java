package com.siskopsya.amm.additional;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siskopsya.amm.MainActivity;
import com.siskopsya.amm.R;
import com.siskopsya.amm.ppob.AngsuranActivity;
import com.siskopsya.amm.ppob.BpjsActivity;
import com.siskopsya.amm.ppob.DataActivity;
import com.siskopsya.amm.ppob.EmoneyActivity;
import com.siskopsya.amm.ppob.GameActivity;
import com.siskopsya.amm.ppob.InternetActivity;
import com.siskopsya.amm.ppob.PdamActivity;
import com.siskopsya.amm.ppob.PgnActivity;
import com.siskopsya.amm.ppob.PlnActivity;
import com.siskopsya.amm.ppob.PulsaActivity;
import com.siskopsya.amm.ppob.PulsaPascaActivity;
import com.siskopsya.amm.ppob.RiwayatActivity;
import com.siskopsya.amm.ppob.TelponActivity;
import com.siskopsya.amm.ppob.TokenActivity;
import com.siskopsya.amm.ppob.TvActivity;
import com.siskopsya.amm.ppob.VoucherActivity;

public class PPOBActivity extends AppCompatActivity {

    private LinearLayout data, pulsa, token,
            telpon, pln, pulsapasca, internet, pdam, tv, pgn, bpjs,
            angsuran, emoney, voucher, game;
    private CardView topup, home, riwayat;
    private TextView saldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppob);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Layanan PPOB");

        data = findViewById(R.id.data);
        pulsa = findViewById(R.id.pulsa);
        token = findViewById(R.id.token);

        telpon = findViewById(R.id.telpon);
        pln = findViewById(R.id.pln);
        pulsapasca = findViewById(R.id.pulsa_pasca);
        internet = findViewById(R.id.internet);
        pdam = findViewById(R.id.pdam);
        tv = findViewById(R.id.tv);
        pgn = findViewById(R.id.pgn);
        bpjs = findViewById(R.id.bpjs);

        angsuran = findViewById(R.id.angsuran);
        emoney = findViewById(R.id.emoney);
        voucher = findViewById(R.id.voucher);
        game = findViewById(R.id.game);

        topup = findViewById(R.id.topup);
        home = findViewById(R.id.home);
        riwayat = findViewById(R.id.history);

        saldo = findViewById(R.id.saldo);

        //---------------------------------onclick------------------
        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, RiwayatActivity.class);
                startActivity(intent);
            }
        });

        //Prabayar
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });
        pulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, PulsaActivity.class);
                startActivity(intent);
            }
        });
        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, TokenActivity.class);
                startActivity(intent);
            }
        });

        //Tagihan
        telpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, TelponActivity.class);
                startActivity(intent);
            }
        });
        pln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, PlnActivity.class);
                startActivity(intent);
            }
        });
        pulsapasca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, PulsaPascaActivity.class);
                startActivity(intent);
            }
        });
        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, InternetActivity.class);
                startActivity(intent);
            }
        });
        pdam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, PdamActivity.class);
                startActivity(intent);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, TvActivity.class);
                startActivity(intent);
            }
        });
        pgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, PgnActivity.class);
                startActivity(intent);
            }
        });
        bpjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, BpjsActivity.class);
                startActivity(intent);
            }
        });

        //lainnya
        angsuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, AngsuranActivity.class);
                startActivity(intent);
            }
        });
        emoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, EmoneyActivity.class);
                startActivity(intent);
            }
        });
        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, VoucherActivity.class);
                startActivity(intent);
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPOBActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        // ------ end Click ---------------------------------

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
