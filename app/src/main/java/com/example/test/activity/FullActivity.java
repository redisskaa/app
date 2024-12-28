package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.test.R;
import com.squareup.picasso.Picasso;

public class FullActivity extends AppCompatActivity {

    TextView fullText, fullNewsText;
    ImageView imageFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full);

        fullNewsText = findViewById(R.id.fullNewsText);
        fullText = findViewById(R.id.full_content_text);
        imageFull = findViewById(R.id.image_full_content);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");
        Picasso.get().load(imageUrl).into(imageFull);
        setTitle(title);
        fullText.setText(content);
        fullNewsText.setText(url);
        Log.d("MYTAG", "onCreate: " + content);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}