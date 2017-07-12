package com.philong.bangxephangamnhac;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.philong.bangxephangamnhac.adapters.ViewPagerAdapter;
import com.philong.bangxephangamnhac.fragments.FragmentHan;
import com.philong.bangxephangamnhac.fragments.FragmentHoa;
import com.philong.bangxephangamnhac.fragments.FragmentNhat;
import com.philong.bangxephangamnhac.fragments.FragmentUSUK;
import com.philong.bangxephangamnhac.fragments.FragmentVietNam;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String SHOW_BAI_HAT = "ShowBaiHat";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.app_name);
        }
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new FragmentVietNam());
        mFragmentList.add(new FragmentUSUK());
        mFragmentList.add(new FragmentHoa());
        mFragmentList.add(new FragmentHan());
        mFragmentList.add(new FragmentNhat());
        mTitleList = new ArrayList<>();
        mTitleList.add("Việt Nam");
        mTitleList.add("US-UK");
        mTitleList.add("Hoa");
        mTitleList.add("Hàn");
        mTitleList.add("Nhật");
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
