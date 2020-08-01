package com.example.qr;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;

public class DateWiseAttendance extends AppCompatActivity {

    TextView present;
    double p_counter=0,t_counter=0;
    double presents;
    String course_name;
    String student_id;
    ListView lv;
    String connection_str;
    ArrayList<String> holder = new ArrayList<>();
    TextView tv1,attendanceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_wise_attendance);

        lv = (ListView) findViewById(R.id.dateWiseLV);
//        tv1 =(TextView) findViewById(R.id.dateTV);
//        attendanceTV =(TextView) findViewById(R.id.attendanceTV);
        present=(TextView) findViewById(R.id.presentET);
        fetchdata();

    }


    public void fetchdata() {
        Intent intent =getIntent();
        course_name = intent.getStringExtra("course");
        student_id = intent.getStringExtra("students_id");
        connection_str = Constants.server+"/datewise_fetch.php?course_name="+course_name+"&student_id="+student_id;


        class background extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {

                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;

                    holder.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String dates = jsonObject.getString("date");
                        String atnd = jsonObject.getString("attendance");
                        String name = dates + "                                    " + atnd;
                        holder.add(name);

                        if (atnd.equals("P"))
                        {
                            p_counter ++;
                        }

                        t_counter ++;
                    }

                    presents = (p_counter/t_counter)*100;

                    String temp = "Total Attendance "+ Double.toString(presents)+" %";
                    present.setText(temp);
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