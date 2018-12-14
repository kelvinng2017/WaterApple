package com.example.user.waterapple;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Rate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference rateDatabase;
    private View mMainView;
    private RecyclerView rate_list;


    public Rate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Rate.
     */
    // TODO: Rename and change types and number of parameters
    public static Rate newInstance(String param1, String param2) {
        Rate fragment = new Rate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView= inflater.inflate(R.layout.fragment_rate, container, false);
        rate_list=mMainView.findViewById(R.id.rate_list);
        rateDatabase = FirebaseDatabase.getInstance().getReference().child("rate");
        rateDatabase.keepSynced(true);

        rate_list.setHasFixedSize(true);
        rate_list.setLayoutManager(new LinearLayoutManager(getContext()));
        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<WaterAppleRate> options=new FirebaseRecyclerOptions.Builder<WaterAppleRate>()
                .setQuery(rateDatabase,WaterAppleRate.class)
                .build();

        FirebaseRecyclerAdapter<WaterAppleRate,rateViewHolder>adapter=new FirebaseRecyclerAdapter<WaterAppleRate, rateViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull rateViewHolder holder, int position, @NonNull WaterAppleRate model) {
                holder.setRateImage(model.getImage());
                holder.setRateName(model.getName());
            }

            @NonNull
            @Override
            public rateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.water_apple_rate_layout,viewGroup,false);
                rateViewHolder viewHolder=new rateViewHolder(view);
                return viewHolder;
            }
        };
        rate_list.setAdapter(adapter);
        adapter.startListening();
    }

    public static class rateViewHolder extends RecyclerView.ViewHolder{
        View view;

        public rateViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setRateName(String name){

            TextView userNameView = (TextView) view.findViewById(R.id.rate_name);
            userNameView.setText(name);
        }

        public void setRateImage(String rateimage){

            ImageView rate_image = (ImageView) view.findViewById(R.id.rate_image);
            Picasso.get().load(rateimage).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().noFade().into(rate_image);


        }
    }


}
