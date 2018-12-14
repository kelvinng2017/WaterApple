package com.example.user.waterapple;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


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
        activity_main_app_bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.rate:
                            Toast.makeText(MainActivity.this, "新增蓮霧種類", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,UploadRate.class);
                            startActivity(intent);

                            break;
                        case R.id.industry:
                            Toast.makeText(MainActivity.this, "新增業者資訊", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.resume:
                            Toast.makeText(MainActivity.this, "新增蓮霧履歷", Toast.LENGTH_SHORT).show();

                            break;



                    }
                    return true;
                }
            });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
