package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class FullActivity extends AppCompatActivity {

    TextView fullText;
    ImageView imageFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full);

        fullText = findViewById(R.id.full_content_text);
        imageFull = findViewById(R.id.image_full_content);

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        String imageUrl = intent.getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(imageFull);
        fullText.setText(content);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}