package com.elhady.testretrofit.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elhady.testretrofit.Interface.ItemClickListener;
import com.elhady.testretrofit.R;
import com.elhady.testretrofit.model.Article;
import com.elhady.testretrofit.model.WebSite;

import java.util.List;

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    TextView source_title;
    TextView source_desc;

    public ListSourceViewHolder(@NonNull View itemView) {
        super(itemView);

        source_title = itemView.findViewById(R.id.source_title);
        source_desc = itemView.findViewById(R.id.source_description);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{

    private Context context;
    //private WebSite webSite;
    private List<Article> articles;

    public ListSourceAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.source_layout,viewGroup,false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSourceViewHolder holder, int position) {
        holder.source_title.setText(articles.get(position).getTitle());
        holder.source_desc.setText(articles.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
