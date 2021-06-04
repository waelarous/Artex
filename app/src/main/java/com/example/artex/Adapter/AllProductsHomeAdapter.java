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

import com.example.artex.Activity.ChartActivity;
import com.example.artex.Activity.HomeActivity;
import com.example.artex.Activity.ProductDetailActivity;
import com.example.artex.Activity.ShopActivity;
import com.example.artex.Class.Product;
import com.example.artex.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllProductsHomeAdapter extends RecyclerView.Adapter<AllProductsHomeAdapter.AllProductsHomeViewHolder> {

    Context context;
    ArrayList<Product> productArrayList;

    public AllProductsHomeAdapter(Context c, ArrayList<Product> product) {
        context = c;
        productArrayList = product;
    }

    @NonNull
    @Override
    public AllProductsHomeAdapter.AllProductsHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new AllProductsHomeAdapter.AllProductsHomeViewHolder(LayoutInflater.from(context).inflate(R.layout.product_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductsHomeAdapter.AllProductsHomeViewHolder holder, int position) {
        final Product product = productArrayList.get(position);

        if(context.getClass().equals(ChartActivity.class)){

            holder.removeItemBtn.setVisibility(View.VISIBLE);

            holder.removeItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.chart.remove(product);

                    float total = 0;

                    for (Product item: HomeActivity.chart) {
                        total += item.getPrice();
                    }

                    ((ChartActivity)context).updateData("(Total = " + total + ")");

                    notifyDataSetChanged();

                    if(total == 0){
                        ((Activity)context).finish();
                    }
                }
            });
        }

        //get data from firebase and set it to the card
        holder.productNameTxt.setText(product.getName());
        holder.productPriceTxt.setText(product.getPrice() + " DT");
        //picasso is used to get image url from a server to put it in imageview

        Picasso.get().load(productArrayList.get(position).getImage()).into(holder.productImageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                if(context.getClass().equals(ShopActivity.class)){
                    // Get the rootView from the activity
                    final View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

                    // Get the textView from the rootView
                    ConstraintLayout constraintLayoutAnimation = rootView.findViewById(R.id.loading_constraint_layout);

                    //disable splash screen
                    constraintLayoutAnimation.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(Exception e) {

            }

        });

        if(context.getClass().equals(ChartActivity.class)){

        }

        if(context.getClass().equals(ShopActivity.class)){
            holder.productCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ProductIntent = new Intent(context, ProductDetailActivity.class);
                    ProductIntent.putExtra("Product", product);

                    switch (product.getCategory())
                    {
                        case 1:
                            ProductIntent.putExtra("Path", "Fouta");
                            break;

                        case 2:
                            ProductIntent.putExtra("Path", "Palaid");
                            break;

                        case 3:
                            ProductIntent.putExtra("Path", "Set Salle De Bain");
                            break;

                        case 4:
                            ProductIntent.putExtra("Path", "Coussin");
                            break;
                    }

                    context.startActivity(ProductIntent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class AllProductsHomeViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTxt, productPriceTxt;
        ImageView productImageView;
        ConstraintLayout productCardBtn;
        ImageView removeItemBtn;

        public AllProductsHomeViewHolder(@NonNull View itemView) {
            super(itemView);

            productNameTxt = (TextView) itemView.findViewById(R.id.product_name_txt);
            productPriceTxt = (TextView) itemView.findViewById(R.id.product_price_txt);
            productImageView = (ImageView) itemView.findViewById(R.id.product_imageview);
            productCardBtn = (ConstraintLayout) itemView.findViewById(R.id.constraint_product_card_btn);
            removeItemBtn = (ImageView) itemView.findViewById(R.id.remove_item);

        }
    }
}
