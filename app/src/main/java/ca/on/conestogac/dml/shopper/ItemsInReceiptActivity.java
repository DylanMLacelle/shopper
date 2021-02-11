package ca.on.conestogac.dml.shopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsInReceiptActivity extends AppCompatActivity {

    private RecyclerView itemsList;
    private RecyclerView.Adapter itemAdapter;
    private RecyclerView.LayoutManager itemLayoutManager;
    private ArrayList<ItemObject> itemObjects;

    private TextView textViewTotal;
    private TextView textViewStoreName;

    ShopperApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_in_receipt);
        setTheme(R.style.AppTheme);
        app = ((ShopperApplication)getApplication());
        int id = getIntent().getIntExtra("receiptId", 0);
        itemObjects = app.GetAllItemsInReceipt(id);

        itemsList = findViewById(R.id.listItemsInReceipt);
        itemLayoutManager = new LinearLayoutManager(this);
        itemAdapter = new ItemsInReceiptAdapter(itemObjects);

        itemsList.setHasFixedSize(true);
        itemsList.setLayoutManager(itemLayoutManager);
        itemsList.setAdapter(itemAdapter);

        textViewTotal = findViewById(R.id.textViewTotal);
        textViewTotal.setText(app.GetTotalForSingleReceipt(
                getIntent().getIntExtra("receiptId", 0)));
        textViewStoreName = findViewById(R.id.textViewStoreName);
        textViewStoreName.setText("Items from " + app.GetSingleReceipt(id).getStoreName());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean returnValue = true;
        switch(item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                returnValue = super.onOptionsItemSelected(item);
        }
        return returnValue;
    }
}