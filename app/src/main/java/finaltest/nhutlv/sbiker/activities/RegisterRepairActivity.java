package finaltest.nhutlv.sbiker.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;

/**
 * Created by NhutDu on 02/04/2017.
 */

public class RegisterRepairActivity extends AppCompatActivity implements View.OnClickListener{



    @BindView(R.id.txt_time_open_register)
    TextView mTxtTimeOpen;

    @BindView(R.id.txt_time_close_register)
    TextView mTxtTimeClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_repair);
        ButterKnife.bind(this);

        mTxtTimeOpen.setOnClickListener(this);
        mTxtTimeClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_time_open_register:
                TimePickerDialog timePickerDialogOpen = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTxtTimeOpen.setText(hourOfDay+" : "+minute);
                    }
                }, 12, 30, true);
                timePickerDialogOpen.show();
                break;

            case R.id.txt_time_close_register:
                TimePickerDialog timePickerDialogClose = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTxtTimeClose.setText(hourOfDay+" : "+minute);
                    }
                }, 12, 30, true);
                timePickerDialogClose.show();
                break;
        }
    }

}
