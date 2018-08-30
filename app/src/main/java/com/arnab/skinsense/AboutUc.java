package com.arnab.skinsense;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by User on 20-03-2018.
 */

public class AboutUc extends android.support.v4.app.Fragment implements View.OnClickListener
{
    ImageView arnab, tanay, ritvik, rajnish;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arnab = view.findViewById(R.id.arnab);
        tanay = view.findViewById(R.id.tanay);
        ritvik = view.findViewById(R.id.ritvik);
        rajnish = view.findViewById(R.id.rajnish);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.arnab:

        }
    }
}
