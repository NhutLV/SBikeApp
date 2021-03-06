package finaltest.nhutlv.sbiker.gcm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.Rating;
import finaltest.nhutlv.sbiker.services.cloud.RateServiceImpl;
import finaltest.nhutlv.sbiker.dialog.ErrorDialog;
import finaltest.nhutlv.sbiker.dialog.FlowerDialog;
import finaltest.nhutlv.sbiker.dialog.OptionDialog;
import finaltest.nhutlv.sbiker.utils.Callback;

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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_evaluation));
        mRateService = new RateServiceImpl();
        mFlowerDialog = new FlowerDialog(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String message = extras.getString("message");
            String placeTo = extras.getString("place_to");
            String placeFrom = extras.getString("place_from");
            final String idHistory = extras.getString("id_history");

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