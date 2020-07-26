package com.elhady.testretrofit.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.elhady.testretrofit.Interface.ItemClickListener;
import com.elhady.testretrofit.R;
import com.elhady.testretrofit.model.Article;

import java.util.List;

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    TextView source_title,source_name;
    TextView source_desc;
    ImageView source_img;
    TextView source_author;

    public ListSourceViewHolder(@NonNull View itemView) {
        super(itemView);

        source_title = itemView.findViewById(R.id.title);
        source_desc = itemView.findViewById(R.id.desc);
        source_author = itemView.findViewById(R.id.author);
        source_name = itemView.findViewById(R.id.source);
       // source_img = itemView.findViewById(R.id.source_image);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

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
        View itemView = inflater.inflate(R.layout.source_layout, viewGroup, false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position) {


        holder.source_title.setText(articles.get(position).getTitle());
        holder.source_desc.setText(articles.get(position).getDescription());
        holder.source_author.setText(articles.get(position).getAuthor());
        holder.source_name.setText(articles.get(position).getSource().getName());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
