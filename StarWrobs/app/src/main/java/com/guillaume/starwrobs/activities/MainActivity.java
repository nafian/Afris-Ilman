package com.guillaume.starwrobs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.controller.DataController;
import com.guillaume.starwrobs.fragments.SWListFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new DataController(getApplicationContext()).refreshData();
                return true;
            case R.id.action_delete_data:
                new DataController(getApplicationContext()).deleteDatabase();
                return true;
            case R.id.action_show_licenses:
                startActivity(new Intent(MainActivity.this, LicensesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(SWListFragment.newInstance(SWListFragment.KEY_PEOPLE), getString(R.string.ui_tab_people));
        adapter.addFragment(SWListFragment.newInstance(SWListFragment.KEY_FILMS), getString(R.string.ui_tab_films));
        adapter.addFragment(SWListFragment.newInstance(SWListFragment.KEY_PLANETS), getString(R.string.ui_tab_planets));
        adapter.addFragment(SWListFragment.newInstance(SWListFragment.KEY_SPECIES), getString(R.string.ui_tab_species));
        adapter.addFragment(SWListFragment.newInstance(SWListFragment.KEY_STARSHIPS), getString(R.string.ui_tab_starships));
        adapter.addFragment(SWListFragment.newInstance(SWListFragment.KEY_VEHICLES), getString(R.string.ui_tab_vehicles));
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
