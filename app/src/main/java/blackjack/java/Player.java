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

//import blackjack.Bet;

import java.io.*;
import java.util.ArrayList;

import android.graphics.Color;
import android.widget.RelativeLayout;


public class Player {
    private int x;
    private int y;
    private Shoe shoe;
    private BlackJackActivity that;
    private Bank bank;
	private ArrayList<Hand> hands;
	private Hand hand;	// Current hand for this player, usually hands.get(0)
    private Bet[] bets = new Bet[8];
    private boolean dealer;
    private int playerNum = 0;
    private boolean dealerBlackJack = false;
    private RelativeLayout layoutTheBot;
    private RelativeLayout layoutTheTop;
    private final int BankRow = -148;
    
     public Player()
    {
        
    }
    
    public Player(int myX, int myY, Shoe myShoe, BlackJackActivity myThat, boolean myDealer, 
    		int thePlayerNum, RelativeLayout layoutBot, RelativeLayout layoutTop)
        {
            x = myX;
            y = myY;
            shoe = myShoe;
            that = myThat;
            dealer = myDealer;
            playerNum = thePlayerNum;
            layoutTheBot = layoutBot;
            layoutTheTop = layoutTop;
			hands = new ArrayList<Hand>();
			hand = null;
            if(!dealer)
            {
            	bank = new Bank(x, y + BankRow, that, that, layoutTheBot);
            	bank.deposit(dealer? 10000: 490);
            }
            if(!dealer)
            	bets[0] = new Bet(x, y - 215, that, bank, layoutTheBot, that);
        }
    
    public void ReHitCardsExact()
    {
		hands.get(0).Hand_Click('L');
    }
    
    public void ReHitCardsExactStay()
    {
		hands.get(0).Hand_Click('R');
    }
    public void PlaceBets()
    {
    	/*
        bets[0].setBackground(Color.white);
		hands.get(0).DiscardHand();
        */
    }
    
    public void MakeHand()
    {
		Hand h = new Hand(x, y, shoe, that, this, dealer, layoutTheTop);
		hands.add(h);
		if (hand == null)
			hand = h;
    }

	public Hand curHand()
	{
		return hand;
	}
    
    public void setDealerBlackJack(boolean theDealerBlackJack)
    {
        dealerBlackJack = theDealerBlackJack;
    }
    
    // Remove NumberPicker and reset game to no players
    public void ResetGame()
    {
    	if(bets[0] != null)
    		bets[0].removeBet();  // This a call to the Bet class object
    	if(bank != null)
    		bank.RemoveBank();  // This is a call to the Bank class object
    	for (Hand h: hands)
    		h.DiscardHand();
    	hands.clear();
     	hand = null;   
    }
    
    public void DiscardHands()
    {
    	if(bets[0] != null)
			bets[0].initColors();   // Sets color of the bet to default:  WHITE
    	for (Hand h: hands)
    		h.DiscardHand();        // Remove the playing cards from the screen for each hand  One at a time.
    	hands.clear();              // Empty the arraylist:  hands.
     	hand = null;   
    }
    
    public void DealOne()
    {
		hands.get(0).DealOne();
    }
    
    public void DealTwo()
    {
		Hand h = hands.get(0);
		h.DealOne();
		if (dealer)
			h.setFacedown(true);
    }

	public boolean isPlayable()
	{
		for (Hand h: hands)
		{
			if (h.isPlayable())
			{
				hand = h;
				return true;
			}
		}
		return false;
	}
    
    public Hand TheHand()
    {
		return hand;
    }
    
    public boolean HandInPlayer(Hand hand)
    {
        for (Hand h: hands)
            if (hand == h)
                return true;
        return false;
    }
    
    public Bet TheBet()
    {
        return bets[0];
    }
    
    
    public void CalcWinnings(int i, int dealerResult)
    {
		Hand h = hands.get(0);
        Bet b = bets[0];
        int dValue = dealerResult;
		int pValue = h.EvaluateHand();
            
        if(dealerBlackJack && pValue != 21) 
            b.lose();
        else if (dValue > 21)
        {
            if (pValue < 22)
                b.win();
            else
                b.lose();
        } else {
            if (pValue > 21)
                b.lose();
            else if (pValue > dValue && !dealerBlackJack)
                b.win();
            else if (pValue == dValue || (dealerBlackJack && pValue == 21))
                b.push();
            else
                b.lose();
        }
    }
    
    public void SaveHands(BufferedWriter file)
    {
		try {
			file.write(String.format("player[%d]\n", playerNum));
                        file.newLine();
			for (Hand h: hands)
			{
				if (h != null)
					h.SaveHand(file);
			}
		} catch (IOException ex) {
		}
    }
    
    
}
