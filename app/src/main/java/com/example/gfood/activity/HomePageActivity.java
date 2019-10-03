package com.example.gfood.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gfood.R;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

public class HomePageActivity extends AppCompatActivity {
    SpaceTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.page_Layout);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CartFragment());
        fragmentList.add(new ProfileFragment());

        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList, savedInstanceState);

    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(tabLayout.getId(), fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }


}
