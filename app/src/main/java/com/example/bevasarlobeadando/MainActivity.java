package com.example.bevasarlobeadando;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Termek> termekList = new ArrayList<>();
    private Button listButton;
    private Button addTermekButton;
    private RetrofitService apiService = null;
    private Integer greatestId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();


    }

    private Termek getParameterTermek(){
        EditText termekNev = findViewById(R.id.termek_nev);
        EditText egysegAr = findViewById(R.id.egysegar);
        EditText mennyiseg = findViewById(R.id.mennyiseg);
        EditText mertekegyseg = findViewById(R.id.mertekegyseg);


        apiService.getAllTermek().enqueue(new Callback<List<Termek>>() {
            @Override
            public void onResponse(Call<List<Termek>> call, Response<List<Termek>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    termekList.clear();
                    termekList.addAll(response.body());
                    termekList.sort(Termek::getId);
                    greatestId = termekList.get(termekList.size() - 1).getId();
                    showToast(MainActivity.this, "Success");
                } else {
                    showToast(MainActivity.this, "Error");
                }
            }

            @Override
            public void onFailure(Call<List<Termek>> call, Throwable t) {
                showToast(MainActivity.this, "Error");
            }

            private void showToast(MainActivity activity, String message) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });


        try {
            Termek toBeReturned =new Termek(greatestId+1,termekNev.getText().toString(), Double.parseDouble(egysegAr.getText().toString()), Double.parseDouble(mennyiseg.getText().toString()), mertekegyseg.getText().toString());
            return toBeReturned;
        }catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Hiba", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    private void init() {
        apiService= RetrofitClient.getInstance().create(RetrofitService.class);
        addTermekButton = findViewById(R.id.hozzaadas);
        addTermekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.createTermek(getParameterTermek()).enqueue(new Callback<Termek>() {
                    @Override
                    public void onResponse(Call<Termek> call, Response<Termek> response) {
                        Toast.makeText(MainActivity.this, "Sikeres hozzáadás", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Termek> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Hozzáadás sikertelen", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        listButton = findViewById(R.id.listazasiNezetGomb);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}