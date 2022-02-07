package com.example.findmyhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        ImageView imageView;
        TextView textView1, textView2,textView3;
        private Context mContext;
        ArrayList<Modelling> messageLists;

        private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        public RecyclerAdapter(Context mContext,ArrayList<Modelling> messageLists) {

            this.mContext = mContext;
            this.messageLists = messageLists;
        }




        @NonNull
        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            textView1.setText(messageLists.get(position).getPrice());
            textView2.setText(messageLists.get(position).getDescription());
            textView3.setText(messageLists.get(position).getLocation());
           Glide.with(mContext).load(messageLists.get(position).getImageUrl()).into(imageView);
            /*//imageView.setImageURI(messageLists.get(position).getImageUrl());
             // Glide.with(mContext).load(messageLists.get(position).getImageUrl()).into(imageView);
            storageReference.getDownloadUrl()
                    .addOnSuccessListener(uri -> {
                        Glide.with(mContext)
                                .load(uri)
                                .into(imageView);
                    });*/

        }

        @Override
        public int getItemCount() {
            return messageLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(@NonNull View itemView){
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                textView1 = itemView.findViewById(R.id.textView1);
                textView2 = itemView.findViewById(R.id.textView2);
                textView3 = itemView.findViewById(R.id.textView3);
            }
        }

    }


