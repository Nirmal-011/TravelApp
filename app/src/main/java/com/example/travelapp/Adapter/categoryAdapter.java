package com.example.travelapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelapp.Domain.Category;
import com.example.travelapp.R;
import com.example.travelapp.databinding.ViewholderCategoryBinding;

import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.Viewholder>{
    private final List<Category> items;
    private int selectedPosition=-1;
    private int lastSelectedPosition=-1;
    private Context context;

    public categoryAdapter(List<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public categoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        ViewholderCategoryBinding binding=  ViewholderCategoryBinding.inflate(inflater,parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Category item=items.get(position);
        holder.binding.title.setText(item.getName());

        Glide.with(holder.itemView.getContext())
                .load(item.getImagePath())
                .into(holder.binding.pic);
        holder.binding.getRoot().setOnClickListener(v -> {
            lastSelectedPosition=selectedPosition;
            selectedPosition=position;
            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);
        });
        holder.binding.title.setTextColor(context.getResources().getColor(R.color.white));

        if(selectedPosition==position){
            holder.binding.pic.setBackgroundResource(0);
            holder.binding.mainLayout.setBackgroundResource(R.drawable.bluebg);
            holder.binding.title.setVisibility(View.VISIBLE);
            holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        else{
            holder.binding.pic.setBackgroundResource(R.drawable.grey_bg);
            holder.binding.mainLayout.setBackgroundResource(0);
            holder.binding.title.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        private final ViewholderCategoryBinding binding;
        public Viewholder(ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}