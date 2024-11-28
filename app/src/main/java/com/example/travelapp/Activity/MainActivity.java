package com.example.travelapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.travelapp.Adapter.RecommendedAdapter;
import com.example.travelapp.Adapter.SliderAdapter;
import com.example.travelapp.Adapter.categoryAdapter;
import com.example.travelapp.Domain.Category;
import com.example.travelapp.Domain.ItemDomain;
import com.example.travelapp.Domain.Location;
import com.example.travelapp.Domain.sliderItems;
import com.example.travelapp.R;
import com.example.travelapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BasicActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    initLocation();
    initBanner();
//    initCategory();
//    initRecommended();

    }

    private void initRecommended() {
        DatabaseReference myref= dataBase.getReference("Popular");
        binding.progressBarPopular.setVisibility(View.VISIBLE);
        ArrayList<ItemDomain>list=new ArrayList<>();

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        ItemDomain item=issue.getValue(ItemDomain.class);
                        if(item!=null){
                            list.add(item);
                        }
                    }
                    if(!list.isEmpty()){
                        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter adapter=new RecommendedAdapter(list);
                        binding.recyclerViewPopular.setAdapter(adapter);
                    }
                    binding.progressBarPopular.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initCategory(){

        DatabaseReference myRef=dataBase.getReference("Category");
        binding.progrssBarCategory.setVisibility(View.VISIBLE);
        ArrayList<Category>list=new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        Category category=issue.getValue(Category.class);
                        if(category!=null) {
                            list.add(category);
                        }
                    }
                    if(!list.isEmpty()){
                        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(
                                MainActivity.this, LinearLayoutManager.HORIZONTAL,false
                        ));
                        RecyclerView.Adapter adapter=new categoryAdapter(list);
                        binding.recyclerViewCategory.setAdapter(adapter);
                    }
                    binding.progrssBarCategory.setVisibility(View.GONE);
                }else{
                    Log.e("FireBase","No categories found in database");
                    binding.progrssBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FireBaseError","DataBase error:"+error.getMessage());
                binding.progrssBarCategory.setVisibility(View.GONE);
            }
        });
    }
    private void initLocation() {
        DatabaseReference myRef= dataBase.getReference("Location");
        ArrayList<Location>locationList= new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue: snapshot.getChildren()){
                        locationList.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location>adapter=new ArrayAdapter<>(MainActivity.this, R.layout.sp_items,locationList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void banners(ArrayList<sliderItems>items){
     binding.viewPagerSlider.setAdapter(new SliderAdapter(items,binding.viewPagerSlider));
     binding.viewPagerSlider.setClipToPadding(false);
     binding.viewPagerSlider.setClipChildren(false);
     binding.viewPagerSlider.setOffscreenPageLimit(3);
     binding.viewPagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.viewPagerSlider.setPageTransformer(compositePageTransformer);

    }
    private void initBanner(){
        DatabaseReference myref=dataBase.getReference("Banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<sliderItems>items=new ArrayList<>();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(sliderItems.class));
                    }
                    banners(items);
                    binding.progressBarBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}