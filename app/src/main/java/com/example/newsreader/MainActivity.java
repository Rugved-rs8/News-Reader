package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NewsListRecyclerAdapter.onClickedNewsListener{

    private NewsListRecyclerAdapter adapter;
    private ArrayList<News> newsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView newsListRecyclerView = findViewById(R.id.newsListRecyclerView);
        fetchData();
        adapter = new NewsListRecyclerAdapter(newsContent, this);
        newsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsListRecyclerView.setAdapter(adapter);
    }

    public void fetchData(){
        String url = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=f8e511e78b964fe68db5218109c3a725";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    newsContent = new ArrayList<>();
                    try {
                        JSONArray newsJsonArray = response.getJSONArray("articles");
                        for(int i=0;i<newsJsonArray.length();i++){
                            JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);
                            newsContent.add(new News(newsJsonObject.getString("title"), newsJsonObject.getString("url"), newsJsonObject.getString("urlToImage")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.updateNews(newsContent);
                    adapter.notifyDataSetChanged();
                }, error -> {
                    // TODO: Handle error
                    Log.d("Api call", " NO Response");
                })

        {
            @Override
            public Map<String, String> getHeaders ()throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("key", "Value");
            return headers;
        }
        };

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onNewsClicked(int position) {
        Log.i("info", "onNewsClicked: Clicked");
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(newsContent.get(position).getUrl()));
    }
}