package ca.on.conestogac.dml.shopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ImageView imageViewCreateList;
    private ImageView imageViewHistory;

    ShopperApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewCreateList = findViewById(R.id.imageViewAdd);
        imageViewHistory = findViewById(R.id.imageViewHistory);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.imageViewAdd:
                        //run add new list activity
                        //Toast.makeText(getApplicationContext(), "Add clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplication(), EditShoppingListActivity.class));
                        break;
                    case R.id.imageViewHistory:
                        startActivity(new Intent(getApplicationContext(), ReceiptActivity.class));
                        break;
                }
            }
        };

        imageViewCreateList.setOnClickListener(listener);
        imageViewHistory.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopper_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean returnValue = true;
        switch(item.getItemId()) {
            case R.id.menu_receipt:
                startActivity(new Intent(getApplication(), ReceiptActivity.class));
                break;
            default:
                returnValue = super.onOptionsItemSelected(item);
        }
        return returnValue;
    }
}