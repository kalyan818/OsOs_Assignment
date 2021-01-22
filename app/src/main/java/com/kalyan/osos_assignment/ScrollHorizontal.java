package com.kalyan.osos_assignment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScrollHorizontal extends RecyclerView.Adapter<ScrollHorizontal.ScrollHorizontalViewHolder> {
    ArrayList<Uri> images;
    Context context;

    public ScrollHorizontal(Context context, ArrayList images){
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ScrollHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false);
        return new ScrollHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollHorizontalViewHolder holder, int position) {
        holder.imageView.setImageURI(images.get(position));
        Toast.makeText(context,"here",Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ScrollHorizontalViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
         public ScrollHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.images_to_load);
        }
    }
}
