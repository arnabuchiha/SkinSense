package com.arnab.skinsense;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lyft.android.scissors.CropView;

public class Crop extends AppCompatActivity {
    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";

    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    CropView cropView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        FloatingActionButton crop=findViewById(R.id.cropButton);
        Intent intent=getIntent();
        Bitmap bitmap= (Bitmap) intent.getParcelableExtra("bitmap");
        cropView=findViewById(R.id.crop_view);
        cropView.setImageBitmap(bitmap);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap croppedBitmap=cropView.crop();
                croppedBitmap=Bitmap.createScaledBitmap(croppedBitmap, INPUT_SIZE, INPUT_SIZE, false);
                Intent intent1=new Intent();
                intent1.putExtra("croppedBitmap",croppedBitmap);
                setResult(2,intent1);
                finish();
            }
        });
    }

}
