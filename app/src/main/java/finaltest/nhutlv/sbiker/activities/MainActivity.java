package finaltest.nhutlv.sbiker.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.RegisterInfoRepairFragment;
import finaltest.nhutlv.sbiker.adapter.ViewPagerHomeAdapter;
import finaltest.nhutlv.sbiker.entities.Coordinate;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.fragment.RepairBikeFragment;
import finaltest.nhutlv.sbiker.tools.BikerInfoDialog;
import finaltest.nhutlv.sbiker.tools.LocationProvider;
import finaltest.nhutlv.sbiker.utils.UserLogin;
import finaltest.nhutlv.sbiker.utils.UtilsConstants;
import finaltest.nhutlv.sbiker.utils.UtilsFunctions;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    LocationProvider.LocationCallback,
                    RoutingListener,
                    GoogleMap.OnInfoWindowClickListener,
                    BikerInfoDialog.myClickListener{

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private ViewPagerHomeAdapter mAdapterViewPage;


    private MapView mMapView;
    private GoogleMap mGoogleMap = null;
    private LocationProvider mLocationProvider;
    private final static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private final static int HISTORY_REQUEST_CODE = 2;
    private final static String TAG ="TAG MainActivity";
    private LatLng mLatLngCurrent;
    private NavigationView navigationView;
    private Place mPlace = null;
    private Status mStatus = null;
    private RelativeLayout mLayout;
    private DecimalFormat df = new DecimalFormat("#,000");

    private TextView mTxtPlaceSearch;
    private TextView mTxtCurrentPlace;
    private ProgressDialog progress;
    SwitchButton mSwitchDriver;

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark,R.color.colorPrimary,R.color.colorApp,
            R.color.colorAccent,R.color.primary_dark_material_light};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        polylines = new ArrayList<>();
        progress = ProgressDialog.show(this, "",
                "Loading...", true);
        mLayout = (RelativeLayout) findViewById(R.id.mapLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        ImageButton editProfile = (ImageButton) header.findViewById(R.id.btn_edit_header);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        mMapView = (MapView) findViewById(R.id.mapView);

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

        mTxtPlaceSearch = (TextView) findViewById(R.id.ed_place_search);
        mTxtCurrentPlace = (TextView) findViewById(R.id.ed_current_place);
        mTxtPlaceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPlace(mTxtPlaceSearch);
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
                if(isChecked){
                    Log.d(TAG, "onCheckedChanged: True");
                }else{
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
            startActivity(new Intent(new Intent(MainActivity.this,RegisterInfoRepairFragment.class)));
            closeDrawer();
            return true;
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivityForResult(intent,HISTORY_REQUEST_CODE);
            closeDrawer();
            return true;
        } else if (id == R.id.nav_favorite) {
            fragment = new RepairBikeFragment();
        } else if (id == R.id.nav_driver) {
           Log.d(TAG, "onNavigationItemSelected: "+mSwitchDriver.isChecked());
           return true;
        } else if (id == R.id.nav_invite_friend) {

        }else if (id==R.id.nav_log_out){
           signOutGmail();
           startActivity(new Intent(new Intent(MainActivity.this,LoginActivity.class)));
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
        Log.d("TAG HANDLE","OK");

        mGoogleMap.setMyLocationEnabled(true);
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        mLatLngCurrent = new LatLng(16.063141, 108.162546);

        mGoogleMap.clear();
        if(mPlace!=null){
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
        String address="";
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLatitude,currentLongitude,1);
            address = addresses.get(0).getAddressLine(0);
            Log.d("TAG CURRENT",address);
            Log.d("TAG CURRENT",addresses.get(0).toString());
            mTxtCurrentPlace.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get location from address
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName("Cầu Rồng", 1);
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                Log.d("TAG LOACTION ",latitude+" - "+longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPlaceToMap(mGoogleMap,getData());

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude))
        // .title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(mLatLngCurrent)
                .title(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mGoogleMap.addMarker(options);
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mLatLngCurrent).zoom(14).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        CircleOptions circleOptions = new CircleOptions()
                .center(mLatLngCurrent)
                .radius(500)
                .strokeWidth(2)
                .strokeColor(getResources().getColor(R.color.colorCircleStroke))
                .fillColor(getResources().getColor(R.color.colorCircleFill));
        mGoogleMap.addCircle(circleOptions);

        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Log.d("TAG BUTTON CLICK","OK");
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
        progress.dismiss();

    }

    // draw circle radius
    private void drawCircleMaps(LatLng latLng, int radius){
        int d = 500; // diameter
        Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.colorApp));
        c.drawCircle(d/2, d/2, d/2, p);

        // generate BitmapDescriptor from circle Bitmap
        BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);

        // mapView is the GoogleMap
        mGoogleMap.addGroundOverlay(new GroundOverlayOptions().
                image(bmD).
                position(latLng,radius*2,radius*2).
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
        Log.d("TAG RESULT ","OK");
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
        }else if(requestCode == HISTORY_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                navigationView.getMenu().getItem(1).setChecked(false);
            }
        }
    }

    //get data
    private List<User> getData(){
        List<User> users = new ArrayList<>();
        users.add(new User("Lê A","01687184516",new Coordinate(16.0762584,108.1608916)));
        users.add(new User("Lê B","01687174516",new Coordinate(16.0772584,108.1598916)));
        users.add(new User("Lê C","01687184916",new Coordinate(16.0752584,108.1578916)));
        users.add(new User("Lê D","01687184716",new Coordinate(16.0712584,108.1518916)));
        users.add(new User("Lê E","01687184519",new Coordinate(16.0782584,108.1528916)));
        users.add(new User("Lê F","01687184556",new Coordinate(16.0732584,108.1538916)));

        return users;
    }

    private void setPlaceToMap(GoogleMap googleMap,List<User> users){

        for(User user:users){
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

    private void callPhone(String numberPhone){
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberPhone));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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
            double distance = UtilsFunctions.round(route.get(i).getDistanceValue()/1000,1);
            long price = (long)distance* UtilsConstants.UNIT_PRICE;
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
//            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()
//                  +": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(mLatLngCurrent);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mGoogleMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(mPlace.getLatLng());
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mGoogleMap.addMarker(options);
    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        User user = (User) marker.getTag();
        Dialog dialog = new BikerInfoDialog(this,this,user);
        dialog.show();
        Log.d("TAG INFO ",user.getNumberPhone());
        Log.d("TAG INFO ",marker.getSnippet());
    }

    @Override
    public void onButtonClick(User user) {
        callPhone(user.getNumberPhone());
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
}