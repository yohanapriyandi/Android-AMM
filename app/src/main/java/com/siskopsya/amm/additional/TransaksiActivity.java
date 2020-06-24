package com.siskopsya.amm.additional;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.siskopsya.amm.MainActivity;
import com.siskopsya.amm.R;
import com.siskopsya.amm.adapter.MenuAdapter;
import com.siskopsya.amm.adapter.TransaksiAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransaksiActivity extends AppCompatActivity {

    private LinearLayout lAda, lNo;
    private ArrayList<String> statusList, noList, saldoList, tglList;
    private String no_anggota, db, status, no, saldo, tgl;
    //Dialog dialog;
    ProgressDialog pDialog;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Transaksi");

        sharedpreferences = getSharedPreferences("siskopsya", Context.MODE_PRIVATE);
        no_anggota = sharedpreferences.getString("no_anggota2", null);
        db=sharedpreferences.getString("db", null);
        lNo = findViewById(R.id.ly_no_data);

        pDialog = new ProgressDialog(TransaksiActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data ....");
        pDialog.show();
        getTransList(no_anggota);
    }

    private void getTransList(String no_anggotae){
        String url_provinsi="https://yayasansehatmadanielarbah.com/api-siskopsya/saldo/trans.php?auth=c2lza29wc3lhOnNpc2tvcHN5YTEyMw==&&no_anggota="+no_anggotae+"&&db="+db;
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        Log.wtf("URL Called", url_provinsi + "");
        statusList = new ArrayList<>();
        noList =  new ArrayList<>();
        saldoList = new ArrayList<>();
        tglList = new ArrayList<>();

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
                        status =jsonObject1.getString("status");
                        no =jsonObject1.getString("no");
                        saldo = jsonObject1.getString("saldo");
                        tgl = jsonObject1.getString("tgl");
                        statusList.add(status);
                        noList.add(no);
                        saldoList.add(saldo);
                        tglList.add(tgl);
                    }
                    for(int m=0;m<jTotal.length();m++) {
                        JSONObject getTotal = jTotal.getJSONObject(m);
                        //txtTotalD = getTotal.getString("total_diagnosa");
                    }
                    //totalD.setText(txtTotalD);
                    RecyclerView menuView = (RecyclerView) findViewById(R.id.rc_trans);
                    TransaksiAdapter menuData = new TransaksiAdapter( noList,statusList,statusList,tglList, saldoList, TransaksiActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(TransaksiActivity.this,1);
                    menuView.setLayoutManager(mLayoutManager);
                    menuView.setAdapter(menuData);
                    if(pDialog.isShowing()){
                        pDialog.dismiss();
                    }
                    if(statusList.size()>0){
                        lNo.setVisibility(View.GONE);
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
                Toast.makeText(TransaksiActivity.this, "Silahkan coba lagi", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TransaksiActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
