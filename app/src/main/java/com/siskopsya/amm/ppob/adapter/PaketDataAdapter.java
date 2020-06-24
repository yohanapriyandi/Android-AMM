package com.siskopsya.amm.ppob.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siskopsya.amm.APIppob.ApiService;
import com.siskopsya.amm.APIppob.Const;
import com.siskopsya.amm.APIppob.DataResult;
import com.siskopsya.amm.APIppob.DataResultList;
import com.siskopsya.amm.APIppob.DataSet;
import com.siskopsya.amm.APIppob.TopupPulsaApiService;
import com.siskopsya.amm.APIppob.TopupResult;
import com.siskopsya.amm.APIppob.TopupResultList;
import com.siskopsya.amm.APIppob.TopupSet;
import com.siskopsya.amm.R;
import com.siskopsya.amm.menu.HutangMurobahahActivity;
import com.siskopsya.amm.menu.HutangQordhulhasanActivity;
import com.siskopsya.amm.menu.InvestasiActivity;
import com.siskopsya.amm.menu.SHUActivity;
import com.siskopsya.amm.menu.SimpananActivity;
import com.siskopsya.amm.menu.TabunganActivity;
import com.siskopsya.amm.ppob.DataActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaketDataAdapter extends RecyclerView.Adapter<PaketDataAdapter.MyViewHolder> {
    private ArrayList<DataResult> dataList;
    private String tanggal ;
    private Activity mActivity;
    Intent intent;
    String dx;
    Retrofit retrofit;
    private int lastPosition = -1;

    public PaketDataAdapter(ArrayList<DataResult> dataList) {
        this.dataList=dataList;
        //this.retrofit=retrofit;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul, txtSaldo, txt_masa_aktif;
        CardView cardMenu;
        public MyViewHolder(View view) {
            super(view);
            txtJudul = (TextView) view.findViewById(R.id.txt_judul);
            txtSaldo = (TextView) view.findViewById(R.id.txt_harga);

            cardMenu = view.findViewById(R.id.card_menu);
            txt_masa_aktif = view.findViewById(R.id.txt_masa_aktif);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paket_data_list, parent, false);
        return new MyViewHolder(itemView);
    }
    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //format harga
        final DataResult datanya = dataList.get(position);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        initializeRetrofit();
        DecimalFormat decimalFormat = new DecimalFormat("Rp #,###", symbols);
        String prezzo = decimalFormat.format(datanya.getPulsaPrice());
        holder.txt_masa_aktif.setText("Masa aktif: "+datanya.getMasaaktif());
        holder.txtJudul.setText(datanya.getPulsaNominal());
        holder.txtSaldo.setText(prezzo);
        holder.cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = dataList.get(position).toString();
                //postMessage();
                TopupSet topupSet = new TopupSet("topup","087868690707",
                        "order001","0817777215","xld25000","d5a09d1af94f36767e348a10ff432f81");
                TopupPulsaApiService apiService = retrofit.create(TopupPulsaApiService.class);
                Call<TopupResultList> result = apiService.createDataSet(topupSet, "https://testprepaid.mobilepulsa.net/v1/legacy/index");
                result.enqueue(new Callback<TopupResultList>() {
                    @Override
                    public void onResponse(Call<TopupResultList> call, retrofit2.Response<TopupResultList> response) {
                        TopupResult dataResultLists = response.body().getData();
                        System.out.println("Resrpone klik"+response.body()+" hitung"+ dataResultLists.getPrice());;
                    }
                    @Override
                    public void onFailure(Call<TopupResultList> call, Throwable t) {
                        System.out.println("Resrpone gagal"+t+" hitung"+ call);;
                    }
                });
                System.out.println("clik");;
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}