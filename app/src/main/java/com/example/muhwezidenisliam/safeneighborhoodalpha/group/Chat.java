package com.example.muhwezidenisliam.safeneighborhoodalpha.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;
import com.example.muhwezidenisliam.safeneighborhoodalpha.Users.Contacts;
import com.example.muhwezidenisliam.safeneighborhoodalpha.firebase.Config;
import com.example.muhwezidenisliam.safeneighborhoodalpha.login.phoneAuth;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by basasa on 9/13/2017.
 */

public class Chat extends AppCompatActivity{
    private String loggedInUserName = "";
    FloatingActionButton fab;
    ListView listView;
    EditText editText;
    String group_name;

    private String userphone;

    private FirebaseAuth mAuth;

    private List<Chat_Model> ChatBubbles;
    private ArrayAdapter<Chat_Model> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_ui_chat);
        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        fab=(FloatingActionButton)findViewById(R.id.fab);
        listView=(ListView) findViewById(R.id.list);
        editText=(EditText) findViewById(R.id.input);
        Firebase.setAndroidContext(this);

        ChatBubbles = new ArrayList<>();
        firebase_read();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(Chat.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {
                    Firebase firebase=new Firebase(Config.FIREBASE_URL);
                    firebase.child("messages")
                            .child(group_name)
                            .child("messages")
                            .push()
                            .setValue(new Chat_Model(editText.getText().toString().trim(),userphone,firebase.getKey()));

                    editText.setText("");
                }
            }
        });
        group_name=(getIntent().getExtras().getString("name"));


            // User is already signed in, show list of messages
            showAllOldMessages();


    }

    private void showAllOldMessages() {

        loggedInUserName =userphone;
        Log.d("Main", "user id: " + loggedInUserName);

        adapter = new MessageAdapter(this, R.layout.item_in_message, ChatBubbles);
        listView.setAdapter(adapter);
    }

    public String getLoggedInUserName() {
        return userphone;
    }

public void firebase_read(){
    Firebase firebase=new Firebase(Config.FIREBASE_URL);
    firebase.child("messages").child((getIntent().getExtras().getString("name"))).child("messages").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(int i=ChatBubbles.size()-1; i>=0; i--)
                ChatBubbles.remove(i);
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Chat_Model newproduct = postSnapshot.getValue(Chat_Model.class);
                ChatBubbles.add(newproduct);
                adapter.notifyDataSetChanged();
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
            userphone=currentUser.getPhoneNumber();
            System.out.println("tttttttttttttttt"+userphone);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.invite:
                Intent intent=new Intent(Chat.this, Contacts.class);
                   intent.putExtra("gname", group_name);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();

    }

}
