package com.siskopsya.amm.additional;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.siskopsya.amm.LoginActivity;
import com.siskopsya.amm.MainActivity;
import com.siskopsya.amm.R;
import com.siskopsya.amm.app.AppController;
import com.siskopsya.amm.menu.HutangMurobahahActivity;
import com.siskopsya.amm.server.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WithdrawActivity extends AppCompatActivity {
    String tNoAnggota, tNamaAnggota,no_anggota,db, tTotalSaldo, DB;
    TextView noAnggota, namaAnggota, totalSaldo;
    EditText jumlah;
    CardView btnSubmit;
    Spinner spnRek;
    SharedPreferences sharedpreferences;
    ProgressDialog pDialog, pDialog2;
    ArrayList<String> noRekList, saldoList;
    DecimalFormatSymbols symbols;
    DecimalFormat decimalFormat;
    String url = Url.URL + "saldo/withdrawReq.php";
    int success, posisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Layanan PPOB");
        sharedpreferences = getSharedPreferences("siskopsya", Context.MODE_PRIVATE);
        no_anggota = sharedpreferences.getString("no_anggota", null);
        noAnggota = findViewById(R.id.no_anggota);
        db=sharedpreferences.getString("db", null);
        namaAnggota = findViewById(R.id.nama_anggota);
        btnSubmit = findViewById(R.id.btn_wd);
        jumlah = findViewById(R.id.nwd);
        totalSaldo = findViewById(R.id.swd);
        spnRek = findViewById(R.id.spinner_no_rek);

        noRekList = new ArrayList<>();
        saldoList = new ArrayList<>();
        noRekList.add("Pilih No Rek");
        saldoList.add("0");

        DB = sharedpreferences.getString("db",null);
        pDialog = new ProgressDialog(WithdrawActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data ....");
        pDialog.show();

        symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        decimalFormat = new DecimalFormat("Rp #,###", symbols);

        getTabungan();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noAng =noAnggota.getText().toString();
                String noRek = noRekList.get(posisi);
                Integer Saldo = Integer.valueOf(saldoList.get(posisi));
                Integer Req = Integer.valueOf(jumlah.getText().toString());
                if(Req>Saldo){
                    Toast.makeText(WithdrawActivity.this, "Saldo Tidak Mencukupi", Toast.LENGTH_LONG).show();
                }else{
                    if(Req<50000){
                        Toast.makeText(WithdrawActivity.this, "Minimal Withdraw Rp. 50.000,00", Toast.LENGTH_LONG).show();
                    }else{
                        sendWD(noAng, noRek, String.valueOf(Req),db);
                    }
                }

            }
        });

        spnRek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(HutangMurobahahActivity.this, "Silahkan"+position, Toast.LENGTH_LONG).show();
                if(position>0){
                    posisi=position;
                    Integer ssaldo = Integer.valueOf(saldoList.get(position));
                    totalSaldo.setText(decimalFormat.format(ssaldo));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getTabungan(){
        final String urll ="https://yayasansehatmadanielarbah.com/api-siskopsya/saldo/withdraw.php?auth=c2lza29wc3lhOnNpc2tvcHN5YTEyMw==&&no_anggota="+no_anggota+"&&db="+db;
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        Log.wtf("URL Called", urll + "");
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(MainActivity.class.getSimpleName(), "Auth Response: " +urll+ response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jArray = jsonObject.getJSONArray("content");
                    JSONArray jNoAkad = jsonObject.getJSONArray("norek");
                    if(jsonObject.getString("data").equals("no data")){
                        //lyNoData.setVisibility(View.VISIBLE);
                        //lyData.setVisibility(View.GONE);
                        if(pDialog.isShowing()){
                            pDialog.dismiss();
                        }
                    }else{
                        for(int a=0;a<jNoAkad.length();a++){
                            JSONObject oNoAkad=jNoAkad.getJSONObject(a);
                            noRekList.add( oNoAkad.getString("rekening"));
                            saldoList.add(oNoAkad.getString("saldo"));
                        }
                        for(int i=0;i<jArray.length();i++){
                            JSONObject jsonObject1=jArray.getJSONObject(i);
                            tNoAnggota =jsonObject1.getString("no_anggota");
                            tNamaAnggota =jsonObject1.getString("nama_anggota");
                        }

                        noAnggota.setText(tNoAnggota);
                        namaAnggota.setText(tNamaAnggota);
                        totalSaldo.setText(decimalFormat.format(Integer.parseInt(saldoList.get(0))));
                        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                WithdrawActivity.this,R.layout.support_simple_spinner_dropdown_item,noRekList){
                            @Override
                            public boolean isEnabled(int position){
                                if(position == 0)
                                {
                                    // Disable the second item from Spinner
                                    return false;
                                }
                                else
                                {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if(position==1) {
                                    // Set the disable item text color
                                    tv.setTextColor(Color.GRAY);
                                }
                                else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        // attaching data adapter to spinner
                        spnRek.setAdapter(spinnerArrayAdapter);
                        if(pDialog.isShowing()){
                            pDialog.dismiss();
                        }
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
                Toast.makeText(WithdrawActivity.this, "Silahkan coba lagi", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void sendWD(final String noAnggota, final String noRek, final String jumlah, final String Db) {
        pDialog = new ProgressDialog(WithdrawActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Mengirim Request ...");
        pDialog.show();
        Log.e("WD","Request Response: "+url);
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("WD","Login Response string: " + response.toString());
                pDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt("success");
                    String no_anggota = jObj.getString("no_anggota");
                    String nama_lengkap = jObj.getString("nama_lengkap");
                    // Check for error node in json
                    if (success == 1) {
                        Log.e("Successfully Request!", jObj.toString());
                        Toast.makeText(getApplicationContext(),
                                "Requset Berhasil Dibuat", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(WithdrawActivity.this, TransaksiActivity.class);
                        finish();
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    Log.wtf("WD", e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("WD", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("no_anggota", noAnggota);
                params.put("no_rek", noRek);
                params.put("jumlah", jumlah);
                params.put("db", Db);
                params.put("auth","c2lza29wc3lhOnNpc2tvcHN5YTEyMw==");
                Log.d("SEND", "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WithdrawActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
