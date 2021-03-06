package finaltest.nhutlv.sbiker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.PlaceSearch;

/**
 * Created by NhutDu on 30/04/2017.
 */

public class PlaceSearchAdapter extends RecyclerView.Adapter<PlaceSearchAdapter.PlaceViewHolde>{

    private Context mContext;
    private List<PlaceSearch> mPlaceSearches;
    private onClickSearch mOnClickSearch;
    private DecimalFormat df = new DecimalFormat("#,000");

    public interface onClickSearch{
        void onClickItem(int position);
    }

    public void setOnClickSearch(onClickSearch onClickSearch) {
        mOnClickSearch = onClickSearch;
    }

    public PlaceSearchAdapter(Context context, List<PlaceSearch> placeSearches) {
        mContext = context;
        mPlaceSearches = placeSearches;
    }

    @Override
    public PlaceViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_search,parent,false);
        return new PlaceViewHolde(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolde holder, int position) {
        PlaceSearch placeSearch = mPlaceSearches.get(position);
        holder.name.setText(placeSearch.getName());
        holder.address.setText(placeSearch.getVicinity());
        int distance = (int) placeSearch.getDistance();
        String sDistance ="";
        Log.d("TAGGGGGGGG",placeSearch.getName() +" - "+distance);
        if(distance<1000){
            sDistance = distance+" m";
        }else{
            sDistance = df.format(distance) +" km";
        }
        holder.distance.setText("Cách "+ sDistance);
    }

    @Override
    public int getItemCount() {
        return mPlaceSearches.size();
    }

    class PlaceViewHolde extends RecyclerView.ViewHolder{

        TextView name;
        TextView address;
        TextView distance;


        public PlaceViewHolde(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_place_search);
            address = (TextView) itemView.findViewById(R.id.address_place_search);
            distance = (TextView) itemView.findViewById(R.id.distance_place_search);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickSearch.onClickItem(getAdapterPosition());
                }
            });
        }
    }

}
