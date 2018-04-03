package com.arnab.skinsense;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;



public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder>{
    private List<UserData> detailsList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public  ViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
        }
    }

    public UserDataAdapter(List<UserData> detailsList){
        this.detailsList=detailsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData details = detailsList.get(position);
        offlinestore offline=new offlinestore();
        Bitmap bitmap=offline.StringToBitMap(details.getBitmap());
        holder.image.setImageBitmap(bitmap);
        holder.name.setText(details.getText().toString());
    }

    @Override
    public int getItemCount() {
        if(detailsList==null)
            return 0;
        return detailsList.size();
    }
}
