package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.test.CustomAdapter;
import com.example.test.NewsApiClient;
import com.example.test.R;
import com.example.test.models.Article;
import com.example.test.models.request.EverythingRequest;
import com.example.test.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class ActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

            NewsApiClient newsApiClient = new NewsApiClient(getResources().getString(R.string.apiKey));
            newsApiClient.getEverything(
                    new EverythingRequest.Builder()
                            .q(query)
                            .language("ru")
                            .build(),
                    new NewsApiClient.ArticlesResponseCallback() {
                        @Override
                        public void onSuccess(ArticleResponse response) {
                            ListView listView = findViewById(R.id.list_item1);
                            List<Article> itemList = new ArrayList<>();

                            for (int i = 0; i < 30; i++) {
                                itemList.add(new Article(
                                        response.getArticles().get(i).getTitle(),
                                        response.getArticles().get(i).getDescription(),
                                        response.getArticles().get(i).getUrlToImage())
                                );
                                Log.d("MYTAG", String.valueOf(itemList.size()));
                            }

                            CustomAdapter adapter = new CustomAdapter(ActivityTwo.this, itemList);
                            listView.setAdapter(adapter);
                            Log.d("MYTAG", response.getArticles().get(0).getTitle());
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            System.out.println(throwable.getMessage());
                        }
                    }
            );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });



    }
}