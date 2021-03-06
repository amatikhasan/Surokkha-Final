package com.example.user.surokkha.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.adapter.DoctorAdapter;
import com.example.user.surokkha.adapter.HospitalAdapter;
import com.example.user.surokkha.classes.SharedPrefManager;
import com.example.user.surokkha.db.DBExternal;
import com.example.user.surokkha.model.DoctorData;
import com.example.user.surokkha.model.HospitalData;

import java.util.ArrayList;

public class ShowHospital extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView emptyHospital;
    ArrayList<HospitalData> obj = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hospital);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        String district=getIntent().getExtras().getString("district");
        String location=getIntent().getExtras().getString("location");

        recyclerView = (RecyclerView) findViewById(R.id.rv_hospital);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyHospital=findViewById(R.id.emptyHospital);

        DBExternal dbExternal = new DBExternal(getApplicationContext());

        obj = dbExternal.showHospital(district,location);
        Log.d("Check Db results", "Db obj length: "+obj.size());

        if(obj.size()>0) {
            HospitalAdapter adapter = new HospitalAdapter(this, obj);
            recyclerView.setAdapter(adapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            emptyHospital.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_update) {
            Toast.makeText(this, "Update Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_rate) {
            Toast.makeText(this, "Rate Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_logout) {
            Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
            SharedPrefManager.getmInstance(this).logout();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return false;
    }

}
