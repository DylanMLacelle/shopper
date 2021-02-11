package ca.on.conestogac.dml.shopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity {

    private RecyclerView receipts;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private ArrayList<ReceiptObject> receiptObjects;
    ShopperApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        setTheme(R.style.AppTheme);
        app = ((ShopperApplication)getApplication());
        receiptObjects = app.GetAllReceipts();

        receipts = findViewById(R.id.listReceipts);
        receipts.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new ReceiptsAdapter(receiptObjects, app.getApplicationContext(), app);

        receipts.setLayoutManager(rLayoutManager);
        receipts.setAdapter(rAdapter);

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