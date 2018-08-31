package com.arnab.skinsense;

import android.app.Fragment;
import android.content.Intent;
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
    ImageView arnab, tanay, ritvik, rajnish, arnabin, tanayin, ritvikin, rajnishin;
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
        arnabin = view.findViewById(R.id.arnabin);
        tanayin = view.findViewById(R.id.tanayin);
        ritvikin = view.findViewById(R.id.ritvikin);
        rajnishin = view.findViewById(R.id.rajnishin);

        arnab.setOnClickListener(this);
        tanay.setOnClickListener(this);
        ritvik.setOnClickListener(this);
        rajnish.setOnClickListener(this);
        arnabin.setOnClickListener(this);
        tanayin.setOnClickListener(this);
        ritvikin.setOnClickListener(this);
        rajnishin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent i = new Intent(view.getContext(), WebViewActivity.class);
        switch(view.getId()){
            case R.id.arnab:{
                i.putExtra("link", "https://github.com/arnabuchiha");
                break;
            }
            case R.id.tanay:{
                i.putExtra("link", "https://github.com/tanaytoshniwal");
                break;
            }
            case R.id.ritvik:{
                i.putExtra("link", "https://github.com/ritvikjain");
                break;
            }
            case R.id.rajnish:{
                i.putExtra("link", "https://github.com/ShadowRajnsih");
                break;
            }
            case R.id.arnabin:{
                i.putExtra("link", "https://www.linkedin.com/in/funky-poseidon/");
                break;
            }
            case R.id.tanayin:{
                i.putExtra("link", "https://www.linkedin.com/in/tanay-toshniwal/");
                break;
            }
            case R.id.ritvikin:{
                i.putExtra("link", "https://www.linkedin.com/in/ritvik-jain-a98a03152/");
                break;
            }
            case R.id.rajnishin:{
                i.putExtra("link", "https://example.com");
                break;
            }
        }
        startActivity(i);
    }
}
