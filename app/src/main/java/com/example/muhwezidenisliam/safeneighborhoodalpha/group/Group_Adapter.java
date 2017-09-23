package com.example.muhwezidenisliam.safeneighborhoodalpha.group;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;

import java.util.List;

/**
 * Created by basasa on 9/15/2017.
 */

public class Group_Adapter extends RecyclerView.Adapter<Group_Adapter.MyViewHolder>{
    private Context mContext;
    private List<Group_Model> group_modelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView groupName,total_members;

        public MyViewHolder(View view) {
            super(view);
            groupName = (TextView) view.findViewById(R.id.group_name);
            total_members = (TextView) view.findViewById(R.id.total_members);
        }
    }

    public Group_Adapter(Context mContext, List<Group_Model> group_modelList) {
        this.mContext = mContext;
        this.group_modelList = group_modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_ui_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Group_Model groupset = group_modelList.get(position);
        holder.groupName.setText(groupset.getName());
        holder.total_members.setText("Total Group Members: 10");
    }

    @Override
    public int getItemCount() {
        return group_modelList.size();
    }
}
