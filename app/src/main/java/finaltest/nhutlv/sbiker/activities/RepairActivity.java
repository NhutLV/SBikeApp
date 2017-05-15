package finaltest.nhutlv.sbiker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.*;
import finaltest.nhutlv.sbiker.adapter.ViewPagerRepairAdapter;

/**
 * Created by NhutDu on 26/04/2017.
 */

public class RepairActivity extends AppCompatActivity{

    private ViewPagerRepairAdapter mAdapter;

    @BindView(R.id.viewPagerRepair)
    ViewPager mViewPager;

    @BindView(R.id.tabLayoutRepair)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_bike);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.title_repair));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAdapter = new ViewPagerRepairAdapter(getSupportFragmentManager(),2);
        mViewPager.setOffscreenPageLimit(2);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repair,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.register_repair:
                startActivity(new Intent(RepairActivity.this, RegisterRepairActivity.class));
                break;
        }
        return true;
    }
}
