package com.example.muhwezidenisliam.safeneighborhoodalpha.group;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by basasa on 9/13/2017.
 */

public class MessageAdapter extends ArrayAdapter<Chat_Model> {
    private Chat activity;
    private List<Chat_Model> messages;

    public MessageAdapter(Chat context, int resource, List<Chat_Model> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        Chat_Model ChatBubble = getItem(position);
        int viewType = getItemViewType(position);


        if (activity.getLoggedInUserName().equals(ChatBubble.getMessageUserPhone())) {
            layoutResource = R.layout.item_out_message;
        } else {
            layoutResource = R.layout.item_in_message;
        }
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set message content
        holder.msg.setText(ChatBubble.getMessageText());
        //holder.time.setText(ChatBubble.getMessageTime());
        holder.phone.setText(ChatBubble.getMessageUserPhone());
        holder.time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", ChatBubble.getMessageTime()));

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime. Value 2 is returned because of left and right views.
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    private class ViewHolder {
        private TextView msg,time,phone;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.message_text);
            time = (TextView) v.findViewById(R.id.message_time);
            phone = (TextView) v.findViewById(R.id.message_user);
        }
    }


}
