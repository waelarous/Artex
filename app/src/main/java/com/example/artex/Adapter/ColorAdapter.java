package com.example.artex.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artex.Class.ColorClass;
import com.example.artex.R;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    Context context;
    ArrayList<ColorClass> colorArrayList;

    public ColorAdapter(Context c, ArrayList<ColorClass> color) {
        context = c;
        colorArrayList = color;
    }

    @NonNull
    @Override
    public ColorAdapter.ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ColorAdapter.ColorViewHolder(LayoutInflater.from(context).inflate(R.layout.color_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ColorViewHolder holder, int position) {
        final ColorClass color = colorArrayList.get(position);

        holder.colorImageView.setBackgroundColor(Color.parseColor(color.getColorHexa()));


    }

    @Override
    public int getItemCount() {
        return colorArrayList.size();
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {

        ImageView colorImageView;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);

            colorImageView = (ImageView) itemView.findViewById(R.id.color_imageView);

        }
    }
}
