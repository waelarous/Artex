package com.example.artex.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artex.Adapter.AllProductsHomeAdapter;
import com.example.artex.Class.Product;
import com.example.artex.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    private ImageButton foutaIconImageBtn , palaidIconImageBtn , setSalleDeBainIconImageBtn , coussinIconImageBtn;
    private TextView foutaIconTxt , palaidIconTxt , setSalleDeBainIconTxt , coussinIconIconTxt;
    private AutoCompleteTextView allProductssearchBarView;
    private RecyclerView allProductRecyclerView;

    //Lists of all products
    private ArrayList<Product> allProductsList;
    //product adapter
    private AllProductsHomeAdapter allProductsHomeAdapter;
    //firebase realTime database reference to connect to firebase database
    private DatabaseReference allProductsDatabaseReference;

    private ImageButton chartImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //get data from xml and link it with our variables
        foutaIconImageBtn = findViewById(R.id.fouta_icon_image_btn);
        palaidIconImageBtn = findViewById(R.id.palaid_icon_image_btn);
        setSalleDeBainIconImageBtn = findViewById(R.id.set_salle_de_bain_icon_image_btn);
        coussinIconImageBtn = findViewById(R.id.coussin_icon_image_btn);
        allProductssearchBarView = findViewById(R.id.product_search_view);
        allProductRecyclerView = findViewById(R.id.all_products_recyclerview);
        foutaIconTxt = findViewById(R.id.fouta_icon_txt);
        palaidIconTxt = findViewById(R.id.palaid_icon_txt);
        setSalleDeBainIconTxt = findViewById(R.id.set_salle_de_bain_icon_txt);
        coussinIconIconTxt = findViewById(R.id.coussin_icon_txt);
        chartImageButton = findViewById(R.id.chart_imageBtn);

        chartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeActivity.chart.size() == 0){
                    Toast.makeText(ShopActivity.this,"Your Chart is Empty!",Toast.LENGTH_SHORT).show();
                }else{
                    Intent chartIntent = new Intent(ShopActivity.this, ChartActivity.class);
                    startActivity(chartIntent);
                }
            }
        });

        //filtre search methodes
        allProductssearchBarView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterSearch(s.toString());
            }
        });

        //make a layout manager of type grid view and set number of item as 2 and attach it to the recyclerview
        allProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //clear and set products list to a new Array List
        allProductsList = new ArrayList<Product>();

        loadData("Fouta");

        //set the top filtre icon buttons color selected
        foutaIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.fouta_active_icon));
        foutaIconTxt.setTextColor(getResources().getColor(R.color.primary_color));

    }

    private void loadData(String path){
        //connect to firebase database
        allProductsDatabaseReference = FirebaseDatabase.getInstance().getReference("Products").child(path);

        //loading data methode for single time in home page
        allProductsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear products list
                allProductsList.clear();

                //foreach loop to get all data from firebase database
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    //create pharmacy object
                    Product productObject = new Product();

                    //load the data from firebase as a object and set it to the productObject
                    productObject = dataSnapshot1.getValue(Product.class);

                    //add the productObject to the productsList

                    allProductsList.add(productObject);

                }

                //attach productsList to the adapter
                allProductsHomeAdapter = new AllProductsHomeAdapter(ShopActivity.this, allProductsList);
                allProductRecyclerView.setAdapter(allProductsHomeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShopActivity.this,"Error !",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterSearch(String text)
    {

        //update allProducts recyclerview when search
        ArrayList<Product> allProducts = new ArrayList<>();

        for(Product item : allProductsList)
        {
            if(item.getName().toLowerCase().contains(text.toLowerCase()))
            {
                allProducts.add(item);
            }
        }

        allProductsHomeAdapter = new AllProductsHomeAdapter(ShopActivity.this, allProducts);
        allProductRecyclerView.setAdapter(allProductsHomeAdapter);

    }

    public void changeData(View view) {
        switch (view.getId()) {

            case R.id.fouta_icon_txt:

            case R.id.fouta_icon_image_btn:
                //********************************* change screen design depends on selected item **************
                foutaIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.fouta_active_icon));
                foutaIconTxt.setTextColor(getResources().getColor(R.color.primary_color));

                palaidIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.palaud_not_active_icon));
                palaidIconTxt.setTextColor(getResources().getColor(R.color.black));

                setSalleDeBainIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.set_salle_de_bain_not_active_icon));
                setSalleDeBainIconTxt.setTextColor(getResources().getColor(R.color.black));

                coussinIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.coussin_not_active_icon));
                coussinIconIconTxt.setTextColor(getResources().getColor(R.color.black));
                //**********************************************************************

                loadData("Fouta");
                break;

            case R.id.palaid_icon_txt:

            case R.id.palaid_icon_image_btn:
                //********************************* change screen design depends on selected item **************
                foutaIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.fouta_not_active_icon));
                foutaIconTxt.setTextColor(getResources().getColor(R.color.black));

                palaidIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.palaid_active_icon));
                palaidIconTxt.setTextColor(getResources().getColor(R.color.primary_color));

                setSalleDeBainIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.set_salle_de_bain_not_active_icon));
                setSalleDeBainIconTxt.setTextColor(getResources().getColor(R.color.black));

                coussinIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.coussin_not_active_icon));
                coussinIconIconTxt.setTextColor(getResources().getColor(R.color.black));
                //**********************************************************************

                loadData("Palaid");
                break;

            case R.id.set_salle_de_bain_icon_txt:

            case R.id.set_salle_de_bain_icon_image_btn:
                //********************************* change screen design depends on selected item **************
                foutaIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.fouta_not_active_icon));
                foutaIconTxt.setTextColor(getResources().getColor(R.color.black));

                palaidIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.palaud_not_active_icon));
                palaidIconTxt.setTextColor(getResources().getColor(R.color.black));

                setSalleDeBainIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.set_salle_de_bain_active_icon));
                setSalleDeBainIconTxt.setTextColor(getResources().getColor(R.color.primary_color));

                coussinIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.coussin_not_active_icon));
                coussinIconIconTxt.setTextColor(getResources().getColor(R.color.black));
                //**********************************************************************

                loadData("Set Salle De Bain");
                break;

            case R.id.coussin_icon_txt:

            case R.id.coussin_icon_image_btn:
                //********************************* change screen design depends on selected item **************
                foutaIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.fouta_not_active_icon));
                foutaIconTxt.setTextColor(getResources().getColor(R.color.black));

                palaidIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.palaud_not_active_icon));
                palaidIconTxt.setTextColor(getResources().getColor(R.color.black));

                setSalleDeBainIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.set_salle_de_bain_not_active_icon));
                setSalleDeBainIconTxt.setTextColor(getResources().getColor(R.color.black));

                coussinIconImageBtn.setBackground(ShopActivity.this.getDrawable(R.drawable.coussin_active_icon));
                coussinIconIconTxt.setTextColor(getResources().getColor(R.color.primary_color));
                //**********************************************************************

                loadData("Coussin");
                break;
        }
    }
}