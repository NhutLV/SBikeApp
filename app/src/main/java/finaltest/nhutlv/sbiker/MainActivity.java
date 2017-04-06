package finaltest.nhutlv.sbiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.fragment.MotorbikeTaxiFragment;
import finaltest.nhutlv.sbiker.fragment.RepairBikeFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.bottomNavigation)
    BottomNavigationView mBottomNavigationView;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frameMain,new MotorbikeTaxiFragment()).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        View header = navigationView.getHeaderView(0);

        ImageButton editProfile = (ImageButton) header.findViewById(R.id.btn_edit_header);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.menu_bottom_motorbike:
                        fragmentTransaction.replace(R.id.frameMain,new MotorbikeTaxiFragment()).commit();
                        mBottomNavigationView.getMenu().getItem(1).setChecked(true);
                        mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                        break;
                    case R.id.menu_bottom_repair:
                        fragmentTransaction.replace(R.id.frameMain,new RepairBikeFragment()).commit();
                        mBottomNavigationView.getMenu().getItem(0).setChecked(true);
                        mBottomNavigationView.getMenu().getItem(1).setChecked(false);
                        break;
                }

                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new MotorbikeTaxiFragment();
        } else if (id == R.id.nav_register) {
            startActivity(new Intent(new Intent(MainActivity.this,RegisterInfoRepairFragment.class)));
            return true;
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        } else if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_driver) {

        } else if (id == R.id.nav_invite_friend) {

        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain,fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
