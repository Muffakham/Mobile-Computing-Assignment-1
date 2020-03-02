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
            fpath = i.getIntExtra("File_path",0);
            fname = i.getStringExtra("File_name");
            lname = i.getStringExtra("lastname");



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

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }

                    /*if (ActivityCompat.shouldShowRequestPermissionRationale(PracticeActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(PracticeActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                100);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }*/

                }
                /*if ( ContextCompat.checkSelfPermission(PracticeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ) {

                    // Permission is not granted
                    // Should we show an explanation?




                }*/
                else{
                    File f = new File(Environment.getExternalStorageDirectory(), "Assignment1");

                    if (!f.exists()) {
                        f.mkdirs();
                    }

                    time_started = System.currentTimeMillis() - time_started;

                    Intent t = new Intent(PracticeActivity.this,videoActivity.class);

                    t.putExtra("INTENT_TIME_WATCHED", time_started);
                    t.putExtra("INTENT_WORD","helloWorld");
                    t.putExtra("LAST_NAME",lname);
                    startActivityForResult(t,9999);
                }



                    // Permission has already been granted






 /*           File m = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign");
            if(!m.exists()) {
                if(m.mkdir()) {
                    Toast.makeText(this,"Directory Created",Toast.LENGTH_SHORT).show();
                }
            }

            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takeVideoIntent.putExtra(EXTRA_DURATION_LIMIT, 10);

            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }*/

            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //bt_change_state.setVisibility(View.GONE);
        Log.e("OnActivityresult",requestCode+" "+resultCode);
        if(requestCode==2000 ) {
            //from video activity
            //vv_record.setVisibility(View.GONE);
            //rb_learn.setChecked(true);
            //bt_cancel.setVisibility(View.GONE);
            //bt_send.setVisibility(View.GONE);
            //bt_record.setVisibility(View.VISIBLE);
            //sp_words.setEnabled(true);
            //rb_learn.setEnabled(true);
            //rb_practice.setEnabled(true);
            //sp_ip_address.setEnabled(true);


        }
        Log.d("twoVideos","IN two videos");

        if(requestCode==9999 && resultCode == 8888) {
            if(intent.hasExtra("INTENT_URI") && intent.hasExtra("INTENT_TIME_WATCHED_VIDEO")) {
                returnedURI = intent.getStringExtra("INTENT_URI");
                Log.d("intentURI","in 1st"+returnedURI);
                time_started_return = intent.getLongExtra("INTENT_TIME_WATCHED_VIDEO",0);

                //vv_record.setVisibility(View.VISIBLE);
                //bt_record.setVisibility(View.GONE);
                //bt_send.setVisibility(View.VISIBLE);
                //bt_cancel.setVisibility(View.VISIBLE);
                //sp_words.setEnabled(false);
                //rb_learn.setEnabled(false);
                //Log.d("selected",""+rb_practice.isSelected());

                //if(rb_practice.isChecked()){
                    //Log.d("twoVideos","IN two videos");
                    //vv_video_learn.setVisibility(View.VISIBLE);
                    //bt_send.setVisibility(View.GONE);
                    //bt_accept.setVisibility(View.VISIBLE);

                }

                time_started = System.currentTimeMillis();
                //vv_video_learn.start();

                //rb_practice.setEnabled(false);
                Log.d("setURI",""+returnedURI);
                //vv_record.setVideoURI(Uri.parse(returnedURI));



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
                    //  int try_number = sharedPreferences.getInt("record_"+sp_words.getSelectedItem().toString(),0);
                    // try_number++;
                    //String toAdd  = sp_words.getSelectedItem().toString()+"_"+try_number+"_"+time_started_return + "_cancelled";
                    //HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("RECORDED",new HashSet<String>());
                    // set.add(toAdd);
                    //  sharedPreferences.edit().putStringSet("RECORDED",set).apply();
                    //   sharedPreferences.edit().putInt("record_"+sp_words.getSelectedItem().toString(), try_number).apply();




                    time_started = System.currentTimeMillis();
                    //vv_video_learn.start();
                }
            }

        }

        /*if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            final Uri videoUri = intent.getData();


            vv_record.setVisibility(View.VISIBLE);
            vv_record.setVideoURI(videoUri);
            vv_record.start();
            play_video(sp_words.getSelectedItem().toString());
            bt_record.setVisibility(View.GONE);
            int i=0;
            File n = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign/"
                    + sharedPreferences.getString(INTENT_ID,"0000")+"_"+sp_words.getSelectedItem().toString()+"_0" + ".mp4");
            while(n.exists()) {
                i++;
                n = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign/"
                        + sharedPreferences.getString(INTENT_ID,"0000")+"_"+sp_words.getSelectedItem().toString()+"_"+i + ".mp4");
            }
            SaveFile saveFile = new SaveFile();
            saveFile.execute(n.getPath(),videoUri.toString());

            bt_send.setVisibility(View.VISIBLE);
            bt_cancel.setVisibility(View.VISIBLE);

            sp_words.setEnabled(false);
            rb_learn.setEnabled(false);
            rb_practice.setEnabled(false);
        }*/
    }
}
