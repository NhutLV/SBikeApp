package finaltest.nhutlv.sbiker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.ViewPagerRepairAdapter;

/**
 * Created by NhutDu on 31/03/2017.
 */

public class RepairBikeFragment extends Fragment {

    private ViewPagerRepairAdapter mAdapter;

    @BindView(R.id.viewPagerRepair)
    ViewPager mViewPager;

    @BindView(R.id.tabLayoutRepair)
    TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair_bike,container,false);
        ButterKnife.bind(this,view);

        mAdapter = new ViewPagerRepairAdapter(getActivity().getSupportFragmentManager(),2);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("Auto");
        mTabLayout.getTabAt(1).setText("Store");

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAG TAB SUB",tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
