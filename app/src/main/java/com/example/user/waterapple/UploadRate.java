package com.example.user.waterapple;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.UUID;

public class UploadRate extends AppCompatActivity {

    private DatabaseReference rateDatabase;
    private FirebaseStorage rateImageStorage;

    ProgressBar progressBar;
    Button select_image_button;
    EditText rate_name_edit_text;
    Button upload_button;
    ImageView upload_rate_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_rate);
        Toolbar upload_rate_toolbar=findViewById(R.id.upload_rate_toolbar);
        setSupportActionBar(upload_rate_toolbar);
        ActionBar upload_rate_actionbar=getSupportActionBar();
        if(upload_rate_actionbar!=null){
            upload_rate_actionbar.setTitle("新增蓮霧種類");
            upload_rate_actionbar.setDisplayHomeAsUpEnabled(true);
        }
        progressBar=findViewById(R.id.progressBar);
        select_image_button=findViewById(R.id.select_rate_image);
        rate_name_edit_text=findViewById(R.id.rate_name_edit_text);
        upload_button=findViewById(R.id.upload_button);
        progressBar.setVisibility(View.INVISIBLE);
        rateDatabase=FirebaseDatabase.getInstance().getReference().child("rate");
        rateImageStorage=FirebaseStorage.getInstance();
        select_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void  onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==RESULT_OK){
            Uri uri=data.getData();
            ContentResolver cr =this.getContentResolver();
            try {
                final Bitmap bitmap=BitmapFactory.decodeStream(cr.openInputStream(uri));
                upload_rate_image.setImageBitmap(bitmap);
                String fileNmae= UUID.randomUUID().toString()+".jpg";
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
                byte[] imageData=baos.toByteArray();
                StorageMetadata storageMetadata=new StorageMetadata
                        .Builder()
                        .setCustomMetadata("MyKey","MyValue")
                        .build();
                final StorageReference mountainsReft =rateImageStorage
                        .getReference()
                        .child(fileNmae);
                UploadTask uploadTask=mountainsReft.putBytes(imageData,storageMetadata);
                progressBar.setVisibility(View.VISIBLE);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadRate.this,"上傳失敗",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UploadRate.this,"上傳成功",Toast.LENGTH_LONG).show();
                        HashMap picData =new HashMap();
                        picData.put("image",taskSnapshot.getUploadSessionUri().getPath());
                        picData.put("path",mountainsReft.getPath());
                        rateDatabase.push().setValue(picData);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int progressPersentage=(int)((taskSnapshot.getBytesTransferred()*100)/(taskSnapshot.getTotalByteCount()));
                        progressBar.setProgress(progressPersentage);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(UploadRate.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(UploadRate.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
