package com.example.muhwezidenisliam.safeneighborhoodalpha.Users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;

import java.util.List;

/**
 * Created by basasa on 9/21/2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>{

    private List<User_Model> contactVOList;
    private Context mContext;
    public ContactsAdapter(List<User_Model> contactVOList, Context mContext){
        this.contactVOList = contactVOList;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contacts, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        User_Model contactVO = contactVOList.get(position);
        holder.tvContactName.setText(contactVO.getUsername());
        holder.tvPhoneNumber.setText(contactVO.getUserPhone());

        if(contactVO.getUsername().startsWith("a") || contactVO.getUsername().startsWith("A"))
            holder.postIcon.setImageResource(R.mipmap.a);
        else if (contactVO.getUsername().startsWith("b") || contactVO.getUsername().startsWith("B"))
            holder.postIcon.setImageResource(R.mipmap.b);
        else if (contactVO.getUsername().startsWith("c") || contactVO.getUsername().startsWith("C"))
            holder.postIcon.setImageResource(R.mipmap.c);
        else if (contactVO.getUsername().startsWith("d") || contactVO.getUsername().startsWith("D"))
            holder.postIcon.setImageResource(R.mipmap.d);

        else if (contactVO.getUsername().startsWith("e") || contactVO.getUsername().startsWith("E"))
            holder.postIcon.setImageResource(R.mipmap.e);
        else if (contactVO.getUsername().startsWith("f") || contactVO.getUsername().startsWith("F"))
            holder.postIcon.setImageResource(R.mipmap.f);
        else if (contactVO.getUsername().startsWith("g") || contactVO.getUsername().startsWith("G"))
            holder.postIcon.setImageResource(R.mipmap.g);
        else if (contactVO.getUsername().startsWith("h") || contactVO.getUsername().startsWith("H"))
            holder.postIcon.setImageResource(R.mipmap.h);
        else if (contactVO.getUsername().startsWith("i") || contactVO.getUsername().startsWith("I"))
            holder.postIcon.setImageResource(R.mipmap.i);
        else if (contactVO.getUsername().startsWith("j") || contactVO.getUsername().startsWith("J"))
            holder.postIcon.setImageResource(R.mipmap.j);
        else if (contactVO.getUsername().startsWith("k") || contactVO.getUsername().startsWith("K"))
            holder.postIcon.setImageResource(R.mipmap.k);
        else if (contactVO.getUsername().startsWith("l") || contactVO.getUsername().startsWith("L"))
            holder.postIcon.setImageResource(R.mipmap.l);
        else if (contactVO.getUsername().startsWith("m") || contactVO.getUsername().startsWith("M"))
            holder.postIcon.setImageResource(R.mipmap.m);
        else if (contactVO.getUsername().startsWith("n") || contactVO.getUsername().startsWith("N"))
            holder.postIcon.setImageResource(R.mipmap.n);
        else if (contactVO.getUsername().startsWith("o") || contactVO.getUsername().startsWith("O"))
            holder.postIcon.setImageResource(R.mipmap.o);
        else if (contactVO.getUsername().startsWith("p") || contactVO.getUsername().startsWith("P"))
            holder.postIcon.setImageResource(R.mipmap.p);
        else if (contactVO.getUsername().startsWith("q") || contactVO.getUsername().startsWith("Q"))
            holder.postIcon.setImageResource(R.mipmap.q);
        else if (contactVO.getUsername().startsWith("r") || contactVO.getUsername().startsWith("R"))
            holder.postIcon.setImageResource(R.mipmap.r);
        else if (contactVO.getUsername().startsWith("s") || contactVO.getUsername().startsWith("S"))
            holder.postIcon.setImageResource(R.mipmap.s);
        else if (contactVO.getUsername().startsWith("t") || contactVO.getUsername().startsWith("T"))
            holder.postIcon.setImageResource(R.mipmap.t);
        else if (contactVO.getUsername().startsWith("u") || contactVO.getUsername().startsWith("U"))
            holder.postIcon.setImageResource(R.mipmap.u);
        else if (contactVO.getUsername().startsWith("v") || contactVO.getUsername().startsWith("V"))
            holder.postIcon.setImageResource(R.mipmap.v);
        else if (contactVO.getUsername().startsWith("w") || contactVO.getUsername().startsWith("W"))
            holder.postIcon.setImageResource(R.mipmap.w);
        else if (contactVO.getUsername().startsWith("x") || contactVO.getUsername().startsWith("X"))
            holder.postIcon.setImageResource(R.mipmap.x);
        else if (contactVO.getUsername().startsWith("y") || contactVO.getUsername().startsWith("Y"))
            holder.postIcon.setImageResource(R.mipmap.y);
        else if (contactVO.getUsername().startsWith("z") || contactVO.getUsername().startsWith("Z"))
            holder.postIcon.setImageResource(R.mipmap.z);
        else
            holder.postIcon.setImageResource(R.mipmap.a);
    }

    @Override
    public int getItemCount() {
        return contactVOList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView postIcon;
        TextView tvContactName;
        TextView tvPhoneNumber;

        public ContactViewHolder(View itemView) {
            super(itemView);
            postIcon = (ImageView) itemView.findViewById(R.id.ivContactImage);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        }
    }
}