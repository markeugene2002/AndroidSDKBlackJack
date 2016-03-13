/*
 * Copyright (c) 2010, Jeffrey F. Cole
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 	Redistributions of source code must retain the above copyright notice, this
 * 	list of conditions and the following disclaimer.
 * 
 * 	Redistributions in binary form must reproduce the above copyright notice, 
 * 	this list of conditions and the following disclaimer in the documentation 
 * 	and/or other materials provided with the distribution.
 * 
 * 	Neither the name of the technologichron.net nor the names of its contributors 
 * 	may be used to endorse or promote products derived from this software 
 * 	without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */

//package net.technologichron.manacalc;
package blackjack.java;

import blackjack.java.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.view.ViewGroup;
//import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * A simple layout group that provides a numeric text area with two buttons to
 * increment or decrement the value in the text area. Holding either button
 * will auto increment the value up or down appropriately. 
 * 
 * @author Jeffrey F. Cole
 *
 */
public class NumberPicker extends LinearLayout {

	private final long REPEAT_DELAY = 50;
	private final int MINIMUM = 0;
	private final int MAXIMUM = 10000;
	private final int INCR = 10;

	private Bet bet;
	private Integer value;
	private UpDown upDown;
	private TextView valueText;
	private Handler repeatUpdateHandler = new Handler();
	private int autoDecrement = 0;
	
	public NumberPicker( Context context, Bet bet) {
		super(context);
		this.bet = bet;
		
		setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams lp = new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);

		UpDown upDown = new UpDown(context, bet);
		addView(upDown, lp);
		BetVal betVal = new BetVal(context, bet);
		addView(betVal, lp);
	}
	
	private class UpDown extends LinearLayout {
		ImageButton upButton;
		TextView padButton;
		ImageButton downButton;
		Bet bet;

		private UpDown(Context context, Bet bet) {
			super(context);
			this.bet = bet;

			setOrientation(LinearLayout.VERTICAL);
			
			LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 0, 0, 0);
			upButton = initButton(context, INCR);
			padButton = new TextView(context);
			padButton.setBackgroundColor(Color.BLACK);
			padButton.setHeight(10);
			downButton = initButton(context, -INCR);
			addView(upButton, lp);
			addView(padButton, lp);
			addView(downButton, lp);
		}

		

		private ImageButton initButton(Context context, int incr) {
			ImageButton button = new ImageButton(context);
			final int thisincr = incr;
			button.setImageResource(incr > 0? R.drawable.plus_24: R.drawable.minus_24);
			button.setBackgroundColor(Color.BLACK);
			
			// Decrement once for a click
			button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
					decrement(thisincr);
	            }
	        });
			
			// Auto decrement for a long click
			button.setOnLongClickListener( 
				new View.OnLongClickListener(){
					public boolean onLongClick(View arg0) {
						autoDecrement = thisincr;
						repeatUpdateHandler.post( new RepetetiveUpdater() );
						return false;
					}
				}
			);
			
			// When the button is released, if we're auto decrementing, stop
			button.setOnTouchListener( new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if( event.getAction() == MotionEvent.ACTION_UP && autoDecrement != 0){
						autoDecrement = 0;
					}
					return false;
				}
			});
			return button;
		}
	}
	
	private class BetVal extends LinearLayout {
		TextView above;
		Bet bet;

		private BetVal(Context context, Bet bet) {
			super(context);
			this.bet = bet;
			setOrientation(LinearLayout.VERTICAL);
			
			above = new TextView(context);
			above.setHeight(30);
			addView(above);
			
			initValueEditText(context);
			addView(valueText);
		}
	}

	/**
	 * This little guy handles the auto part of the auto incrementing feature.
	 * In doing so it instantiates itself. There has to be a pattern name for
	 * that...
	 * 
	 * @author Jeffrey F. Cole
	 *
	 */
	class RepetetiveUpdater implements Runnable {
		public void run() {
			if (autoDecrement != 0) {
				decrement(autoDecrement);
				repeatUpdateHandler.postDelayed( new RepetetiveUpdater(), REPEAT_DELAY );
			}
		}
	}

	private void initValueEditText( Context context){
		
		value = new Integer( 0 );
		
		//valueText = new EditText( context );
		valueText = new TextView(context);
		valueText.setTextSize(20);
		
	/*
		// Since we're a number that gets affected by the button, we need to be
		// ready to change the numeric value with a simple ++/--, so whenever
		// the value is changed with a keyboard, convert that text value to a
		// number. We can set the text area to only allow numeric input, but 
		// even so, a carriage return can get hacked through. To prevent this
		// little quirk from causing a crash, store the value of the internal
		// number before attempting to parse the changed value in the text area
		// so we can revert to that in case the text change causes an invalid
		// number
		valueText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int arg1, KeyEvent event) {
				int backupValue = value;
				bet.changeValue(-value);
				try {
					value = Integer.parseInt( ((EditText)v).getText().toString() );
				} catch( NumberFormatException nfe ){
					value = backupValue;
				}
				bet.wager(value);
				return false;
			}
		});
		
		// Highlight the number when we get focus
		valueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if( hasFocus ){
					((EditText)v).selectAll();
				}
			}
		});
	*/
		valueText.setGravity( Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL );
		valueText.setTextColor(Color.WHITE);
		valueText.setBackgroundColor(Color.BLACK);
		valueText.setText( value.toString() );
		//valueText.setInputType( InputType.TYPE_CLASS_NUMBER );
	}

	public void decrement(int change) {
		if (value + change > MAXIMUM)
			change = MAXIMUM - value;
		if (value + change < MINIMUM)
			change = MINIMUM - value;
		bet.wager(change);
		valueText.setText(value.toString());
	}
	
	public void setValue( int value ){
		if( value > MAXIMUM ) value = MAXIMUM;
		if( value >= 0 ){
			this.value = value;
			valueText.setText( this.value.toString() );
		}
	}

	public void setColor(String s)
	{
		int choice;

		switch(s.charAt(0)) {
			case 'w': choice = Color.GREEN; break;
			case 'l': choice = Color.RED; break;
			default: choice = Color.WHITE; break;
		}
		valueText.setTextColor(choice);
	}
}

