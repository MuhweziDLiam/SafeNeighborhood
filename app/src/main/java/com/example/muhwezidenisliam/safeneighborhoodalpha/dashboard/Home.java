package com.example.muhwezidenisliam.safeneighborhoodalpha.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;
import com.example.muhwezidenisliam.safeneighborhoodalpha.emergency.MainActivity;
import com.example.muhwezidenisliam.safeneighborhoodalpha.emergency.ShakeGesture;
import com.example.muhwezidenisliam.safeneighborhoodalpha.location.MyLocation;
import com.example.muhwezidenisliam.safeneighborhoodalpha.reports.ReceiveReport;
import com.example.muhwezidenisliam.safeneighborhoodalpha.reports.SendReport;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

import butterknife.ButterKnife;

/**
 * Created by Muhwezi Denis Liam on 9/14/2017.
 */

public class Home extends AppCompatActivity{


    private SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dash_board);
        ButterKnife.bind(this);


        sectionAdapter = new SectionedRecyclerViewAdapter();

        sectionAdapter.addSection(new MovieSection(getString(R.string.section_one), getTopRatedMoviesList(), this));
        sectionAdapter.addSection(new MovieSection(getString(R.string.section_two), getMostPopularMoviesList(), this));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        GridLayoutManager glm = new GridLayoutManager(this, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(sectionAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();

            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(R.string.app_name);

    }

    private List<Movie> getTopRatedMoviesList() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.quick_menu)));

        List<Movie> movieList = new ArrayList<>();

        for (String str : arrayList) {
            String[] array = str.split("\\|");
            movieList.add(new Movie(array[0], array[1]));
        }

        return movieList;
    }

    private List<Movie> getMostPopularMoviesList() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.emergencies)));

        List<Movie> movieList = new ArrayList<>();

        for (String str : arrayList) {
            String[] array = str.split("\\|");
            movieList.add(new Movie(array[0], array[1]));
        }

        return movieList;
    }

    class MovieSection extends StatelessSection {

        String title;
        List<Movie> list;
        Context context;

        public MovieSection(String title, List<Movie> list,Context context) {
            super(R.layout.home_dash_board_header, R.layout.home_dash_board_item);

            this.title = title;
            this.context = context;
            this.list = list;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String name = list.get(position).getName();

            String category = list.get(position).getCategory();

            itemHolder.tvItem.setText(name);

            itemHolder.imgItem.setImageDrawable
                    (getResources().getDrawable(getResources().getIdentifier(category, "drawable", context.getPackageName())));

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(title.equalsIgnoreCase("Quick Menu"))
                    {
                        switch (position){
                            case 0:
                                startActivity(new Intent(Home.this, SendReport.class));
                                break;

                            case 1:
                                startActivity(new Intent(Home.this, MainActivity.class));
                                break;

                            case 2:
                                startActivity(new Intent(Home.this, ReceiveReport.class));
                                break;

                            case 3:
                                startActivity(new Intent(Home.this, MyLocation.class));
                                break;
                        }

                    }

                    else
                    {

                    }
//                    Toast.makeText(Home.this, String.format("Clicked on position #%s of Section %s",
//                            sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()), title),
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;


        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView tvItem;
        private final ImageView imgItem;


        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            tvItem = (TextView) view.findViewById(R.id.tvItem);

            imgItem = (ImageView) view.findViewById(R.id.imgItem);
        }
    }

    class Movie {
        String name;
        String category;

        public Movie(String name, String category) {
            this.name = name;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
