package finaltest.nhutlv.sbiker.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import finaltest.nhutlv.sbiker.adapter.BaseFragmentAdapter;
import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.RepairServiceImpl;
import finaltest.nhutlv.sbiker.dialog.ErrorDialog;
import finaltest.nhutlv.sbiker.dialog.FlowerDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.SBConstants;
import finaltest.nhutlv.sbiker.utils.SBFunctions;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 08/04/2017.
 */

public class StoreRepairFragment extends BaseFragment{

    private RepairServiceImpl mRepairService;
    private FlowerDialog mFlowerDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRepairService = new RepairServiceImpl();
        mFlowerDialog = new FlowerDialog(getActivity());
        mFlowerDialog.showDialog();
        mRepairService.getListRepairByType(SBConstants.FRAGMENT_STORE_REPAIR,new Callback<ArrayList<Repairer<User>>>() {
            @Override
            public void onResult(ArrayList<Repairer<User>> repairers) {
                mFlowerDialog.hideDialog();
                for(Repairer<User> repairer :repairers){
                    int distance =(int) SBFunctions.getDistance2Point(UserLogin.getUserLogin().getLatLng(),
                            repairer.getLatLang());
                    repairer.setDistance(distance);
                }
                Collections.sort(repairers, new Comparator<Repairer<User>>() {
                    @Override
                    public int compare(Repairer<User> o1, Repairer<User> o2) {
                        if(o1.getDistance()<o2.getDistance()){
                            return -1;
                        }else if(o1.getDistance()<o2.getDistance()){
                            return 1;
                        }
                        return 0;
                    }
                });
                mAdapter.addAll(repairers);
            }

            @Override
            public void onFailure(String message) {
                mFlowerDialog.hideDialog();
                new ErrorDialog(getContext(),message).show();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        mAdapter.setOnRepairListener(new BaseFragmentAdapter.onRepairListener() {
            @Override
            public void onCallPhone(int position) {
                callPhone(mRepairers.get(position).getNumberPhone());
            }
        });

        return view;
    }

    void refreshItems() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRepairService.getListRepairByType(SBConstants.FRAGMENT_STORE_REPAIR,
                        new Callback<ArrayList<Repairer<User>>>() {
                    @Override
                    public void onResult(ArrayList<Repairer<User>> repairers) {
                        for(Repairer<User> repairer :repairers){
                            int distance =(int) SBFunctions.getDistance2Point(UserLogin.getUserLogin().getLatLng(),
                                    repairer.getLatLang());
                            repairer.setDistance(distance);
                        }
                        Collections.sort(repairers, new Comparator<Repairer<User>>() {
                            @Override
                            public int compare(Repairer<User> o1, Repairer<User> o2) {
                                if(o1.getDistance()<o2.getDistance()){
                                    return -1;
                                }else if(o1.getDistance()<o2.getDistance()){
                                    return 1;
                                }
                                return 0;
                            }
                        });
                        mAdapter.addAll(repairers);
                    }

                    @Override
                    public void onFailure(String message) {
                        new ErrorDialog(getContext(), message).show();
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    private void callPhone(String phone) {
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        startActivityForResult(i, SBConstants.CALL_PHONE_REQUEST_CODE);
    }
}
