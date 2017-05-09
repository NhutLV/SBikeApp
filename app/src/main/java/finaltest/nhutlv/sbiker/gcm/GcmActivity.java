package finaltest.nhutlv.sbiker.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.Rating;
import finaltest.nhutlv.sbiker.services.cloud.RateServiceImpl;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.tools.FlowerDialog;
import finaltest.nhutlv.sbiker.tools.OptionDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import okhttp3.ResponseBody;

public class GcmActivity extends AppCompatActivity {

    private static final String TAG = GcmActivity.class.getSimpleName();
    private RateServiceImpl mRateService;
    @BindView(R.id.rate_title)
    TextView mTitle;

    @BindView(R.id.txt_rating_place_from)
    TextView mPlaceFrom;

    @BindView(R.id.txt_rating_place_to)
    TextView mPlaceTo;

    @BindView(R.id.txt_rating)
    TextView mRating;

    @BindView(R.id.radio_group_rate)
    RadioGroup mRadioGroup;

    @BindView(R.id.comment_rate)
    EditText mEdComment;

    @BindView(R.id.btn_submit_rate)
    Button mSubmit;

    private String text;
    private FlowerDialog mFlowerDialog;

    private RadioButton checkedRadioButton;
    //Creating a broadcast receiver for gcm registration
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcm);
        ButterKnife.bind(this);
        mRateService = new RateServiceImpl();
        mFlowerDialog = new FlowerDialog(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String message = extras.getString("message");
            String placeTo = extras.getString("place_to");
            String placeFrom = extras.getString("place_from");
            final String idHistory = extras.getString("id_history");

            Log.d(TAG,idHistory);
            mTitle.setText(message);
            mPlaceTo.setText("Điểm đến : "+placeTo);
            mPlaceFrom.setText("Điểm khởi hành : "+placeFrom);
            String comment ="";
            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    checkedRadioButton = (RadioButton) findViewById(checkedId);
                    text = checkedRadioButton.getText().toString();
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            });
            final String finalComment = comment;
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlowerDialog.showDialog();
                    mRateService.sendRate(idHistory, text, finalComment, new Callback<Rating>() {
                        @Override
                        public void onResult(Rating responseBody) {
                            mFlowerDialog.hideDialog();
                            new OptionDialog(GcmActivity.this, "Cảm ơn bạn đã gởi đánh giá cho chúng tôi", new OptionDialog.onMyClickDialog() {
                                @Override
                                public void onClickDialog() {
                                    finish();
                                }
                            }).show();
                            Log.d(TAG,"OK");
                        }

                        @Override
                        public void onFailure(String message) {
                            mFlowerDialog.hideDialog();
                            new ErrorDialog(GcmActivity.this,message).show();
                        }
                    });
                }
            });
        }
    }
}