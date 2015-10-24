package com.rambabusaravanan.android.framework.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andro Babu on Oct 20, 2015.
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private final List titles;

    public BaseFragmentAdapter(FragmentManager supportFragmentManager, List<Fragment> fragments) {
        super(supportFragmentManager);
        this.fragments = fragments;
        this.titles = new ArrayList<>();
    }

    public <T> BaseFragmentAdapter(FragmentManager supportFragmentManager, List<Fragment> fragments, List<T> titles) {
        super(supportFragmentManager);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() == fragments.size() && titles.get(position).toString().length() > 0)
            return titles.get(position).toString();
        else
            return position + "";
    }
}
