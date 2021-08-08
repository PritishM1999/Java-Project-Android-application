package com.example.drag.transformer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    EditText usernamex,emailx,password;
    Button register;

    FirebaseAuth auth;
    DatabaseReference reference;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar =(Toolbar)findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernamex=(EditText)findViewById(R.id.username);
        emailx=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);

        register=(Button)findViewById(R.id.regis);

        auth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtUsr=usernamex.getText().toString();
                String txtEmail=emailx.getText().toString();
                String txtPass=password.getText().toString();

                if(TextUtils.isEmpty(txtUsr)||TextUtils.isEmpty(txtEmail)||TextUtils.isEmpty(txtPass))
                {
                    Toast.makeText(Registration.this,"All field Required",Toast.LENGTH_LONG).show();
                }
                else if(txtPass.length()<6)
                {
                    Toast.makeText(Registration.this,"password must be at least 6 Characters",Toast.LENGTH_LONG).show();
                }
                else
                {
                    regist(txtUsr,txtEmail,txtPass);
                }
            }
        });
    }

    private void regist(final String username ,String email ,String password)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser fireusr = auth.getCurrentUser();
                    String usrid=fireusr.getUid();
                    reference=FirebaseDatabase.getInstance().getReference("Users").child(usrid);

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",usrid);
                    hashMap.put("Username",username);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Intent inttend=new Intent(Registration.this,mainPage.class);
                                inttend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(inttend);
                                finish();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Registration.this,"You can't register without Email and pass",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
