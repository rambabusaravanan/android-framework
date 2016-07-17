package com.rambabusaravanan.android.framework.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andro Babu on Oct 20, 2015.
 */
public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments;
    private final List titles;

    public BaseFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
        this.titles = new ArrayList<>();
    }

    public <T> BaseFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<T> titles) {
        super(fragmentManager);
        this.fragments = fragments;
        this.titles = titles;
    }

    public BaseFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments, int stringArrayResId, Context context) {
        super(fragmentManager);
        this.fragments = fragments;
        String[] array = context.getResources().getStringArray(stringArrayResId);
        this.titles = Arrays.asList(array);
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
        if (titles != null && titles.size() > position && titles.get(position).toString().length() > 0)
            return titles.get(position).toString();
        else
            return null;
    }
}
