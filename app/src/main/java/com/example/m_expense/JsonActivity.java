//Used university materials
package com.example.m_expense;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class JsonActivity extends AppCompatActivity{

    MyDatabaseHelper myDB;
    MyDatabaseHelper myDB2;
    ArrayList<String> trip_id_json, trip_name_json,trip_destination_json, trip_date_json, trip_risk_json, trip_description_json;
    ArrayList<String> expence_id2_json,expence_typeExpense_json,expence_amountExpense_json,expence_timeExpense_json,expence_commentExpense_json;
    StringBuffer buffer = new StringBuffer();
    private WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        myDB = new MyDatabaseHelper(JsonActivity.this);
        myDB2 = new MyDatabaseHelper(JsonActivity.this);
        trip_id_json = new ArrayList<>();
        trip_name_json = new ArrayList<>();
        trip_destination_json = new ArrayList<>();
        trip_date_json = new ArrayList<>();
        trip_risk_json = new ArrayList<>();
        trip_description_json = new ArrayList<>();
        expence_id2_json = new ArrayList<>();
        expence_typeExpense_json = new ArrayList<>();
        expence_amountExpense_json = new ArrayList<>();
        expence_timeExpense_json = new ArrayList<>();
        expence_commentExpense_json = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        while (cursor.moveToNext())
        {
            trip_id_json.add(cursor.getString(0));
            trip_name_json.add(cursor.getString(1));
            trip_destination_json.add(cursor.getString(2));
            trip_date_json.add(cursor.getString(3));
            trip_risk_json.add(cursor.getString(4));
            trip_description_json.add(cursor.getString(5));
        }
        buffer.append("{");
        for(int i=0;i< trip_id_json.size();i++)
        {
            buffer.append("{\"tripID\":\""+trip_id_json.get(i)+"\"," +
                    "\"tripName\":\""+trip_name_json.get(i)+"\","+
                    "\"tripDestination\":\""+trip_destination_json.get(i)+"\","+
                    "\"tripDate\":\""+trip_date_json.get(i)+"\","+
                    "\"tripRisk\":\""+trip_risk_json.get(i)+"\","+
                    "\"tripDescription\":\""+trip_description_json.get(i)+"\","+
                    "\"expences\":[");
            Cursor cursor2 = myDB2.readTripData2(trip_id_json.get(i));
            while (cursor2.moveToNext())
            {
                expence_id2_json.add(cursor2.getString(0));
                expence_typeExpense_json.add(cursor2.getString(1));
                expence_amountExpense_json.add(cursor2.getString(2));
                expence_timeExpense_json.add(cursor2.getString(3));
                expence_commentExpense_json.add(cursor2.getString(4));
            }
            for(int j=0;j< expence_id2_json.size();j++)
            {
                buffer.append("{\"expencesID\":\""+expence_id2_json.get(j)+"\"," +
                        "\"expencesType\":\""+expence_typeExpense_json.get(j)+"\","+
                        "\"expencesAmount\":\""+expence_amountExpense_json.get(j)+"\","+
                        "\"expencesTime\":\""+expence_timeExpense_json.get(j)+"\","+
                        "\"expencesComment\":\""+expence_commentExpense_json.get(j)+"\""+
                        "\"},");
            }
            buffer.append("]");
        }
        buffer.append("}");
        Log.d("msg json2",""+buffer);
        browser = (WebView) findViewById(R.id.webkit);
        try {
            URL pageURL = new URL(getString(R.string.url));
            HttpURLConnection con =
                    (HttpURLConnection) pageURL.openConnection();
            String jsonString = getString(R.string.json);
            JsonThread myTask = new JsonThread(this, con, jsonString);
            Thread t1 = new Thread(myTask, "JSON Thread");
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class JsonThread implements Runnable{
        private AppCompatActivity activity;
        private HttpURLConnection con;
        private String jsonPayLoad;
        public JsonThread(AppCompatActivity activity,
                          HttpURLConnection con, String jsonPayload)
        {
            this.activity = activity;
            this.con = con;
            this.jsonPayLoad = jsonPayload;
        }

        @Override
        public void run() {
            String response = "";
            if (prepareConnection()) {
                response = postJson();
            } else {
                response = "Error preparing the connection";
            }
            showResult(response);
        }

        private void showResult(String response) {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    String page = generatePage(response);
                    ((JsonActivity)activity).browser.loadData(page,
                            "text/html", "UTF-8");
                }
            });
        }
        private String generatePage(String content) {
            return "<html><body><p>" + content + "</p></body></html>";
        }
        private String postJson() {
            String response = "";
            try {
                String postParameters = "jsonpayload="
                        + URLEncoder.encode(jsonPayLoad, "UTF-8");
                con.setFixedLengthStreamingMode(postParameters.getBytes().length);
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(postParameters);
                out.close();
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    response = readStream(con.getInputStream());
                } else {
                    response = buffer.toString();
                }
            } catch (Exception e) {
                response = "Error executing code";
            }    return response;
        }

        private String readStream(InputStream in) {
            StringBuilder sb = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in))) {
                String nextLine = "";
                while ((nextLine = reader.readLine()) != null) {
                    sb.append(nextLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }    return sb.toString();
        }

        private boolean prepareConnection() {
            try {
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                return true;
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}











