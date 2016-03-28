package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 0;
    int price = 5;
    String mName;
    boolean mCheckedWhippedCream;
    boolean mCheckedChocolate;
    String priceMessage = "Total $";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        } else {
            String text = getString(R.string.increment);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            displayQuantity(quantity);
        } else {
            String text = getString(R.string.decrement);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        if (quantity == 0) {
            priceMessage = "Free";
        }

        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        mCheckedWhippedCream = checkBox1.isChecked();

        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox_chocolate);
        mCheckedChocolate = checkBox2.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        mName = nameEditText.getText().toString();

        String message = createSummaryOrder(calculatePrice());

        String subject = getString(R.string.subject_mail) + mName;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //intent.putExtra(Intent.EXTRA_STREAM, attachment);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     //* @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice() {
        if (mCheckedChocolate && mCheckedWhippedCream) {
            price = 8;
        } else if (mCheckedWhippedCream) {
            price = 6;
        } else if (mCheckedChocolate) {
            price = 7;
        } else {
            price = 5;
        }
        return quantity * price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }

    private String createSummaryOrder(int price) {
        String priceMessage = getString(R.string.name) + mName;
        priceMessage += "\n" + getString(R.string.addwc) + " " + mCheckedWhippedCream;
        priceMessage += "\n" + getString(R.string.addch) + " " + mCheckedChocolate;
        priceMessage += "\n" + getString(R.string.quantity) + ": " + quantity;
        priceMessage += "\n" + getString(R.string.total) + price;
        priceMessage += "\n" + getString(R.string.thanks);
        return priceMessage;
    }
}