package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.test.R;
import com.example.test.network.NetworkCheck;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonStart);
        editText = findViewById(R.id.search_edit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void clickButton(View view) {

        String inputText = editText.getText().toString().trim();

        if(NetworkCheck.isNetworkConnected(this)){
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Поле не должно быть пустым", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, ActivityTwo.class);
                intent.putExtra("query", editText.getText().toString());
                intent.putExtra("apikey", getResources().getString(R.string.apiKey));
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "Похоже у вас проблемы с интернетом", Toast.LENGTH_SHORT).show();
        }
    }
}