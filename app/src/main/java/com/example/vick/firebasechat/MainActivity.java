package com.example.vick.firebasechat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity {
    ListView LV;
    Button btn1, btn2;
    final String FIREBASEURL = "https://chatservice-8c7a7.firebaseio.com/contacts";
    Firebase fireBaseRef;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        connToFireBase();
        FirebaseHandler();

        btn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataToFireBase();
                //showListViewItem();
            }
        });

        btn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "data remove", Toast.LENGTH_SHORT).show();
                fireBaseRef.child("5").removeValue();
            }
        });
    }

    private void initWidget(){
        LV = (ListView)findViewById(R.id.listView);
        btn1 = (Button)findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1);
        LV.setAdapter(adapter);
    }

    private void connToFireBase(){
        Firebase.setAndroidContext(this);
        fireBaseRef = new Firebase(FIREBASEURL);
    }

    private void showListViewItem(){
        adapter.add("Item");
    }

    private void AddDataToFireBase(){
        //fireBaseRef.child("name").setValue("Jerry");
        fireBaseRef.child("5").child("name").setValue("Yilla");
    }

    private void FirebaseHandler(){

        fireBaseRef.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        adapter.add((String) dataSnapshot.child("name").getValue());
                        //Toast.makeText(getApplicationContext(), (String) dataSnapshot.child("name").getValue(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        adapter.remove((String) dataSnapshot.child("name").getValue());
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                }
        );
    }
}
