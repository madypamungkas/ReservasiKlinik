package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import id.technow.reservasiklinik.Adapter.DataPasienAdapter;
import id.technow.reservasiklinik.Model.DataPasienModel;

public class DataPasien extends AppCompatActivity {
    private RecyclerView rvPasien;
    ArrayList<DataPasienModel> model;
    DataPasienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pasien);
        rvPasien = findViewById(R.id.rvPasien);

    }
    private void loadData(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        adapter = new DataPasienAdapter( DataPasien.this,  model);
        rvPasien.setLayoutManager(new LinearLayoutManager(DataPasien.this));
        rvPasien.setLayoutManager(staggeredGridLayoutManager);
        rvPasien.setAdapter(adapter);
    }

    public void tambahAnggota(View view) {
        Intent intent = new Intent(DataPasien.this, TambahPasienActivity.class);
        startActivity(intent);
    }
}
