package com.arnab.skinsense;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AlphaBAT69 on 20-03-2018.
 */

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder>{
    private List<UserData> detailsList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, description;
        public  ViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
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
        holder.name.setText(details.getName());
        holder.description.setText(details.getText());
    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }
}
