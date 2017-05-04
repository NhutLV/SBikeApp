package finaltest.nhutlv.sbiker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.History;

/**
 * Created by NhutDu on 27/04/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private Context mContext;
    private ArrayList<History> mHistories;
    private onMyListener mOnMyListener;

    public HistoryAdapter(Context context, ArrayList<History> histories, onMyListener onMyListener) {
        mContext = context;
        mHistories = histories;
        mOnMyListener = onMyListener;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_history,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        History history = mHistories.get(position);
        holder.mTxtStart.setText(history.getPlaceFrom());
        holder.mTxtDestination.setText(history.getPlaceTo());
        holder.mTxtTime.setText(history.getTimeCall());
        holder.mTxtDistance.setText(String.valueOf(history.getDistance()) +" km");
        holder.mTxtPrice.setText(String.valueOf(history.getPrice())+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return mHistories.size();
    }

    public interface onMyListener{
        void onClickBiker(int position);
        void onClickItem(int position);
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView mTxtStart;
        TextView mTxtDestination;
        TextView mTxtBiker;
        TextView mTxtDistance;
        TextView mTxtPrice;
        TextView mTxtTime;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            mTxtStart = (TextView) itemView.findViewById(R.id.place_start);
            mTxtDestination = (TextView) itemView.findViewById(R.id.place_destination);
            mTxtBiker = (TextView) itemView.findViewById(R.id.name_biker);
            mTxtDistance = (TextView) itemView.findViewById(R.id.distance);
            mTxtPrice = (TextView) itemView.findViewById(R.id.price);
            mTxtTime = (TextView) itemView.findViewById(R.id.time_call);

            mTxtBiker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMyListener.onClickBiker(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMyListener.onClickItem(getAdapterPosition());
                }
            });
        }
    }
}
