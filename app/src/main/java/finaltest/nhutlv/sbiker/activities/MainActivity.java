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
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.gcm.GcmIntentService;
import finaltest.nhutlv.sbiker.services.cloud.HistoryServiceImpl;
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

    private final static int HISTORY_REQUEST_CODE = 2;
    private final static int CALL_PHONE_REQUEST_CODE = 3;

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
    //Creating a broadcast receiver for gcm registration
    private BroadcastReceiver mRegistrationBroadcastReceiver;

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
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        TextView username = (TextView) header.findViewById(R.id.txt_name_header);
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

        //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if (intent.getAction().equals(GcmIntentService.REGISTRATION_SUCCESS)) {
                    //Getting the registration token from the intent
                    String token = intent.getStringExtra("token");
                    new PrefManagement(getContext()).putValueString(SBConstants.PREF_TOKEN_GCM, token);
                    //Displaying the token as toast
//                    Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();
                    Log.e("Token", token);
                    //if the intent is not with success then displaying error messages
                } else if (intent.getAction().equals(GcmIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
//                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GcmIntentService.class);
            startService(itent);
        }


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
                    layoutParams.setMargins(0, 0, 30, 200);
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
        Log.d(TAG, "onResume: ");
        mLocationProvider.connect();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GcmIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GcmIntentService.REGISTRATION_ERROR));
    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mMapView.onPause();
        mLocationProvider.disconnect();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mSwitchDriver = (SwitchButton) findViewById(R.id.switchDriver);
        mSwitchDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (UserLogin.getUserLogin().getIsApproved()== 1) {
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
                            new ErrorDialog(MainActivity.this, "Thông tin của bạn đang được kiểm duyệt\nVui lòng thử lại sau").show();
                            mSwitchDriver.setChecked(false);
                        } else {
                            mSwitchDriver.setChecked(false);
                            startActivity(new Intent(MainActivity.this, BecomeDriverActivity.class));
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
        Fragment fragment = null;
        if (id == R.id.nav_register) {
            startActivity(new Intent(new Intent(MainActivity.this, RepairActivity.class)));
            closeDrawer();
            return true;
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivityForResult(intent, HISTORY_REQUEST_CODE);
            closeDrawer();
            return true;
        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
            closeDrawer();
            return true;
        } else if (id == R.id.nav_driver) {
            Log.d(TAG, "onNavigationItemSelected: " + mSwitchDriver.isChecked());
            return true;
        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(intent);
            closeDrawer();
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
                .target(mLatLngCurrent).zoom(14).build();

        if (!checked) {
            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            checked = true;
        }

        CircleOptions circleOptions = new CircleOptions()
                .center(mLatLngCurrent)
                .radius(1000)
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

        /*final GMapV2Direction md = new GMapV2Direction();
        md.getDocument(mLatLngCurrent, latLngDest,
                GMapV2Direction.MODE_DRIVING, new Callback<Document>() {
                    @Override
                    public void onResult(Document document) {
                        ArrayList<LatLng> directionPoint = md.getDirection(document);
                        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                                Color.RED);

                        for (int i = 0; i < directionPoint.size(); i++) {
                            rectLine.add(directionPoint.get(i));
                        }
                        Polyline polylin = mGoogleMap.addPolyline(rectLine);
                    }

                    @Override
                    public void onFailure() {

                    }
                });*/

    }

    // draw circle radius
    private void drawCircleMaps(LatLng latLng, int radius) {
        int d = 500; // diameter
        Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.colorApp));
        c.drawCircle(d / 2, d / 2, d / 2, p);

        // generate BitmapDescriptor from circle Bitmap
        BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);

        // mapView is the GoogleMap
        mGoogleMap.addGroundOverlay(new GroundOverlayOptions().
                image(bmD).
                position(latLng, radius * 2, radius * 2).
                transparency(0.4f));
    }

    // activity for result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogleMap.clear();
        if (resultCode == RESULT_OK) {
            if (requestCode == SBConstants.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                mPlace = bundle.getParcelable("searchPlace");
                mStatus = bundle.getParcelable("searchStatus");
                mUserService.getListUserByRadius(11000, mLatLngCurrent, new Callback<List<User>>() {
                    @Override
                    public void onResult(List<User> users) {
                        Log.d(TAG, "Get list by radius is OK");
                        setPlaceToMap(mGoogleMap, users);
                    }

                    @Override
                    public void onFailure(String message) {
                        new ErrorDialog(getContext(), message).show();
                    }
                });
            } else if (requestCode == HISTORY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    navigationView.getMenu().getItem(1).setChecked(false);
                }
            } else if (requestCode == CALL_PHONE_REQUEST_CODE) {
                Log.d(TAG, "onActivityResult: CALL");
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
        Log.d(TAG, "onDestroy: ");
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory: ");
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
            mDistance = SBFunctions.round(route.get(i).getDistanceValue() / 1000, 1);
            mPrice = (mDistance <= 1) ? 10000 : (int) mDistance * SBConstants.UNIT_PRICE;
            mTimeSpend = route.get(i).getDurationValue() / 60;
            String detail = "Khoảng cách " + sDistance + "\nMất "
                    + SBFunctions.parseTime(mTimeSpend)
                    + " - " + String.valueOf(df.format(mPrice)) + "VNĐ";

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
        if(mPlace==null){
            Toast.makeText(getContext(),"Vui lòng chọn địa diểm bạn muốn đến",Toast.LENGTH_LONG).show();
        }else{
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
                    if(mPlace!=null){
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
        if(UserLogin.getGoogleApiClient()!=null){
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
                        Toast.makeText(getContext(),"Logout is successfully!!",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(UserLogin.getGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getContext(),"Revoke Access is successfully!!",Toast.LENGTH_LONG).show();
                    }
                });
    }

    //get Context Activity
    private Context getContext() {
        return this;
    }
}
