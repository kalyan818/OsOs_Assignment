package com.kalyan.osos_assignment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleAdapterViewHolder> {
    Context context;



    ArrayList<String> Titles;
    ArrayList<Uri> images_list = new ArrayList<Uri>();
    private OnItemClickListener mListner;



    public interface OnItemClickListener{

        void ImageClick(int position);
    }






    public SampleAdapter(Context context,ArrayList<String> Titles,ArrayList<Uri> images_list){
        this.context = context;
        this.Titles = Titles;
        this.images_list = images_list;
    }





    public void setOnItemClickListener(OnItemClickListener listener){
        mListner = listener;
    }

    @NonNull
    @Override
    public SampleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_1,parent,false);
        return new SampleAdapter.SampleAdapterViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleAdapterViewHolder holder, int position) {
        holder.recycler_title.setText(Titles.get(position).toString());
        if(images_list!=null){
            ScrollHorizontal scrollHorizontal = new ScrollHorizontal(context,images_list);
            holder.subRecycler.setHasFixedSize(true);
            holder.subRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            holder.subRecycler.setAdapter(scrollHorizontal);
        }
    }

    @Override
    public int getItemCount() {
        return Titles.size();
    }

    class SampleAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView recycler_title;
        ImageView imageView;
        RecyclerView subRecycler;
        public SampleAdapterViewHolder(@NonNull View itemView, final OnItemClickListener listner) {
            super(itemView);
            recycler_title =itemView.findViewById(R.id.recycler_title);
            imageView = itemView.findViewById(R.id.recycler_gallery);
            subRecycler = itemView.findViewById(R.id.sub_recycler);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listner.ImageClick(position);
                        }
                    }
                }
            });
        }
    }
}
