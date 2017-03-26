package com.condingblocks.networkks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnDOwnload;
    EditText etUrl;
    TextView tvData;
    ListView lvJson;
    ArrayList<model> adapterArray = new ArrayList<>();
    Adapter adapter;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDOwnload = (Button) findViewById(R.id.btnDownload);
        etUrl = (EditText) findViewById(R.id.etUrl);
        tvData = (TextView) findViewById(R.id.tvData);
        lvJson = (ListView) findViewById(R.id.lv);

        adapter = new Adapter(adapterArray , this);

        lvJson.setAdapter(adapter);

        btnDOwnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrl.getText().toString();
                DownloadData downloadData = new DownloadData();
                downloadData.execute(url);
            }
        });

    }

    public class DownloadData extends AsyncTask<String, Void , ArrayList<model>>{

        @Override
        protected ArrayList<model> doInBackground(String... params) {
            URL url = null;
            ArrayList<model> jsonArray = new ArrayList<>();


            try {
                url = new URL("https://jsonplaceholder.typicode.com/posts");
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


                String json = sb.toString();

                jsonArray = getJson(json);

//                return sb.toString();

                return jsonArray;

            } catch (IOException e) {
                e.printStackTrace();
            }


//            HttpURLConnection  httpURLConnection = (HttpURLConnection) urlConnection;


//            return "Web page not availabe";
         return jsonArray;
        }

        @Override
        protected void onPostExecute(ArrayList<model> jsonArray) {

            Log.d(TAG, "onPostExecute: " + jsonArray.get(1));

            for (int i = 0 ;i < jsonArray.size() ; i++){
                adapterArray.add(jsonArray.get(i));
                adapter.notifyDataSetChanged();
            }



            // List View

            super.onPostExecute(jsonArray);
        }
    }

    static ArrayList<model> getJson(String json){

        //convert string to array list coming in JSON format

        ArrayList<model> modelJson = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject thisJsonObject = jsonArray.getJSONObject(i);
                model model = new model(thisJsonObject.getInt("userId") , thisJsonObject.getInt("id") ,
                        thisJsonObject.getString("title") , thisJsonObject.getString("body"));
                modelJson.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getJson: " + String.valueOf(modelJson.get(1).getBody()));
        return modelJson;
    }

}
