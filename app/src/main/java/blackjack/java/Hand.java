package blackjack.java;

import java.io.*;
import java.util.ArrayList;







import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Hand {
	private ArrayList<PicBox> pBoxes = new ArrayList<PicBox>();
	private int x;
	private int y;
	private int deltaX = -14;
	private int deltaY = -8;
	private Shoe shoe;
	private BlackJackActivity that;
	private BlackJackActivity playerThat;
	private boolean firstAce;
	private boolean anyAce = false;
	private boolean soft = false;
	private boolean bust = false;
	private boolean stayed = false;
	private boolean dealerBlackjack = false;
	private boolean blackjack = false;
	private int handValue;
	private TextView tBox;
	private boolean dealer;
	int endHandValue = 0;
	private int myA;
	private Player player;
	private int cardsIndex = 1;
	private static final String TAG = "DroidActivity"; 
	private RelativeLayout theLayout;
	private LinearLayout layoutContainer;
	private long msInc = 500;
	
	public Hand(int myX, int myY, Shoe myShoe, BlackJackActivity myThat, Player myPlayer,
				boolean myDealer, RelativeLayout thatLayout)
	{
		x = myX;
		y = myY;
		shoe = myShoe;
		that = myThat;
		dealer = myDealer;
		playerThat = myThat;
		player = myPlayer;
		theLayout = thatLayout;
		tBox = new TextView(that); 
		tBox.setText("");
		tBox.setTextColor(Color.BLACK);
		theLayout.addView(tBox, getRL(x, y + 120));	
	}
	
	public Activity handThat()
	{
		return that;
	}

	/*
	 * Methods for accessing instance variables
	 */
	public void setBet()
	{
		return; 
	}
	
	public void setDealerBlackJack(boolean theDealerBlackJack)
	{
		dealerBlackjack = theDealerBlackJack;
	}
	
	public boolean dealerBlackJack()
	{
		return dealerBlackjack;
	}
	
	public boolean dealer()
	{
		return dealer;
	}
	
	public boolean isFirstAce()
	{
		return firstAce;
	}
	
	public boolean isSoft()
	{
		return soft;
	}

	public boolean isBlackjack()
	{
		return blackjack;
	}

	public boolean isPlayable()
	{
		if (blackjack || bust || stayed)
			return false;
		return true;
	}

	public int getHValue()
	{
		return handValue;
	}
	
	public void doStay()
	{
		if (isPlayable())
		{
			stayed = true;
			ShowHand();
		}
	}

	public void setFacedown(boolean what)
	{
		if (pBoxes.size() >= 2)
		{
			PicBox p = pBoxes.get(1);
			p.Facedown(what);
			p.setFace();
		}
	}

	public boolean DealOne()   // Returns true if still hittable after card dealt
	{
		PicBox p;

		int index = pBoxes.size();
		p = new PicBox(x - index * deltaX, y - index * deltaY, shoe, this);
		pBoxes.add(p);
		index++;
		
		handValue = Evaluate();
		switch (index)
		{
			case 1: 
			{
				firstAce = (p.GetPCard().CardValueEnum() == Value.Ace);
				break;
			}
			case 2:
				if (anyAce && handValue == 11)
				{
					blackjack = true;
					if (index == 2 && dealer)
					{
						dealerBlackjack = true;
						p.Facedown(false);
					}
				}

				break;
			default:
				if (handValue > 21)
				{
					bust = true;
				}
				break;
		}
		p.show(that.getLayout());
		ShowHand();
		return isPlayable();
	}

	int blinkCtr;
	Hand blinkHand;
	private Handler handler;

	public void blinkHand()
	{   
		handler = new Handler();

		blinkCtr = 4;
		blinkHand = this;
		handler.post(visChange);
	}   

	private Runnable visChange = new Runnable() {
		public void run() {
			for (PicBox p : pBoxes)
			{   
				if ((blinkCtr & 1) == 1)
					p.setVisibility(View.VISIBLE);
				else
					p.setVisibility(View.INVISIBLE);
			}   
			if (--blinkCtr > 0)
				handler.postDelayed(this, 200);
		}   
	};  
	
	public void ShowHand()
	{
			if (blackjack)
				tBox.setText("BlackJack");
			else
			{
				tBox.setText(String.format("%s %s %s",
					soft ? "Soft " : "",
					handValue + (soft? 10: 0),
					bust ? " - Bust" : stayed ? " - Stay" : ""));
			}
	}

	public void setValueVisible(boolean what)
	{
		tBox.setVisibility(what? View.VISIBLE: View.INVISIBLE);
	}

	public int Evaluate()
	{
		int value = 0;

		for (PicBox p : pBoxes)
			value += EvaluateCard(p.GetPCard());
		soft = value <= 11 && anyAce;
		return value;
	}
	
	public int EvaluateHand()
	{
	   return handValue + (soft? 10: 0);
	}
	
	private int EvaluateCard(PlayingCard c)
	{
		Value v = c.CardValueEnum();

		switch(v) {
			case Ace:
				anyAce = true;
				return 1;
			case Two: return 2;
			case Three: return 3;
			case Four: return 4;
			case Five: return 5;
			case Six: return 6;
			case Seven: return 7;
			case Eight: return 8;
			case Nine: return 9;
			case Ten:
			case Jack:
			case Queen:
			case King:
			default:
				return 10;
		}
	}

	public void Hand_Click(char theButton)
	{
		if (player != that.curPlayer() || this != player.curHand() 
				|| player == that.Dealer() || !this.isPlayable())
		{
			if (blinkCtr <= 0)
				blinkHand();
			return;
		}
		switch (theButton)
		{
			case 'C' :	 // Left button:  Hit hand  (Short press)
				if (DealOne() == false)
					that.allowHitNextHand();
				break;
			case 'L' :	// Right button:  Stay hand  (Long press)
				doStay();
				that.allowHitNextHand();
				break;
		}
	}
	
	public void DiscardHand()
	{
		
		for (PicBox p : pBoxes)
		{
			theLayout.removeView(p);  		// Remove the playing cards from the screen.
			theLayout.removeView(tBox);  	// Remove the textboxes from the screen.
			PlayingCard c = p.GetPCard();  	// Return the playing card object "pCard" from PlayingCard class.
			shoe.DisCard(c);  // Adds card to "discards" arraylist in Shoe class.  Where it is stored until deck is
							  // shuffled again in "RandomizeDiscardsIntoShoe(); in Shoe class"
		}
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
	
	public void SaveHand(BufferedWriter file)
	{
		try {
			file.write(String.format("hand[" + playerThat + "]"));
                        file.newLine();
			for (PicBox p : pBoxes)
			{
                            if (p == null)
                                break;
                            file.write(p.GetPCard().ToString() + "\n");
                            file.newLine();
                            cardsIndex++;
			}
		} catch (IOException ex) {
		}
	}
        
}



