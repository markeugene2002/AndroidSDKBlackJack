/*
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


public class Bank extends TextView{
    private int x;
    private int y;
    private int balance = 0;
    private RelativeLayout layout;
    private RelativeLayout.LayoutParams rl;
    
    public Bank(Context context){
    	super(context);
    	
    }
    public Bank(int myX, int myY, BlackJackActivity myThat, Context context, RelativeLayout myLayout){
    	super(context);
        x = myX;
        y = myY;
        layout = myLayout;
        rl = getRL(x + 40, y);
        setText((Integer.toString(balance)));
        layout.addView(this, rl);
    }
    
    public void RemoveBank()
    {
    	layout.removeView(this);
    }
    
	public int withdraw(int withdrawal)
    {
        int amountWithdrawn = withdrawal;
        assert(withdrawal >= 0);
        if (withdrawal > balance)
            amountWithdrawn = balance;
        balance -= amountWithdrawn;
        setText(Integer.toString(balance));
        return amountWithdrawn;
    }

    public int deposit(int deposit)
    {
        assert(deposit >= 0);
        balance += deposit;
        setText(Integer.toString(balance));
        return deposit;
    }
    
    /*
    public int getBalance()
    {
        return balance;
    }
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
