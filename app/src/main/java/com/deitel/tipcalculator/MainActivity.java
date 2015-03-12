// MainActivity.java
// Calculates bills using 15% and custom percentage tips.
package com.deitel.tipcalculator;

import java.text.NumberFormat; // for currency formatting

import android.app.Activity; // base class for activities
import android.os.Bundle; // for saving state information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

// MainActivity class for the Tip Calculator app
public class MainActivity extends Activity
{
    // currency and percent formatters
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private int numberInParty = 1;
    private double customPercent = 0.18; // initial custom tip percentage

    private TextView amountDisplayTextView; // shows formatted bill amount
    private TextView numberInPartyDisplayTextView;
    private TextView percentCustomTextView; // shows custom tip percentage

    private TextView totalTip15TextView; // shows 15% tip
    private TextView totalPerPerson15TextView; // shows total with 15% tip
    private TextView totalTipCustomTextView; // shows custom tip amount
    private TextView totalPerPersonCustomTextView; // shows total with custom tip


    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); // call superclass's version
        setContentView(R.layout.activity_main); // inflate the GUI


        // get references to the TextViews
        // that MainActivity interacts with programmatically
        amountDisplayTextView =
                (TextView) findViewById(R.id.amountDisplayTextView);
        numberInPartyDisplayTextView =
                (TextView) findViewById(R.id.numberInPartyDisplayTextView);
        percentCustomTextView =
                (TextView) findViewById(R.id.percentCustomTextView);

        totalTip15TextView = (TextView) findViewById(R.id.totalTip15TextView);
        totalPerPerson15TextView = (TextView) findViewById(R.id.totalPerPerson15TextView);
        totalTipCustomTextView = (TextView) findViewById(R.id.totalTipCustomTextView);
        totalPerPersonCustomTextView = (TextView) findViewById(R.id.totalPerPersonCustomTextView);

        // update GUI based on billAmount and customPercent
        amountDisplayTextView.setText(
                currencyFormat.format(billAmount));

        updateStandard(); // update the 15% tip TextViews
        updateCustom(); // update the custom tip TextViews

        numberInPartyDisplayTextView.setText(
                String.valueOf(numberInParty));

        // set amountEditText's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set numberInPartyEditText's TextWatcher
        EditText numberInPartyEditText =
                (EditText) findViewById(R.id.numberInPartyEditText);
        numberInPartyEditText.addTextChangedListener(numberInPartyEditTextWatcher);

        // set customTipSeekBar's OnSeekBarChangeListener
        SeekBar customTipSeekBar =
                (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    } // end method onCreate

    // updates 15% tip TextViews
    private void updateStandard()
    {
        // calculate 15% tip and total
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = (billAmount + fifteenPercentTip)/numberInParty;

        // display 15% tip and total formatted as currency
        totalTip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        totalPerPerson15TextView.setText(currencyFormat.format(fifteenPercentTotal));
    } // end method updateStandard

    // updates the custom tip and total TextViews
    private void updateCustom()
    {
        // show customPercent in percentCustomTextView formatted as %
        percentCustomTextView.setText(percentFormat.format(customPercent));

        // calculate the custom tip and total
        double customTip = billAmount * customPercent;
        double customTotal = (billAmount + customTip)/numberInParty;

        // display custom tip and total formatted as currency
        totalTipCustomTextView.setText(currencyFormat.format(customTip));
        totalPerPersonCustomTextView.setText(currencyFormat.format(customTotal));
    } // end method updateCustom

    // called when the user changes the position of SeekBar
    private OnSeekBarChangeListener customSeekBarListener =
            new OnSeekBarChangeListener()
            {
                // update customPercent, then call updateCustom
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    // sets customPercent to position of the SeekBar's thumb
                    customPercent = progress / 100.0;
                    updateCustom(); // update the custom tip TextViews
                } // end method onProgressChanged

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                } // end method onStartTrackingTouch

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                } // end method onStopTrackingTouch
            }; // end OnSeekBarChangeListener

    // event-handling object that responds to amountEditText's events
    private TextWatcher amountEditTextWatcher = new TextWatcher()
    {
        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {
            // convert amountEditText's text to a double
            try
            {
                billAmount = Double.parseDouble(s.toString()) / 100.0;

            } // end try
            catch (NumberFormatException e)
            {
                billAmount = 0.0; // default if an exception occurs
            } // end catch

            // display currency formatted bill amount
            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard(); // update the 15% tip TextViews
            updateCustom(); // update the custom tip TextViews
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s)
        {
        } // end method afterTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after)
        {
        } // end method beforeTextChanged
    }; // end amountEditTextWatcher

    private TextWatcher numberInPartyEditTextWatcher = new TextWatcher()
    {
        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {
            // convert amountEditText's text to a double
            try
            {
                numberInParty = Integer.parseInt(s.toString());
                numberInPartyDisplayTextView.setText(String.valueOf(numberInParty));
            } // end try
            catch (NumberFormatException e)
            {
                numberInPartyDisplayTextView.setText("");
            } // end catch

            updateStandard(); // update the 15% tip TextViews
            updateCustom(); // update the custom tip TextViews
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s)
        {
        } // end method afterTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after)
        {
        } // end method beforeTextChanged
    }; // end amountEditTextWatcher
} // end class MainActivity


/*************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
