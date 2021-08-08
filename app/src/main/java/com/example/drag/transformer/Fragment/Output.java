package com.example.drag.transformer.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drag.transformer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Output extends Fragment {
    TextView outvolt,outcurr,outpower;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myvolt = database.getReference("OUTVoltage");
    DatabaseReference mycurr = database.getReference("OUTCurrent");
    DatabaseReference mypov = database.getReference("OUTPower");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_output, container, false);

       outvolt=view.findViewById(R.id.ovoltage);
       outcurr=view.findViewById(R.id.ocurrent);
       outpower=view.findViewById(R.id.opower);

       myvolt.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String value = dataSnapshot.getValue(String.class);
               outvolt.setText(value+" V");
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        mycurr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                outcurr.setText(value+" Amp");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mypov.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                outpower.setText(value+" Watt");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       return view;
    }
}
