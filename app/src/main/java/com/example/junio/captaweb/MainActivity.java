package com.example.junio.captaweb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.junio.captaweb.model.Book;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://fast-eyrie-18313.herokuapp.com/books.json";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String CREATED_AT = "created_at";
    private static final String UPDATED_AT = "updated_at";
    TextView text,text1,text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        text = (TextView) findViewById(R.id.resultado);
        text1 = (TextView) findViewById(R.id.resultado1);
        text2 = (TextView) findViewById(R.id.resultado2);

        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Atualizando dados, aguarde");

        new ConnectionManager(dialog,this).execute();

    }


    private class ConnectionManager extends AsyncTask<Context, Void, List<Book>> {

        ProgressDialog progress;
        MainActivity act;
        private ConnectionManager(ProgressDialog progress, MainActivity act){
            this.progress=progress;
            this.act=act;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected List<Book> doInBackground(Context... params) {
            List<Book> list=new ArrayList<>();

            try {
                JSONArray jsonArray=listarLivros();

                List<HashMap<String ,String>> listBook=new ArrayList<HashMap<String, String>>();
                for (int i=0;i<jsonArray.length();i++){

                    JSONObject book=jsonArray.getJSONObject(i);
                    //HashMap<String,String> map=new HashMap<>();
                    Book nBook=new Book();
                    int idBook= Integer.parseInt(book.get("id").toString());
                    nBook.setId(idBook);
                    nBook.setTitle(book.get("title").toString());
                    nBook.setAuthor(book.get("author").toString());

                    list.add(nBook);

                  //  map.put("title",book.getString("title"));

                }



            } catch (IOException e) {
                e.printStackTrace();

                Log.d("--->>","Deu erro: "+e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);


            if(books.isEmpty()){
                text.setText("Erro");
            }else {
                for (Book book:books) {
                    text.setText(String.valueOf(book.getId()));
                    text1.setText(book.getTitle());
                    text2.setText(book.getAuthor());
                }
            }
            progress.dismiss();
        }
    }




    public JSONArray listarLivros() throws IOException,JSONException {


        java.net.URL url = new URL(URL);


        HttpURLConnection urlConnection = (HttpURLConnection)
                url.openConnection();


        urlConnection.setRequestMethod("GET");

        urlConnection.setRequestProperty("Content-Type","application.json");

        InputStream inputStream;

        if(urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST){
            inputStream=urlConnection.getInputStream();
        }else{
            inputStream=urlConnection.getErrorStream();
        }

        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String temp,response="";

        while ((temp=reader.readLine())!= null){
            response += temp;
        }

        Log.d("-->>",!response.isEmpty()?response:"nada");
        Log.d("@@@@@","- Fin listagem de livros");

        return new JSONArray(response);
    }

}

