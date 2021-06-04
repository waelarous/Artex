package com.example.artex.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artex.Adapter.AllProductsHomeAdapter;
import com.example.artex.Adapter.ColorAdapter;
import com.example.artex.Class.ColorClass;
import com.example.artex.Class.Product;
import com.example.artex.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    private Product loadedProduct;
    private ImageView productImageView;
    private TextView productNameTxt , productDescriptionTxt , productStockTxt , productPriceTxt;
    private RecyclerView productRecyclerViewColors;

    //Lists of all colors
    private ArrayList<ColorClass> colorsList;
    //color adapter
    private ColorAdapter colorsAdapter;
    //firebase realTime database reference to connect to firebase database
    private DatabaseReference colorsDatabaseReference;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        loadedProduct = (Product) getIntent().getParcelableExtra("Product");
        path = getIntent().getStringExtra("Path");


        //get data from xml and link it with our variables
        productImageView = findViewById(R.id.product_imageview);
        productNameTxt = findViewById(R.id.product_name_txt);
        productDescriptionTxt = findViewById(R.id.product_description_txt);
        productStockTxt = findViewById(R.id.product_stock_txt);
        productPriceTxt = findViewById(R.id.product_price_txt);
        productRecyclerViewColors = findViewById(R.id.product_recyclerview_color);

        //set our variable with the object that we passed it from adapter
        productNameTxt.setText(loadedProduct.getName());
        productDescriptionTxt.setText(loadedProduct.getDescription());
        productPriceTxt.setText(loadedProduct.getPrice() + " DT");

        //check if stock == 0 write not available else number of stock
        if(loadedProduct.getStock() == 0){
            productStockTxt.setText("Not Available");
            productStockTxt.setTextColor(Color.RED);
        }else{
            productStockTxt.setText(loadedProduct.getStock() + " In Stock.");
        }


        //picasso is used to get image url from a server to put it in imageview
        Picasso.get().load(loadedProduct.getImage()).into(productImageView);

        //load colors from firebase and display them

        //connect to firebase database
        colorsDatabaseReference = FirebaseDatabase.getInstance().getReference("Products").child(path).child(loadedProduct.getId()).child("Colors");

        //make linearLayoutManager variable with horizontal view to attach it to colors list
        LinearLayoutManager horizontalLinearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //make a layout manager of type grid view and set number of item as 2 and attach it to the recyclerview
        productRecyclerViewColors.setLayoutManager(horizontalLinearLayoutManager);

        //clear and set products list to a new Array List
        colorsList = new ArrayList<ColorClass>();

        //loading data methode for single time in home page
        colorsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear colors list
                colorsList.clear();

                //foreach loop to get all data from firebase database
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    //create Color object
                    ColorClass colorObject = new ColorClass();

                    //load the data from firebase as a object and set it to the colorObject
                    colorObject.setColorHexa(dataSnapshot1.getValue(String.class));

                    //add the colorObject to the colorsList

                    colorsList.add(colorObject);

                }

                //attach colorsList to the adapter
                colorsAdapter = new ColorAdapter(ProductDetailActivity.this, colorsList);
                productRecyclerViewColors.setAdapter(colorsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductDetailActivity.this,"Error !",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addToChart(View view) {

        Toast.makeText(ProductDetailActivity.this,"Product Added To Chart!",Toast.LENGTH_SHORT).show();
        HomeActivity.chart.add(loadedProduct);
    }
}