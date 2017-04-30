package finaltest.nhutlv.sbiker.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

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
import finaltest.nhutlv.sbiker.services.cloud.SearchServiceImpl;
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.CustomDialog;
import finaltest.nhutlv.sbiker.utils.SBFunctions;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NhutDu on 30/04/2017.
 */

public class SearchPlaceActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private static final String TAG = SearchPlaceActivity.class.getSimpleName();
    private static Retrofit mRetrofit = null;
    private static final int PROXIMITY_RADIUS = 5000;
    private LatLng mCurrentLatLng;
    private EventBus mEventBus;
    private SearchServiceImpl mService;
    private RecyclerView mRvSearch;
    private List<PlaceSearch> mPlaceSearches;
    private PlaceSearchAdapter mAdapter;
    private BottomNavigationView mBottomNavigationView;
    private String location;
    private String type;
    private int radius;
    private CustomDialog mCustomDialog;
    private DecimalFormat df = new DecimalFormat("#,000");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        mService = new SearchServiceImpl();

        mCustomDialog = new CustomDialog(this,"Loading...");
        mRvSearch = (RecyclerView) findViewById(R.id.rv_search);
        mPlaceSearches = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvSearch.setLayoutManager(manager);
        mAdapter = new PlaceSearchAdapter(this,mPlaceSearches);
        mRvSearch.setAdapter(mAdapter);
        mRvSearch.addItemDecoration(new DividerItemDecoration(this,1));

        mBottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        radius = PROXIMITY_RADIUS;
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
                        Log.d(TAG, "onNavigationItemSelected: "+mCurrentLatLng.latitude);
                        Log.d(TAG, "onNavigationItemSelected: "+mCurrentLatLng.longitude);
                        Log.d(TAG, "onNavigationItemSelected: "+type);
                        Log.d(TAG, "onNavigationItemSelected: "+radius);

                        mCustomDialog.showDialog();
                        mService.getListPlaceSearch(mCurrentLatLng, type, radius, new Callback<List<PlaceSearch>>() {
                            @Override
                            public void onResult(List<PlaceSearch> placeSearches) {
                                for(PlaceSearch placeSearch:placeSearches){
                                    Log.d(TAG, "onResult: "+placeSearch.getName());
                                    Location location = placeSearch.getGeometry().getLocation();
                                    LatLng latLng = new LatLng(location.getLat(),location.getLng());
                                    placeSearch.setDistance(SBFunctions.getDistance2Point(mCurrentLatLng,latLng));
                                }
                                mPlaceSearches = placeSearches;
                                mAdapter = new PlaceSearchAdapter(SearchPlaceActivity.this,mPlaceSearches);
                                mRvSearch.setAdapter(mAdapter);
                                mCustomDialog.hideDialog();
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
        Log.d(TAG, "getEvent: ");
        mCurrentLatLng = latLng;
    }
}
