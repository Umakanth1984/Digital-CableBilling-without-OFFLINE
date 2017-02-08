package com.digitalrupay.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.digitalrupay.activities.Customer.fragment.ComplaintsHistoryFragment;
import com.digitalrupay.activities.Customer.fragment.CustomersComplaintsFragment;

import java.util.HashMap;

/**
 * Created by Audlink_sri on 12/12/2016.
 */

public class CustomerPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private HashMap<Integer, Fragment> mPageReferenceMap;

    public CustomerPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        mPageReferenceMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ComplaintsHistoryFragment complaintsFragment = new ComplaintsHistoryFragment();
                mPageReferenceMap.put(position, complaintsFragment);
                return complaintsFragment;
            case 1:
                CustomersComplaintsFragment registerComplaintFragment = new CustomersComplaintsFragment();
                mPageReferenceMap.put(position, registerComplaintFragment);
                return registerComplaintFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    /* To remove inactive fragments from mPageReferenceMap we need to implement destroyItem callback */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }
}
