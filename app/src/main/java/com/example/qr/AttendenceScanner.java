package com.example.qr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import  android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.example.qr.homePage.resultTv;
import static com.example.qr.homePage.val1Tv;

public class AttendenceScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public String student_id;
    public String[] values = new String[]{};
    public String dates;
    public String section_id;
    public String course_id;
    public String attendance;
    public String time_qr;


    ProgressDialog progressDialog;

    String disp;

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //===============================
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        Intent intent = getIntent();
        student_id = intent.getStringExtra("student_id");

//        ===============================

    }

    public void setdata()
    {
        String ans= (String) resultTv.getText();
        values = ans.split(",");
        dates = values[0];
        course_id = values[1];
        section_id = values[2];
        attendance = values[3];

    }

    @Override
    public void handleResult(Result result) {

              disp = result.getText() +"," +student_id;


////                //String ans=(String) resultTv.getText();
                values = disp.split(",");
                dates = values[0];
                course_id = values[1];
                section_id = values[2];
                attendance = values[3];
                time_qr = values[4];
                student_id =values[5];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressDialog.dismiss();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Toast.makeText(homePage.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    if (jsonObject.getString("message").equals("Data Added Successfully")) {
//
//                        //ListActivity.ma.refresh_list();
//                        finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                    Toast.makeText(AttendenceScanner.this,response,Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.hide();
                Toast.makeText(AttendenceScanner.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("student_id", student_id);
                params.put("course_id",course_id);
                params.put("section_id", section_id);
                params.put("attendance", attendance);
                params.put("dates", dates);
                params.put("time_qr",time_qr);
//                Log.d("params" , params.toString());

                return params;

            }
        };
        RequestQueue requestQueue =Volley.newRequestQueue(AttendenceScanner.this);
        requestQueue.add(stringRequest);
        //MySingleton.getInstance(homePage.this).addTorequestque(stringRequest);



        onBackPressed();

    }


    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();

    }

    protected void onResume()
    {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }





    //======send==========
//
//    public void InsertData(final String name, final String email){
//
//        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
//            @Override
//            protected String doInBackground(String... params) {
//
//                String NameHolder = name ;
//                String EmailHolder = email ;
//
//
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
//                nameValuePairs.add(new BasicNameValuePair("name", NameHolder));
//                nameValuePairs.add(new BasicNameValuePair("email", EmailHolder));
//
//                try {
//                    HttpClient httpClient = new DefaultHttpClient();
//
//                    HttpPost httpPost = new HttpPost(ServerURL);
//
//                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                    HttpResponse httpResponse = httpClient.execute(httpPost);
//
//                    HttpEntity httpEntity = httpResponse.getEntity();
//
//
//                } catch (ClientProtocolException e) {
//
//                } catch (IOException e) {
//
//                }
//                return "Data Inserted Successfully";
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//
//                super.onPostExecute(result);
//
//                Toast.makeText(MainActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
//
//            }
//        }
//
//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//
//        sendPostReqAsyncTask.execute(name, email);
//    }
//

    //======send==========

    public void setViews()
    {

    }

//    SurfaceView surfaceView;
//    Button event;
//    CameraSource cameraSource;
//    BarcodeDetector barcodeDetector;
//    final int RequestCameraPermissionID = 1001;
//
////=================QR===================
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case RequestCameraPermissionID: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                        // ActivityCompat.requestPermissions(AttendanceScanner.this,new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
//                        return;
//                    }
//
//                    try {
//                        cameraSource.start(surfaceView.getHolder());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            break;
//        }
//    }
//
//    //======================================
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attendence_scanner);
//
//        //================menu call=====================
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //===========exitButton===========================
//
//        event = (Button) findViewById(R.id.exitScannerBtn);
//        event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AttendenceScanner.this, homePage.class);
//                startActivity(intent);
//            }
//        });
//
//
////=====================QR handling=====================================================
//        surfaceView = (SurfaceView) findViewById(R.id.campreview);
//        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
//        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();
//
//
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(AttendenceScanner.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
//                    return;
//                }
//                try {
//                    cameraSource.start(surfaceView.getHolder());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                cameraSource.stop();
//            }
//        });
////qr code scanning and handling yet to be done
//
////==============================================================================================
////============================focus============================================================
//
//    }


}