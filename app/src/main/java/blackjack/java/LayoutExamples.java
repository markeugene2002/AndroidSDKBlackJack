/*
 * This is a class which has examples of working with an xml layout
 */
package blackjack.java;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class LayoutExamples extends Activity {
	
	
	
	ImageButton imageButton1; 
	ImageButton imageButton2; 
	ImageButton imageButton3; 
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fbrl); // Load the xml layout fbrl
        RelativeLayout layout;
        
        // Prepare to associate current xml view with new layout
        // for adding new controls such as buttons etc.
        layout = (RelativeLayout) findViewById(R.id.fbrl);
        
        // Example of adding button dynamically to existing xml layout.
        Button dealCardsBtn = new Button(this);
		dealCardsBtn.setText("Deal Cards");
		dealCardsBtn.setId(-1);
		layout.addView(dealCardsBtn, getRL(0,200));
        
		// Animation block of code for reference.
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        
        Button button1 = new Button(this);
        button1 = (Button) findViewById(R.id.button1);
        
        Button button2 = new Button(this);
        button2 = (Button) findViewById(R.id.button2);
        //button2.startAnimation(animation);
        
        Button button3 = new Button(this);
        button3 = (Button) findViewById(R.id.button3);
        
        
        imageButton1 = new ImageButton(this);
        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        
        imageButton2 = new ImageButton(this);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        
        imageButton3 = new ImageButton(this);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        
        // Example of adding images to controls in layouts
	    button1.setBackgroundResource(R.drawable.cardback);
	    button2.setBackgroundResource(R.drawable.plus_24);
	    button3.setBackgroundResource(R.drawable.minus_24);
	    
	    // Example of changing background color on controls in layouts
	    imageButton3.setBackgroundColor(Color.BLACK);
	    imageButton2.setBackgroundColor(Color.BLACK);
	    imageButton1.setBackgroundColor(Color.BLACK);
	    imageButton3.startAnimation(animation); // Start animation reference example
	    button1.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0)
				{
					// Go back to previous activity
					finish();
				}
			});

	    button2.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0)
				{
					// Go back to previous activity
					finish();
				}
			});
	    
	    button3.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0)
				{
					// Go back to previous activity
					finish();
				}
			});
	    
	    /*
	     * Example of using on touch listener for a control such as
	     * an image button.  Also where the on touch method of the control takes
	     * a reference to itself.
	     */
	    imageButton1.setOnTouchListener(new OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	            if(event.getAction() == MotionEvent.ACTION_DOWN) {
	            	imageButton1.setBackgroundColor(Color.CYAN);
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            	imageButton1.setBackgroundColor(Color.BLACK);
	            }
				return false;
	        }
	    });

	    imageButton2.setOnTouchListener(new OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	            if(event.getAction() == MotionEvent.ACTION_DOWN) {
	            	imageButton2.setBackgroundColor(Color.CYAN);
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            	imageButton2.setBackgroundColor(Color.BLACK);
	            }
				return false;
	        }
	    });
	    
	    imageButton3.setOnTouchListener(new OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	            if(event.getAction() == MotionEvent.ACTION_DOWN) {
	            	imageButton3.setBackgroundColor(Color.CYAN);
	            	finish();
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            	imageButton3.setBackgroundColor(Color.BLACK);
	            }
				return false;
	        }
	    });
	    
	    /*  On click listener examples.  Keep for reference
	    
	    imageButton1.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0)
				{
					imageButton1.setImageResource(R.drawable.clubs_4);
					// Go back to previous activity
					//finish();
				}
			});

	    imageButton2.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0)
				{
					// Go back to previous activity
					//finish();
				}
			});
	    
	    imageButton3.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0)
				{
					// Go back to previous activity
					//finish();
				}
			});*/
	}
    
    /*
     * This procedure is used throughout the project it takes the x y arguments 
     * for the position of an control such as a button and returns it to
     * the "public void addView (View child, ViewGroup.LayoutParams params)"
     * method as the params parameter.
    */
    
    private RelativeLayout.LayoutParams getRL(int x, int y)
	{
		RelativeLayout.LayoutParams rl = new
			RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl.topMargin = y;
		rl.leftMargin = x;
		return rl;
	}
}
