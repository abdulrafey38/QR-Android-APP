package com.example.qr;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Background extends AsyncTask<String,Void,String> {
    Context ctx;
    Background(Context ctx)
    {
        this.ctx=ctx;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url= Constants.server+"/prac_send.php";
        String method=params[0];
        if(method.equals("register"))
        {

            String student_id=params[1];
            String course_id=params[2];
            String section_id=params[3];
            String attendance=params[4];
            String date=params[5];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data= URLEncoder.encode("student_id","UTF-8")+"="+URLEncoder.encode(student_id,"UTF-8")+
                        "&"+URLEncoder.encode("course_id","UTF-8")+"="+URLEncoder.encode(course_id,"UTF-8")+
                        "&"+ URLEncoder.encode("section_id","UTF-8")+"="+URLEncoder.encode(section_id,"UTF-8")+
                        "&"+ URLEncoder.encode("attendance","UTF-8")+"="+URLEncoder.encode(attendance,"UTF-8")+
                        "&"+ URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Attendance Saved";
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
