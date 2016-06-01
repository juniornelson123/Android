package com.example.junio.captaweb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.junio.captaweb.model.Book;

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
import java.util.List;

/**
 * Created by junio on 01/06/16.
 */
public class BookAsynTask extends AsyncTask<Void,Void,List<Book>> {
    public static final String URI="https://fast-eyrie-18313.herokuapp.com/";

    ProgressDialog progress;
    Activity act;

    public BookAsynTask(ProgressDialog dialog, Activity act){
        progress=dialog;
        this.act=act;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Executa o ProgressDialog ate que todos os dados sejam carregados
        progress.show();
    }

    @Override
    protected List<Book> doInBackground(Void... params) {
        //Retorna uma lista com os livros

        List<Book> list=new ArrayList<>();

        try {

            JSONArray jsonArray=listar("books");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject book=jsonArray.getJSONObject(i);

                Book mBook=new Book();
                int idBook=Integer.parseInt(book.get("id").toString());
                mBook.setId(idBook);
                mBook.setTitle(book.get("title").toString());
                mBook.setAuthor(book.get("author").toString());

                list.add(mBook);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    //METODO QUE FAZ A CONEXAO COM O WEBSERVICE E RETORNA UM JSONARRAY
    public JSONArray listar(String table) throws IOException,JSONException{

        //CRIA A URL
        URL url=new URL(URI+table+".json");

        //ABRE A CONEXAO COM A URL
        HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();

        //SELECIONA O METODO HTTP QUE SERA UTILIZADO
        urlConnection.setRequestMethod("GET");

        //SETA AS PROPRIEDADES DE UM ARQUIVO JSON
        urlConnection.setRequestProperty("Content-Type","application.json");

        //CRIA UM STREAM PARA CONVERSAO DO JSON
        InputStream is;

        //VERIFICA SE A RESPOSTA DA CONEXAO FOI BEM SUCEDIDA E SETA O JSON CONSUMIDO
        if(urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST){
            is=urlConnection.getInputStream();
        }else {
            is=urlConnection.getErrorStream();
        }

        //CONVERTE O STREAM PARA STRING
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        String temp,response="";

        while ((temp=reader.readLine())!= null){
            response += temp;
        }

        Log.d("-->>",!response.isEmpty()?response:"nada");
        android.util.Log.d("@@@@@","- Fin listagem de livros");

        return new JSONArray(response);
    }
}
