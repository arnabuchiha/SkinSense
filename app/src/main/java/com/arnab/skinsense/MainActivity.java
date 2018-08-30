package com.arnab.skinsense;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    //Firebase Variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String email;

    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";

    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    private FloatingActionButton btnDetectObject, btnToggleCamera;
    private ImageView imageViewResult;
    private CameraView cameraView;
    private ProgressDialog pd;
    private ImageView alertimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);
        imageViewResult = findViewById(R.id.imageViewResult);
       // textViewResult = findViewById(R.id.textViewResult);
        //textViewResult.setMovementMethod(new ScrollingMovementMethod());
        btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);
        pd=new ProgressDialog(MainActivity.this);
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();
                BitmapHelper.getInstance().setBitmap(bitmap);
                pd.dismiss();
                Intent intent=new Intent(MainActivity.this,Crop.class);
                //intent.putExtra("bitmap",filename);
                startActivityForResult(intent,2);
                /*String filename = "bitmap.png";

                try {
                    FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                    bitmap.recycle();
                    pd.dismiss();
                    Intent intent=new Intent(MainActivity.this,Crop.class);
                    intent.putExtra("bitmap",filename);
                    startActivityForResult(intent,2);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //bitmap=Bitmap.createScaledBitmap((bitmap,bitmap.getWidth(),bitmap.getHeight(),false);
                //bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                */

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
                pd.setMessage("Loading");
                pd.setCancelable(false);
                pd.show();

            }
        });

        initTensorFlowAndLoadModel();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            Bitmap bitmap=data.getParcelableExtra("croppedBitmap");
            imageViewResult.setImageBitmap(bitmap);
            //alertimg = findViewById(R.id.img);
            //alertimg.setImageBitmap(bitmap);
            //alertimg.invalidate();
            final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

            //alertimg.setImageBitmap(bitmap);
            //AlertView alert = new AlertView(MainActivity.this);
            //alert.showDialog(this, results.toString(),bitmap);

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert);
            //Toast.makeText(this, ""+alertimg, Toast.LENGTH_SHORT).show();
            //alertimg.setImageBitmap(bitmap);
            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(results.toString());
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();



            //textViewResult.setText(results.toString());
            /*AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.layout, null);
            ImageView imageView=dialoglayout.findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);*/

            /*textViewResult.setText(results.toString());
            TextView textView=dialoglayout.findViewById(R.id.pred);
            textView.setText(results.toString());
            builder.setView(dialoglayout);
            builder.show();*/
            offlinestore offline=new offlinestore();
            String bit=offline.BitMapToString(bitmap);
            UserData ud = new UserData(results.toString(),bit);
            ListHolder.list.add(ud);
            Hawk.put(FirebaseAuth.getInstance().getCurrentUser().getEmail(),ListHolder.list);
            if(Hawk.contains(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
            Toast.makeText(this,Hawk.get(FirebaseAuth.getInstance().getCurrentUser().getEmail()).toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_FILE,
                            LABEL_FILE,
                            INPUT_SIZE,
                            IMAGE_MEAN,
                            IMAGE_STD,
                            INPUT_NAME,
                            OUTPUT_NAME);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MainActivity.this,NavigationActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
