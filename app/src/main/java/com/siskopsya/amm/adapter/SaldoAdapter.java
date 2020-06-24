package com.siskopsya.amm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.siskopsya.amm.R;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class SaldoAdapter extends RecyclerView.Adapter<SaldoAdapter.MyViewHolder> {

    private ArrayList<String>
            kodeSaldoList= new ArrayList<>(),
            saldoList= new ArrayList<>(),
            tglAkadList= new ArrayList<>(),
            tenorList = new ArrayList<>(),
            pinjamanList = new ArrayList<>(),
            bayarList  = new ArrayList<>(),
            sisaList = new ArrayList<>();

    private Activity mActivity;
    Intent intent;
    String dx;
    private Context context;
    private int lastPosition = -1;
    public SaldoAdapter( ArrayList<String> kodeSaldoList,
                         ArrayList<String> saldoList,
                         ArrayList<String> tglAkadList,
                         ArrayList<String> tenorList,
                         ArrayList<String> pinjamanList,
                         ArrayList<String> bayarList,
                         ArrayList<String> sisaList,
                         Context context) {
        this.kodeSaldoList = kodeSaldoList;
        this.saldoList = saldoList;
        this.tglAkadList = tglAkadList;
        this.tenorList = tenorList;
        this.pinjamanList = pinjamanList;
        this.bayarList = bayarList;
        this.sisaList = sisaList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtKodeSaldo, txtSaldo, txtTglAkad, txtTenor,
                txtPinjaman, txtBayar, txtSisa;
        CardView cardSaldo;
        LinearLayout ly_saldo, ly_deskripsi;

        public MyViewHolder(View view) {
            super(view);
            txtKodeSaldo = (TextView) view.findViewById(R.id.kode_saldo);
            txtSaldo = (TextView) view.findViewById(R.id.saldo);
            txtTglAkad = (TextView) view.findViewById(R.id.tgl_akad);
            txtTenor = (TextView) view.findViewById(R.id.tenor);
            txtPinjaman = (TextView) view.findViewById(R.id.pinjaman);
            txtBayar = (TextView) view.findViewById(R.id.bayar);
            txtSisa = (TextView) view.findViewById(R.id.sisa);
            ly_saldo = view.findViewById(R.id.ly_saldo);
            ly_deskripsi = view.findViewById(R.id.ly_deskripsi);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saldo_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //format harga
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("Rp #,###", symbols);
        //inisial format rupiah
        String totalSaldo = FormatBaru(saldoList.get(position));
        String pinjaman = FormatBaru(pinjamanList.get(position));
        String bayar = FormatBaru(bayarList.get(position));
        String sisa = FormatBaru(sisaList.get(position));

        //set data ke view
        holder.txtKodeSaldo.setText(kodeSaldoList.get(position));
        holder.txtSaldo.setText(totalSaldo);
        holder.txtTglAkad.setText(tglAkadList.get(position));
        holder.txtTenor.setText(tenorList.get(position));
        holder.txtPinjaman.setText(pinjaman);
        holder.txtBayar.setText(bayar);
        holder.txtSisa.setText(sisa);
        final boolean[] opened = new boolean[getItemCount()];
        //click show detail
        holder.ly_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!opened[0]){
                    holder.ly_deskripsi.setVisibility(View.VISIBLE);

                } else {
                    holder.ly_deskripsi.setVisibility(View.GONE);

                }
                opened[0] = !opened[0];
            }
        });
    }
    public String FormatBaru(String duit){
        String[] debitArray = duit.split("");
        String debitFinal="";
        Integer hd=0;
        for(int d=0;d<debitArray.length;d++){
            debitFinal+= debitArray[debitArray.length-d-1];
            if(hd==2){
                if(debitArray.length-d-1==0){

                }else{
                    debitFinal+= ".";
                    hd=0;
                }
            }else{
                hd++;
            }

        }
        Log.d("DEBIT FINAL", "onBindViewHolder: "+debitFinal);
        return "Rp. "+new StringBuilder(debitFinal).reverse().toString();
    }
    @Override
    public int getItemCount() {
        return kodeSaldoList.size();
    }
}