package finaltest.nhutlv.sbiker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import finaltest.nhutlv.sbiker.fragment.AutoRepairFragment;
import finaltest.nhutlv.sbiker.fragment.BaseFragment;
import finaltest.nhutlv.sbiker.fragment.StoreRepairFragment;

/**
 * Created by NhutDu on 18/10/2016.
 */
public class ViewPagerRepairAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public ViewPagerRepairAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AutoRepairFragment();
            case 1:
                return new StoreRepairFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
