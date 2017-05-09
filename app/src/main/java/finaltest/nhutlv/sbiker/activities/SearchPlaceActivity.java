package finaltest.nhutlv.sbiker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.PlaceSearchAdapter;
import finaltest.nhutlv.sbiker.entities.Location;
import finaltest.nhutlv.sbiker.entities.PlaceSearch;
import finaltest.nhutlv.sbiker.entities.SearchEntity;
import finaltest.nhutlv.sbiker.services.cloud.SearchServiceImpl;
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.tools.FlowerDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.CustomDialog;
import finaltest.nhutlv.sbiker.utils.SBFunctions;
import retrofit2.Retrofit;

/**
 * Created by NhutDu on 30/04/2017.
 */

public class SearchPlaceActivity extends AppCompatActivity{

    private static final String TAG = SearchPlaceActivity.class.getSimpleName();
    private static final int PROXIMITY_RADIUS = 5000;
    private final static int PLACE_AUTOCOMPLETE_SEARCH_REQUEST_CODE = 110;
    private LatLng mCurrentLatLng;
    private EventBus mEventBus;
    private SearchServiceImpl mService;
    private RecyclerView mRvSearch;
    private TextView mTxtNoResult;
    private List<PlaceSearch> mPlaceSearches;
    private PlaceSearchAdapter mAdapter;
    private BottomNavigationView mBottomNavigationView;
    private String type;
    private int radius =10000;
    private CustomDialog mCustomDialog;
    private FlowerDialog mFlowerDialog;
    private SearchView searchView;
    private Place mPlace = null;
    private Status mStatus = null;
    private Toolbar mToolbar;
    private EditText mEdSearch;
    private DecimalFormat df = new DecimalFormat("#,000");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);

        mService = new SearchServiceImpl();
        mCustomDialog = new CustomDialog(this,"Loading...");
        mFlowerDialog = new FlowerDialog(this);

        mRvSearch = (RecyclerView) findViewById(R.id.rv_search);
        mTxtNoResult = (TextView) findViewById(R.id.txt_no_result);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEdSearch = (EditText) findViewById(R.id.ed_search_place);
        TextView textView = (TextView) mToolbar.findViewById(R.id.txt_back);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setTitle("Search Place");
        setSupportActionBar(mToolbar);

        mTxtNoResult.setVisibility(View.GONE);
        mPlaceSearches = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvSearch.setLayoutManager(manager);
        mFlowerDialog.showDialog();
        mService.getListPlaceSearch(mCurrentLatLng, "university", radius, new Callback<List<PlaceSearch>>() {
            @Override
            public void onResult(List<PlaceSearch> placeSearches) {
                mFlowerDialog.hideDialog();
                if(placeSearches==null){
                    mRvSearch.removeAllViews();
                    mTxtNoResult.setVisibility(View.VISIBLE);
                }else{
                    mCustomDialog.hideDialog();
                    mTxtNoResult.setVisibility(View.GONE);
                    for(PlaceSearch placeSearch:placeSearches){
                        Log.d(TAG, "onResult: "+placeSearch.getName());
                        Location location = placeSearch.getGeometry().getLocation();
                        LatLng latLng = new LatLng(location.getLat(),location.getLng());
                        placeSearch.setDistance(SBFunctions.getDistance2Point(mCurrentLatLng,latLng));
                    }
                    mPlaceSearches = placeSearches;
                    mAdapter = new PlaceSearchAdapter(SearchPlaceActivity.this,mPlaceSearches);
                    mAdapter.setOnClickSearch(new PlaceSearchAdapter.onClickSearch() {
                        @Override
                        public void onClickItem(int position) {
                            PlaceSearch placeSearch = mPlaceSearches.get(position);
                            Intent intent = new Intent();
                            finaltest.nhutlv.sbiker.entities.Place place =
                                    new finaltest.nhutlv.sbiker.entities.Place();
                            Location location = placeSearch.getGeometry().getLocation();
                            LatLng latLng = new LatLng(location.getLat(),location.getLng());
                            place.setAddress(placeSearch.getVicinity());
                            place.setName(placeSearch.getName());
                            place.setLatLng(latLng);
                            intent.putExtra("searchPlace",place);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                    mRvSearch.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(String message) {
                mCustomDialog.hideDialog();
                new ErrorDialog(SearchPlaceActivity.this,message).show();
            }
        });

        mRvSearch.addItemDecoration(new DividerItemDecoration(this,1));

        mBottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        mEdSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                findPlace();
            }
        });

        mEdSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPlace();
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_bottom_hospital:
                                type = "hospital";
                                break;
                            case R.id.menu_bottom_movies:
                                type = "university";
                                break;
                            case R.id.menu_bottom_restaurant:
                                type = "restaurant";
                                break;
                        }
                        mFlowerDialog.showDialog();
                        mService.getListPlaceSearch(mCurrentLatLng, type, radius, new Callback<List<PlaceSearch>>() {
                            @Override
                            public void onResult(List<PlaceSearch> placeSearches) {
                                mFlowerDialog.hideDialog();
                                if(placeSearches==null){
                                    mPlaceSearches.clear();
                                    mAdapter = new PlaceSearchAdapter(SearchPlaceActivity.this,mPlaceSearches);
                                    mRvSearch.setAdapter(mAdapter);
                                    mTxtNoResult.setVisibility(View.VISIBLE);
                                }else{
                                    mTxtNoResult.setVisibility(View.GONE);
                                    for(PlaceSearch placeSearch:placeSearches){
                                        Log.d(TAG, "onResult: "+placeSearch.getName());
                                        Location location = placeSearch.getGeometry().getLocation();
                                        LatLng latLng = new LatLng(location.getLat(),location.getLng());
                                        placeSearch.setDistance(SBFunctions.getDistance2Point(mCurrentLatLng,latLng));
                                    }
                                    mPlaceSearches = placeSearches;
                                    mAdapter = new PlaceSearchAdapter(SearchPlaceActivity.this,mPlaceSearches);
                                    mAdapter.setOnClickSearch(new PlaceSearchAdapter.onClickSearch() {
                                        @Override
                                        public void onClickItem(int position) {
                                           PlaceSearch placeSearch = mPlaceSearches.get(position);
                                            Intent intent = new Intent();
                                            finaltest.nhutlv.sbiker.entities.Place place =
                                                    new finaltest.nhutlv.sbiker.entities.Place();
                                            Location location = placeSearch.getGeometry().getLocation();
                                            LatLng latLng = new LatLng(location.getLat(),location.getLng());
                                            place.setAddress(placeSearch.getVicinity());
                                            place.setName(placeSearch.getName());
                                            place.setLatLng(latLng);
                                            intent.putExtra("searchPlace",place);
                                            setResult(RESULT_OK,intent);
                                            finish();
                                        }
                                    });
                                    mRvSearch.setAdapter(mAdapter);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                mCustomDialog.hideDialog();
                                new ErrorDialog(SearchPlaceActivity.this,message).show();
                            }
                        });
                        return true;
                    }
                });
    }



    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }



    private static Gson createGson() {
        return new GsonBuilder().setLenient()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(LatLng latLng){
        mCurrentLatLng = latLng;
    }

    // search place by name
    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_SEARCH_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_SEARCH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mPlace = PlaceAutocomplete.getPlace(this, data);
                Log.d("TAGGGGGGGG",mPlace.getAddress().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                mStatus = PlaceAutocomplete.getStatus(this, data);
                Log.i("TAG PLACE", mStatus.getStatusMessage());
            } else if (requestCode == RESULT_CANCELED) {
                Log.i("TAG PLACE", "Cancel");
            }
        }
        finaltest.nhutlv.sbiker.entities.Place place = new finaltest.nhutlv.sbiker.entities.Place();
        if(mPlace!=null){
            place.setAddress(mPlace.getAddress().toString());
            place.setName(mPlace.getName().toString());
            place.setLatLng(mPlace.getLatLng());
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("searchPlace",place);
            bundle.putParcelable("searchStatus",mStatus);
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

}
