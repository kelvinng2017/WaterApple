package com.example.user.waterapple;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Industry extends Fragment {

    private View mMainView;
    private DatabaseReference industryDataBase;
    private RecyclerView industry_list;

    public Industry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView= inflater.inflate(R.layout.fragment_industry, container, false);
        industry_list=mMainView.findViewById(R.id.industry_list);
        industryDataBase=FirebaseDatabase.getInstance().getReference().child("業者資訊");
        industryDataBase.keepSynced(true);
        industry_list.setHasFixedSize(true);
        industry_list.setLayoutManager(new LinearLayoutManager(getContext()));
        return mMainView;
    }

    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<WaterAppleIndustry> options=new FirebaseRecyclerOptions.Builder<WaterAppleIndustry>()
                .setQuery(industryDataBase,WaterAppleIndustry.class)
                .build();

        FirebaseRecyclerAdapter<WaterAppleIndustry,IndustryViewHolder> adapter = new FirebaseRecyclerAdapter<WaterAppleIndustry, IndustryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull IndustryViewHolder holder, int position, @NonNull WaterAppleIndustry model) {
                holder.setIndustryName(model.getName());
                holder.setIndustryAddress(model.getAddress());
                holder.setIndustryPhone(model.getPhone());
                holder.setIndustryProduct(model.getProduct());
            }

            @NonNull
            @Override
            public IndustryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.business_layout,viewGroup,false);
                IndustryViewHolder viewHolder=new IndustryViewHolder(view);
                return viewHolder;
            }
        };
        industry_list.setAdapter(adapter);
        adapter.startListening();

    }

    public static class IndustryViewHolder extends RecyclerView.ViewHolder{
        View view;

        public IndustryViewHolder(@NonNull View itemView){
            super(itemView);
            view=itemView;
        }

        public void setIndustryName(String industryName){
            TextView IndustryNameText_View=(TextView)view.findViewById(R.id.business_name_data);
            IndustryNameText_View.setText(industryName);
        }
        public void setIndustryAddress(String industryAddress){
            TextView IndustryAddressText_View=(TextView)view.findViewById(R.id.business_address_data);
            IndustryAddressText_View.setText(industryAddress);
        }
        public void setIndustryPhone(String industPhone){
            TextView IndustryPhoneText_View=(TextView)view.findViewById(R.id.business_phone_data);
            IndustryPhoneText_View.setText(industPhone);
        }
        public void setIndustryProduct(String industryProduct){
            TextView IndustryProductText_View=(TextView)view.findViewById(R.id.business_product_data);
            IndustryProductText_View.setText(industryProduct);
        }
    }
}
