package finaltest.nhutlv.sbiker.activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.services.cloud.RepairServiceImpl;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.tools.FlowerDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 02/04/2017.
 */

public class RegisterRepairActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.name_register_repair)
    EditText mName;

    @BindView(R.id.address_register_repair)
    EditText mAddress;

    @BindView(R.id.phone_register_repair)
    EditText mNumberPhone;

    @BindView(R.id.time_open_register_repair)
    TextView mTimeOpen;

    @BindView(R.id.time_close_register_repair)
    TextView mTimeClose;

    @BindView(R.id.radio_group_type)
    RadioGroup mRadioGroup;

    @BindView(R.id.btn_register_repair)
    Button mSubmit;

    private RepairServiceImpl mRepairService;
    private FlowerDialog mFlowerDialog;
    private int hourOpen;
    private int minuteOpen;
    private int hourClose;
    private int minuteClose;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_repair);
        ButterKnife.bind(this);

        mFlowerDialog = new FlowerDialog(getContext());
        mRepairService = new RepairServiceImpl();
        Calendar calendar = Calendar.getInstance();
        hourClose = hourOpen = calendar.get(Calendar.HOUR_OF_DAY);
        minuteClose = minuteOpen = calendar.get(Calendar.MINUTE);
        mTimeOpen.setOnClickListener(this);
        mTimeClose.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.radio_auto){
                    type =0;
                }else if(checkedId==R.id.radio_store){
                    type =1;
                }
            }
        });
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.time_open_register_repair:
                TimePickerDialog timePickerDialogOpen = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        minuteOpen = minute;
                        hourOpen = hourOfDay;
                        mTimeOpen.setText(hourOfDay+" : "+minute);
                    }
                }, hourOpen, minuteOpen, true);
                timePickerDialogOpen.show();
                break;

            case R.id.time_close_register_repair:
                TimePickerDialog timePickerDialogClose = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourClose = hourOfDay;
                        minuteClose = minute;
                        mTimeClose.setText(hourOfDay+" : "+minute);
                    }
                }, hourClose, minuteClose, true);
                timePickerDialogClose.show();
                break;

            case R.id.btn_register_repair:
                if(submitForm()){
                    Repairer<String> repairer = new Repairer<>();
                    repairer.setName(mName.getText().toString());
                    repairer.setAddress(mAddress.getText().toString());
                    repairer.setNumberPhone(mNumberPhone.getText().toString());
                    repairer.setTimeClose(mTimeClose.getText().toString());
                    repairer.setTimeOpen(mTimeOpen.getText().toString());
                    repairer.setUserCreated(UserLogin.getUserLogin().getIdUser());
                    repairer.setType(type);
                    mFlowerDialog.showDialog();
                    mRepairService.saveRepair(repairer, new Callback<Repairer<String>>() {
                        @Override
                        public void onResult(Repairer<String> stringRepairer) {
                            mFlowerDialog.hideDialog();
                            Toast.makeText(getContext(),"Đăng ký thành công",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(String message) {
                            mFlowerDialog.hideDialog();
                            new ErrorDialog(getContext(),message).show();
                        }
                    });
                }
                break;
        }
    }

    private boolean validateName(){
        if(TextUtils.isEmpty(mName.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập tên").show();
            return false;
        }
        return true;
    }

    private boolean validateAddress(){
        if(TextUtils.isEmpty(mAddress.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập địa chỉ").show();
            return false;
        }
        return true;
    }

    private boolean validatePhone(){
        if(TextUtils.isEmpty(mNumberPhone.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập số điện thoại").show();
            return false;
        }
        return true;
    }

    private boolean validateTimeOpen(){
        if(TextUtils.isEmpty(mTimeOpen.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng chọn thời gian mở cửa").show();
            return false;
        }
        return true;
    }

    private boolean validateTimeClose(){
        if(TextUtils.isEmpty(mTimeOpen.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng chọn thời gian đóng cửa").show();
            return false;
        }
        return true;
    }

    private boolean validateType(){
        if(mRadioGroup.getCheckedRadioButtonId()==-1){
            new ErrorDialog(getContext(),"Vui lòng chọn type").show();
            return false;
        }
        return true;
    }

    private boolean submitForm(){
        if(!validateName()){
            return false;
        }
        if(!validateAddress()){
            return false;
        }
        if(!validatePhone()){
            return false;
        }
        if(!validateType()){
            return false;
        }
        if(!validateTimeOpen()){
            return false;
        }
        if(!validateTimeClose()){
            return false;
        }
        return true;
    }

    //get Context
    private Context getContext(){
        return this;
    }
}
