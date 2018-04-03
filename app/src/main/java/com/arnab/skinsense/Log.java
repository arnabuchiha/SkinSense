package com.arnab.skinsense;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log extends AppCompatActivity implements View.OnClickListener{
    private EditText email,password;
    private TextView login;
    private TextView signup;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login:{
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(Log.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(Log.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if(user.isEmailVerified()){
                                        //Toast.makeText(Log.this, "done", Toast.LENGTH_SHORT).show();
                                        setResult(9);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(Log.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                break;
            }
            case R.id.signup:{
                startActivityForResult(new Intent(Log.this, SignupActivity.class), 8);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==requestCode){

        }
        else {
            setResult(-1);
            finish();
        }
    }
}
