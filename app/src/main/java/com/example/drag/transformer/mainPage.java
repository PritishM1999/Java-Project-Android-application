package com.example.drag.transformer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.drag.transformer.Fragment.Input;
import com.example.drag.transformer.Fragment.Output;
import com.example.drag.transformer.Fragment.Temperature;
import com.example.drag.transformer.Module.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class mainPage extends AppCompatActivity {

    TextView usernam;

    FirebaseUser firebaseusr;
    DatabaseReference reference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mytemp = database.getReference("Temperature");

    DatabaseReference shortcir = database.getReference("OUTVoltage");

    public static int flag1=0;

    public  final int NOTIFICATION_ID=001;
    public final String CHANNEL_ID = "personal_notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar =findViewById(R.id.toolx1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        usernam = (TextView) findViewById(R.id.usrx);

        firebaseusr = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseusr.getUid());




        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                usernam.setText(user.getUsax());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TabLayout tabLayout=findViewById(R.id.tablay);
        ViewPager viewPager=findViewById(R.id.viewpager);

        ViewPagerAdpater viewPagerAdpater = new ViewPagerAdpater(getSupportFragmentManager());

        viewPagerAdpater.addFragment(new Input(),"Input");
        viewPagerAdpater.addFragment(new Temperature(),"Temperature");
        viewPagerAdpater.addFragment(new Output(),"Output");

        viewPager.setAdapter(viewPagerAdpater);
        tabLayout.setupWithViewPager(viewPager);

        mytemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valuex = dataSnapshot.getValue(String.class);
                if(Double.parseDouble(valuex)>=40.00)
                {
                    sendNotification();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        shortcir.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valuex = dataSnapshot.getValue(String.class);
                if(Double.parseDouble(valuex)<=01.00)
                {
                    sendNotification1();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuid:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(mainPage.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }

    class ViewPagerAdpater extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
        ViewPagerAdpater(FragmentManager fm)
        {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment,String title)
        {
            fragments.add(fragment);
            titles.add(title);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


    public void sendNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Solenoid";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Smoke Status");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        Uri Sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), Sound);
        r.play();
        //MediaPlayer mp = MediaPlayer. create (getApplicationContext(), alarmSound);
        //mp.start();
        builder.setSmallIcon(R.drawable.ic_android_black_24dp);
        builder.setContentTitle("IoT Transformer Notification");
        builder.setContentText("Alert temperature is greater than 40°C");
        builder.setSound(Sound);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }



    public void sendNotification1() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Solenoid";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Smoke Status");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        Uri Sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), Sound);
        r.play();
        //MediaPlayer mp = MediaPlayer. create (getApplicationContext(), alarmSound);
        //mp.start();
        builder.setSmallIcon(R.drawable.ic_android_black_24dp);
        builder.setContentTitle("IoT Transformer Notification");
        builder.setContentText("Short Circuit is detected in Output");
        builder.setSound(Sound);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }
}
