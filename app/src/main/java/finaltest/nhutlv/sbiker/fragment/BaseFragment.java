package finaltest.nhutlv.sbiker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import finaltest.nhutlv.sbiker.tools.DividerItemDecoration;

/**
 * Created by NhutDu on 27/04/2017.
 */

public class BaseFragment extends Fragment {

    private RecyclerView mRvBase;
    private BaseFragmentAdapter mAdapter;
    private ArrayList<Repairer> mRepairers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base,container,false);
        mRvBase = (RecyclerView) view.findViewById(R.id.rv_base);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRepairers = getRepairers();
        mAdapter = new BaseFragmentAdapter(getActivity(),mRepairers);
        mRvBase.setLayoutManager(manager);
        mRvBase.setAdapter(mAdapter);
        mRvBase.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        mRvBase.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    public ArrayList<Repairer> getRepairers(){
        ArrayList<Repairer> list = new ArrayList<>();
        list.add(new Repairer());
        list.add(new Repairer());
        list.add(new Repairer());
        list.add(new Repairer());
        list.add(new Repairer());
        list.add(new Repairer());
        list.add(new Repairer());
        list.add(new Repairer());

        return list;
    }
}
