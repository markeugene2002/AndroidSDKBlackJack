package blackjack.java;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NumberChooser extends LinearLayout{

	//private TextView layoutSpacer;  // Defined this locally
	private final Handler handler = new Handler(); // Part of run operation used in Runnables
	//private CreateNumChooser myNumChoose;  // Defined this locally
	private Bet bet;
	private final long REPEAT_DELAY = 150;
	private final int MINIMUM = 0;
	private final int MAXIMUM = 10000;
	private int value;  // Was "Integer value;".  Changed to this and changed "valueText.setText( value.toString() );" later
	//in the program to "valueText.setText(String.format("%s", value));".  Which worked fine.
	private TextView valueText;

	public NumberChooser(Context context){
		super(context);

	}

	public NumberChooser(Context context, Bet bet) {
		super(context);
		CreateNumChooser myNumChoose;
		this.bet = bet;

		setOrientation(LinearLayout.HORIZONTAL);
		
		LayoutParams lp = new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		myNumChoose = new CreateNumChooser(context, bet);  // Initializes bet control buttons
		addView(myNumChoose, lp);
		BetVal betVal = new BetVal(context, bet);
		addView(betVal, lp);
	}

	private class CreateNumChooser extends LinearLayout{

		ImageButton upButton;
		ImageButton downButton;
		private CreateNumChooser(Context context, Bet bet) {
			super(context);
			TextView layoutSpacer;
			setOrientation(LinearLayout.VERTICAL);
			
			LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 0, 0, 0);
			layoutSpacer = new TextView(context);
			upButton = new ImageButton(context);
			upButton.setBackgroundResource(R.drawable.plus_24);
			downButton = new ImageButton(context);
			downButton.setBackgroundResource(R.drawable.minus_24);
			
			layoutSpacer.setHeight(30);
			addView(upButton, lp);
			addView(layoutSpacer, lp);
			addView(downButton, lp);
			CreateNumChoose(upButton, downButton);
		}
	}
	
	private class BetVal extends LinearLayout {
		TextView layoutSpacerTwo;
		// Sets up a spacer in the view for positioning of valueText bet image
		private BetVal(Context context, Bet bet) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
			// layoutSpacerTwo is simply a place holder in the view to move down valuText.
			layoutSpacerTwo = new TextView(context);
			layoutSpacerTwo.setHeight(20);
			addView(layoutSpacerTwo);
			
			initValueEditText(context);
			addView(valueText);
		}
	}
	
	private void initValueEditText( Context context){
		// Create the bet object on the screen
		value = 0;  // 03/08/16.  Was "value = new Integer(0)" but there was a warning about unnecessary boxing.
		//value = new Integer(0);
		valueText = new TextView(context);
		valueText.setTextSize(20);
		valueText.setGravity( Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL );
		valueText.setTextColor(Color.WHITE);
		//valueText.setBackgroundColor(Color.BLACK);
		//valueText.setText( value.toString() );  // This line replaced with:
		valueText.setText(String.format("%s", value));
	}

	public void CreateNumChoose(final ImageButton upButton, final ImageButton downButton){
		
		upButton.setOnTouchListener(new OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	            if(event.getAction() == MotionEvent.ACTION_DOWN) {
	            	upButton.setBackgroundColor(Color.BLACK);  // Blank the plus image
	            	handler.post(autoIncr);  // Increment the bet
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            	upButton.setBackgroundColor(Color.WHITE);
	            	upButton.setBackgroundResource(R.drawable.plus_24);
	            	handler.removeCallbacks(autoIncr);
	            }
				return false;
	        }
	    }); 
		
		downButton.setOnTouchListener(new OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	            if(event.getAction() == MotionEvent.ACTION_DOWN) {
	            	downButton.setBackgroundColor(Color.BLACK);  // Blank the minus image
	            	handler.post(autoDecr);  // Decrement the bet
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            	downButton.setBackgroundColor(Color.WHITE);
	            	downButton.setBackgroundResource(R.drawable.minus_24);
	            	handler.removeCallbacks(autoDecr);
	            }
				return false;
	        }
	    });
	}
	
	private Runnable autoIncr = new Runnable() {
	    public void run() {
	        // Auto increment the bet 
	    	decrement(10);
	        // Call the runnable again
	        handler.postDelayed(this, REPEAT_DELAY);
	    }
	};
	
	private Runnable autoDecr = new Runnable() {
	    public void run() {
	        // Auto decrement the bet 
	    	decrement(-10);
	        // Call the runnable again
	        handler.postDelayed(this, REPEAT_DELAY);
	    }
	};
	
	public void decrement(int change) {  // This method actually decrements or increments bet
		if (value + change > MAXIMUM)	 // depending on whether change is positive or negative.
			change = MAXIMUM - value;
		if (value + change < MINIMUM)
			change = MINIMUM - value;
		bet.wager(change);
		//valueText.setText(value.toString());  // This line replaced with:
		valueText.setText(String.format("%s", value));
	}
	
	public void setValue( int value ){
		if( value > MAXIMUM ) value = MAXIMUM;
		if( value >= 0 ){
			this.value = value;
			//valueText.setText( this.value.toString() );  // This line replaced with:
			valueText.setText(String.format("%s", value));
		}
	}

	public void setColor(String s){
		int choice;
		// Set the color of the bet to appropriate one for hand result
		switch(s.charAt(0)) {  //  s.charAt(0) means first character of string is evaluated.
			case 'w': choice = Color.GREEN; break;
			case 'l': choice = Color.RED; break;
			case 'p': choice = Color.BLUE; break;
			default: choice = Color.WHITE; break;
		}
		valueText.setTextColor(choice);
	}
	// Use toast(text) to send visual text messages to the user as a temporary dialog
	public void toast(String text){
    	Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }
}