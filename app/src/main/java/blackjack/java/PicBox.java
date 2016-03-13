/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.java;


import blackjack.java.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 *
 * @author Mark-Laptop
 */
public class PicBox extends ImageButton {
	//private static final String cardBackPath = "/mnt/sdcard/Android/data/cards/CardBack.png";
	private static final String cardBackPath = "/data/data/blackjackdev.java/cards/CardBack.png";
	private int x;
	private int y;
	private Hand h;
	private PlayingCard pCard;
	private RelativeLayout.LayoutParams rl;
	private boolean facedown = false;

	public PicBox(int myX, int myY, Shoe shoe, Hand myHand)
	{
		super(myHand.handThat());
		h = myHand;
		x = myX;
		y = myY;

		pCard = shoe.GetCard();
		setFace();
		this.setPadding(0, 0, 0, 0);
		this.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				h.Hand_Click('C');
				//doClick(arg0);
			}
		});
		
		this.setOnLongClickListener(new OnLongClickListener() {          
			public boolean onLongClick(View arg0) {             
				// TODO Auto-generated method stub 
				h.Hand_Click('L');
			return true;         
			}     
		}); 

		rl = getRL(x, y);
	}

	public Hand getHand()
	{
		return h;
	}

	public void show(RelativeLayout layout)
	{
		layout.addView(this, rl);
	}

	public PlayingCard GetPCard()
	{
		return pCard;
	}

	public int GetX()
	{
		return x;
	}

	public int GetY()
	{
		return y;
	}

	public void setFace()
	{
		setImageResource(facedown? R.drawable.cardback: pCard.ToRes());
		h.setValueVisible(!facedown);
	}

	public void Facedown(boolean what)
	{
		facedown = what;
	}

	/*
	 * This procedure is used throughout the project it takes the x y arguments for the position 
	 * of an control such as a button and returns it to the "public void addView 
	 * (View child, ViewGroup.LayoutParams params)" method as the params parameter.
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


