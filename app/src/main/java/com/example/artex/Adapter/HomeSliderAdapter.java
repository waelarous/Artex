package com.example.artex.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artex.Activity.ProductDetailActivity;
import com.example.artex.Class.Product;
import com.example.artex.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.HomeSliderViewHolder> {

    Context context;
    ArrayList<Product> productArrayList;

    public HomeSliderAdapter(Context c, ArrayList<Product> product) {
        context = c;
        productArrayList = product;
    }

    @NonNull
    @Override
    public HomeSliderAdapter.HomeSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HomeSliderAdapter.HomeSliderViewHolder(LayoutInflater.from(context).inflate(R.layout.home_slider_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSliderAdapter.HomeSliderViewHolder holder, int position) {
        final Product product = productArrayList.get(position);

        Picasso.get().load(productArrayList.get(position).getImage()).into(holder.productImageView);

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class HomeSliderViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;

        public HomeSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageView = (ImageView) itemView.findViewById(R.id.product_imageview);

        }
    }
}

