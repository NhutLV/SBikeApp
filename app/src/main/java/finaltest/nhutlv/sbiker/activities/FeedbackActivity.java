package finaltest.nhutlv.sbiker.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.Feedback;
import finaltest.nhutlv.sbiker.services.cloud.FeedbackServiceImpl;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.tools.FlowerDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.CustomDialog;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 24/04/2017.
 */

public class FeedbackActivity extends AppCompatActivity {

    @BindView(R.id.ed_title_feedback)
    EditText mEdTitle;

    @BindView(R.id.ed_content_feedback)
    EditText mEdContent;

    @BindView(R.id.btn_submit_feedback)
    Button mBtnFeedback;

    private FeedbackServiceImpl mService;
    FlowerDialog mFlowerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFlowerDialog = new FlowerDialog(this,"Send...");
        mBtnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFrom()){
                    mFlowerDialog.showDialog();
                    Feedback feedback = new Feedback();
                    feedback.setTitle(mEdTitle.getText().toString());
                    feedback.setContent(mEdContent.getText().toString());
                    feedback.setIdUser(UserLogin.getUserLogin().getIdUser());
                    mService = new FeedbackServiceImpl();
                    mService.saveFeedback(feedback, new Callback<Feedback>() {
                        @Override
                        public void onResult(Feedback feedback) {
                            mFlowerDialog.hideDialog();
                            Toast.makeText(FeedbackActivity.this,"Send feedback is successfully !",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(String message) {
                            mFlowerDialog.hideDialog();
                            new ErrorDialog(FeedbackActivity.this,message).show();
                        }
                    });
                }
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

    private boolean validateFrom(){
        String content = mEdContent.getText().toString();
        if(TextUtils.isEmpty(content)){
            new ErrorDialog(FeedbackActivity.this,"Không thể bỏ trống nội dung phản hồi").show();
            return false;
        }else if(content.length()<20){
            new ErrorDialog(FeedbackActivity.this,"Nội dung phản hồi phản hơn 20 ký tự").show();
            return false;
        }
        return true;
    }

}
