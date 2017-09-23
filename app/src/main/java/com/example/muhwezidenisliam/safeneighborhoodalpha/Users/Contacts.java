package com.example.muhwezidenisliam.safeneighborhoodalpha.Users;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;
import com.example.muhwezidenisliam.safeneighborhoodalpha.firebase.Config;
import com.example.muhwezidenisliam.safeneighborhoodalpha.group.Chat;
import com.example.muhwezidenisliam.safeneighborhoodalpha.group.Group_Model;
import com.example.muhwezidenisliam.safeneighborhoodalpha.group.RecyclerTouchListener;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {
    RecyclerView rvContacts;
    List<User_Model> contactVOList;
    Chat chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        Firebase.setAndroidContext(this);
        getAllContacts();
        chat=new Chat();
    }

    private void getAllContacts() {
        contactVOList = new ArrayList();
        User_Model contactVO;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactVO = new User_Model();
                    contactVO.setUsername(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setUserPhone(phoneNumber);
                    }

                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    contactVOList.add(contactVO);
                }
            }

            ContactsAdapter contactAdapter = new ContactsAdapter(contactVOList, getApplicationContext());
            rvContacts.setLayoutManager(new LinearLayoutManager(this));

            rvContacts.addOnItemTouchListener(new RecyclerTouchListener(this, rvContacts,new RecyclerTouchListener.ClickListener(){

                @Override
                public void onClick(View view, int position) {

                    User_Model user_model= contactVOList.get(position);
                    Firebase firebase=new Firebase(Config.FIREBASE_URL);

                    Group_Model group_model=new Group_Model();
                    if (user_model.getUserPhone().trim().startsWith("+")){
                        group_model.setPhone(user_model.getUserPhone().replaceAll(" ",""));
                    }
                    else{
                        group_model.setPhone("+256"+user_model.getUserPhone().substring(1).replaceAll(" ",""));

                    }


                    firebase.child("groups").child((getIntent().getExtras().getString("gname"))).child("users").push().setValue(group_model);
                    firebase.child("users").push().setValue(user_model);

                    Toast.makeText(Contacts.this, "" + user_model.getUsername()+" Has been added into  "+(getIntent().getExtras().getString("gname")), Toast.LENGTH_SHORT).show();
                    Contacts.this.finish();
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            rvContacts.setAdapter(contactAdapter);


        }
    }
}
