package finaltest.nhutlv.sbiker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.Repairer;

public class AutoRepairAdapter extends RecyclerSwipeAdapter<AutoRepairAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Repairer> mRepairs;

    public AutoRepairAdapter(Context context, ArrayList<Repairer> repairers) {
        mContext = context;
        mRepairs = repairers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Repairer repairer = mRepairs.get(position);
        holder.name.setText(repairer.getFullName());
        holder.content.setText(repairer.getAddress());

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.delete_alarm));
            }
        });
        holder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyClickListener.onDelete(position);
            }
        });

//        mItemManger.bindView(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return mRepairs.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView content;
        TextView time;
        SwipeLayout swipeLayout;
        ImageView edit;
        ImageView delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
            time = (TextView) itemView.findViewById(R.id.item_time);
            content = (TextView) itemView.findViewById(R.id.item_content);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            edit = (ImageView) itemView.findViewById(R.id.edit_alarm);
            delete = (ImageView) itemView.findViewById(R.id.delete_alarm);

        }
    }

    MyClickListener mMyClickListener;

    public void setMyClickListener(MyClickListener myClickListener) {
        mMyClickListener = myClickListener;
    }

    public interface MyClickListener {
        void onDelete(int position);
    }
}

