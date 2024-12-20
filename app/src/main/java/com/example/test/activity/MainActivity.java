package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.test.R;
import com.example.test.network.NetworkCheck;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    Spinner spinner;
    ArrayList<String> list;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonStart);
        editText = findViewById(R.id.search_edit);
        spinner = findViewById(R.id.mySpinner);
        Spinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // String selectedItem = parent.getItemAtPosition(position).toString();

                String item = list.get(position);
                switch (item){
                    case "Русский":
                        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                        lang = "ru";
                        break;
                    case "Английский":
                        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                        lang = "en";
                        break;
                    case "Все языки":
                        lang = "";
                        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        lang = "ru";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Действия, если ничего не выбрано
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void Spinner(){
        list = new ArrayList<>();
        list.add("Все языки");
        list.add("Русский");
        list.add("Английский");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
                intent.putExtra("lang", lang);
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "Похоже у вас проблемы с интернетом", Toast.LENGTH_SHORT).show();
        }
    }
}