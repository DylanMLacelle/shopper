package ca.on.conestogac.dml.shopper;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.MyViewHolder> {
    private ArrayList<ReceiptObject> mDataset;
    private Context mContext; //for test toasts
    private ShopperApplication app;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewStore;
        private TextView textViewDate;
        private TextView textViewPrice;
        private LinearLayout layout;
        private ImageView image;

        public MyViewHolder(View v) {
            super(v);
            textViewStore = v.findViewById(R.id.textViewStore);
            textViewDate = v.findViewById(R.id.textViewDate);
            textViewPrice = v.findViewById(R.id.textViewPrice);
            image = v.findViewById(R.id.imageViewBorder);
            layout = v.findViewById(R.id.receiptLayout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReceiptsAdapter(ArrayList<ReceiptObject> myDataset, Context context,
                           ShopperApplication application) {
        mDataset = myDataset;
        mContext = context;
        app = application;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ReceiptsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View  v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipt_card_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Assign alternating colors for views
        if(position % 2 == 0) {
            //holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.image.setImageResource(R.drawable.apple_border_green);
        }
        else {
            //holder.layout.setBackgroundColor(Color.parseColor("#f5f0e4"));
            holder.image.setImageResource(R.drawable.apple_border_red);
        }

        holder.textViewPrice.setText(app.GetTotalForSingleReceipt(mDataset.get(position).getId()));
        holder.textViewDate.setText(mDataset.get(position).getDate());
        holder.textViewStore.setText(mDataset.get(position).getStoreName());
        //final String msg = holder.textViewStore.getText().toString() + " clicked.";
        final int id = mDataset.get(position).getId();

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ItemsInReceiptActivity.class);
                intent.putExtra("receiptId", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
