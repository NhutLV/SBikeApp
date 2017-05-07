package finaltest.nhutlv.sbiker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.BaseFragmentAdapter;
import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;
import finaltest.nhutlv.sbiker.tools.EndlessScrollRecyclerViewListener;

/**
 * Created by NhutDu on 27/04/2017.
 */

public class BaseFragment extends Fragment{

    protected RecyclerView mRvBase;
    protected BaseFragmentAdapter mAdapter;
    protected ArrayList<Repairer<User>> mRepairers;
    protected LinearLayoutManager manager;
    protected EndlessScrollRecyclerViewListener mScrollListener;
    protected SwipeRefreshLayout mSwipeRefreshLayout;;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base,container,false);
        mRvBase = (RecyclerView) view.findViewById(R.id.rv_base);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        manager = new LinearLayoutManager(getActivity());
        mRepairers = new ArrayList<>();
        mAdapter = new BaseFragmentAdapter(getActivity(),mRepairers);
        mRvBase.setLayoutManager(manager);
        mRvBase.setAdapter(mAdapter);
        mRvBase.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        mRvBase.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

}
