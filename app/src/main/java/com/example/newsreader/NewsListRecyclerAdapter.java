package com.example.newsreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class NewsListRecyclerAdapter extends RecyclerView.Adapter<NewsListRecyclerAdapter.NewsListItemViewHolder>{

     private ArrayList<News> newsContent;
     private onClickedNewsListener monClickedNewsListener;

     public NewsListRecyclerAdapter(ArrayList<News> newsContent, onClickedNewsListener monClickedNewsListener){
         this.monClickedNewsListener = monClickedNewsListener;
         this.newsContent = newsContent;
     }

    @NonNull
    @Override
    public NewsListItemViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_list_item, parent, false);
        return new NewsListItemViewHolder(view, monClickedNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListRecyclerAdapter.NewsListItemViewHolder holder, int position) {
         holder.newsTextView.setText(newsContent.get(position).getTitle());
         Glide.with(holder.itemView.getContext()).load(newsContent.get(position).getImageUrl()).into(holder.newsImageView);
    }

    @Override
    public int getItemCount() {
        return (newsContent == null) ? 0 : newsContent.size();
    }

    public void updateNews(ArrayList<News> updatedList){
         newsContent.clear();
         newsContent.addAll(updatedList);
         notifyDataSetChanged();
    }

    public interface onClickedNewsListener{
        void onNewsClicked(int position);
    }


    public static class NewsListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView newsImageView;
        TextView newsTextView;
        onClickedNewsListener onclickednewsListener;
        public NewsListItemViewHolder(@NonNull View itemView, onClickedNewsListener onclickedNewsListener) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.newsImageView);
            newsTextView = itemView.findViewById(R.id.newsTextView);
            this.onclickednewsListener = onclickedNewsListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onclickednewsListener.onNewsClicked(getAdapterPosition());
        }
    }

}