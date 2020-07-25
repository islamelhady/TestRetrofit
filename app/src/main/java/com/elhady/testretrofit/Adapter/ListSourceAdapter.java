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
import com.elhady.testretrofit.model.WebSite;

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    TextView source_name;

    public ListSourceViewHolder(@NonNull View itemView) {
        super(itemView);

        source_name = itemView.findViewById(R.id.source_name);
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
    private WebSite webSite;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
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
        holder.source_name.setText(webSite.getArticles().get(position).getSource().getName());

    }

    @Override
    public int getItemCount() {
        return webSite.getArticles().size();
    }
}
