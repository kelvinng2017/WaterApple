package com.example.user.waterapple;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class Resume extends AppCompatActivity {
    private  String rate_id;
    private DatabaseReference resume_database;
    private ImageView resume_image;
    private TextView name_data,ancient_name_data,production_period_data,introduction_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        Toolbar resume_app_layout=findViewById(R.id.resume_app_layout);
        setSupportActionBar(resume_app_layout);
        ActionBar resume_app_actionbar=getSupportActionBar();
        if(resume_app_actionbar!=null){
            resume_app_actionbar.setDisplayHomeAsUpEnabled(true);
            resume_app_actionbar.setTitle("蓮霧履歷");
        }
        rate_id=getIntent().getExtras().get("rate_id").toString();
        Log.i("resume_id_rate_id",rate_id);
        resume_database=FirebaseDatabase.getInstance().getReference().child("rate").child(rate_id);
        name_data=findViewById(R.id.name_data);
        ancient_name_data=findViewById(R.id.ancient_name_data);
        production_period_data=findViewById(R.id.production_period_data);
        introduction_data=findViewById(R.id.introduction_data);
        resume_image=findViewById(R.id.resume_image);
        resume_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    String name=dataSnapshot.child("name").getValue().toString();
                    String ancient_name=dataSnapshot.child("ancient_name").getValue().toString();
                    String production_period=dataSnapshot.child("production_period").getValue().toString();
                    String introduction=dataSnapshot.child("introduction").getValue().toString();
                    String image=dataSnapshot.child("image").getValue().toString();
                    name_data.setText(name);
                    ancient_name_data.setText(ancient_name);
                    production_period_data.setText(production_period);
                    introduction_data.setText(introduction);
                    Picasso.get().load(image).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().noFade().into(resume_image);
                }else {
                    name_data.setText("無");
                    ancient_name_data.setText("無");
                    production_period_data.setText("無");
                    introduction_data.setText("無");
                    resume_image.setVisibility(View.INVISIBLE);
                    //Picasso.get().load(image).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().noFade().into(resume_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(Resume.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Resume.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
