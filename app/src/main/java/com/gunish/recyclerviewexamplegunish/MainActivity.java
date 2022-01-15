package com.gunish.recyclerviewexamplegunish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Object> viewItems=new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter=new RecyclerAdapter(this,viewItems);
        mRecyclerView.setAdapter(mAdapter);
        addItemsFromJSON();


    }

    private void addItemsFromJSON() {

        try {
            String jsonDataString = readJSONDataFromFile();
            JSONArray jsonArray=new JSONArray(jsonDataString);
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject itemObj=jsonArray.getJSONObject(i);
                String name=itemObj.getString("name");
                String date=itemObj.getString("date");
                Holidays holidays=new Holidays(name,date);
                viewItems.add(holidays);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    private String readJSONDataFromFile() throws IOException {
        InputStream inputStream=null;
        StringBuilder builder=new StringBuilder();
        try {
            String jsonString=null;
            inputStream=getResources().openRawResource(R.raw.holidays);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            while ((jsonString=bufferedReader.readLine())!=null)
            {
                builder.append(jsonString);
            }
        }
        finally {
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return new String(builder);
    }
}