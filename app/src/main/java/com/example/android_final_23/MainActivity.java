package com.example.android_final_23;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button download;
    private TextView httpText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpText = (TextView) findViewById(R.id.httpText);
        download = (Button) findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GoodTask().execute("http://atm201605.appspot.com/h");
            }
        });
    }
    class GoodTask extends AsyncTask<String, Integer, String> {
        private static final int TIME_OUT = 1000;
        String jsonString1 = "";
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(String... countTo) {
            try{
                HttpURLConnection conn = null;
                URL url = new URL(countTo[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "UTF-8"));
                jsonString1 = reader.readLine();
                reader.close();
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                if (jsonString1.equals("")) {
                    Thread.sleep(1000);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return "網路中斷"+e;
            }
            return jsonString1;
        }
        public void onPostExecute(String result )
        { super.onPreExecute();
            httpText.setText(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}