package com.example.bevasarlobeadando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private List<Termek> termekList = new ArrayList<>();
    private ListView termekekListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        RetrofitService apiService = RetrofitClient.getInstance().create(RetrofitService.class);
        loadTermekek(apiService);

    }


    private void loadTermekek(RetrofitService apiService) {
        apiService.getAllTermek().enqueue(new Callback<List<Termek>>() {
            @Override
            public void onResponse(Call<List<Termek>> call, Response<List<Termek>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    termekList.clear();
                    termekList.addAll(response.body());
                    termekekListView.setAdapter(new TermekAdapter(termekList, ListActivity.this));
                } else {
                    Toast.makeText(ListActivity.this, "Hiba a termék betöltésénél", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Termek>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Hiba a termék betöltésénél", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void init() {
        termekekListView = findViewById(R.id.termekek_list);
        termekekListView.setAdapter(new TermekAdapter(termekList, this));

        Button back = findViewById(R.id.vissza);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        termekekListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Termek selectedTermek = termekList.get(position);


                Intent intent = new Intent(ListActivity.this, TermekActivity.class);

                //ID-m mindig 0 valamiért :(
                selectedTermek.setId(Integer.parseInt(view.getTag().toString()));
                intent.putExtra("termek", selectedTermek);
                startActivity(intent);
            }
        });
    }
}