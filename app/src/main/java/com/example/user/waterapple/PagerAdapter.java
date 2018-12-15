package com.example.user.waterapple;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                Rate rate =new Rate();
                return rate;

            case 1:
                Industry industry=new Industry();
                return  industry;



            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "蓮霧種類";

            case 1:
                return "業者資訊";




            default:
                return null;
        }

    }
}
