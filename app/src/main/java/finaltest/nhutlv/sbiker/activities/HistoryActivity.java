package finaltest.nhutlv.sbiker.activities;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.HistoryAdapter;
import finaltest.nhutlv.sbiker.entities.Coordinate;
import finaltest.nhutlv.sbiker.entities.History;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.tools.BikerInfoDialog;
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;

/**
 * Created by NhutDu on 08/04/2017.
 */

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.onMyListener{

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private ArrayList<History> mHistories;
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mHistories = new ArrayList<>();
        mHistories = getHistories();
        mAdapter = new HistoryAdapter(this,mHistories,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvHistory.setLayoutManager(manager);
        mRvHistory.setAdapter(mAdapter);
        mRvHistory.addItemDecoration(new DividerItemDecoration(this,1));
        mRvHistory.setItemAnimator(new DefaultItemAnimator());
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

    private ArrayList<History> getHistories(){
        ArrayList<History> list = new ArrayList<>();
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",6.8,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",8.8,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",6.9,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",12.8,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",4.6,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",6.8,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",6.0,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",9.6,35000));
        list.add(new History("1",new Date(),"54 Điện Biên Phủ," +
                " Đà Nẵng","Kiệt 82 Nguyễn Lương Bằng, Hòa Khánh Bắc, Đà Nẵng",6.9,35000));
        return list;
    }

    @Override
    public void onClickBiker(int position) {
        User user = new User("Lê A", "01687184516", new Coordinate(16.0762584, 108.1608916));
        new BikerInfoDialog(this, new BikerInfoDialog.myClickListener() {
            @Override
            public void onButtonClick(User user) {
            }
        },user).show();
    }

    @Override
    public void onClickItem(int position) {
        Toast.makeText(this,"Detail History index "+ (position+1),Toast.LENGTH_LONG ).show();
    }

}
