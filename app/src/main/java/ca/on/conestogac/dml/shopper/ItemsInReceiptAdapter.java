package ca.on.conestogac.dml.shopper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsInReceiptAdapter extends RecyclerView.Adapter<ItemsInReceiptAdapter.ItemViewHolder> {
    private ArrayList<ItemObject> mItems;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewItemName;
        private TextView textViewPrice;
        private CheckBox checkBoxTaxed;
        private ImageView image;

        public ItemViewHolder(View v) {
            super(v);
            textViewItemName = v.findViewById(R.id.textViewItemName);
            textViewPrice = v.findViewById(R.id.textViewItemPrice);
            checkBoxTaxed = v.findViewById(R.id.checkBoxTaxed);
            image = v.findViewById(R.id.imageViewBorder);
        }
    }

    public ItemsInReceiptAdapter(ArrayList<ItemObject> items) {
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.checkBoxTaxed.setChecked(mItems.get(position).ismIsTaxed());
        holder.textViewPrice.setText("$" + mItems.get(position).getmPrice());
        holder.textViewItemName.setText(mItems.get(position).getmItemName());

        if(position % 2 == 0) {
            //holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.image.setImageResource(R.drawable.apple_border_green);
        }
        else {
            //holder.layout.setBackgroundColor(Color.parseColor("#f5f0e4"));
            holder.image.setImageResource(R.drawable.apple_border_red);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
