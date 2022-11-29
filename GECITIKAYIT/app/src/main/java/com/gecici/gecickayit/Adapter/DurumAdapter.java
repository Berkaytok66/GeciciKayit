package com.gecici.gecickayit.Adapter;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gecici.gecickayit.Claslar.imei;
import com.gecici.gecickayit.R;

import java.util.ArrayList;

public class DurumAdapter extends RecyclerView.Adapter <DurumAdapter.DurumHolder>{
    private ArrayList<imei> mDurumList;
    private Context mContext;
    private imei imeiDurum;
    private View view;

    public DurumAdapter(ArrayList<imei> mDurumList, Context mContext) {
        this.mDurumList = mDurumList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DurumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.durum_item,parent,false);
        return new DurumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DurumHolder holder, int position) {

        imeiDurum = mDurumList.get(position);
        holder.imeiText.setText(imeiDurum.getImeiNumber());
        holder.durumText.setText(imeiDurum.getImeiDurum());
        holder.SparisText.setText(imeiDurum.getSparisNumber());
        holder.imeiTime.setText(imeiDurum.getImeiTime());
        holder.kullaniciGmail.setText(imeiDurum.getKullaniciEmail());


    }

    @Override
    public int getItemCount() {
        return mDurumList.size();
    }

    class DurumHolder extends RecyclerView.ViewHolder{
        TextView imeiText, durumText,SparisText,imeiTime,kullaniciGmail;
        String copyText;
        ImageView copyButton;
        public DurumHolder(@NonNull View itemView) {
            super(itemView);
            imeiText = itemView.findViewById(R.id.imeiNumberText);
            durumText = itemView.findViewById(R.id.imeiDurumText);
            SparisText = itemView.findViewById(R.id.sparisNumarası);
            copyButton = itemView.findViewById(R.id.copy_button);
            imeiTime =itemView.findViewById(R.id.imeiTime);
            kullaniciGmail =itemView.findViewById(R.id.kullaniciGmail);
            copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("IMEI: ",imeiText.getText()+" İMEİ numaralı / "+durumText.getText()+"/ Durumuna Sahip "+
                            SparisText.getText()+" Numaralı sparişe sahip "+kullaniciGmail.getText()+" Kullanıcısıyım "+ imeiTime.getText()+" Tarihinde verdiğim sparişin incelenmesini talep ediyorum.");
                    clipboardManager.setPrimaryClip(clipData);
                    if (clipboardManager !=null){
                        Toast.makeText(mContext, "COPY", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}