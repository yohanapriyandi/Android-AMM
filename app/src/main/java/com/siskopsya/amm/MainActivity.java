package com.siskopsya.amm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.siskopsya.amm.adapter.MenuAdapter;
import com.siskopsya.amm.tools.BottomSheetFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> judulList, saldoList, gambarList;
    //Dialog dialog;
    ProgressDialog pDialog;
    //TextView totalD, totalR, tidak;
    String txtJudul, txtSaldo, txtGambar, no_anggota, nama_lengkap, db;
    boolean doubleBackToExitPressedOnce = false;
    TextView txt_logout, nama;
    SharedPreferences sharedpreferences;
    LinearLayout ly_setting, ly_reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences("siskopsya", Context.MODE_PRIVATE);
        no_anggota = sharedpreferences.getString("no_anggota", null);
        nama_lengkap= sharedpreferences.getString("nama_lengkap", null);
        db=sharedpreferences.getString("db", null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data ....");
        pDialog.show();
        getMenuList(no_anggota);
        txt_logout=findViewById(R.id.txt_logout);
        ly_setting=findViewById(R.id.btn_setting);
        ly_reload = findViewById(R.id.btn_refresh);
        nama = findViewById(R.id.txt_nama);
        nama.setText("Assalamu'alaikum "+nama_lengkap);

        BottomSheetFragment btomSheet =
                BottomSheetFragment.newInstance().newInstance();
        ly_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setCancelable(false);
                pDialog.setMessage("Memuat data ....");
                pDialog.show();
                getMenuList(no_anggota);
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("session_status", false);
                editor.commit();
                Toast.makeText(getApplicationContext(),
                        "Berhasil Logout ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("CEK_LOGIN", "baru");
                finish();
                startActivity(intent);
            }
        });

        ly_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                //finish();
                //startActivity(intent);
                btomSheet.show(getSupportFragmentManager(),
                        "add_photo_dialog_fragment");
            }
        });

    }

    private void getMenuList(String no_anggotae){
        //String url_provinsi="https://yayasansehatmadanielarbah.com/api-siskopsya/menulist.php?auth=c2lza29wc3lhOnNpc2tvcHN5YTEyMw==&&no_anggota="+no_anggotae;
        String url_provinsi="https://yayasansehatmadanielarbah.com/api-siskopsya/menulist.php?auth=c2lza29wc3lhOnNpc2tvcHN5YTEyMw==&&no_anggota="+no_anggotae+"&&db="+db;
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        Log.wtf("URL Called", url_provinsi + "");
        judulList = new ArrayList<>();
        saldoList =  new ArrayList<>();
        gambarList = new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_provinsi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(MainActivity.class.getSimpleName(), "Register Response: " + response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jArray = jsonObject.getJSONArray("content");
                    JSONArray jTotal = jsonObject.getJSONArray("total");
                    for(int i=0;i<jArray.length();i++){
                        JSONObject jsonObject1=jArray.getJSONObject(i);
                        txtJudul =jsonObject1.getString("judul");
                        txtSaldo =jsonObject1.getString("saldo");
                        txtGambar = jsonObject1.getString("gambar");
                        judulList.add(txtJudul);
                        saldoList.add(txtSaldo);
                        gambarList.add(txtGambar);
                    }
                    for(int m=0;m<jTotal.length();m++) {
                        JSONObject getTotal = jTotal.getJSONObject(m);
                        //txtTotalD = getTotal.getString("total_diagnosa");
                    }
                    //totalD.setText(txtTotalD);
                    RecyclerView menuView = (RecyclerView) findViewById(R.id.rc_menu);
                    MenuAdapter menuData = new MenuAdapter( judulList, saldoList,
                            gambarList, MainActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this,1);
                    menuView.setLayoutManager(mLayoutManager);
                    menuView.setAdapter(menuData);
                    if(pDialog.isShowing()){
                        pDialog.dismiss();
                    }
                }catch (JSONException e){e.printStackTrace(); }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(pDialog.isShowing()){
                    pDialog.dismiss();
                }
                Toast.makeText(MainActivity.this, "Silahkan coba lagi", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
