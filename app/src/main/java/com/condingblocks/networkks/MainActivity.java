package com.condingblocks.networkks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    Button btnDOwnload;
    EditText etUrl;
    TextView tvData;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDOwnload = (Button) findViewById(R.id.btnDownload);
        etUrl = (EditText) findViewById(R.id.etUrl);
        tvData = (TextView) findViewById(R.id.tvData);

        btnDOwnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrl.getText().toString();
                DownloadData downloadData = new DownloadData();
                downloadData.execute(url);
            }
        });

    }

    public class DownloadData extends AsyncTask<String, Void , String>{

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpURLConnection = null;
            try {
                 httpURLConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int responseCode = 0;
            try {
                responseCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "doInBackground: ");
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader ir = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(ir);

                StringBuilder sb = new StringBuilder();
                String buffer;


                while( (buffer = reader.readLine()) != null){
                    sb.append(buffer);
                }

                return sb.toString();


            } catch (IOException e) {
                e.printStackTrace();
            }


//            HttpURLConnection  httpURLConnection = (HttpURLConnection) urlConnection;


            return "Web page not availabe";
        }

        @Override
        protected void onPostExecute(String s) {

            tvData.setText(s);

            super.onPostExecute(s);
        }
    }

}
