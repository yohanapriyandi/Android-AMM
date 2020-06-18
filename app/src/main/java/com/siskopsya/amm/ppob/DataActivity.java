package com.siskopsya.amm.ppob;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.siskopsya.amm.APIppob.ApiService;
import com.siskopsya.amm.APIppob.Const;
import com.siskopsya.amm.APIppob.DataResult;
import com.siskopsya.amm.APIppob.DataResultList;
import com.siskopsya.amm.APIppob.DataSet;
import com.siskopsya.amm.R;
import com.siskopsya.amm.additional.PPOBActivity;
import com.siskopsya.amm.ppob.adapter.PaketDataAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DataActivity extends AppCompatActivity {
    private String TAG = "DataActivity";
    private String urlUtama = "";
    private EditText nomor;
    private  Integer cek =0, hitung=0;
    private Retrofit retrofit;
    private ProgressDialog dialog;
    ArrayList<String> noSeluler, opSeluler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Paket Data");
        nomor=findViewById(R.id.nomor_input);
        setupTabLayout();
        initializeRetrofit();
        opSeluler = new ArrayList<String>();
        noSeluler = new ArrayList<String>();
        initialOperator();
        nomor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "count "+String.valueOf(count)+" char "+s+" start "+start+" beforde "+before);
                if(s.length()>8){
                    //call request harga
                    cek=1;
                    if(cek>0){
                        postMessage();
                        //cekHarga("data","axis_paket_internet");
                        cek=0;
                    }
                }else{
                    cek=0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Loading");
    }

    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private void postMessage(){
        //params.put("Accept","application/json");
        //params.put("Content-Type","application/json");
        DataSet dataSet = new DataSet("pricelist","087868690707",
                "09c6ace28e672941cb74d1b22eb645aa","all");
        ApiService apiService = retrofit.create(ApiService.class);
        Call<DataResultList> result = apiService.createDataSet(dataSet, "https://testprepaid.mobilepulsa.net/v1/legacy/index/data/axis_paket_internet");
        result.enqueue(new Callback<DataResultList>() {
            @Override
            public void onResponse(Call<DataResultList> call, retrofit2.Response<DataResultList> response) {
                ArrayList<DataResult> dataResultLists = response.body().getData();
                System.out.println("Resrpone topup"+response.body()+" hitung"+ dataResultLists.size());;
                RecyclerView menuView = (RecyclerView) findViewById(R.id.rc_list);
                PaketDataAdapter paketDataAdapter = new PaketDataAdapter(dataResultLists);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(DataActivity.this,1);
                menuView.setLayoutManager(mLayoutManager);
                menuView.setAdapter(paketDataAdapter);
            }
            @Override
            public void onFailure(Call<DataResultList> call, Throwable t) {
                System.out.println("Resrpone gagal"+t+" hitung"+ call);;
            }
        });
        System.out.println("clik");;
    }
    private void setupTabLayout(){
        TabLayout mTabLayout = findViewById(R.id.tab);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabTapped(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabTapped(tab.getPosition());
            }
        });
    }
    private void onTabTapped(int position){
        switch (position){
            case 0:
                //first tab click
                break;
            default:
                Toast.makeText(this, "Tapped " + position, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataActivity.this, PPOBActivity.class);
        startActivity(intent);
        finish();
    }
    public void initialOperator(){
        //XL
        noSeluler.add("0859");
        noSeluler.add("0877");
        noSeluler.add("0878");
        noSeluler.add("0817");
        noSeluler.add("0818");
        noSeluler.add("0819");
        for(int xl=0;xl<6;xl++){
            opSeluler.add("XL");
        }
        //TELKOMSEL
        noSeluler.add("0811");
        noSeluler.add("0812");
        noSeluler.add("0183");
        noSeluler.add("0821");
        noSeluler.add("0822");
        noSeluler.add("0823");
        noSeluler.add("0852");
        noSeluler.add("0853");
        noSeluler.add("0851");
        for(int tsel=0;tsel<9;tsel++){
            opSeluler.add("TELKOMSEL");
        }
        //TRI
        noSeluler.add("0898");
        noSeluler.add("0899");
        noSeluler.add("0895");
        noSeluler.add("0896");
        noSeluler.add("0897");
        for(int tri=0;tri<5;tri++){
            opSeluler.add("TRI");
        }
        //INDOSAT OREDOO
        noSeluler.add("0814");
        noSeluler.add("0815");
        noSeluler.add("0816");
        noSeluler.add("0855");
        noSeluler.add("0856");
        noSeluler.add("0857");
        noSeluler.add("0858");
        for(int indo=0;indo<7;indo++){
            opSeluler.add("INDOSAT");
        }
        //SMARTFREN
        noSeluler.add("0889");
        noSeluler.add("0881");
        noSeluler.add("0882");
        noSeluler.add("0883");
        noSeluler.add("0886");
        noSeluler.add("0887");
        noSeluler.add("0888");
        noSeluler.add("0884");
        noSeluler.add("0885");
        for(int smart=0;smart<9;smart++){
            opSeluler.add("SMARTFREN");
        }
        //AXIS
        noSeluler.add("0832");
        noSeluler.add("0833");
        noSeluler.add("0838");
        noSeluler.add("0831");
        for(int axis=0;axis<4;axis++){
            opSeluler.add("AXIS");
        }
    }
    public String cekOp(String nomor){
        int posNomor = noSeluler.indexOf(nomor);
        String operator = opSeluler.get(posNomor);
        return operator;
    }
}
