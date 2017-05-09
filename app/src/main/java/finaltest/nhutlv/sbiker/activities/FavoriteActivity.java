package finaltest.nhutlv.sbiker.activities;

/**
 * Created by NhutDu on 08/04/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.daimajia.swipe.util.Attributes;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.FavoriteAdapter;
import finaltest.nhutlv.sbiker.entities.Favorite;
import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.UserService;
import finaltest.nhutlv.sbiker.services.cloud.UserServiceImpl;
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.tools.FlowerDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.MyClickListener {

    /**
     * RecyclerView: The new recycler view replaces the list view. Its more modular and therefore we
     * must implement some of the functionality ourselves and attach it to our fragment_auto.
     * <p/>
     * 1) Position items on the screen: This is done with LayoutManagers
     * 2) Animate & Decorate views: This is done with ItemAnimators & ItemDecorators
     * 3) Handle any touch events apart from scrolling: This is now done in our adapter's ViewHolder
     */

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Favorite> mBikers;
    private UserServiceImpl mUserService;
    private FlowerDialog mFlowerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_auto);
        mBikers = new ArrayList<>();
        mUserService = new UserServiceImpl();
        mFlowerDialog = new FlowerDialog(getContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(this,1));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        // Adapter:
        mFlowerDialog.showDialog();
        mUserService.getListFavorite(UserLogin.getUserLogin().getIdUser(), new Callback<List<Favorite>>() {
            @Override
            public void onResult(List<Favorite> users) {
                mFlowerDialog.hideDialog();
                if(users.size() ==0){
                    setContentView(R.layout.no_item_favorite);
                }else{
                    mBikers.clear();
                    mBikers.addAll(users);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                mFlowerDialog.hideDialog();
                new ErrorDialog(getContext(),message).show();
            }
        });
        mAdapter = new FavoriteAdapter(this,mBikers);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ((FavoriteAdapter) mAdapter).setMode(Attributes.Mode.Single);
        ((FavoriteAdapter) mAdapter).setMyClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);
    }


    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };

    @Override
    public void onDelete(int position) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    //get context activity
    private Context getContext(){
        return this;
    }
}
