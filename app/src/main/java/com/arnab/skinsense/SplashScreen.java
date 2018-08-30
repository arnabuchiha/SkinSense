package com.arnab.skinsense;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();

        final TextView text = findViewById(R.id.textsplash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                text.append("L");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        text.append("o");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                text.append("a");
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        text.append("d");
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                text.append("i");
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        text.append("n");
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                text.append("g");
                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        text.append(".");
                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                text.append(".");
                                                                                handler.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        text.append(".");
                                                                                        handler.postDelayed(new Runnable() {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                                                                                                finish();
                                                                                            }
                                                                                        }, 1500);
                                                                                    }
                                                                                }, 300);
                                                                            }
                                                                        }, 300);
                                                                    }
                                                                }, 300);
                                                            }
                                                        }, 300);
                                                    }
                                                }, 300);
                                            }
                                        }, 300);
                                    }
                                }, 300);
                            }
                        }, 300);
                    }
                }, 300);
            }
        }, 300);
    }
}
