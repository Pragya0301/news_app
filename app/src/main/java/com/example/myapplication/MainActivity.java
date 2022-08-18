package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNewsTitles = new ArrayList<>();
    //private TextView my_text_view;
    //private Button getBtn;

    private TextView result;
    String received="";
    private OkHttpClient client;
    String strUrl = "https://bing-news-search1.p.rapidapi.com/news?safeSearch=Off&textFormat=Raw";
    String key = "094e5b1b0dmshcd512ca3a4e09b5p188f63jsn2f79bbb241d7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: started..");
        //AsyncTask
        //result = (TextView) findViewById(R.id.output);
        //getBtn = (Button) findViewById(R.id.button2);
        client = new OkHttpClient();

        //Async request
        new fetchNews().execute();
        /*getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getWebservice();
                //Async request
                new fetchNews().execute();
            }
        });
        */


        /*int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            result = (TextView) findViewById(R.id.output);
            getBtn = (Button) findViewById(R.id.button2);
            getBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getWebservice();
                }
            });
            client = new OkHttpClient();
            //assign the TextView
            //my_text_view = findViewById(R.id.response_received);
            //OkayHttp
            //getWebservice();


        }*/


    }

    //Subclass
    public class fetchNews extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            Log.d("status","In postExecute");
            Log.d("status",received);
            //result.setText(received);
            initRecyclerView();
            Log.d("status","initRecyclerView called..");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("status","In preExecute");
        }


        @Override
        protected String doInBackground(String... strings) {
            //getWebservice();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://bing-news-search1.p.rapidapi.com/news?safeSearch=Off&textFormat=Raw")
                    .get()
                    .addHeader("x-bingapis-sdk", "true")
                    .addHeader("x-rapidapi-key", "094e5b1b0dmshcd512ca3a4e09b5p188f63jsn2f79bbb241d7")
                    .addHeader("x-rapidapi-host", "bing-news-search1.p.rapidapi.com")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                received = response.body().string();
                Log.d("status",received);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //this line yahin rehni chahiye
                            //result.setText(received);
                            // on postEceute... me dall saktye h
                            JSONObject json = null;
                            json = new JSONObject(received);
                            final JSONArray news = json.getJSONArray("value");

                            for (int i = 0; i < news.length(); ++i) {
                                JSONObject rec = news.getJSONObject(i);

                                //News title = name
                                String name = rec.getString("name");
                                // ...
                                Log.d("status",name);
                                mNewsTitles.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init.recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mNewsTitles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getWebservice(){
        /*final Request request = new Request.Builder()
                .url("https://bing-news-search1.p.rapidapi.com/news?safeSearch=Off&textFormat=Raw")
                .get()
                .addHeader("x-bingapis-sdk", "true")
                .addHeader("x-rapidapi-key", "520adff99emsh33453e8a236727dp14b3b9jsn0cb064df7b67")
                .addHeader("x-rapidapi-host", "bing-news-search1.p.rapidapi.com")
                .build();*/
         final Request request = new Request.Builder().url("https://www.ssaurel.com/tmp/todos").build();
        Log.d("status","REquest.Builer().. done successfully");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("status","onFailure");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("status","onFailure->runonUithread");
                        e.printStackTrace();
                        result.setText("Failure !" );
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("status","onResponse");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("status","onREsponse runonUithread");
                        try {
                            Log.d("status","try");
                            result.setText(response.body().string());
                            Log.d("status","try2");
                            Log.d("status",response.body().string());
                        } catch (IOException ioe){
                            ioe.printStackTrace();
                            Log.d("status","catch");
                            result.setText("Error during get body");
                            Log.d("status","catch2");
                        }
                    }
                });
            }
        });
    }



    /*public void disable(View v){
        View myView = findViewById(R.id.button2);
        myView.setEnabled(false);
        Button btn = (Button) myView;
        btn.setText("New Disabled");

       v.setEnabled(false);
        Button button = (Button) v; //cast View into Button, to change the text, since setText is available for Button
        button.setText("Disabled");

        //Log.d("success", "Disabled the button");
    }*/

    public class NewsFeed{
        protected String news_title;

        public NewsFeed(String news_title) {
            this.news_title = news_title;
        }
    }

}


