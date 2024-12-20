package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ActivityTwo extends AppCompatActivity {
    String query;
    String apiKey;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two);
        EverthingRequest();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void EverthingRequest (){

        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        apiKey = intent.getStringExtra("apikey");
        lang = intent.getStringExtra("lang");

        NewsApiClient newsApiClient = new NewsApiClient(apiKey);
        EverythingRequest builder =  new EverythingRequest.Builder()
                .q(query)
                .language(lang)
                .pageSize(15)
                .page(1)
                .build();

        NewsApiClient.ArticlesResponseCallback callback = new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse res) {
                ProgressBar pBar = findViewById(R.id.progressBar);
                ListView listView = findViewById(R.id.list_item1);
                List<Article> itemList = new ArrayList<>();
                int pageSize = Integer.parseInt(builder.getPageSize());
                Log.d("MYTAG", "pageSize: " + pageSize);
                setTitle("Найдено новостей: " + res.getTotalResults());
                String results = String.valueOf(res.getTotalResults());

                if (res.getTotalResults() <= 20){
                    pageSize = res.getTotalResults();
                    Log.e("MYTAG", "сработало: " + pageSize);
                }

                if (results.equals("0")){
                    Toast.makeText(ActivityTwo.this, "Новостей нет, измените запрос",
                            Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    if (res.getStatus().equals("ok")){
                        try {
                            for (int i = 0; i < pageSize; i++) {
                                itemList.add(new Article(
                                        res.getArticles().get(i).getTitle(),
                                        "",
                                        res.getArticles().get(i).getUrlToImage())
                                );
                                Log.d("MYTAG", "for: " + res.getArticles().get(i).getContent());
                            }
                        }catch (IndexOutOfBoundsException e){
                            Log.e("MYTAG", "IndexOutOfBoundsException: " + e.getMessage());
                        }

                        listView.setVisibility(View.VISIBLE);
                        pBar.setVisibility(View.GONE);
                        CustomAdapter adapter = new CustomAdapter(ActivityTwo.this, itemList);
                        listView.setAdapter(adapter);
                        Log.d("MYTAG", "itemList: " + Arrays.toString(itemList.toArray()));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intentFull = new Intent(ActivityTwo.this, FullActivity.class);

                                if (lang.equals("ru")){
                                    intentFull.putExtra("content", res.getArticles().get(position).getDescription().trim());
                                }else {
                                    intentFull.putExtra("content", res.getArticles().get(position).getContent().trim());
                                }

                                intentFull.putExtra("imageUrl", res.getArticles().get(position).getUrlToImage());
                                intentFull.putExtra("url", res.getArticles().get(position).getUrl());
                                startActivity(intentFull);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                String[] errors = getResources().getStringArray(R.array.errors);

                switch (Objects.requireNonNull(throwable.getMessage())) {
                    case "apiKeyDisabled":
                        Toast.makeText(ActivityTwo.this,
                                errors[0], Toast.LENGTH_LONG).show();
                        break;
                    case "apiKeyExhausted":
                        Toast.makeText(ActivityTwo.this,
                                errors[1], Toast.LENGTH_LONG).show();
                        break;
                    case "apiKeyInvalid":
                        Toast.makeText(ActivityTwo.this,
                                errors[2], Toast.LENGTH_LONG).show();
                        break;
                    case "apiKeyMissing":
                        Toast.makeText(ActivityTwo.this,
                                errors[3], Toast.LENGTH_LONG).show();
                        break;
                    case "parameterInvalid":
                        Toast.makeText(ActivityTwo.this,
                                errors[4], Toast.LENGTH_LONG).show();
                        break;
                    case "parametersMissing":
                        Toast.makeText(ActivityTwo.this,
                                errors[5], Toast.LENGTH_LONG).show();
                        break;
                    case "rateLimited":
                        Toast.makeText(ActivityTwo.this,
                                errors[6], Toast.LENGTH_LONG).show();
                        break;
                    case "sourcesTooMany":
                        Toast.makeText(ActivityTwo.this,
                                errors[7], Toast.LENGTH_LONG).show();
                        break;
                    case "sourceDoesNotExist":
                        Toast.makeText(ActivityTwo.this,
                                errors[8], Toast.LENGTH_LONG).show();
                        break;
                    case "unexpectedError":
                        Toast.makeText(ActivityTwo.this,
                                errors[9], Toast.LENGTH_LONG).show();
                        break;

                    default: Toast.makeText(ActivityTwo.this, "Разработчик не учел эту ошибку ((", Toast.LENGTH_LONG).show();
                }

                finish();
            }
        };
        newsApiClient.getEverything(builder,callback);

    }
}