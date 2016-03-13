/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.java;

/**
 *
 * @author Mark
 */

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Bet extends TextView{
    private int x;
    private int y;
    private BlackJackActivity that;
    private Bank bank;
    private int value = 10; // Value of initial bet
    private RelativeLayout layout;
	private NumberChooser nc;
    
    public Bet(int myX, int myY, BlackJackActivity myThat, Bank myBank, RelativeLayout myLayout, Context context) 
    {
    	super(context);
        x = myX;
        y = myY;
        that = myThat;
        bank = myBank;
        layout = myLayout;
		nc = new NumberChooser(that, this);
		nc.setValue(value);
		nc.setColor("reset");
		layout.addView(nc, getRL(x + 25, y - 30));
    }
    
    // Remove bet NumberChooser from ResetGame initiated in BlackJackActivity
    public void removeBet()
    {
    	layout.removeView(nc);
    }
    
	public void initColors()
	{
		nc.setColor("reset");  // Sets color of the bet to default:  WHITE
	}

	public void changeValue(int howmuch)
	{
		value += howmuch;
	}
   
    public void wager(int howmuch)
    {
        if (howmuch < 0 && value < -howmuch)
            return;
        if (howmuch > 0)
            value += bank.withdraw(howmuch);
        else
            value -= bank.deposit(-howmuch);
		nc.setValue(value);
		nc.setColor("reset");
    }

    public void win()
    {
        value *= 2;
        
		nc.setValue(value);
		nc.setColor("win");
    }
    
    public void lose()
    {
        value = 0;
        
		nc.setValue(value);
		nc.setColor("lose");
    }
    
    public void push()
    {
        setText(Integer.toString(value));
        nc.setColor("push");
    }
    
	public int setBet(int savedBet)
    {
        value = savedBet;
        return value;
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
