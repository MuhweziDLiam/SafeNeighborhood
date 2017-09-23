package com.example.muhwezidenisliam.safeneighborhoodalpha.group;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;
import com.example.muhwezidenisliam.safeneighborhoodalpha.firebase.Config;
import com.example.muhwezidenisliam.safeneighborhoodalpha.login.phoneAuth;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by basasa on 9/13/2017.
 */

public class Group_List extends AppCompatActivity {
    RecyclerView groupRecycler;
    ProgressDialog dialog;
    List<Group_Model> grouplist;
    Group_Adapter adapter;
    TextView group_number;
    Button create,join;
    final Context context = this;
    long count=0;
    private String phone;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.group_ui_list);
        groupRecycler = (RecyclerView) findViewById(R.id.groupsRecycler);
        group_number=(TextView)findViewById(R.id.group_number);

        create=(Button)findViewById(R.id.create_btn);
        join=(Button)findViewById(R.id.join_btn);
        populate_groups();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        Firebase.setAndroidContext(this);
        populategroups();


    }
public void populategroups(){
    grouplist.clear();
    Firebase firebase =new Firebase(Config.FIREBASE_URL);
    dialog = new ProgressDialog(this);
    dialog.setMessage("loading groups...");
    dialog.show();

    firebase.child("groups").addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>(){};
                Map<String, Object> map = snapshot.getValue(m);

                Log.i("Mufz", "User length Value is: " + snapshot.getChildrenCount());
                for(int i=0; i<snapshot.getChildrenCount(); i++) {


                    for (DataSnapshot childd : snapshot.getChildren()) {
                        for (DataSnapshot childdd : childd.getChildren()) {
                            Group_Model user = childdd.getValue(Group_Model.class);
                            Log.i("Mufz", "User phone Value is: " + user.getPhone());

                            if (user.getPhone().equalsIgnoreCase(phone)){
                                count= count+snapshot.getChildrenCount();
                                group_number.setText(String.valueOf(count));
                                long counting=0;
                                counting=counting+childdd.getChildrenCount();
                               // user.setCount(counting);
                                user.setName(snapshot.getKey());

                                Log.i("Mufz", "User values Value is: " + snapshot.getKey());
                                grouplist.add(user);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        //This might work but it retrieves all the data
                    }

                }
                dialog.dismiss();
            }
        }


        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    });
}

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            System.out.println("uuuuuuuuuuuuuuuuuuu");
        } else {
           phone=currentUser.getPhoneNumber();
            System.out.println("tttttttttttttttt"+phone);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        populategroups();
        //threadconnection();
    }
    private void showDialog() {

        final Firebase firebase =new Firebase(Config.FIREBASE_URL);
        /* Alert Dialog Code Start*/
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Group Creation"); //Set Alert dialog title here
        alert.setMessage("Enter Your Group Name Here"); //Message here

        // Set an EditText view to get user input
        final EditText input = new EditText(context);
        alert.setView(input);

        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Group_Model myObject = new Group_Model();

                String group_name = input.getEditableText().toString();
                String phone2=phone;

                myObject.setPhone(phone2);
                firebase.child("groups").child(group_name).child("users").push().setValue(myObject);

            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
//
//                firebase.child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
//                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//
//                            Log.v("",""+ childDataSnapshot.getKey()); //displays the key for the node
//
//                            System.out.println("uuuuuuuuuuuuuuuuuuu"+childDataSnapshot.getKey());
//
//                               }
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });



                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }
public void populate_groups(){

    grouplist = new ArrayList<>();
    adapter = new Group_Adapter(this, grouplist);

    groupRecycler.setHasFixedSize(true);
    LinearLayoutManager llm = new LinearLayoutManager(this);
    groupRecycler.setLayoutManager(llm);


    groupRecycler.addOnItemTouchListener(new RecyclerTouchListener(this, groupRecycler,new RecyclerTouchListener.ClickListener(){

        @Override
        public void onClick(View view, int position) {
           Intent go = new Intent(Group_List.this, Chat.class);
            go.putExtra("name", grouplist.get(position).getName());
            startActivity(go);
        }

        @Override
        public void onLongClick(View view, int position) {
            //To at long click.
        }
    }));

    groupRecycler.setAdapter(adapter);

}
}