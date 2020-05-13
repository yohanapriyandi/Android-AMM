package com.siskopsya.amm.menu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.siskopsya.amm.MainActivity;
import com.siskopsya.amm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

public class InvestasiActivity extends AppCompatActivity {
    private ArrayList<String> kodeProjekList,  namaProjectList, akadAwalList, nisbahInvestorList, nisbahMudhoribList,
    nilaiProjekList, porsiModalList, tahunTemp
            ,bulanList, omsetList, labaList, biayaList, nisbahList;
    private ArrayList<ArrayList> tahunList;
    //Dialog dialog;
    ProgressDialog pDialog, pDialog2;
    //TextView totalD, totalR, tidak;
    String tKodeProjek, tNamaProject, tAkadaAwal, tNilaiProjek, tPorsiModal, no_anggota,
            tTahun, tBulan,
            tOmset, tBiaya, tLaba, tNisbah,
            tOmsetSum, tBiayaSum, tLabaSum, tNisbahSum
            , DB;
    TextView  AkadaAwal, NilaiProjek, PorsiModal,
            Omset, Biaya, Laba, Nisbah,
            OmsetSum, BiayaSum, LabaSum, NisbahSum,
            investor, mudhorib, namaProject;
    Spinner spnTahun, spnBulan,KodeProjek;
    //LinearLayout lyNoData, lyData;
    SharedPreferences sharedpreferences;
    Integer tahun=0, jumlahProjek=0;
    DecimalFormatSymbols symbols;
    DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investasi);
        sharedpreferences = getSharedPreferences("siskopsya", Context.MODE_PRIVATE);
        no_anggota = sharedpreferences.getString("no_anggota", null);
        DB = sharedpreferences.getString("db",null);
        KodeProjek = findViewById(R.id.code_project);
        AkadaAwal = findViewById(R.id.akad_awal);
        NilaiProjek = findViewById(R.id.nilai_project);
        PorsiModal = findViewById(R.id.porsi_modal);
        Omset = findViewById(R.id.omset);
        Biaya = findViewById(R.id.biaya);
        Laba = findViewById(R.id.laba);
        Nisbah = findViewById(R.id.nisbah_sum);
        OmsetSum = findViewById(R.id.omset_sum);
        BiayaSum = findViewById(R.id.biaya_sum);
        LabaSum = findViewById(R.id.laba_sum);
        NisbahSum = findViewById(R.id.nisbah_sum);
        investor = findViewById(R.id.investor);
        mudhorib = findViewById(R.id.mudhorib);
        namaProject = findViewById(R.id.nama_project);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Investasi");
        pDialog = new ProgressDialog(InvestasiActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data ....");
        pDialog.show();
        getTahunList();
        // spinner kode projek -----------
        kodeProjekList = new ArrayList<>();
        namaProjectList= new ArrayList<>();
        akadAwalList = new ArrayList<>();
        nilaiProjekList = new ArrayList<>();
        porsiModalList = new ArrayList<>();
        tahunList = new ArrayList<>();
        ArrayList tahuntempAwal = new ArrayList();
        tahuntempAwal.add("Pilih Tahun");
        tahunList.add(tahuntempAwal);
        kodeProjekList.add("Tidak ada projek terpilih");
        namaProjectList.add("-");
        akadAwalList.add("-");
        nilaiProjekList.add("0");
        porsiModalList.add("0");
        bulanList = new ArrayList<>();
        omsetList = new ArrayList<>();
        labaList = new ArrayList<>();
        biayaList= new ArrayList<>();
        nisbahList = new ArrayList<>();
        bulanList.add("Pilih Bulan");
        omsetList.add("0");
        labaList.add("0");
        biayaList.add("0");
        nisbahList.add("0");
        nisbahMudhoribList= new ArrayList<>();
        nisbahInvestorList= new ArrayList<>();
        nisbahMudhoribList.add("0");
        nisbahInvestorList.add("0");
        //format harga
        symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        decimalFormat = new DecimalFormat("Rp #,###", symbols);
        spnTahun = findViewById(R.id.tahun_bagi);
        spnBulan = findViewById(R.id.bulan);
        spnTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(HutangMurobahahActivity.this, "Silahkan"+position, Toast.LENGTH_LONG).show();
                if(position>0){
                    tahun=1;
                    if(tahun>0){
                        String strTahun = spnTahun.getSelectedItem().toString();
                        pDialog2 = new ProgressDialog(InvestasiActivity.this);
                        pDialog2.setCancelable(false);
                        pDialog2.setMessage("Memuat data project....");
                        pDialog2.show();
                        getSaldoList(strTahun, "000", tKodeProjek, no_anggota);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Integer omset = Integer.valueOf(omsetList.get(position));
                    Integer biaya= Integer.valueOf(biayaList.get(position));
                    Integer laba = Integer.valueOf(labaList.get(position));
                    Integer nisbah = Integer.valueOf(nisbahList.get(position));
                    Omset.setText(decimalFormat.format(omset));
                    Biaya.setText(decimalFormat.format(biaya));
                    Laba.setText(decimalFormat.format(laba));
                    Nisbah.setText(decimalFormat.format(nisbah));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        KodeProjek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                namaProject.setText(namaProjectList.get(position));
                AkadaAwal.setText(akadAwalList.get(position));
                NilaiProjek.setText(decimalFormat.format(Integer.parseInt(nilaiProjekList.get(position))));
                PorsiModal.setText(porsiModalList.get(position)+"%");
                mudhorib.setText(nisbahMudhoribList.get(position)+"%");
                investor.setText(nisbahInvestorList.get(position)+"%");
                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        InvestasiActivity.this,R.layout.support_simple_spinner_dropdown_item,tahunList.get(position)){
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
                spnTahun.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    private void getTahunList(){
        final String urll ="https://yayasansehatmadanielarbah.com/api-siskopsya/saldo/investasi.php?auth=c2lza29wc3lhOnNpc2tvcHN5YTEyMw==&&no_anggota="+no_anggota+"&&db="+DB;
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
                    JSONArray jTotal = jsonObject.getJSONArray("total");
                    JSONArray jNoAkad = jsonObject.getJSONArray("tahun");
                    //JSONArray jKodeProjek = jsonObject.getJSONArray("projek");
                    if(jsonObject.getString("data").equals("no data")){
                        //lyNoData.setVisibility(View.VISIBLE);
                        //lyData.setVisibility(View.GONE);
                        if(pDialog.isShowing()){
                            pDialog.dismiss();
                        }
                    }else{
                        for(int i=0;i<jArray.length();i++){
                            JSONObject jsonObject1=jArray.getJSONObject(i);
                            tKodeProjek=jsonObject1.getString("kode_projek");
                            tAkadaAwal =jsonObject1.getString("akad_awal");
                            tNilaiProjek= jsonObject1.getString("nilai_projek");
                            tPorsiModal= jsonObject1.getString("porsi_modal");
                            kodeProjekList.add(tKodeProjek);
                            namaProjectList.add(jsonObject1.getString("nama_project"));
                            akadAwalList.add(tAkadaAwal);
                            nilaiProjekList.add(tNilaiProjek);
                            porsiModalList.add(tPorsiModal);
                            tahunTemp = new ArrayList<>();
                            nisbahMudhoribList.add(jsonObject1.getString("nisbah_mudhorib"));
                            nisbahInvestorList.add(jsonObject1.getString("nisbah_investor"));
                            tahunTemp.add("Pilh Tahun");
                            for(int a=0;a<jNoAkad.length();a++){
                                JSONObject oNoAkad=jNoAkad.getJSONObject(a);
                                tTahun = oNoAkad.getString("tahun");
                                tahunTemp.add(tTahun);
                            }
                        }
                        //lyNoData.setVisibility(View.GONE);
                        //lyData.setVisibility(View.VISIBLE);
                        //totalD.setText(txtTotalD);
                        //inisial format rupiah
                        String rpNilaiProjek = decimalFormat.format(Integer.parseInt(tNilaiProjek));
                        String rpPorsimodal = decimalFormat.format(Integer.parseInt(tPorsiModal));
                        //kode projekkk belum
                        final ArrayAdapter<String> projekArrayAdapter = new ArrayAdapter<String>(
                                InvestasiActivity.this,R.layout.support_simple_spinner_dropdown_item,kodeProjekList){
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
                        KodeProjek.setAdapter(projekArrayAdapter);
                        namaProject.setText(namaProjectList.get(0));
                        AkadaAwal.setText(akadAwalList.get(0));
                        NilaiProjek.setText(decimalFormat.format(Integer.parseInt(nilaiProjekList.get(0))));
                        PorsiModal.setText(decimalFormat.format(Integer.parseInt(porsiModalList.get(0))));
                        mudhorib.setText(nisbahMudhoribList.get(0)+"%");
                        investor.setText(nisbahInvestorList.get(0)+"%");
                        tahunList.add(tahunTemp);
                        // Creating adapter for spinner
                        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(HutangMurobahahActivity.this, android.R.layout.simple_spinner_item, noAkadList);
                        // Drop down layout style - list view with radio button
                        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Initializing an ArrayAdapter
                        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                InvestasiActivity.this,R.layout.support_simple_spinner_dropdown_item,tahunList.get(0)){
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
                        spnTahun.setAdapter(spinnerArrayAdapter);
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
                Toast.makeText(InvestasiActivity.this, "Silahkan coba lagi", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void getSaldoList(String tahunStr, String bulanStr, String kodeStr, String noAng){
        final String urlsaldo ="https://yayasansehatmadanielarbah.com/api-siskopsya/saldo/investasiSaldo.php?auth=c2lza29wc3lhOnNpc2tvcHN5YTEyMw==&&no_anggota="+no_anggota+"&&tahun="+tahunStr+"&&bulan="+bulanStr+"&&kode="+kodeStr+"&&no_anggota="+noAng+"&&db="+DB;
        RequestQueue requestQueue2= Volley.newRequestQueue(this);
        Log.wtf("URL Called", urlsaldo + "");
        StringRequest stringRequest2=new StringRequest(Request.Method.GET,
                urlsaldo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                Log.e(InvestasiActivity.class.getSimpleName(), "Auth Response: " +urlsaldo+ response2);
                try{
                    JSONObject jsonObject2=new JSONObject(response2);
                    JSONObject jsonObject12 = jsonObject2.getJSONObject("content");
                    JSONObject jSaldoObjek = jsonObject2.getJSONObject("total_saldo");
                    if(jsonObject2.getString("data").equals("no data")){
                        if(pDialog2.isShowing()){
                            pDialog2.dismiss();
                        }
                    }else{
                            tOmsetSum = String.valueOf(jSaldoObjek.getInt("omset_sum"));
                            tBiayaSum= String.valueOf(jSaldoObjek.getInt("biaya_sum"));
                            tLabaSum= String.valueOf(jSaldoObjek.getInt("laba_sum"));
                            tNisbahSum= jSaldoObjek.getString("nisbah_sum");
                            String arrBulan[] = jsonObject12.getString("nama_bulan").split("kk2020");
                            String arrOmset[] = jsonObject12.getString("omset").split("kk2020");
                            String arrBiaya[] = jsonObject12.getString("biaya").split("kk2020");
                            String arrLaba[] = jsonObject12.getString("laba").split("kk2020");
                            String arrNisbah[] = jsonObject12.getString("nisbah").split("kk2020");
                            for (int b=1; b<13;b++){
                                bulanList.add(arrBulan[b]);
                                omsetList.add(arrOmset[b]);
                                biayaList.add(arrBiaya[b]);
                                labaList.add(arrLaba[b]);
                                nisbahList.add(arrNisbah[b]);
                            }
                            tOmset =omsetList.get(0);
                            tBiaya=biayaList.get(0);
                            tLaba =labaList.get(0);
                            tNisbah= nisbahList.get(0);
                        //format harga
                        DecimalFormatSymbols symbolsz = new DecimalFormatSymbols();
                        symbolsz.setGroupingSeparator('.');
                        symbolsz.setDecimalSeparator(',');
                        DecimalFormat decimalFormat = new DecimalFormat("Rp #,###", symbolsz);
                        //inisial format rupiah
                        String rpOmset = decimalFormat.format(Integer.parseInt(tOmset));
                        String rpBiaya=decimalFormat.format(Integer.parseInt(tBiaya));
                        String rpLaba = decimalFormat.format(Integer.parseInt(tLaba));
                        String rpNisbah = decimalFormat.format(Integer.parseInt(tNisbah));
                        String rpOmsetSum = decimalFormat.format(Integer.parseInt(tOmsetSum));
                        String rpBiayaSum=decimalFormat.format(Integer.parseInt(tBiayaSum));
                        String rpLabaSum = decimalFormat.format(Integer.parseInt(tLabaSum));
                        String rpNisbahSum = decimalFormat.format(Integer.parseInt(tNisbahSum));
                        Omset.setText(rpOmset);
                        Biaya.setText(rpBiaya);
                        Laba.setText(rpLaba);
                        Nisbah.setText(rpNisbah);
                        OmsetSum.setText(rpOmsetSum);
                        BiayaSum.setText(rpBiayaSum);
                        LabaSum.setText(rpLabaSum);
                        NisbahSum.setText(rpNisbahSum);
                        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                InvestasiActivity.this,R.layout.support_simple_spinner_dropdown_item,bulanList){
                        };
                        // attaching data adapter to spinner
                        spnBulan.setAdapter(spinnerArrayAdapter);
                        if(pDialog2.isShowing()){
                            pDialog2.dismiss();
                        }
                    }

                }catch (JSONException e){e.printStackTrace(); }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(pDialog2.isShowing()){
                    pDialog2.dismiss();
                }
                Toast.makeText(InvestasiActivity.this, "Silahkan coba lagi", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        int socketTimeout2 = 30000;
        RetryPolicy policy2 = new DefaultRetryPolicy(socketTimeout2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest2.setRetryPolicy(policy2);
        requestQueue2.add(stringRequest2);
    }
}
