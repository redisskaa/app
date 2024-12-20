package com.example.test;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.test.models.request.EverythingRequest;
import com.example.test.models.request.SourcesRequest;
import com.example.test.models.request.TopHeadlinesRequest;
import com.example.test.models.response.ArticleResponse;
import com.example.test.models.response.SourcesResponse;
import com.example.test.network.APIClient;
import com.example.test.network.APIService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsApiClient {
    private final String mApiKey;
    private Map<String, String> query;
    private final APIService mAPIService;

    public NewsApiClient(String apiKey){
        mApiKey = apiKey;
        mAPIService = APIClient.getAPIService();
        query = new HashMap<>();
        query.put("apiKey", apiKey);
    }

    //Callbacks
    public interface SourcesCallback{
        void onSuccess(SourcesResponse response);
        void onFailure(Throwable throwable);
    }

    public interface ArticlesResponseCallback{
        void onSuccess(ArticleResponse response);
        void onFailure(Throwable throwable);
    }


    private Throwable errMsg(String str) {
        Throwable throwable = null;
        try {
            JSONObject obj = new JSONObject(str);
            throwable = new Throwable(obj.getString("code"));
        } catch (JSONException e) {
            e.getStackTrace();
        }

        if (throwable == null){
            throwable = new Throwable("An error occured");
        }


        return throwable;
    }

    private Map<String, String> createQuery(){
        query = new HashMap<>();
        query.put("apiKey", mApiKey);
        return query;
    }

    public void getSources(SourcesRequest sourcesRequest, final SourcesCallback callback){
        query = createQuery();
        query.put("category", sourcesRequest.getCategory());
        query.put("language", sourcesRequest.getLanguage());
        query.put("country", sourcesRequest.getCountry());

        query.values().removeAll(Collections.singleton(null));


        mAPIService.getSources(query)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<SourcesResponse> call, @NonNull Response<SourcesResponse> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            callback.onSuccess(response.body());
                        } else {
                            try {
                                assert response.errorBody() != null;
                                callback.onFailure(errMsg(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SourcesResponse> call, @NonNull Throwable throwable) {
                        callback.onFailure(throwable);
                    }
                });
    }
    public void getTopHeadlines(TopHeadlinesRequest topHeadlinesRequest, final ArticlesResponseCallback callback){


        query = createQuery();
        query.put("country", topHeadlinesRequest.getCountry());
        query.put("language", topHeadlinesRequest.getLanguage());
        query.put("category", topHeadlinesRequest.getCategory());
        query.put("sources", topHeadlinesRequest.getSources());
        query.put("q", topHeadlinesRequest.getQ());
        query.put("pageSize", topHeadlinesRequest.getPageSize());
        query.put("page", topHeadlinesRequest.getPage());

        query.values().removeAll(Collections.singleton(null));
        query.values().removeAll(Collections.singleton("null"));


        mAPIService.getTopHeadlines(query)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull Response<ArticleResponse> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            callback.onSuccess(response.body());
                        } else {
                            try {
                                assert response.errorBody() != null;
                                callback.onFailure(errMsg(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable throwable) {
                        callback.onFailure(throwable);
                    }
                });
    }
    public void getEverything(EverythingRequest everythingRequest, final ArticlesResponseCallback callback){
        query = createQuery();
        query.put("q", everythingRequest.getQ());
        query.put("sources", everythingRequest.getSources());
        query.put("domains", everythingRequest.getDomains());
        query.put("from", everythingRequest.getFrom());
        query.put("to", everythingRequest.getTo());
        query.put("language", everythingRequest.getLanguage());
        query.put("sortBy", everythingRequest.getSortBy());
        query.put("pageSize", everythingRequest.getPageSize());
        query.put("page", everythingRequest.getPage());

        query.values().removeAll(Collections.singleton(null));
        query.values().removeAll(Collections.singleton("null"));

        mAPIService.getEverything(query).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull Response<ArticleResponse> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            callback.onSuccess(response.body());
                            Log.e("MYTAG", "HttpURLConnection: " + response.code());
                        } else {
                            Log.e("MYTAG", "HttpURLConnection: " + response.code());
                            try {
                                assert response.errorBody() != null;
                                callback.onFailure(errMsg(response.errorBody().string()));
                            } catch (IOException e) {
                                System.out.println(e.getStackTrace().length);

                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable throwable) {
                        callback.onFailure(throwable);
                    }
                });
    }
}