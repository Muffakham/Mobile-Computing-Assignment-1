package com.example.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import android.app.Activity;

import android.content.Intent;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


public class videoActivity extends Activity implements SurfaceHolder.Callback {

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private Button mToggleButton;

    private TextView tv_time;
    Intent returnIntent;
    String returnfile;
    videoActivity activity;
    String word;
    private boolean mInitSuccesful;

    CountDownTimer time;
    //long time_watched;
    Button upld;
    String lastname;
    String ipdr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        lastname = "zero";
        upld = (Button)findViewById(R.id.bt_upload);
        upld.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {



                String server_ip = ipdr;
                //Log.d("msg",server_ip);
                RequestParams params = new RequestParams();


                    try {
                        params.put("uploaded_file", new File(returnfile));
                        params.put("id","mmoham64");
                        params.put("accept","1");

                    } catch(FileNotFoundException e) {}


                    // send request
                    AsyncHttpClient client = new AsyncHttpClient();

                    client.post("http://"+server_ip +"/Assignment1/upload_video.php", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                            // handle success response
                            Log.e("msg success",statusCode+"");
                            if(statusCode==200) {
                                Toast.makeText(videoActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                activity.setResult(8888,returnIntent);
                                activity.finish();


                                                            }
                            else {
                                Toast.makeText(videoActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                            // handle failure response
                            Log.e("msg fail",statusCode+"");

                            //Toast.makeText(videoActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onProgress(long bytesWritten, long totalSize) {


                            super.onProgress(bytesWritten, totalSize);
                        }


                        @Override
                        public void onStart() {

                            super.onStart();
                        }

                        @Override
                        public void onFinish() {

                            super.onFinish();
                        }
                    });



            }
        });
        activity = this;
        returnIntent = new Intent();
        /*if(getIntent().hasExtra("INTENT_WORD")) {
            word = getIntent().getStringExtra("INTENT_WORD");

            Log.d("State","State name in video activity " + word);
        }*/

        if(getIntent().hasExtra("IP_ADDR"))
        {
            ipdr = getIntent().getStringExtra("IP_ADDR");
        }
        if(getIntent().hasExtra("LAST_NAME"))
        {
            lastname = getIntent().getStringExtra("LAST_NAME");
        }
       /* if(getIntent().hasExtra("INTENT_TIME_WATCHED")) {
            time_watched = getIntent().getLongExtra("INTENT_TIME_WATCHED",0);
        }*/
        mSurfaceView = (SurfaceView) findViewById(R.id.sv_camera);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);

        tv_time = (TextView) findViewById(R.id.tv_time);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        mToggleButton = (Button) findViewById(R.id.bt_start);
        time = new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long l) {
                int a = (int) (l / 1000);
                tv_time.setText(a + " ");
                if(a<1)
                {
                    mToggleButton.setEnabled(false);
                }
            }

            @Override
            public void onFinish() {
                mMediaRecorder.stop();
                //mToggleButton.setText("Start Recording");

                //mToggleButton.setEnabled(false);
                mMediaRecorder.reset();
                if(time!=null) {
                    time.cancel();


                }

                returnIntent.putExtra("INTENT_URI",returnfile);
                //returnIntent.putExtra("INTENT_TIME_WATCHED_VIDEO" , time_watched);
                activity.setResult(8888,returnIntent);


                //activity.finish();
            }
        };



            mToggleButton.setOnClickListener(new OnClickListener() {
                @Override
                // toggle video recording
                public void onClick(final View v) {


                    if(((Button) v).getText().toString().equalsIgnoreCase("start recording"))
                    {
                        ((Button) v).setText("Stop Recording");
                        ((Button) v).setEnabled(true);
                        mMediaRecorder.start();
                        time.start();
                    }

                    else{

                        mMediaRecorder.stop();
                        mMediaRecorder.reset();
                        ((Button) v).setText("Start Recording");
                        ((Button) v).setEnabled(false);
                        if (time != null) {
                            time.cancel();
                        }
                        returnIntent.putExtra("INTENT_URI", returnfile);
                        activity.setResult(8888,returnIntent);
                        //activity.finish();
                    }


                }
            });







        }



    boolean fileCreated = false;
    private void initRecorder(Surface surface) throws IOException {

        if(mCamera == null) {
            mCamera = Camera.open(1);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            mCamera.unlock();

        }

        if(mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setPreviewDisplay(surface);
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        int i=0;
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Assignment1/"
                +"GESTURE_PRACTICE_"+"0_"+lastname  + ".mp4");

        while(file.exists()) {
            i++;
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/Assignment1/"
                    +"GESTURE_PRACTICE_"+i +"_"+lastname+ ".mp4");
        }

        if(file.createNewFile()) {
            fileCreated = true;
            Log.e("file path",file.getPath());
            returnfile = file.getPath();
        }
        returnIntent.putExtra("INTENT_URI",returnfile);
       // returnIntent.putExtra("INTENT_TIME_WATCHED_VIDEO" , time_watched);
        activity.setResult(8888,returnIntent);


        mMediaRecorder.setOutputFile(file.getPath());
        mMediaRecorder.setMaxDuration(5000);
        mMediaRecorder.setVideoSize(320,240);
        mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mediaRecorder, int i, int i1) {
                if (i == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                    if(time!=null) {
                        time.cancel();
                    }
                    returnIntent.putExtra("INTENT_URI",returnfile);
                   // returnIntent.putExtra("INTENT_TIME_WATCHED_VIDEO" , time_watched);
                    activity.setResult(8888,returnIntent);
                }

            }
        });


        mMediaRecorder.setOrientationHint(270);

        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoEncodingBitRate(3000000);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// MPEG_4_SP

        try {

            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        mInitSuccesful = true;
    }


    @Override
    public void onBackPressed() {

        if(time!=null)
            time.cancel();

        returnIntent.putExtra("INTENT_URI",returnfile);
       // returnIntent.putExtra("INTENT_TIME_WATCHED_VIDEO" , time_watched);
        activity.setResult(7777,returnIntent);
        //activity.finish();

        super.onBackPressed();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            if(!mInitSuccesful)
                initRecorder(mHolder.getSurface());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {}

    private void shutdown() {

        mMediaRecorder.reset();
        mMediaRecorder.release();
        //hello
        mCamera.release();
        mMediaRecorder = null;
        returnIntent.putExtra("INTENT_URI",returnfile);
       // returnIntent.putExtra("INTENT_TIME_WATCHED_VIDEO" , time_watched);
        activity.setResult(7777,returnIntent);
        mCamera = null;
        finish();
    }
    static InetAddress ip() throws SocketException {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        NetworkInterface ni;
        while (nis.hasMoreElements()) {
            ni = nis.nextElement();
            if (!ni.isLoopback()/*not loopback*/ && ni.isUp()/*it works now*/) {
                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                    //filter for ipv4/ipv6
                    if (ia.getAddress().getAddress().length == 4) {
                        //4 for ipv4, 16 for ipv6
                        return ia.getAddress();
                    }
                }
            }
        }
        return null;
    }




}