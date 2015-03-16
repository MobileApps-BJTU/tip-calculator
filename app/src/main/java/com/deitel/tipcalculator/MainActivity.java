// MainActivity.java
// Calculates bills using 15% and custom percentage tips.
package com.deitel.tipcalculator;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
// for currency formatting
// base class for activities
// for saving state information
// for EditText event handling
// EditText listener
// for bill amount input
// for changing custom tip percentage
// SeekBar listener
// for displaying text

// MainActivity class for the Tip Calculator app
public class MainActivity extends Activity {
	// currency and percent formatters
	private static final NumberFormat currencyFormat = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat numberFormat = NumberFormat
			.getNumberInstance();
	private static final NumberFormat percentFormat = NumberFormat
			.getPercentInstance();

	private RadioGroup group;

	private int personAmount = 1; // person amount entered by the user
	private double billAmount = 0.0; // bill amount entered by the user
	private double customPercent = 0.18; // initial custom tip percentage
	private TextView amountDisplayTextView; // shows formatted bill amount

	private TextView amountPersonDisplay; // shows the amount of person

	private TextView percentCustomTextView; // shows custom tip percentage
	private TextView tip15TextView; // shows 15% tip
	private TextView totalPerPerson15TextView; // shows total with 15% tip
	private TextView tipCustomTextView; // shows custom tip amount
	private TextView totalPerPersonTextView; // shows total with custom tip

	private TextView tip;
	private TextView totalPer;
	private TextView amount;
	private TextView person;
	private TextView custom;

	// called when the activity is first created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // call superclass's version
		setContentView(R.layout.activity_main); // inflate the GUI

		// get references to the TextViews
		// that MainActivity interacts with programmatically
		group = (RadioGroup) findViewById(R.id.radioGroup);

		tip = (TextView) findViewById(R.id.tipTextView);
		totalPer = (TextView) findViewById(R.id.totalTextView);
		amount = (TextView) findViewById(R.id.amountTextView);
		person = (TextView) findViewById(R.id.person);
		custom = (TextView) findViewById(R.id.customPercentTextView);

		amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);

		amountPersonDisplay = (TextView) findViewById(R.id.personDisplay);

		percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
		tip15TextView = (TextView) findViewById(R.id.tip15TextView);
		tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);

		totalPerPerson15TextView = (TextView) findViewById(R.id.totalPerPerson15TextView);
		totalPerPersonTextView = (TextView) findViewById(R.id.totalPerPersonTextView);

		amountPersonDisplay.setText(numberFormat.format(personAmount));

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				int radioButtonId = arg0.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				if (rb.getText().equals("简体中文")) {
					amount.setText("数量");
					person.setText("人数");
					custom.setText("比例 %");
					tip.setText("小费");
					totalPer.setText("每人账单");
					Toast.makeText(getApplicationContext(), "切换至简体中文",
						     Toast.LENGTH_SHORT).show();
				} else if (rb.getText().equals("English")) {
					amount.setText("Amount");
					person.setText("Number in party");
					custom.setText("Custom %");
					tip.setText("Total Tip");
					totalPer.setText("Total per person");
					Toast.makeText(getApplicationContext(), "The language has changed to English",
						     Toast.LENGTH_SHORT).show();
				}
			}
		});

		// update GUI based on billAmount and customPercent
		amountDisplayTextView.setText(currencyFormat.format(billAmount));
		updateStandard(); // update the 15% tip TextViews
		updateCustom(); // update the custom tip TextViews

		// set amountEditText's TextWatcher
		EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(amountEditTextWatcher);

		// set personAmountEditTextView's TextWatcher
		EditText personAmountEditText = (EditText) findViewById(R.id.personAmount);
		personAmountEditText
				.addTextChangedListener(amountPersonEditTextWatcher);

		// set customTipSeekBar's OnSeekBarChangeListener
		SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
		customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	} // end method onCreate

	// updates 15% tip TextViews
	private void updateStandard() {
		// calculate 15% tip and total
		double fifteenPercentTip = billAmount * 0.15;
		double fifteenPercentTotal = (billAmount + fifteenPercentTip)
				/ personAmount;

		// display 15% tip and total formatted as currency
		tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
		totalPerPerson15TextView.setText(currencyFormat
				.format(fifteenPercentTotal));
	} // end method updateStandard

	// updates the custom tip and total TextViews
	private void updateCustom() {
		// show customPercent in percentCustomTextView formatted as %
		percentCustomTextView.setText(percentFormat.format(customPercent));

		// calculate the custom tip and total
		double customTip = billAmount * customPercent;
		double customTotal = (billAmount + customTip) / personAmount;

		// display custom tip and total formatted as currency
		tipCustomTextView.setText(currencyFormat.format(customTip));
		totalPerPersonTextView.setText(currencyFormat.format(customTotal));
	} // end method updateCustom

	// called when the user changes the position of SeekBar
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
		// update customPercent, then call updateCustom
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// sets customPercent to position of the SeekBar's thumb
			customPercent = progress / 100.0;
			updateCustom(); // update the custom tip TextViews
		} // end method onProgressChanged

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		} // end method onStartTrackingTouch

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		} // end method onStopTrackingTouch
	}; // end OnSeekBarChangeListener

	// event-handling object that responds to amountEditText's events
	private TextWatcher amountEditTextWatcher = new TextWatcher() {
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// convert amountEditText's text to a double
			try {
				billAmount = Double.parseDouble(s.toString()) / 100.0;
			} // end try
			catch (NumberFormatException e) {
				billAmount = 0.0; // default if an exception occurs
			} // end catch

			// display currency formatted bill amount
			amountDisplayTextView.setText(currencyFormat.format(billAmount));
			updateStandard(); // update the 15% tip TextViews
			updateCustom(); // update the custom tip TextViews
		} // end method onTextChanged

		@Override
		public void afterTextChanged(Editable s) {
		} // end method afterTextChanged

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} // end method beforeTextChanged
	}; // end amountEditTextWatcher

	// event-handling object that responds to amountPersonEdit's events
	private TextWatcher amountPersonEditTextWatcher = new TextWatcher() {
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// convert amountEditText's text to a double
			try {
				personAmount = Integer.parseInt(s.toString());
			} // end try
			catch (NumberFormatException e) {
				personAmount = 1; // default if an exception occurs
			} // end catch

			// display currency formatted bill amount
			amountPersonDisplay.setText(numberFormat.format(personAmount));
			updateStandard(); // update the 15% tip TextViews
			updateCustom(); // update the custom tip TextViews
		} // end method onTextChanged

		@Override
		public void afterTextChanged(Editable s) {
		} // end method afterTextChanged

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} // end method beforeTextChanged
	};
} // end class MainActivity

/*************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 *************************************************************************/
