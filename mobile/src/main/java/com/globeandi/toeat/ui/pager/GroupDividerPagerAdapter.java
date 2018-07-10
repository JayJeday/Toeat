package com.globeandi.toeat.ui.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.globeandi.toeat.ui.pager.triphistory.TripHistoryFragment;
import com.globeandi.toeat.ui.pager.upcomingtrips.UpcomingTripFragment;

public class GroupDividerPagerAdapter extends FragmentStatePagerAdapter {

    private int numTabs;

    public GroupDividerPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        numTabs = 0;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UpcomingTripFragment.newInstance();
            case 1:
                return TripHistoryFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    public void setCount(int count){
        this.numTabs = count;
    }
}
