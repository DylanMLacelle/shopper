package ca.on.conestogac.dml.shopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddListActivity extends AppCompatActivity {

    private Button btnCreateNewList;
    private EditText inputStoreName;
    ShopperApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        app = (ShopperApplication)getApplication();
        btnCreateNewList = findViewById(R.id.btnAddList);
        inputStoreName = findViewById(R.id.inputStoreName);
        btnCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new receipt to db
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                app.AddReceipt(inputStoreName.getText().toString().trim(), df.format(date));
                //start activity with new id of receipt
                Intent intent = new Intent(getApplication(), EditShoppingListActivity.class);
                intent.putExtra("receiptId", app.GetLastestReceiptId());
                startActivity(intent);
            }
        });
    }
}