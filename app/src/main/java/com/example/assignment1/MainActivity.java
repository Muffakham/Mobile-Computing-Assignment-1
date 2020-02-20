package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    String[] gestureList = {"buy", "house", "fun", "hope", "arrive", "really", "read", "lip",
                            "mouth", "some", "communicate", "write", "create", "pretend", "sister",
                            "man", "one", "drive", "perfect", "mother"
                            };
    String[] urlList = {
            "https://www.signingsavvy.com/media/mp4-ld/6/6442.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/23/23234.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/22/22976.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/22/22197.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/26/26971.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/24/24977.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/7/7042.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/26/26085.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/22/22188.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/23/23931.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/22/22897.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/27/27923.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/22/22337.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/25/25901.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/21/21587.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/21/21568.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/26/26492.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/23/23918.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/24/24791.mp4",
            "https://www.signingsavvy.com/media/mp4-ld/21/21571.mp4"
    };
    DownloadManager downloadManager;
    Spinner spin;
    Button down;
    String selectedGesture = "";
    String urlPath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        down = (Button)findViewById(R.id.button);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGesture.equals("") && urlPath.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please select a gesture!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    //downloadFile(urlPath, selectedGesture);
                    ProgressBack PB = new ProgressBack();
                    PB.execute("");
                }
            }
        });
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedGesture = gestureList[position];
                urlPath = urlList[position];
                System.out.println("in the item selected function");

                //Intent i = new Intent();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> spin_adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, gestureList);
        spin.setAdapter(spin_adapter);

    }


    private void downloadFile(String fileURL, String fileName) {

        final int TIMEOUT_CONNECTION = 5000;//5sec
        final int TIMEOUT_SOCKET = 30000;//30sec

        long startTime = System.currentTimeMillis();
        //Toast.makeText(MainActivity.this,"Image download started",Toast.LENGTH_LONG).show();




        try {
            String rootDir = "/storage/emulated/0/sample.mp4";
            File rootFile = new File(rootDir);
            rootFile.mkdir();
            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();


            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            c.setReadTimeout(TIMEOUT_CONNECTION);
            c.setConnectTimeout(TIMEOUT_SOCKET);
            FileOutputStream f = new FileOutputStream(new File(rootDir));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            Log.d("Error....", e.toString());
        }

    }
    /*public long downloadVideos(String path, String fileName)
    {


        long downloadReference;

        System.out.println("the path is "+path);





        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse());

        request.setTitle("download");
        request.setDescription("your file is downloading ...");
        request.allowScanningByMediaScanner();

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        System.out.println("in the function");

        //request.setTitle("Data Download");

        //Setting description of request
        //request.setDescription("Android Data download using DownloadManager.");


        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS,"testing.mp4");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI| DownloadManager.Request.NETWORK_MOBILE);

        DownloadManager manager =(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        downloadReference = 123213l;

        return downloadReference;

        /*FileOutputStream fos  = null;
        //FileInputStream fis  = null;
        String fileStorePath =  null;
        String rootdir = Environment.getExternalStorageDirectory()
                + File.separator + "Video";
        //fileStorePath = rootdir+File.separator+fileName;
        File vidFile = new File(rootdir);
        vidFile.mkdir();

        try{
            fos = new FileOutputStream(vidFile);
            URL u  =new URL(path);
            URLConnection c = u.openConnection();
            int length = c.getContentLength();
            DataInputStream s = new DataInputStream(u.openStream());
            byte[] buffer = new byte[length];
            s.readFully(buffer);
            s.close();

            DataOutputStream fs = new DataOutputStream(fos);
            fs.write(buffer);
            fs.flush();
            fs.close();

            fos.close();

            fos = new FileOutputStream(fileStorePath);


        }
        catch(Exception e )
        {
            System.out.println(e);
            //Log.d(TAG, "downloadVideos: ");
        }


    }*/



    private class ProgressBack extends AsyncTask<String,String,String> {
        ProgressDialog PD;
        @Override
        protected void onPreExecute() {
            PD= ProgressDialog.show(MainActivity.this,null, "Please Wait ...", true);
            PD.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... arg0) {
            downloadFile("https://www.signingsavvy.com/media/mp4-ld/22/22188.mp4",
                    "Sample.mp4");
            return "hello";

        }
        protected void onPostExecute(Boolean result) {
            PD.dismiss();

        }

    }

}
