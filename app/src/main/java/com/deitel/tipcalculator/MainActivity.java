// MainActivity.java
// Calculates bills using 15% and custom percentage tips.
package com.deitel.tipcalculator;

import java.text.NumberFormat; // for currency formatting
import java.util.Locale;

import android.app.Activity; // base class for activities
import android.os.Bundle; // for saving state information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.view.View;
import android.widget.Button;
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text
import android.widget.Toast;

// MainActivity class for the Tip Calculator app
public class MainActivity extends Activity
{
    // currency and percent formatters
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private static final NumberFormat numberInstance = NumberFormat.getNumberInstance();
    private double billAmount = 0.0; // bill amount entered by the user
    private double customPercent = 0.18; // initial custom tip percentage
    private TextView amountDisplayTextView; // shows formatted bill amount
    private TextView percentCustomTextView; // shows custom tip percentage
    private TextView tip15TextView; // shows 15% tip
    private TextView total15TextView; // shows total with 15% tip
    private TextView tipCustomTextView; // shows custom tip amount
    private TextView totalCustomTextView; // shows total with custom tip
    private EditText customerNumberTextView;
    private TextView AverageMoneyTextView;
    private TextView Average15MoneyTextView;
    private int number=1;
    private TextView amountTextView;
    private TextView customPercentTextView;
    private TextView tipTextView;
    private TextView totalTextView;
    private TextView AverageTextView;
    private TextView NumberTextView;
    private int TranslateState = 1;
    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); // call superclass's version
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to the TextViews
        // that MainActivity interacts with programmatically
        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);
        customerNumberTextView = (EditText)findViewById(R.id.customerNumberTextView);
        AverageMoneyTextView = (TextView)findViewById(R.id.AverageMoneyTextView);
        Average15MoneyTextView = (TextView)findViewById(R.id.Average15MoneyTextView);
        amountTextView =  (TextView)findViewById(R.id.amountTextView);
        customPercentTextView = (TextView)findViewById(R.id.customPercentTextView);
        tipTextView = (TextView)findViewById(R.id.tipTextView);
        totalTextView = (TextView)findViewById(R.id.totalTextView);
        AverageTextView = (TextView)findViewById(R.id.AverageTextView);
        NumberTextView = (TextView)findViewById(R.id.NumberTextView);

        //calculate the average money
        final Button catculator = (Button)findViewById(R.id.catculator);
        /*
        catculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double average15,average;
                //number = Integer.parseInt(customerNumberTextView.getText().toString());
                if(number>1){
                    average15 = billAmount*0.15;
                    average15 +=billAmount;
                    average15=average15/number;
                    Average15MoneyTextView.setText(currencyFormat.format(average15));

                    average = billAmount * customPercent;
                    average += billAmount;
                    average=average/number;
                    AverageMoneyTextView.setText(currencyFormat.format(average));


                }
            }
        });*/

        final Button translate = (Button)findViewById(R.id.translate);
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TranslateState==2){
                    amountTextView.setText("花费");
                    customPercentTextView.setText("小费率");
                    tipTextView.setText("小费");
                    totalTextView.setText("总花费");
                    NumberTextView.setText("人数");
                    AverageTextView.setText("平均花费");
                    catculator.setText("计算");
                    translate.setText("翻译成英文");
                    TranslateState=1;
                }else  if (TranslateState==1){
                    amountTextView.setText("Amount");
                    customPercentTextView.setText("Custom %");
                    tipTextView.setText("Tip");
                    totalTextView.setText("Total");
                    NumberTextView.setText("Number");
                    AverageTextView.setText("Average");
                    catculator.setText("catculate");
                    translate.setText("translate to Chinese");
                    TranslateState=2;
                }
            }
        });

        // update GUI based on billAmount and customPercent
        amountDisplayTextView.setText(currencyFormat.format(billAmount));

        updateStandard(); // update the 15% tip TextViews
        updateCustom(); // update the custom tip TextViews
        updataAverage();// update the average
        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);

        amountEditText.addTextChangedListener(amountEditTextWatcher);
        customerNumberTextView.addTextChangedListener(numberEditTextWatcher);

        // set customTipSeekBar's OnSeekBarChangeListener
        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    } // end method onCreate

    private void updataAverage(){

        double average15,average;

        if(number>=1) {
            average15 = billAmount * 0.15;
            average15 += billAmount;
            average15 = average15 / number;
            Average15MoneyTextView.setText(currencyFormat.format(average15));

            average = billAmount * customPercent;
            average += billAmount;
            average = average / number;
            AverageMoneyTextView.setText(currencyFormat.format(average));
        }
    }

    // updates 15% tip TextViews
    private void updateStandard()
    {
        // calculate 15% tip and total
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        // display 15% tip and total formatted as currency
        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
    } // end method updateStandard

    // updates the custom tip and total TextViews
    private void updateCustom()
    {
        // show customPercent in percentCustomTextView formatted as %
        percentCustomTextView.setText(percentFormat.format(customPercent));

        // calculate the custom tip and total
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        // display custom tip and total formatted as currency
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
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
            updataAverage(); //update the average
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

    private TextWatcher numberEditTextWatcher = new TextWatcher()
    {
        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count)
        {
            try
            {
                number = Integer.parseInt(s.toString())     ;
            } // end try
            catch (NumberFormatException e)
            {
                number = 0; // default if an exception occurs
            } // end catch
            if(number==0){
                Toast.makeText(getApplicationContext(), "The num of people can't be zero!",Toast.LENGTH_SHORT).show();
                         }
            else {
                // display currency formatted bill amount
                updateStandard(); // update the 15% tip TextViews
                updateCustom(); // update the custom tip TextViews
                updataAverage(); //update the average
            }
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s)
        {

        } // end method afterTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after)
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
