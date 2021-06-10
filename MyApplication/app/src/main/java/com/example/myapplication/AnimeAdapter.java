package com.example.myapplication;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>  {

    // Member variables.
    private ArrayList<Anime> mAnimeData;
    private final LayoutInflater mInflater;
    private static ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        AnimeAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

    AnimeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setAnimes(List<Anime> animes){
        mAnimeData = (ArrayList<Anime>) animes;
        notifyDataSetChanged();
    }

    class AnimeViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mAnimeImage;

        private AnimeViewHolder(View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mAnimeImage = itemView.findViewById(R.id.sportsImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new AnimeViewHolder(itemView);
    }

    public void onBindViewHolder(AnimeViewHolder holder, int position) {
        Anime current = mAnimeData.get(position);
        holder.mTitleText.setText(current.getTitle());
        holder.mInfoText.setText(current.getInfo());
        holder.mAnimeImage.setImageResource(current.getImageResource());
    }

    @Override
    public int getItemCount() {
        if (mAnimeData != null)
            return mAnimeData.size();
        else return 0;
    }

    public Anime getAnimeAtPosition(int position) {
        return mAnimeData.get(position);
    }

}