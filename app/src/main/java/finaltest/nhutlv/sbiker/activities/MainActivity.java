package finaltest.nhutlv.sbiker.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.ViewPagerHomeAdapter;
import finaltest.nhutlv.sbiker.entities.Coordinate;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.tools.BikerInfoDialog;
import finaltest.nhutlv.sbiker.tools.LocationProvider;
import finaltest.nhutlv.sbiker.utils.CustomDialog;
import finaltest.nhutlv.sbiker.utils.UserLogin;
import finaltest.nhutlv.sbiker.utils.SBConstants;
import finaltest.nhutlv.sbiker.utils.SBFunctions;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LocationProvider.LocationCallback,
        RoutingListener,
        GoogleMap.OnInfoWindowClickListener,
        BikerInfoDialog.myClickListener {

    private final static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private final static int HISTORY_REQUEST_CODE = 2;
    private final static int CALL_PHONE_REQUEST_CODE = 3;
    private final static int SEARCH_REQUEST_CODE = 3;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private ViewPagerHomeAdapter mAdapterViewPage;
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
    private DecimalFormat df = new DecimalFormat("#,000");

    private TextView mTxtPlaceSearch;
    private TextView mTxtCurrentPlace;
    private ProgressDialog progress;
    private SwitchButton mSwitchDriver;
    private CustomDialog mCustomDialog;
    private boolean isBecome = false;

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorApp,
            R.color.colorAccent, R.color.primary_dark_material_light};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mEventBus = EventBus.getDefault();
        polylines = new ArrayList<>();
        mCustomDialog = new CustomDialog(this,"Loading...");
        if(!isLocationEnabled(this)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("GPS is Off\nDo you want to turn on GPS");
            dialog.setPositiveButton("Go Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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
        }else{
            mCustomDialog.showDialog();
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
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        ImageButton editProfile = (ImageButton) header.findViewById(R.id.btn_edit_header);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        mMapView = (MapView) findViewById(R.id.mapView);
        mTxtPlaceSearch = (TextView) findViewById(R.id.ed_place_search);
        mTxtCurrentPlace = (TextView) findViewById(R.id.ed_current_place);
        mTxtPlaceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchPlaceActivity.class);
                startActivityForResult(intent,SEARCH_REQUEST_CODE);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
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
                    if(!isBecome){
                        Log.d(TAG, "onCheckedChanged: True, isBecome FALSE");
                        isBecome = true;
                        startActivity(new Intent(MainActivity.this,BecomeDriverActivity.class));
                    }else{
                        Log.d(TAG, "onCheckedChanged: True");
                    }
                } else {
                    Log.d(TAG, "onCheckedChanged: False");
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
            signOutGmail();
            startActivity(new Intent(new Intent(MainActivity.this, SignInActivity.class)));
            finish();
            return true;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void handleNewLocation(final Location location) {
        Log.d("TAG HANDLE", "OK");
        mGoogleMap.setMyLocationEnabled(true);
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        mLatLngCurrent = new LatLng(currentLatitude, currentLongitude);
        mEventBus.postSticky(mLatLngCurrent);

        mGoogleMap.clear();
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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String address = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
            address = addresses.get(0).getAddressLine(0);
            mTxtCurrentPlace.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get location from address
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName("Cầu Rồng", 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                Log.d("TAG LOACTION ", latitude + " - " + longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPlaceToMap(mGoogleMap, getData());

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude))
        // .title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(mLatLngCurrent)
                .title(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mGoogleMap.addMarker(options);
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mLatLngCurrent).zoom(16).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
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
        mCustomDialog.hideDialog();

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

    // search place by name
    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // activity for result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogleMap.clear();
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mPlace = PlaceAutocomplete.getPlace(this, data);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                mStatus = PlaceAutocomplete.getStatus(this, data);
                Log.i("TAG PLACE", mStatus.getStatusMessage());
            } else if (requestCode == RESULT_CANCELED) {
                Log.i("TAG PLACE", "Cancel");
            }
        } else if (requestCode == HISTORY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                navigationView.getMenu().getItem(1).setChecked(false);
            }
        } else if (requestCode == CALL_PHONE_REQUEST_CODE) {
            Log.d(TAG, "CALL PHONE");
        }
    }

    //get data
    private List<User> getData() {
        List<User> users = new ArrayList<>();
        users.add(new User("Lê A", "01687184516", new Coordinate(16.0762584, 108.1608916)));
        users.add(new User("Lê B", "01687174516", new Coordinate(16.0772584, 108.1598916)));
        users.add(new User("Lê C", "01687184916", new Coordinate(16.0752584, 108.1578916)));
        users.add(new User("Lê D", "01687184716", new Coordinate(16.0712584, 108.1518916)));
        users.add(new User("Lê E", "01687184519", new Coordinate(16.0782584, 108.1528916)));
        users.add(new User("Lê F", "01687184556", new Coordinate(16.0732584, 108.1538916)));

        return users;
    }

    private void setPlaceToMap(GoogleMap googleMap, List<User> users) {

        for (User user : users) {
            MarkerOptions options = new MarkerOptions()
                    .position(user.getCoordinate().getLatLng())
                    .title(user.getFullName())
                    .snippet(user.getNumberPhone())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            Marker marker = googleMap.addMarker(options);
            marker.setTag(user);
        }
        googleMap.setOnInfoWindowClickListener(this);
    }

    private void callPhone(String numberPhone) {
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numberPhone));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivityForResult(i, CALL_PHONE_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        Log.d(TAG, "onResume: ");
        mLocationProvider.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mMapView.onPause();
        mLocationProvider.disconnect();
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
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mGoogleMap.addPolyline(polyOptions);
            polylines.add(polyline);
            double distance = SBFunctions.round(route.get(i).getDistanceValue()/1000,1);
            long price = (long)distance* SBConstants.UNIT_PRICE;
            String detail = "Khoảng cách "+distance+"km - Mất "
                    + Math.ceil((double) (route.get(i).getDurationValue()/60))+" Phút"
                    + " Giá tiền "+String.valueOf(df.format(price))+" đồng";

            final Snackbar snackbar = Snackbar.make(mLayout,detail, BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            snackbar.show();
            Log.d("TAGGGGG","Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "
                                    + route.get(i).getDurationValue());
        }
    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        User user = (User) marker.getTag();
        Dialog dialog = new BikerInfoDialog(this,this,user);
        dialog.show();
    }

    @Override
    public void onButtonClick(User user) {
        callPhone(user.getNumberPhone());
    }

    @Override
    public void onCheckBox(User user) {
        mEventBus.postSticky(user);
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

    private void closeDrawer(){
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
                    Log.i(TAG, "restart app");
                    // restart app
                    isPhoneCalling = false;
                }
            }
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
