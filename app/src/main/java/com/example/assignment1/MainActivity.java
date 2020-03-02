package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private final int REQUEST_PERMISSION_PHONE_STATE=1;
    int [] list = {
            R.raw.buy,
            R.raw.house,
            R.raw.fun,
            R.raw.hope,
            R.raw.arrive,
            R.raw.really,
            R.raw.read,
            R.raw.lip,
            R.raw.mouth,
            R.raw.some,
            R.raw.communicate,
            R.raw.wirte,
            R.raw.create,
            R.raw.pretend,
            R.raw.sister,
            R.raw.man,
            R.raw.one,
            R.raw.drive,
            R.raw.perfect,
            R.raw.mother,
    };
    EditText lastname;
    /*String[] urlList = {

            "android.resource://" + getPackageName() + "/" + R.raw.buy,
            "android.resource://" + getPackageName() + "/" + R.raw.house,
            "android.resource://" + getPackageName() + "/" + R.raw.fun,
            "android.resource://" + getPackageName() + "/" + R.raw.hope,
            "android.resource://" + getPackageName() + "/" + R.raw.arrive,
            "android.resource://" + getPackageName() + "/" + R.raw.really,
            "android.resource://" + getPackageName() + "/" + R.raw.read,
            "android.resource://" + getPackageName() + "/" + R.raw.lip,
            "android.resource://" + getPackageName() + "/" + R.raw.mouth,
            "android.resource://" + getPackageName() + "/" + R.raw.some,
            "android.resource://" + getPackageName() + "/" + R.raw.communicate,
            "android.resource://" + getPackageName() + "/" + R.raw.wirte,
            "android.resource://" + getPackageName() + "/" + R.raw.create,
            "android.resource://" + getPackageName() + "/" + R.raw.pretend,
            "android.resource://" + getPackageName() + "/" + R.raw.sister,
            "android.resource://" + getPackageName() + "/" + R.raw.man,
            "android.resource://" + getPackageName() + "/" + R.raw.one,
            "android.resource://" + getPackageName() + "/" + R.raw.perfect,
            "android.resource://" + getPackageName() + "/" + R.raw.mother,

    };*/
    DownloadManager downloadManager;
    Spinner spin;
    Button down;
    String selectedGesture = "";
    int urlPath=0;
    Button watch ;
    String FPath;
    String ln = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastname = (EditText)findViewById(R.id.editText);
        //down = (Button)findViewById(R.id.button);
        watch = (Button)findViewById(R.id.button3);
        showPhoneStatePermission();
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln = lastname.getText().toString();

                if (urlPath == 0 && selectedGesture.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select a gesture", Toast.LENGTH_LONG).show();




                } else {

                    if(ln.equalsIgnoreCase(""))
                    {
                        Toast.makeText(MainActivity.this, "Please select a gesture", Toast.LENGTH_LONG).show();
                    }
                    else {

                        Intent intent = new Intent(MainActivity.this, PracticeActivity.class);
                        intent.putExtra("File_path", urlPath);
                        intent.putExtra("File_name", selectedGesture);
                        intent.putExtra("lastname",ln);
                        startActivity(intent);
                    }
                }
            }
        });
        /*down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGesture.equals("") && urlPath.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please select a gesture!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    showPhoneStatePermission();


                    //ProgressBack PB = new ProgressBack();
                    //PB.execute("");
                }
            }
        });*/
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedGesture = gestureList[position];
                urlPath = list[position];
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



            String filePath= getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)+"";



            //String ff =
            //File file=new File(getExternalFilesDir(null),fileName+".mp4");

            //File folder = new File(filePath);

            //if (!folder.exists()) {
              //  folder.mkdirs();
            //}

            try {

                Uri downloadUri = Uri.parse(fileURL);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);



                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                request.allowScanningByMediaScanner();
                FPath = Environment.getExternalStorageDirectory().getPath() + "/MyExternalStorageAppPath/"+ fileName;
                //request.setDestinationInExternalFilesDir(MainActivity.this,  requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName+".mp4");
                //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName+".mp4");
                //request.setDestinationInExternalPublicDir(Environment)
                    //request.setDestinationUri(Uri.fromFile(file));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setVisibleInDownloadsUi(true);
                DownloadManager downloadManager = (DownloadManager)getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
                long id= downloadManager.enqueue(request);
                Toast.makeText(this, fileName, Toast.LENGTH_LONG).show();
                Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();

            }

            catch (Exception ex){
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            }







        /*final int TIMEOUT_CONNECTION = 5000;//5sec
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
        }*/

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

    /*private class ProgressBack extends AsyncTask<String,String,String> {
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

    }*/

    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_PHONE_STATE);
            } else {
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_PHONE_STATE);
            }
        } else {

            //downloadFile(urlPath, selectedGesture);
            Toast.makeText(MainActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                        //downloadFile(urlPath, selectedGesture);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

}
