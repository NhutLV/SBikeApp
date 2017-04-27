package finaltest.nhutlv.sbiker.fragment;

/**
 * Created by NhutDu on 08/04/2017.
 */

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Arrays;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.adapter.AutoRepairAdapter;
import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class Favorite extends Fragment implements AutoRepairAdapter.MyClickListener {

    /**
     * RecyclerView: The new recycler view replaces the list view. Its more modular and therefore we
     * must implement some of the functionality ourselves and attach it to our fragment_auto.
     * <p/>
     * 1) Position items on the screen: This is done with LayoutManagers
     * 2) Animate & Decorate views: This is done with ItemAnimators & ItemDecorators
     * 3) Handle any touch events apart from scrolling: This is now done in our adapter's ViewHolder
     */

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<Repairer> mRepairers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto,container,false);

        mRepairers = new ArrayList<>();
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));
        mRepairers.add(new Repairer("Lê Viết Nhựt","Quang Nam","01687184516","1"));


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        // Adapter:
        mAdapter = new AutoRepairAdapter(getActivity(),mRepairers);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        ((AutoRepairAdapter) mAdapter).setMode(Attributes.Mode.Single);
        ((AutoRepairAdapter) mAdapter).setMyClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new FadeInLeftAnimator());

        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);
        return view;
    }

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };

    @Override
    public void onDelete(int position) {

    }
}
