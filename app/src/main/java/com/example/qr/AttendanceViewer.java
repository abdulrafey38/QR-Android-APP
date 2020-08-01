package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AttendanceViewer extends AppCompatActivity {


    static ListView lv;
    ArrayList<String> holder = new ArrayList<>();
    String courseName;
    String students_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_viewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        students_id = intent.getStringExtra("student_id");
        lv = (ListView) findViewById(R.id.courseLv);
        fetchdata();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position>=0)
                {
                    String subject = (String)lv.getItemAtPosition(position);
                    Intent intent = new Intent(AttendanceViewer.this,DateWiseAttendance.class);
                    intent.putExtra("course",subject);
                    intent.putExtra("students_id",students_id);
                    startActivity(intent);
                }
            }
        });

    }

    public void fetchdata(/*String connection_str*/) {
        Intent intent = getIntent();
        String student_id = intent.getStringExtra("student_id");
        String connection_str = Constants.server+"/course_fetch.php?student_id="+student_id;


        class background extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {

                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;

                    holder.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        holder.add(name);
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
}
