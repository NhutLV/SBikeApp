package finaltest.nhutlv.sbiker.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.specials.out.TakingOffAnimator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.HistoryAdapter;
import finaltest.nhutlv.sbiker.entities.Coordinate;
import finaltest.nhutlv.sbiker.entities.History;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.HistoryServiceImpl;
import finaltest.nhutlv.sbiker.tools.BikerInfoDialog;
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.tools.FlowerDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 08/04/2017.
 */

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.onMyListener{

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private ArrayList<History> mHistories;
    private HistoryAdapter mAdapter;
    private HistoryServiceImpl mHistoryService;
    private FlowerDialog mFlowerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        mHistoryService = new HistoryServiceImpl();
        mFlowerDialog = new FlowerDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mFlowerDialog.showDialog();
        mHistories = new ArrayList<>();
        mAdapter = new HistoryAdapter(this,mHistories,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvHistory.setLayoutManager(manager);
        mRvHistory.setAdapter(mAdapter);
        mRvHistory.addItemDecoration(new DividerItemDecoration(this,1));
        mRvHistory.setItemAnimator(new DefaultItemAnimator());
        mHistoryService.getListHistory(UserLogin.getUserLogin().getIdUser(), new Callback<List<History>>() {
            @Override
            public void onResult(List<History> histories) {
                mFlowerDialog.hideDialog();
                mHistories.clear();
                mHistories.addAll(histories);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                mFlowerDialog.hideDialog();
                new ErrorDialog(getContext(),message).show();
            }
        });
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


    @Override
    public void onClickBiker(int position) {
        User user = new User("Lê A", "01687184516", new Coordinate(16.0762584, 108.1608916));
        new BikerInfoDialog(this, new BikerInfoDialog.myClickListener() {
            @Override
            public void onButtonClick(User user) {
            }

            @Override
            public void onCheckBox(User user) {

            }
        },user).show();
    }

    @Override
    public void onClickItem(int position) {
        Toast.makeText(this,"Detail History index "+ (position+1),Toast.LENGTH_LONG ).show();
    }

    private Context getContext(){
        return this;
    }

}
