package ca.on.conestogac.dml.shopper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditShoppingListActivity extends AppCompatActivity {

    //Edit list variables
    private LinearLayout layoutEdit;
    private TextView textViewStoreName;
    private EditText inputItem;
    private EditText inputPrice;
    private Button btnAddToList;
    private Switch isTaxed;

    //Add list variables
    private LinearLayout layoutAdd;
    private Button btnCreateNewList;
    private EditText inputStoreName;

    //View list variables
    private Button btnViewList;
    private TextView receiptListStoreHead;
    private TextView receiptListTotal;
    private RecyclerView itemsList;
    private RecyclerView.Adapter itemAdapter;
    private RecyclerView.LayoutManager itemLayoutManager;
    private ArrayList<ItemObject> itemObjects;

    private int receiptId;
    private ShopperApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_list);
        setTheme(R.style.AppTheme);

        //edit declarations
        layoutEdit = findViewById(R.id.LayoutItem);
        textViewStoreName = findViewById(R.id.textViewEditStoreName);
        inputItem = findViewById(R.id.inputItem);
        inputPrice = findViewById(R.id.inputPrice);
        isTaxed = findViewById(R.id.switchTax);
        btnAddToList = findViewById(R.id.btnAddItem);

        //add declarations
        layoutAdd = findViewById(R.id.LayoutReceipt);
        btnCreateNewList = findViewById(R.id.btnAddList);
        inputStoreName = findViewById(R.id.inputStoreName);

        //view list declarations
        btnViewList = findViewById(R.id.btnViewList);
        itemsList = findViewById(R.id.listItemsInReceipt);
        receiptListStoreHead = findViewById(R.id.textViewStoreName);
        receiptListTotal = findViewById(R.id.textViewTotal);

        app = (ShopperApplication)getApplication();

        EnableLowerLayout(layoutEdit, false);


        //Button add item to list
        btnAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //app = ((ShopperApplication)getApplication());
                if(ValidateItem()) {
                    double price = Double.parseDouble(inputPrice.getText().toString());
                    int tax = 0;
                    if(isTaxed.isChecked())
                        tax = 1;
                    app.AddItem(inputItem.getText().toString(), tax, price);
                    app.AddReceiptItem(receiptId, app.GetLastestItemId());
                }
                //reset fields
            }
        });

        //Button create list
        btnCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateStore()) {
                    //add new receipt to db
                    Date date = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                    app.AddReceipt(inputStoreName.getText().toString().trim(), df.format(date));

                    EnableLowerLayout(layoutEdit, true);
                    receiptId = app.GetLastestReceiptId();
                    layoutAdd.setVisibility(View.GONE);
                    textViewStoreName.setText(inputStoreName.getText());
                }
            }
        });

        //Button view items
        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ItemsInReceiptActivity.class)
                        .putExtra("receiptId", receiptId);
                startActivity(intent);
            }
        });
    }

    private boolean ValidateItem() {
        boolean ret = true;
        if(inputItem.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Item name can't be blank.",
                    Toast.LENGTH_SHORT).show();
            ret = false;
        }
        if(inputPrice.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Price can't be blank.",
                    Toast.LENGTH_SHORT).show();
            ret = false;
        }
        return ret;
    }
    private boolean ValidateStore() {
        boolean ret = true;
        if(inputStoreName.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Store name can't be blank.",
                    Toast.LENGTH_SHORT).show();
            ret = false;
        }
        return ret;
    }

    private void EnableLowerLayout(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                EnableLowerLayout(child, enabled);
            }
        }

        if(enabled)
            layoutEdit.setAlpha(1f);
        else
            layoutEdit.setAlpha(0.1f);
    }
}