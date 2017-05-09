package finaltest.nhutlv.sbiker.activities;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class RegisterRepairActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {


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
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LatLng mLatLng = null;
    public static boolean mMapIsTouched = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_repair);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

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
                if (checkedId == R.id.radio_auto) {
                    type = 0;
                } else if (checkedId == R.id.radio_store) {
                    type = 1;
                }
            }
        });
        mSubmit.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_open_register_repair:
                TimePickerDialog timePickerDialogOpen = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        minuteOpen = minute;
                        hourOpen = hourOfDay;
                        mTimeOpen.setText(hourOfDay + " : " + minute);
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
                        mTimeClose.setText(hourOfDay + " : " + minute);
                    }
                }, hourClose, minuteClose, true);
                timePickerDialogClose.show();
                break;

            case R.id.btn_register_repair:
                if (submitForm()) {
                    Repairer<String> repairer = new Repairer<>();
                    repairer.setName(mName.getText().toString());
                    repairer.setAddress(mAddress.getText().toString());
                    repairer.setNumberPhone(mNumberPhone.getText().toString());
                    repairer.setTimeClose(mTimeClose.getText().toString());
                    repairer.setTimeOpen(mTimeOpen.getText().toString());
                    repairer.setUserCreated(UserLogin.getUserLogin().getIdUser());
                    repairer.setType(type);
                    repairer.setLatitude(mLatLng.latitude);
                    repairer.setLongitude(mLatLng.longitude);
                    mFlowerDialog.showDialog();
                    mRepairService.saveRepair(repairer, new Callback<Repairer<String>>() {
                        @Override
                        public void onResult(Repairer<String> stringRepairer) {
                            mFlowerDialog.hideDialog();
                            Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(String message) {
                            mFlowerDialog.hideDialog();
                            new ErrorDialog(getContext(), message).show();
                        }
                    });
                }
                break;
        }
    }

    private boolean validateName() {
        if (TextUtils.isEmpty(mName.getText().toString())) {
            new ErrorDialog(getContext(), "Vui lòng nhập tên").show();
            return false;
        }
        return true;
    }

    private boolean validateAddress() {
        if (TextUtils.isEmpty(mAddress.getText().toString())) {
            new ErrorDialog(getContext(), "Vui lòng nhập địa chỉ").show();
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        if (TextUtils.isEmpty(mNumberPhone.getText().toString())) {
            new ErrorDialog(getContext(), "Vui lòng nhập số điện thoại").show();
            return false;
        }
        return true;
    }

    private boolean validateTimeOpen() {
        if (TextUtils.isEmpty(mTimeOpen.getText().toString())) {
            new ErrorDialog(getContext(), "Vui lòng chọn thời gian mở cửa").show();
            return false;
        }
        return true;
    }

    private boolean validateTimeClose() {
        if (TextUtils.isEmpty(mTimeOpen.getText().toString())) {
            new ErrorDialog(getContext(), "Vui lòng chọn thời gian đóng cửa").show();
            return false;
        }
        return true;
    }

    private boolean validateType() {
        if (mRadioGroup.getCheckedRadioButtonId() == -1) {
            new ErrorDialog(getContext(), "Vui lòng chọn type").show();
            return false;
        }
        return true;
    }

    private boolean submitForm() {
        if (!validateName()) {
            return false;
        }
        if (!validateAddress()) {
            return false;
        }
        if (!validatePhone()) {
            return false;
        }
        if (!validateType()) {
            return false;
        }
        if (!validateTimeOpen()) {
            return false;
        }
        if (!validateTimeClose()) {
            return false;
        }
        if(mLatLng==null){
            new ErrorDialog(getContext(),"Vui lòng chọn đại điểm trên bản đồ").show();
            return false;
        }
        return true;
    }

    //get Context
    private Context getContext() {
        return this;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UserLogin.getUserLogin().getLatLng(), 17));
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub
                mLatLng = arg0;
                mGoogleMap.clear();
                Log.d("TAGGGGG", arg0.latitude + "-" + arg0.longitude);
                MarkerOptions placeSearch = new MarkerOptions()
                        .position(arg0)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                mGoogleMap.addMarker(placeSearch).showInfoWindow();
            }
        });
    }
}
