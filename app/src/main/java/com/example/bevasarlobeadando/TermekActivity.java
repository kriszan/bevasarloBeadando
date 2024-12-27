package com.example.bevasarlobeadando;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermekActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termek);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();


    }


    private void init() {
        Button modosito = findViewById(R.id.t_modositas);
        Termek recivedTermek = getIntent().getParcelableExtra("termek");

        EditText nev = findViewById(R.id.t_termek_nev);
        nev.setText(recivedTermek.getTermekNev());
        EditText ar = findViewById(R.id.t_egysegar);
        ar.setText(String.valueOf(recivedTermek.getEgysegAr()));
        EditText mennyiseg = findViewById(R.id.t_mennyiseg);
        mennyiseg.setText(String.valueOf(recivedTermek.getMennyiseg()));
        EditText mertekegyseg = findViewById(R.id.t_mertekegyseg);
        mertekegyseg.setText(String.valueOf(recivedTermek.getMertekegyseg()));

        modosito.setOnClickListener(v -> {
            RetrofitService service = RetrofitClient.getInstance().create(RetrofitService.class);
            Termek ttoadd = new Termek(recivedTermek.getId(), nev.getText().toString(), Double.parseDouble( ar.getText().toString()),Double.parseDouble ( mennyiseg.getText().toString()), mertekegyseg.toString());
            Call<Termek> call = service.updateTermek(recivedTermek.getId(), ttoadd);


            call.enqueue(new Callback<Termek>() {
                @Override
                public void onResponse(Call<Termek> call, Response<Termek> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(TermekActivity.this, "Product updated successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TermekActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String error = response.errorBody().toString();
                        Toast.makeText(TermekActivity.this, "Failed to update product. Server error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Termek> call, Throwable t) {
                    Toast.makeText(TermekActivity.this, "Failed to update product. Network error!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button torol = findViewById(R.id.t_torles);
        torol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getBaseContext())
                        .setTitle("Törlés")
                        .setMessage("Biztos törölni akarod?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                RetrofitService service = RetrofitClient.getInstance().create(RetrofitService.class);
                                Call<Void> call = service.deleteTermek(recivedTermek.getId());

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(TermekActivity.this, "Product deleted successfully!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(TermekActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(TermekActivity.this, "Failed to delete product. Server error!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(TermekActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        Button megse = findViewById(R.id.t_megse);
        megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermekActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}