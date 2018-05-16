package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.R.attr.name;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_text_view);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedcream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        //Only email apps should handle this.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * this calculates price of order.
     *
     * add price of whippedCream
     * add price of chocolate
     * total price of order
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int priceOfOneCup = 5;

        //price of whippedCream topping.
        if (addWhippedCream) {
            priceOfOneCup = priceOfOneCup + 1;
        }

        //price of chocolate topping.
        if (addChocolate) {
            priceOfOneCup = priceOfOneCup + 2;
        }

        // Calculate total price of total order.
        return quantity * priceOfOneCup;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increases the quantity on the screen.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // show toast for not more than 100
            Toast.makeText(this, "You can't have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //exits method when it hits 100
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This is called when the minus button is clicked
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // show toast for not less than 1
            Toast.makeText(this, "You can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //exits method when hit 100
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

}