package com.bc181.dwimantara.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.bc181.dwimantara.InputActivity;
import com.bc181.dwimantara.R;
import com.bc181.dwimantara.TampilActivity;
import com.bc181.dwimantara.model.Content;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private List<Content> dataContent;
    private Context context;

    public ContentAdapter(List<Content> dataContent, Context context) {
        this.dataContent = dataContent;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        Content tempContent = dataContent.get(position);
        holder.id = tempContent.getId();
        holder.nama = tempContent.getNama();
        holder.tahun = tempContent.getTahun();
        holder.brand = tempContent.getBrand();
        holder.jenis = tempContent.getJenis();
        holder.tvNama.setText(holder.nama + " " + holder.tahun);
        holder.tvStock.setText(tempContent.getStock());
        String imgLocation = tempContent.getPhoto();
        if(!imgLocation.equals(null)) {
            //Picasso.get().load(imgLocation).resize(64, 64).into(holder.imgAvatar);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(imgLocation)
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imgAvatar);
        }
        holder.imgAvatar.setContentDescription(tempContent.getPhoto());
    }

    @Override
    public int getItemCount() {
        return dataContent.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView tvNama, tvStock;
        private ImageView imgAvatar;
        private int id;
        private String nama, tahun, brand, jenis;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_name);
            tvStock = itemView.findViewById(R.id.tv_stock);
            imgAvatar = itemView.findViewById(R.id.profile_image);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent openDisplay = new Intent(context, TampilActivity.class);
            openDisplay.putExtra("NAMA", nama +" "+ tahun);
            openDisplay.putExtra("STOCK", tvStock.getText());
            openDisplay.putExtra("BRAND", brand);
            openDisplay.putExtra("IMAGE", imgAvatar.getContentDescription());
            openDisplay.putExtra("JENIS", jenis);
            itemView.getContext().startActivity(openDisplay);
        }

        @Override
        public boolean onLongClick(View v) {
            Intent openInput = new Intent(context, InputActivity.class);
            openInput.putExtra("OPERATION", "update");
            openInput.putExtra("ID", id);
            openInput.putExtra("NAMA", nama);
            openInput.putExtra("TAHUN", tahun);
            openInput.putExtra("STOCK", tvStock.getText());
            openInput.putExtra("BRAND", brand);
            openInput.putExtra("IMAGE", imgAvatar.getContentDescription());
            openInput.putExtra("JENIS", jenis);
            itemView.getContext().startActivity(openInput);
            return true;
        }
    }
}