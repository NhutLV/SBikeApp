package finaltest.nhutlv.sbiker.activities;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.dialog.ErrorDialog;
import finaltest.nhutlv.sbiker.entities.PriceList;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.PriceListServiceImpl;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 16/05/2017.
 */

public class PriceListActivity extends AppCompatActivity {

    @BindView(R.id.first_price_list)
    TextView mFirstPrice;

    @BindView(R.id.second_price_list)
    TextView mSecondPrice;

    @BindView(R.id.fee_price_list)
    TextView mFeePrice;

    DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getResources().getString(R.string.price_list));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(UserLogin.getPriceList()!=null){
            mFirstPrice.setText(formatter.format(UserLogin.getPriceList().getFirstPrice()) + " VNĐ/1km");
            mSecondPrice.setText(formatter.format(UserLogin.getPriceList().getSecondPrice()) + " VNĐ/1km");
            mFeePrice.setText(formatter.format(UserLogin.getPriceList().getFeePrice()) + " VNĐ/1 chuyến");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                break;
        }
        return true;
    }

    private Context getContext() {
        return this;
    }
}
