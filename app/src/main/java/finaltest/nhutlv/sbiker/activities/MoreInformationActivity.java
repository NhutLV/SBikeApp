package finaltest.nhutlv.sbiker.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 28/05/2017.
 */

public class MoreInformationActivity extends AppCompatActivity {

    @BindView(R.id.ed_more_number_idenf)
    EditText mNumberIdenf;

    @BindView(R.id.ed_more_date_idenf)
    EditText mDateIdenf;

    @BindView(R.id.ed_more_place_idenf)
    EditText mPlaceIdenf;

    @BindView(R.id.ed_more_number_license)
    EditText mNumberLicense;

    @BindView(R.id.ed_more_seri_license)
    EditText mSeriLicense;

    @BindView(R.id.ed_more_number_card)
    EditText mNumberCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getResources().getString(R.string.title_information));
        if(UserLogin.getUserLogin()!=null){
            User user = UserLogin.getUserLogin();
            mNumberIdenf.setText(user.getNumberIdentification());
            mPlaceIdenf.setText(user.getPlaceIdentification());
            mDateIdenf.setText(user.getDateIdentification());
            mNumberLicense.setText(user.getNumberLicense());
            mSeriLicense.setText(user.getSeriLicense());
            mNumberCard.setText(user.getNumberCard());
        }
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
}
