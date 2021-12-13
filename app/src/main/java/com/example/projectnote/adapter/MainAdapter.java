package com.example.projectnote.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectnote.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<com.example.projectnote.adapter.MainAdapter.MyViewHolder> {
    private Context context;
    private List<String> mainArray;


    public MainAdapter(Context context) { //создание списка
        this.context = context;
        mainArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) { // функций рисует элементы в RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout,viewGroup , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // Эта функций заполняет элементы в каждом item
        holder.setData(mainArray.get(position));
    }

    @Override
    public int getItemCount() {
        return mainArray.size(); //отсюда будут браться элементы из списка
    }

    static class  MyViewHolder extends RecyclerView.ViewHolder{ // находит элемент по itemView

        private TextView tvTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
        public  void setData (String title) {
            tvTitle.setText(title);
        }
    }
    public void updateAdapter (List<String> newList){
        mainArray.clear();
        mainArray.addAll(newList);
        notifyDataSetChanged();
    }

}
