package ufcg.embedded.miniprojeto.toolbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import ufcg.embedded.miniprojeto.fragments.CustomerFragment;
import ufcg.embedded.miniprojeto.fragments.CustomerInfoFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private Map<Integer, String> mFragmentsTags;
    private FragmentManager mFragmentManager;
    private String[] titlesTabs;

    public PagerAdapter(FragmentManager fm, String[] titlesTabs) {
        super(fm);
        mFragmentManager = fm;
        this.titlesTabs = titlesTabs;
        mFragmentsTags = new HashMap<Integer, String>();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
                return new CustomerFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titlesTabs[position];
    }

    @Override
    public int getCount() {
        return titlesTabs.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentsTags.put(position, tag);
        }
        return obj;
    }

}
