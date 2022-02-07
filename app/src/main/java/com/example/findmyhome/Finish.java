package com.example.findmyhome;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Finish extends AppCompatActivity {



    private TextView textView; //Checking homepage link

    private Button upload;
    private EditText price, location,contactDetails;
    private ImageView v;
    private ProgressBar bar;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Image");
    private DatabaseReference ProductReference = FirebaseDatabase.getInstance().getReference("Products");

    private StorageReference storage = FirebaseStorage.getInstance().getReference();
    private Uri imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_uploading);



        upload = findViewById(R.id.rectangle_4);
        //viewAll = findViewById(R.id.rectangle_);
        v = findViewById(R.id.imageView);
        bar = findViewById(R.id.progerssBar);
        price  = findViewById(R.id.price);
        location  = findViewById(R.id.location);
        contactDetails  = findViewById(R.id.contactDetails);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Finish.this, Home.class));

            }
        });



        String StudentRecordIDFromServer = ProductReference.push().getKey();
        assert StudentRecordIDFromServer != null;
      //  ProductReference.child(phoneNumber).child(product.getProductID()).setValue(product);

        ActivityResultLauncher<String> start = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        if (uri != null){
                            imageUrl = uri;
                            v.setImageURI(imageUrl);
                        }else{
                            Toast.makeText(Finish.this,"No url set",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               start.launch("image/*");
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location.getText().toString().isEmpty()){
                    location.setError("Enter location");
                    return;
                }if (price.getText().toString().isEmpty()){
                    location.setError("Enter price");
                    return;
                }if (contactDetails.getText().toString().isEmpty()){
                    location.setError("Enter contact details");
                    return;
                }
                if(imageUrl != null){
                    uploadPhotos(imageUrl);
                }else
                    Toast.makeText(Finish.this,"Please choose an Image",Toast.LENGTH_SHORT).show();
            }
        });




    }

    private  void uploadPhotos(Uri uri){
        StorageReference files = storage.child(System.currentTimeMillis()+" "+getFileExtension(uri));
        files.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                files.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(imageUrl.toString(),location.getText().toString(),price.getText().toString(),contactDetails.getText().toString());
                        ProductReference.push().setValue(model);
                        String modeID = reference.push().getKey();
                        Toast.makeText(Finish.this,"Uploaded successfully",Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.INVISIBLE);

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                bar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bar.setVisibility(View.INVISIBLE);
                Toast.makeText(Finish.this,"Error: "+e,Toast.LENGTH_SHORT).show();
            }
        });
    }

private String getFileExtension(Uri uri){
    ContentResolver c =  getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(c.getType(uri));

}




}

class  Model{
    private String imageUrl;
    private String location, price,contactDetails;
    public  Model(){

    }

    public  Model(String imageUrl, String location, String price, String contactDetails){
        this.imageUrl = imageUrl;
        this.contactDetails = contactDetails;
        this.price = price;
        this.location = location;

    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getContactDetails() {
        return contactDetails;
    }
}
