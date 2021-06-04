package com.example.artex.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.artex.Adapter.AllProductsHomeAdapter;
import com.example.artex.Adapter.HomeSliderAdapter;
import com.example.artex.Class.Product;
import com.example.artex.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView aboutUsTxtBtn;
    private RecyclerView allProductRecyclerView;
    private ArrayList<Product> allProductsList = new ArrayList<>();
    private HomeSliderAdapter allProductsHomeAdapter;
    private SnapHelper snapHelper;
    private Button startShoppingBtn;
    public static ArrayList<Product> chart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        aboutUsTxtBtn = findViewById(R.id.about_us_txt_btn);
        allProductRecyclerView = findViewById(R.id.all_products_recyclerview);
        startShoppingBtn = findViewById(R.id.start_shoping_now_btn);

        //navigation to about activity
        aboutUsTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AboutIntent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(AboutIntent);
            }
        });

        allProductsList.add(new Product("product1","fouta","",0f,"https://firebasestorage.googleapis.com/v0/b/artex-f0622.appspot.com/o/coussin%20test.jpg?alt=media&token=712b83c6-26ad-4a26-9992-0ff316d0ba30",0,0));
        allProductsList.add(new Product("product1","fouta","",0f,"https://firebasestorage.googleapis.com/v0/b/artex-f0622.appspot.com/o/towel%203%20test.jpg?alt=media&token=fbe254fc-5a5c-4758-ab07-ebdec038b26f",0,0));
        allProductsList.add(new Product("product3","fouta","",0f,"https://firebasestorage.googleapis.com/v0/b/artex-f0622.appspot.com/o/towel%20test.jpg?alt=media&token=5ebd3eb3-384d-453c-aa7a-2d7a28832d35",0,0));
        allProductsList.add(new Product("product4","fouta","",0f,"https://firebasestorage.googleapis.com/v0/b/artex-f0622.appspot.com/o/towel2%20test.png?alt=media&token=5f336512-709a-4345-95b5-3e1eb9c37a3c",0,0));
        allProductsList.add(new Product("product5","fouta","",0f,"https://firebasestorage.googleapis.com/v0/b/artex-f0622.appspot.com/o/towel4%20test.jpg?alt=media&token=a1883bd2-3ff8-4c91-963c-4f939113114b",0,0));
        allProductsList.add(new Product("product6","fouta","",0f,"https://firebasestorage.googleapis.com/v0/b/artex-f0622.appspot.com/o/towel5%20test.jpg?alt=media&token=17c4669e-f566-4ca8-8c6a-fe5ed1a17dbd",0,0));
        allProductsList.add(new Product("product7","fouta","",0f,"https://firebasestorage.googleapis.com/v0/b/artex-f0622.appspot.com/o/towel5%20test.jpg?alt=media&token=17c4669e-f566-4ca8-8c6a-fe5ed1a17dbd",0,0));


        LinearLayoutManager horizontalLinearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        allProductRecyclerView.setLayoutManager(horizontalLinearLayoutManager);

        snapHelper = new LinearSnapHelper();

        snapHelper.attachToRecyclerView(allProductRecyclerView);

        //attach productsList to the adapter
        allProductsHomeAdapter = new HomeSliderAdapter(this, allProductsList);
        allProductRecyclerView.setAdapter(allProductsHomeAdapter);

        startShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShopIntent = new Intent(HomeActivity.this, ShopActivity.class);
                startActivity(ShopIntent);
            }
        });

    }
}