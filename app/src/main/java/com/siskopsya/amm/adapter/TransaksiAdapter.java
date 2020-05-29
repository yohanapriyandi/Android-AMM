package com.siskopsya.amm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.MyViewHolder> {

    private ArrayList<String>
            jenisList= new ArrayList<>(),
            totalSaldoList = new ArrayList<>(),
            debitList= new ArrayList<>(),
            kreditList= new ArrayList<>(),
            saldoList = new ArrayList<>();


    private Activity mActivity;
    Intent intent;
    String dx;
    private Context context;
    private int lastPosition = -1;
    public TransaksiAdapter(ArrayList<String> jenisList,
                            ArrayList<String> totalSaldoList,
                            ArrayList<String> debitList,
                            ArrayList<String> kreditList,
                            ArrayList<String> saldoList,
                            Context context) {
        this.jenisList = jenisList;
        this.totalSaldoList = totalSaldoList;
        this.debitList = debitList;
        this.kreditList = kreditList;
        this.saldoList = saldoList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtStatus, txtNoTrans, txtTgl, txtSaldo;
        CardView status;

        public MyViewHolder(View view) {
            super(view);
            txtStatus = (TextView) view.findViewById(R.id.trans_status);
            txtNoTrans = view.findViewById(R.id.no_trans);
            txtTgl = (TextView) view.findViewById(R.id.tgl_trans);
            txtSaldo = (TextView) view.findViewById(R.id.trans_saldo);
            status = view.findViewById(R.id.card_status);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaksi_list, parent, false);
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
        String saldo = decimalFormat.format(Integer.parseInt(saldoList.get(position)));

        //set data ke view
        holder.txtNoTrans.setText(jenisList.get(position));
        holder.txtStatus.setText(debitList.get(position));
        holder.txtTgl.setText(kreditList.get(position));
        holder.txtSaldo.setText(saldo);
        if (debitList.get(position)=="request"){
            holder.status.setCardBackgroundColor(Color.RED);
        }
    }
    @Override
    public int getItemCount() {
        return jenisList.size();
    }
}