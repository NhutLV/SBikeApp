package finaltest.nhutlv.sbiker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finaltest.nhutlv.sbiker.R;

/**
 * Created by NhutDu on 08/04/2017.
 */

public class StoreRepairFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_repair,container,false);
        return view;
    }
}
