package com.example.findmyhome;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.Query;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.findmyhome.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;



    public class Homepage extends AppCompatActivity {

        Button logOut;

        RecyclerView recyclerView;
        ImageView image;
        private DatabaseReference ref;
        ArrayList<Modelling> messagesArrayList;
        private RecyclerAdapter recyclerAdapter;
        private Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            logOut = findViewById(R.id.buttonLogOut);
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(Homepage.this, "Logout successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });

            recyclerView = findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            ref = FirebaseDatabase.getInstance().getReference();

            messagesArrayList = new ArrayList<>();
            clearAll();
            getDataFromFirebase();
        }
        private  void getDataFromFirebase(){
            Query query = ref.child("Products");
            query.addValueEventListener(new ValueEventListener() {

                @Override

                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   clearAll();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   System.out.println(dataSnapshot.child("imageUrl").getValue().toString());

                            Modelling modelling = new Modelling((String)dataSnapshot.child("imageUrl").getValue(),(String)dataSnapshot.child("location").getValue(),(String)dataSnapshot.child("price").getValue(), (String)dataSnapshot.child("contactDetails").getValue());
                            modelling.setPrice((String) dataSnapshot.child("price").getValue());
                            modelling.setLocation((String) dataSnapshot.child("location").getValue());
                            modelling.setDescription((String) dataSnapshot.child("contactDetails").getValue());
                            modelling.setImageUrl((String) dataSnapshot.child("imageUrl").getValue());
                            System.out.println(modelling.getImageUrl());
                            messagesArrayList.add(modelling);

                    }

                 recyclerAdapter = new RecyclerAdapter(getApplicationContext(),messagesArrayList);
                 recyclerView.setAdapter(recyclerAdapter);
                 recyclerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        private void clearAll(){
            if(messagesArrayList != null){
                messagesArrayList.clear();
              if (recyclerAdapter != null)
              recyclerAdapter.notifyDataSetChanged();
            }else
                messagesArrayList = new ArrayList<>();
        }

    }

