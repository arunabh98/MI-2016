package com.example.darknight.mi2016;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darknight.mi2016.ServerConnection.GsonModels;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sajalnarang on 26/11/16.
 */

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.ViewHolder> {
    private List<GsonModels.Genre> genreList;
    private Context context;
    private ItemCLickListener itemCLickListener;

    public GenreListAdapter(List<GsonModels.Genre> genreList, ItemCLickListener itemCLickListener) {
        this.genreList = genreList;
        this.itemCLickListener = itemCLickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.genre_list_row, parent, false);
        final ViewHolder userViewHolder = new ViewHolder(userView);
        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCLickListener.onItemClick(v, userViewHolder.getAdapterPosition());
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GsonModels.Genre selectedGenre = genreList.get(position);
        holder.genreName.setText(selectedGenre.getName());
        Picasso.with(context).load(selectedGenre.getIconUrl()).into(holder.genreIcon);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView genreIcon;
        private TextView genreName;

        public ViewHolder(View itemView) {
            super(itemView);

            genreName = (TextView) itemView.findViewById(R.id.genre_name);
            genreIcon = (ImageView) itemView.findViewById(R.id.genre_icon);
        }
    }
}
