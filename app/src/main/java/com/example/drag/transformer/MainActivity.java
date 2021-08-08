package com.example.drag.transformer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText emailx,password;
    TextView regi;
    Button logx;
    FirebaseAuth auth;

    FirebaseUser fireusr;

    @Override
    protected void onStart() {
        super.onStart();

        fireusr=FirebaseAuth.getInstance().getCurrentUser();


        if(fireusr!=null)
        {
            Intent ht1 = new Intent(MainActivity.this,mainPage .class);
            startActivity(ht1);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();

        emailx=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);

        regi = (TextView)findViewById(R.id.actureg);
        logx = (Button) findViewById(R.id.loginx);

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ht1 = new Intent(MainActivity.this,Registration.class);
                startActivity(ht1);
            }
        });

        logx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtemail=emailx.getText().toString();
                String txtpass=password.getText().toString();
                if(TextUtils.isEmpty(txtemail)||TextUtils.isEmpty(txtpass))
                {
                    Toast.makeText(MainActivity.this,"All field Required",Toast.LENGTH_LONG).show();
                }
                else {
                    auth.signInWithEmailAndPassword(txtemail,txtpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent inttend=new Intent(MainActivity.this,mainPage.class);
                                inttend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(inttend);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Authentication is Failed!!!!!",Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                }
            }
        });
    }
}
