package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.HashSet;

public class PracticeActivity extends AppCompatActivity {

    int fpath =0;
    String fname="";
    VideoView v;
    TextView t;
    Button c;
    long time_started = 0;
    long time_started_return = 0;
    String returnedURI;
    String lname="";
    String ipad="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);



        v= (VideoView)findViewById(R.id.videoView);
        t= (TextView)findViewById(R.id.textView3);
        c = (Button)findViewById(R.id.button2);

        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.start();

                }

            }
        };
        v.setOnCompletionListener(onCompletionListener);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!v.isPlaying()) {
                    v.start();
                }
            }
        });

        Intent i  = getIntent();
        if (i.hasExtra("File_path"))
        {
            fpath = i.getIntExtra("File_path",0);}
        if (i.hasExtra("File_naame")){
            fname = i.getStringExtra("File_name");}
        if (i.hasExtra("lastname")){
            lname = i.getStringExtra("lastname");}
        if (i.hasExtra("IP_ADDR")){

            ipad = i.getStringExtra("IP_ADDR");


        }
        if(!fname.isEmpty())
        {
            t.setText(fname);
        }
        if(fpath!=0) {
            //String d = "arrive";
            //String pt = "android.resource://" + getPackageName() + "/" + R.raw.arrive;
            String h = "android.resource://" + getPackageName() + "/" +fpath;
            Uri uri = Uri.parse(h);
            v.setVideoURI(uri);
            v.start();
        }

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ContextCompat.checkSelfPermission(PracticeActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?

                    if (ActivityCompat.shouldShowRequestPermissionRationale(PracticeActivity.this,
                            Manifest.permission.CAMERA)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(PracticeActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                101);

                    }

                }

                else{
                    File f = new File(Environment.getExternalStorageDirectory(), "Assignment1");

                    if (!f.exists()) {
                        f.mkdirs();
                    }

                    time_started = System.currentTimeMillis() - time_started;

                    Intent t = new Intent(PracticeActivity.this,videoActivity.class);

                    t.putExtra("INTENT_TIME_WATCHED", time_started);
                    t.putExtra("LAST_NAME",lname);
                    t.putExtra("IP_ADDR",ipad);
                    startActivityForResult(t,9999);
                }


            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if(requestCode==9999 && resultCode == 8888) {
            if(intent.hasExtra("INTENT_URI") && intent.hasExtra("INTENT_TIME_WATCHED_VIDEO")) {
                returnedURI = intent.getStringExtra("INTENT_URI");
                Log.d("intentURI","in 1st"+returnedURI);
                time_started_return = intent.getLongExtra("INTENT_TIME_WATCHED_VIDEO",0);



                }

                time_started = System.currentTimeMillis();



            }



        if(requestCode==9999 && resultCode==7777)
        {
            if(intent!=null) {
                //create folder
                if(intent.hasExtra("INTENT_URI") && intent.hasExtra("INTENT_TIME_WATCHED_VIDEO")) {
                    returnedURI = intent.getStringExtra("INTENT_URI");
                    Log.d("intentURI","in 2nd"+returnedURI);
                    time_started_return = intent.getLongExtra("INTENT_TIME_WATCHED_VIDEO",0);
                    File f = new File(returnedURI);
                    f.delete();



                    time_started = System.currentTimeMillis();

                }
            }

        }


    }
}
