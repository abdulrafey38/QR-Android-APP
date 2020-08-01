package com.example.qr;
import androidx.appcompat.app.AppCompatActivity;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class homePage extends AppCompatActivity {
//=================Fetch Data=========================
    static String  email = LoginActivity.em;
    private static final String connection_str = Constants.server+"/prac_fetch.php?email="+email;
    String serverurl = Constants.server+"/prac_send.php";
    static ListView lv;
    ArrayList<String> holder = new ArrayList<>();



//=================Fetch Data==========================


//================Send data============================
    public String student_id;
    public String[] values = new String[]{};
    public String dates;
    public String section_id;
    public String course_id;
    public String attendance;
    ProgressDialog progressDialog;


//================Send data============================

    public static TextView val1Tv;
    public static TextView resultTv;
    Button markAttendance,viewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
//========================================menu call
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//=================================================
        fetchdata();
        markAttendance = (Button) findViewById(R.id.markAttendancePageBtn);
        markAttendance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this,AttendenceScanner.class);
                intent.putExtra("student_id",student_id);
                startActivity(intent);

            }
        });
        viewer = (Button) findViewById(R.id.viewAttendanceBtn);
        viewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this,AttendanceViewer.class);
                intent.putExtra("student_id",student_id);
                startActivity(intent);
            }
        });




    }
    //============menu making====================
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                MySingleton.getInstance(this).logout();
                finish();
                Intent intentlogout = new Intent(this, LoginActivity.class);
                this.startActivity(intentlogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void fetchdata() {

        lv = (ListView) findViewById(R.id.nameLv);

        class background extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {

                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;

                    holder.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String id = jsonObject.getString("id");
                        holder.add(name);
                        holder.add(jsonObject.getString("S_UID"));
                        student_id = id;
                        Intent intent = new Intent(homePage.this,AttendenceScanner.class);
                        intent.putExtra("student_id",student_id);
                    }

                    ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, holder);
                    lv.setAdapter(at);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... strings) {


                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuffer data = new StringBuffer();

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        data.append(line + "\n");
                    }
                    bufferedReader.close();
                    //    httpURLConnection.disconnect();
                    return data.toString();

                }
                catch (Exception e) {
                    return e.getMessage();
                }


            }
        }
        background bg = new background();
        bg.execute(connection_str);

    }

    //======fetch=========

}




