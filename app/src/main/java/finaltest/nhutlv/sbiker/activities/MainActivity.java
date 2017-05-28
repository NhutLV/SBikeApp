package finaltest.nhutlv.sbiker.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kyleduo.switchbutton.SwitchButton;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.dialog.LogoutDialog;
import finaltest.nhutlv.sbiker.entities.History;
import finaltest.nhutlv.sbiker.entities.Place;
import finaltest.nhutlv.sbiker.entities.PriceList;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.HistoryServiceImpl;
import finaltest.nhutlv.sbiker.services.cloud.PriceListServiceImpl;
import finaltest.nhutlv.sbiker.services.cloud.UserServiceImpl;
import finaltest.nhutlv.sbiker.dialog.BikerInfoDialog;
import finaltest.nhutlv.sbiker.dialog.ErrorDialog;
import finaltest.nhutlv.sbiker.dialog.FlowerDialog;
import finaltest.nhutlv.sbiker.tools.LocationProvider;
import finaltest.nhutlv.sbiker.tools.PrefManagement;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;
import finaltest.nhutlv.sbiker.utils.SBConstants;
import finaltest.nhutlv.sbiker.utils.SBFunctions;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LocationProvider.LocationCallback,
        RoutingListener,
        GoogleMap.OnInfoWindowClickListener,
        BikerInfoDialog.myClickListener {

    private final static int PROFILE_REQUEST_CODE = 1;
    private final static int HISTORY_REQUEST_CODE = 2;
    private final static int CALL_PHONE_REQUEST_CODE = 3;
    private final static int FAVORITE_REQUEST_CODE = 4;
    private final static int REPAIR_REQUEST_CODE = 5;
    private final static int BECOME_REQUEST_CODE = 6;
    private final static int PRICE_LIST_REQUEST_CODE = 7;
    private final static int FEEDBACK_REQUEST_CODE = 8;

    private EventBus mEventBus;

    private MapView mMapView;
    private GoogleMap mGoogleMap = null;
    private LocationProvider mLocationProvider;
    private final static String TAG = "TAG MainActivity";
    private LatLng mLatLngCurrent;
    private NavigationView navigationView;
    private Place mPlace = null;
    private Status mStatus = null;
    private RelativeLayout mLayout;
    private double mDistance = 0;
    private int mPrice = 0;
    private int mTimeSpend = 0;
    private String mAddress = "";
    private DecimalFormat df = new DecimalFormat("#,000");

    private Geocoder mGeocoder;
    private TextView mTxtPlaceSearch;
    private TextView mTxtCurrentPlace;
    private FlowerDialog mFlowerDialog;
    private FlowerDialog mFlowerDialogHistory;
    private SwitchButton mSwitchDriver;
    private int isBecome = 0;
    private int isDriving = 0;
    private int isApproved = 0;
    private UserServiceImpl mUserService;
    private HistoryServiceImpl mHistoryService;
    boolean doubleBackToExitPressedOnce = false;
    private static DecimalFormat formatter = new DecimalFormat("###,###,000");
    TextView username;
    //Creating a broadcast receiver for gcm registration

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorApp,
            R.color.colorAccent, R.color.primary_dark_material_light};
    private String mIdBiker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserService = new UserServiceImpl();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHistoryService = new HistoryServiceImpl();
        mGeocoder = new Geocoder(this, Locale.getDefault());
        mEventBus = EventBus.getDefault();
        polylines = new ArrayList<>();

        mFlowerDialog = new FlowerDialog(this);
        mFlowerDialogHistory = new FlowerDialog(getContext(), "Saving history...");

        if (!isLocationEnabled(this)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("GPS is Off\nDo you want to turn on GPS");
            dialog.setPositiveButton("Go Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        } else {
            mFlowerDialog.showDialog();
        }
        mLayout = (RelativeLayout) findViewById(R.id.mapLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //get header
        View header = navigationView.getHeaderView(0);
        ImageButton editProfile = (ImageButton) header.findViewById(R.id.btn_edit_header);
        CircleImageView avatar = (CircleImageView) header.findViewById(R.id.profile_image);
        String path = UserLogin.getUserLogin().getAvatarPath();
        if (path == null || path.equals("")) {
            path = "Ahihi";
        }
        Picasso.with(this)
                .load(path)
                .resize(96, 96)
                .placeholder(R.drawable.image)
                .centerCrop()
                .into(avatar);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivityForResult(intent, PROFILE_REQUEST_CODE);
            }
        });
        username = (TextView) header.findViewById(R.id.txt_name_header);
        username.setText(UserLogin.getUserLogin().getFullName());

        mMapView = (MapView) findViewById(R.id.mapView);
        mTxtPlaceSearch = (TextView) findViewById(R.id.ed_place_search);
        mTxtCurrentPlace = (TextView) findViewById(R.id.ed_current_place);
        mTxtPlaceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchPlaceActivity.class);
                startActivityForResult(intent, SBConstants.PLACE_AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //get current location
        mLocationProvider = new LocationProvider(this, this);
        getPrice();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isBecome = UserLogin.getUserLogin().getIsBecome();
        isDriving = UserLogin.getUserLogin().getIsDriving();

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                if (mMapView != null &&
                        mMapView.findViewById(Integer.parseInt("1")) != null) {
                    // Get the button view
                    View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                    // and next place it, on bottom right (as Google Maps app)
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                            locationButton.getLayoutParams();
                    // position on right bottom
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                    layoutParams.setMargins(0, 0, 30, 240);
                }
            }
        });
        if (mPlace != null) {
            MarkerOptions placeSearch = new MarkerOptions()
                    .position(mPlace.getLatLng())
                    .title(mPlace.getName().toString())
                    .snippet(mPlace.getAddress().toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mGoogleMap.addMarker(placeSearch).showInfoWindow();
            mTxtPlaceSearch.setText(mPlace.getAddress().toString());
            Routing routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.DRIVING)
                    .withListener(this)
                    .waypoints(mLatLngCurrent, mPlace.getLatLng())
                    .build();
            routing.execute();
        }
    }

    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        mLocationProvider.connect();
    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        mLocationProvider.disconnect();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mSwitchDriver = (SwitchButton) findViewById(R.id.switchDriver);
        mSwitchDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (UserLogin.getUserLogin().getIsApproved() == 1) {
                        isDriving = 1;
                        mUserService.setIsDriving(UserLogin.getUserLogin().getIdUser(), isDriving,
                                mLatLngCurrent, new Callback<User>() {
                                    @Override
                                    public void onResult(User user) {
                                        Toast.makeText(getContext(), "Bạn đã bật chế độ tài xê", Toast.LENGTH_LONG).show();
                                        UserLogin.setUserLogin(user);
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        Log.d(TAG, "onFailure: ");
                                        mSwitchDriver.setChecked(false);
                                        new ErrorDialog(MainActivity.this, message).show();
                                    }
                                });
                    } else {
                        isDriving = 0;
                        if (isBecome == 1) {
                            new ErrorDialog(MainActivity.this, "Thông tin của bạn đang được kiểm duyệt\n" +
                                    "Vui lòng thử lại sau").show();
                            mSwitchDriver.setChecked(false);
                        } else {
                            mSwitchDriver.setChecked(false);
                            startActivityForResult(new Intent(MainActivity.this, BecomeDriverActivity.class),
                                    BECOME_REQUEST_CODE);
                        }
                    }
                } else {
                    isDriving = 0;
                    mUserService.setIsDriving(UserLogin.getUserLogin().getIdUser(), isDriving,
                            mLatLngCurrent, new Callback<User>() {
                                @Override
                                public void onResult(User user) {
                                    Toast.makeText(getContext(), "Bạn tắt chế độ tài xế", Toast.LENGTH_LONG).show();
                                    UserLogin.setUserLogin(user);
                                }

                                @Override
                                public void onFailure(String message) {
                                    mSwitchDriver.setChecked(false);
                                    new ErrorDialog(MainActivity.this, message).show();
                                }
                            });
                }
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_register) {
            closeDrawer();
            startActivityForResult(new Intent(MainActivity.this, RepairActivity.class),REPAIR_REQUEST_CODE);
            return true;
        } else if (id == R.id.nav_history) {
            closeDrawer();
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivityForResult(intent, HISTORY_REQUEST_CODE);
            return true;
        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivityForResult(intent, FAVORITE_REQUEST_CODE);
            return true;
        } else if (id == R.id.nav_driver) {
            return true;
        } else if (id == R.id.nav_feedback) {
            closeDrawer();
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivityForResult(intent, FEEDBACK_REQUEST_CODE);
            return true;
        } else if (id == R.id.nav_log_out) {
//            signOutGmail();
//            startActivity(new Intent(new Intent(MainActivity.this, SignInActivity.class)));
//            finish();
            new LogoutDialog(getContext(), new LogoutDialog.onMyListenerLogout() {
                @Override
                public void onLogout() {
                    logOut();
                }
            }).show();
            return true;
        } else if (id == R.id.nav_price_list) {
            closeDrawer();
            Intent intent = new Intent(MainActivity.this, PriceListActivity.class);
            startActivityForResult(intent,PRICE_LIST_REQUEST_CODE);
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void handleNewLocation(final Location location) {
        boolean checked = false;
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        Log.d("TAGGGGG", currentLatitude + " - " + currentLongitude);
        mLatLngCurrent = new LatLng(currentLatitude, currentLongitude);
        mAddress = getAddress(mLatLngCurrent);
        mTxtCurrentPlace.setText(mAddress);
        UserLogin.getUserLogin().setLatLng(mLatLngCurrent);
        mEventBus.postSticky(mLatLngCurrent);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.clear();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mUserService.getListUserByRadius(200000, mLatLngCurrent, new Callback<List<User>>() {
            @Override
            public void onResult(List<User> users) {
                mFlowerDialog.hideDialog();
                Log.d(TAG, "Get list by radius is OK");
                setPlaceToMap(mGoogleMap, users);
            }

            @Override
            public void onFailure(String message) {
                mFlowerDialog.hideDialog();
                new ErrorDialog(getContext(), message).show();
            }
        });


        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude))
        // .title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(mLatLngCurrent)
                .title(mAddress)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mGoogleMap.addMarker(options);

        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mLatLngCurrent).zoom(13).build();

        if (!checked) {
            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            checked = true;
        }

        CircleOptions circleOptions = new CircleOptions()
                .center(mLatLngCurrent)
                .radius(3000)
                .strokeWidth(2)
                .strokeColor(getResources().getColor(R.color.colorCircleStroke))
                .fillColor(getResources().getColor(R.color.colorCircleFill));
        mGoogleMap.addCircle(circleOptions);
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mGoogleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
                mAddress = getAddress(mLatLngCurrent);
                mTxtCurrentPlace.setText(mAddress);
                return true;
            }
        });

    }

    // activity for result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogleMap.clear();
        if(resultCode ==  RESULT_OK){
            switch (requestCode){
                case PROFILE_REQUEST_CODE:
                    username.setText(UserLogin.getUserLogin().getFullName());
                    break;
                case SBConstants.PLACE_AUTOCOMPLETE_REQUEST_CODE:
                    Bundle bundle = data.getExtras();
                    mPlace = bundle.getParcelable("searchPlace");
                    mStatus = bundle.getParcelable("searchStatus");
                    mUserService.getListUserByRadius(11000, mLatLngCurrent, new Callback<List<User>>() {
                        @Override
                        public void onResult(List<User> users) {
                            setPlaceToMap(mGoogleMap, users);
                        }

                        @Override
                        public void onFailure(String message) {
                            new ErrorDialog(getContext(), message).show();
                        }
                    });
                    break;
                case PRICE_LIST_REQUEST_CODE:
                    resetNavigation();
                    break;
                case HISTORY_REQUEST_CODE:
                    resetNavigation();
                    break;
                case FAVORITE_REQUEST_CODE:
                    resetNavigation();
                    break;
                case BECOME_REQUEST_CODE:
                    resetNavigation();
                    break;
                case REPAIR_REQUEST_CODE:
                    resetNavigation();
                    break;
                case FEEDBACK_REQUEST_CODE:
                    resetNavigation();
                    break;
            }
        }
    }


    private void setPlaceToMap(GoogleMap googleMap, List<User> users) {

        for (User user : users) {
            MarkerOptions options = new MarkerOptions()
                    .position(user.getLatLng())
                    .title(user.getFullName())
                    .snippet(user.getNumberPhone())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            Marker marker = googleMap.addMarker(options);
            marker.setTag(user);
        }
        googleMap.setOnInfoWindowClickListener(this);
    }

    private void callPhone(User user) {
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.getNumberPhone()));
        mIdBiker = user.getIdUser();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        startActivityForResult(i, CALL_PHONE_REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mGoogleMap.addPolyline(polyOptions);
            polylines.add(polyline);
            int distance = (int) SBFunctions.getDistance2Point(mLatLngCurrent, mPlace.getLatLng());
            String sDistance = "";
            if (distance < 1000) {
                sDistance = distance + " m";
            } else {
                sDistance = df.format(distance) + " km";
            }
            mDistance = distance;
            mPrice = SBFunctions.calculationMoney((double) distance / 1000);
            mTimeSpend = route.get(i).getDurationValue() / 60;
            String detail = "Khoảng cách " + sDistance + "\nMất "
                    + SBFunctions.parseTime(mTimeSpend)
                    + " - " + formatter.format(mPrice) + "VNĐ";

            final Snackbar snackbar = Snackbar.make(mLayout, detail, BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("HIDE", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            snackbar.show();
        }
    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        User user = (User) marker.getTag();
        Dialog dialog = new BikerInfoDialog(this, this, user);
        dialog.show();
    }

    @Override
    public void onButtonClick(User user) {
        if (mPlace == null) {
            Toast.makeText(getContext(), "Vui lòng chọn địa diểm bạn muốn đến", Toast.LENGTH_LONG).show();
        } else {
            callPhone(user);
        }
    }

    @Override
    public void onCheckBox(User user, boolean isChecked) {
        int check = isChecked ? 1 : 0;
        mUserService.sendFavorite(UserLogin.getUserLogin().getIdUser(), user.getIdUser(), check, new Callback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {
                Log.d(TAG, "Favorite OK");
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signOutGmail() {
        Auth.GoogleSignInApi.signOut(UserLogin.getGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d(TAG, "onResult: Login Out Google ");
                    }
                });
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(TAG, "RINGING, number: " + incomingNumber);
                Log.i(TAG, "RINGING, number: " + System.currentTimeMillis());
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(TAG, "OFFHOOK");
                Log.i(TAG, "OFFF: " + System.currentTimeMillis());
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(TAG, "IDLE");
                Log.i(TAG, "IDLE: " + System.currentTimeMillis());
                if (isPhoneCalling) {
                    isPhoneCalling = false;
                    if (mPlace != null) {
                        History<String> history = new History();
                        history.setIdUser(UserLogin.getUserLogin().getIdUser());
                        history.setBiker(mIdBiker);
                        history.setDistance(mDistance);
                        history.setTimeCall(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date().getTime()));
                        history.setTimeSpend(mTimeSpend);
                        history.setPrice(mPrice);
                        history.setPlaceFrom(mAddress);
                        history.setLatitudeFrom(mLatLngCurrent.latitude);
                        history.setLongitudeFrom(mLatLngCurrent.longitude);
                        history.setPlaceTo(mPlace.getAddress());
                        history.setLatitudeTo(mPlace.getLatLng().latitude);
                        history.setLongitudeTo(mPlace.getLatLng().longitude);

                        String token = new PrefManagement(getContext()).getValueString(SBConstants.PREF_TOKEN_GCM);
                        mHistoryService.saveHistory(history, token, new Callback<History<String>>() {
                            @Override
                            public void onResult(History<String> history) {
                                Log.d(TAG, history.toString());
                            }

                            @Override
                            public void onFailure(String message) {
                                new ErrorDialog(getContext(), message).show();
                            }
                        });
                    }

                }
            }
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void logOut() {
        UserLogin.setUserLogin(null);
        LoginManager.getInstance().logOut();
        if (UserLogin.getGoogleApiClient() != null) {
            signOut();
        }
        new PrefManagement(this).putValueString(SBConstants.PREF_AUTO_LOGIN, "");
        startActivity(new Intent(this, SignInActivity.class));
        Toast.makeText(this, "Log out is successfully !", Toast.LENGTH_LONG).show();
        finish();
    }

    //get Address by LatLng
    private String getAddress(LatLng latLng) {
        String address = "";
        try {
            List<Address> addresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(UserLogin.getGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getContext(), "Logout is successfully!!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(UserLogin.getGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getContext(), "Revoke Access is successfully!!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getPrice() {
        PriceListServiceImpl service = new PriceListServiceImpl();
        service.getPriceList(new Callback<PriceList>() {
            @Override
            public void onResult(PriceList priceList) {
                UserLogin.setPriceList(priceList);
            }

            @Override
            public void onFailure(String message) {
                UserLogin.setPriceList(null);
                new ErrorDialog(getContext(), message).show();
            }
        });
    }

    private void resetNavigation(){
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(3).setChecked(false);
        navigationView.getMenu().getItem(4).setChecked(false);
        navigationView.getMenu().getItem(5).setChecked(false);
    }

    //get Context Activity
    private Context getContext() {
        return this;
    }
}
