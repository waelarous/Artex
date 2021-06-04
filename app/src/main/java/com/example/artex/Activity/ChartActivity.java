package com.example.artex.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.widget.TextView;

import com.example.artex.Adapter.AllProductsHomeAdapter;
import com.example.artex.Class.Product;
import com.example.artex.R;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private RecyclerView allProductRecyclerView;
    private AllProductsHomeAdapter allProductsHomeAdapter;
    private TextView totalTxt;
    private float total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        allProductRecyclerView = findViewById(R.id.all_products_recyclerview);
        totalTxt = findViewById(R.id.total_txt);

        allProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        allProductsHomeAdapter = new AllProductsHomeAdapter(this, HomeActivity.chart);
        allProductRecyclerView.setAdapter(allProductsHomeAdapter);

        for (Product item: HomeActivity.chart) {
            total += item.getPrice();
        }

        totalTxt.setText("(Total = " + total + ")");

    }

    public void updateData(String data){
        totalTxt.setText(data);
    }
}