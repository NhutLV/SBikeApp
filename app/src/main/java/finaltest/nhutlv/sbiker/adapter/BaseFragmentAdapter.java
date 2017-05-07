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
import java.util.List;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.entities.User;

/**
 * Created by NhutDu on 27/04/2017.
 */

public class BaseFragmentAdapter extends RecyclerView.Adapter<BaseFragmentAdapter.BaseViewHolder> {

    private Context mContext;
    private ArrayList<Repairer<User>> mRepairers;
    private onRepairListener mOnRepairListener;
    public interface onRepairListener{
        void onCallPhone(int position);
    }

    public BaseFragmentAdapter(Context context, ArrayList<Repairer<User>> repairers) {
        mContext = context;
        mRepairers = repairers;
    }

    public void setOnRepairListener(onRepairListener onRepairListener) {
        mOnRepairListener = onRepairListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_base,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Repairer<User> repairer = mRepairers.get(position);
        holder.mTxtTime.setText(repairer.getTimeOpen()+" - "+repairer.getTimeClose());
        holder.mTxtName.setText(repairer.getName());
        holder.mTxtPhone.setText(repairer.getNumberPhone());
        holder.mTxtAddress.setText(repairer.getAddress());
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
            mTxtTime = (TextView) itemView.findViewById(R.id.time_repair);
            mCallPhone = (ImageButton) itemView.findViewById(R.id.call_repair);

            mCallPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRepairListener.onCallPhone(getAdapterPosition());
                }
            });
        }
    }

    public void clear() {
        mRepairers.clear();
        notifyDataSetChanged();

    }



// Add a list of items

    public void addAll(List<Repairer<User>> list) {
        mRepairers.clear();
        mRepairers.addAll(list);
        notifyDataSetChanged();

    }
}
