package com.example.user.waterapple;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private final ThreadLocal<PagerAdapter> pagerAdapter = new ThreadLocal<PagerAdapter>();
    protected TabLayout activity_main_tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar activity_main_app_bar=findViewById(R.id.activity_main_app_bar);
        setSupportActionBar(activity_main_app_bar);
        ActionBar activity_main_action_bar=getSupportActionBar();
        if(activity_main_action_bar!=null){
            activity_main_action_bar.setTitle("蓮霧查詢系統");
        }
        ViewPager activity_main_tabPager = findViewById(R.id.activity_main_tabPager);
        pagerAdapter.set(new PagerAdapter(getSupportFragmentManager()));
        activity_main_tabPager.setAdapter(pagerAdapter.get());
        activity_main_tabs=findViewById(R.id.activity_main_tabs);
        activity_main_tabs.setupWithViewPager(activity_main_tabPager);

    }
}
