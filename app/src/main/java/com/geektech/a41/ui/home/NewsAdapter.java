package com.geektech.a41.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.a41.NewsModel;
import com.geektech.a41.OnItemClickListener;
import com.geektech.a41.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsModel> list;
    private OnItemClickListener onItemClickListener;


    public NewsAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

/*    public void addItem(NewsModel model) {
        list.add(0, model);
        notifyItemInserted(0);
    }*/

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NewsModel getItem(int position) {
        return list.get(position);
    }


/*
    public void deleteItem(NewsModel model) {
        list.remove(model);
        notifyDataSetChanged();
    }*/

    public void addItems(List<NewsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.titleText);
            tvDate = itemView.findViewById(R.id.tvDate);
        }


        public void bind(NewsModel newsModel) {
            Locale kg = new Locale("kg","KG");
            SimpleDateFormat data = new SimpleDateFormat("HH:mm:ss, dd.MMM.yyyy", kg);
            String dateFormat = String.valueOf(data.format(newsModel.getDate()));
            tvDate.setText(dateFormat);

            textView.setText(newsModel.getText());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });

        }
    }
}
