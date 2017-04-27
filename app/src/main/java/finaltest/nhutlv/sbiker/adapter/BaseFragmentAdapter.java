package finaltest.nhutlv.sbiker.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.Repairer;

/**
 * Created by NhutDu on 27/04/2017.
 */

public class BaseFragmentAdapter extends RecyclerView.Adapter<BaseFragmentAdapter.BaseViewHolder> {

    private Context mContext;
    private ArrayList<Repairer> mRepairers;

    public BaseFragmentAdapter(Context context, ArrayList<Repairer> repairers) {
        mContext = context;
        mRepairers = repairers;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_base,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mRepairers.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder{

        TextView mTxtName;
        TextView mTxtAddress;
        TextView mTxtPhone;
        TextView mTxtDistance;
        TextView mTxtTime;
        ImageButton mCallPhone;


        public BaseViewHolder(View itemView) {
            super(itemView);
            mTxtName = (TextView) itemView.findViewById(R.id.name_repair);
            mTxtAddress = (TextView) itemView.findViewById(R.id.address_repair);
            mTxtDistance = (TextView) itemView.findViewById(R.id.distance_current);
            mTxtPhone = (TextView) itemView.findViewById(R.id.phone_repair);
            mTxtTime = (TextView) itemView.findViewById(R.id.time_call);
            mCallPhone = (ImageButton) itemView.findViewById(R.id.call_repair);
        }
    }
}
