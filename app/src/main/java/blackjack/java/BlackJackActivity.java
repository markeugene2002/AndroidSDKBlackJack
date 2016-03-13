package blackjack.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import blackjack.java.R;
import blackjack.java.R.layout;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.content.Intent;
import android.content.SharedPreferences;

public class BlackJackActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Shoe shoe;
	private RelativeLayout layoutBot;
	public RelativeLayout layoutTop;
	private RelativeLayout layoutTwo;
	public LinearLayout layoutContainer;
	private int deltax = 0, deltay = 0;
	private int deltaval = 15;
	private String[] array_spinner = new String[] {"1", "2", "3", "4"};
	private static final String TAG = "DroidActivity"; 
	private int numPlayers = 2; // Set number of players.  (Must set 
    private int numHands = 2; // number of hands too.)
	private ArrayList<Player> players;
	private Player player;	// Current player
	private Player dealer;	// Last player, namely, dealer
    private Hand dealerHand;
    private int curHandX;
    private TextView bets;
    private TextView balances;
    private String redealPath;
    private Button placeBetsButton;
    private Button dealCardsButton;
    private Button saveGameButton;
    private Button redealHandsButton;
    private boolean redealt;
    public int dealerHandResult;
    public int playerHandResult;
    public boolean dealerBlackJack = false;
	private int msInc = 50;  // msInc = speed of cards being dealt
	private final Handler handler = new Handler();
	private final int ButRow1 = 0;
	private final int ButRow2 = 100;
	private int PlayerRow = 200;
	private final int DealerRow = 30;
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final int GREEN = 0xFF00FF00;
	private SharedPreferences prefs;
	private ListView lv;
	private boolean playerHdBust = false;
    private int playerCol = 150;
    private int dealerCol = 50;
    private int defScreenWidth;
    private int defScreenHeight;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	// SharedPreferences are an internal storage medium for saving various values for
    	// retrieval globally.  Persistent data is available even after application is killed.        
        SharedPreferences.Editor prefsEd;
        
        prefs = getSharedPreferences(PREFS_NAME, 0);
        prefsEd = prefs.edit();
        if (!prefs.contains("Speed")) {
        	prefsEd.putInt("Speed", 0);
        	prefsEd.commit();
        }
        msInc = prefs.getInt("Speed", 0);

        
        layoutTop = new RelativeLayout(this);
        layoutBot = new RelativeLayout(this); 
        
        setContentView(layout.main_screen);
        Display display = getWindowManager().getDefaultDisplay();
        defScreenWidth = display.getWidth();
        defScreenHeight = display.getHeight();
        
        layoutBot = (RelativeLayout) findViewById(R.id.layoutBot);
        layoutTop = (RelativeLayout) findViewById(R.id.layoutTop);
        layoutTop.setBackgroundColor(GREEN);
        //layoutBot.setBackgroundColor(Color.BLUE);
        final FilChoose fc = new FilChoose(layoutBot, this, this);
		players = new ArrayList<Player>();
		shoe = new Shoe(3);
		redealt = false;
		
		Button dealCardsBtn = new Button(this);
		dealCardsBtn = (Button)findViewById(R.id.dealCardsBtn);
		Button saveGameButton = new Button(this);
		saveGameButton = (Button)findViewById(R.id.saveGameButton);
		Button redealHandsButton = new Button(this);
		redealHandsButton = (Button)findViewById(R.id.redealHandsButton);
		Button saveExactButton = new Button(this);
		saveExactButton = (Button)findViewById(R.id.saveExactButton);
		
		
		
		dealCardsBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				redealt = false;  // Testing:  Used in redealing hands.
				dealCards();
			}
		});

		
		saveGameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				SaveHands();

				if (redealt)
					toast("Game already redealt");
				else
				{
					redealt = true;
					toast("Hit 'Deal Cards' to redeal the game");
					shoe.reDeal();
				}
			}
		});
		
		saveExactButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				SaveHands();
			}
		});
		
		redealHandsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				
			}
			});
		
        createHands();
		
    }
    
    private void ResetGame()
    {
    	for (Player p: players)  // Calls ResetGame() from the Player class
		{
			p.ResetGame();
		}       
    }
    
    private void createHands() 
    {
    	int playerStartCol = 30;
    	PlayerRow = defScreenHeight / 3;
    	
    	switch (numPlayers){
    	case 2: dealerCol = defScreenWidth / 5;
    			playerCol = defScreenWidth;
    			playerStartCol = (defScreenWidth / 3) + 30;
    			break;
    	case 3: dealerCol =	defScreenWidth / 7;
    			playerCol = defScreenWidth / 2;
    			playerStartCol = (defScreenWidth / 9);
    			toast("playerStartCol = " + Integer.toString(playerStartCol));
    			break;
    	case 4: dealerCol =	defScreenWidth / 9;
				playerCol = defScreenWidth / 3;
				break;
		default:
				dealerCol = 50;
				playerCol = 175;
    	}
    	
    	for (int i = 0; i < numPlayers - 1; i++) // create 'numPlayers' number of new players.
            players.add(new Player(playerStartCol + i * playerCol, PlayerRow, shoe, this, numPlayers - 1 == i, i, 
            layoutBot, layoutTop));

        // Dealer hand --
        players.add(new Player(numPlayers * dealerCol, DealerRow, shoe, this, true, numPlayers - 1, 
        		layoutBot, layoutTop));
	}
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	switch (item.getItemId()){
    	
    	case R.id.fast:
    		msInc = 50;
    		break;
    		
    	case R.id.medium:
    		msInc = 250;
    		break;
    		
    	case R.id.slow:
    		msInc = 500;
    		break;
    		
    	case R.id.onedeal:
    		numPlayers = 2;
    	    numHands = 2;
    	    ResetGame();
    	    players.clear();
    	    createHands();
    	    break;
    	    
    	case R.id.twodeals:
    		numPlayers = 3;
    	    numHands = 3;
    	    ResetGame();
    	    players.clear();
    	    createHands();
    	    break;
    	    
    	case R.id.threedeals:
    		numPlayers = 4;
    	    numHands = 4;
    	    ResetGame();
    	    players.clear();
    	    createHands();
    	    break;
    	    
    	case R.id.layoutexamples:
    		Intent i = new Intent(BlackJackActivity.this, LayoutExamples.class);
    		startActivity(i);
    		break;
    		
    	case R.id.androidchooser:
    		Intent ac = new Intent(BlackJackActivity.this, AndroidFileBrowser.class);
    		startActivity(ac);
    		break;
    		
    	case R.id.filechooser:
    		Intent fc = new Intent(BlackJackActivity.this, FileChooser.class);
    		startActivity(fc);
    		break;
    		default:
    			return super.onOptionsItemSelected(item);
    		
    	}
    	// SharedPreferences are an internal storage medium for saving various values for
    	// retrieval globally.  Persistent data is available even after application is killed.
    	SharedPreferences.Editor prefsEd = prefs.edit();
    	prefsEd.putInt("Speed", msInc);
    	prefsEd.commit();
    	return true;
    }
    
    public void toast(String text)
    {
    	Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    int dealCounter;

	private void dealCards()
	{
		shoe.newGame();  // Testing:  gameDeal is a Stack used for storing all dealt cards, to redeal
						 // if requested by the user/developer

		for (Player p: players)  // Loop through all players and execute code.
		{
			p.DiscardHands();  	 // Remove the playing cards from the screen.  And adds the cards to
							   	 // "discards" in Shoe class.
			p.MakeHand();
		}

		player = players.get(0);
		dealer = players.get(players.size() - 1);
		dealCounter = 0;
		handler.post(dealCard);
		
	}
	
	public Player Dealer()
	{
		return dealer;		
	}
	
	public Player curPlayer()
	{
		return player;
	}
	
	private Runnable dealCard = new Runnable() {
		public void run() {
			Player p = players.get(dealCounter++ % numHands);
			if (dealCounter <= numHands)
				p.DealOne();
			else
				p.DealTwo();
			if (dealCounter < numHands * 2)
				handler.postDelayed(this, msInc);  // This is a delay in milliseconds.
			else
				postDeal();
		}
	};

	private void postDeal() {
        handler.removeCallbacks(dealCard);
		dealer = players.get(players.size() - 1);
		dealerHand = dealer.curHand();
				
		if (dealerHand.isFirstAce())	// Offer insurance if dealer shows an Ace
			/*doInsurance()*/;
				
		if (dealerHand.isBlackjack())	// If dealer has Blackjack, do dealer stuff
		{
			doDealer();
			return;
		}

		// Mark hittable the first non-Blackjack hand.
		// If all hands are Blackjack hands, it's time for the dealer.		
		boolean anyhittable = false;
		curHandX = 0;
		for (Player p: players)
		{
			if (p == dealer || p.isPlayable())
			{
				player = p;
				break;
			}
			curHandX++;
		}
		if (curHandX == players.size() - 1)
			doDealer();
	}

	public void allowHitNextHand()
	{
		while (true)
		{
			Player p = players.get(++curHandX);
			if (p != dealer)
			{
				if (!p.isPlayable())
					continue;
				player = p;
			}
			else
				doDealer();
			break;
		}
	}
    
	  // Called after last player is dealt and BlackJacked, busted, or stayed.
	  private void doDealer()
	     {
		  	 int playerHdBustTot = 0;
		  	 player = dealer;
	         Hand h = dealerHand;
	         h.setFacedown(false);
	         h.ShowHand();

	         for(Player p : players)  // Check to see if all players busted and don't deal dealer if that's the case
	         {
	        	 if (p == dealer)
	        		 break;
	        	 playerHdBust = PlayerHandBust(p);
	        	 playerHdBustTot += (playerHdBust) ? 1 : 0;  // Add number of times true (or bust),
	        	 if(playerHdBustTot == players.size() - 1)   // if all hands true (or bust) then
	        		 playerHdBust = true;					 // playerHdBust is true
	        	 else
	        		 playerHdBust = false;
	         }
	         if(!playerHdBust)  // If all hands busted, don't deal dealer any more cards
		         while (dealerRulesHit()) // Process the dealer hand.
		             h.DealOne();
	         // Determine the dealer's results for use in Player.java EvaluateHand().
	         dealerHandResult = dealer.curHand().EvaluateHand();

			 for (Player p : players)  // Index through Player.java hands[] array to get player results
	         {
				if (p == dealer)
					break;
	            p.setDealerBlackJack(dealerBlackJack);
	            p.CalcWinnings(0, dealerHandResult); // Calculate who won and determine payouts or losses.
	            p.setDealerBlackJack(false); 
	         }
	         dealerBlackJack = false;
	         playerHdBust = false;
	     }
	  
	  private boolean PlayerHandBust(Player p)
	  {
		  playerHandResult = p.curHand().EvaluateHand();
		  if(playerHandResult > 21)
			  return true;  // Player busted, return true
		  else
			  return false;
	  }
	  
	  private boolean dealerRulesHit()
	     {
	         // Parse HandValue and hit on Hard 16 or lower, Soft 17 or lower
	         Hand h = dealerHand;
	         if (h.isBlackjack())
	             return false;

	         // Hit soft 17, hard 16
	         if (h.isSoft())
	             return (h.getHValue() + 10 <= 17);
	         else
	             return (h.getHValue() <= 16);
	     }
	  
	  public void SaveHands()
	  {
	      String path = "/data/data/blackjackdev.java/savedhands/savehands.txt";

	      try {
	          BufferedWriter file = new BufferedWriter(new FileWriter(path));
	          for (Player p: players)
	          {
	              p.SaveHands(file);
	          }
	          file.close();
	      } catch (IOException ex) {
	          ex.printStackTrace();
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
    			RelativeLayout.LayoutParams( layoutBot.getLayoutParams()
    					);
    		rl.topMargin = y;
    		rl.leftMargin = x;
    		return rl;
    	}
    
    	public RelativeLayout getLayout()
    	{
    		return layoutTop;
    	}
    	
    	 public void RedealHandsExact(String path)
    	 {
    	     String l;
    	     int cardsHit = 0;
    	     ArrayList<Integer> cardsHitArrayList = new ArrayList<Integer>();
    	     try 
    	     {
    	         BufferedReader file = new BufferedReader(new FileReader(path));
    	     
    	         while ((l = file.readLine()) != null)
    	         {
    	             if (l.length() < 3)
    	                 continue;
    	             if ((l.startsWith("hand")))
    	                 while (true)
    	                 {
    	                     l = file.readLine();
    	                     if(l == null)
    	                         break;
    	                     if (l.length() < 3)
    	                         continue;
    	                     if(l.startsWith("player"))
    	                         break;
    	                     cardsHit++;
    	                 }
    	             cardsHitArrayList.add(cardsHit); 
    	             cardsHit = 0;
    	         }
    	         
    	         file.close();
    	     } catch (IOException ex) {
    	         ex.printStackTrace();
    	     }
    	     dealCards();
    	     int x = 0;
    	     int y = 0;
    	     int i = 0;
    	     for (Player player: players)
    	     {
    	         i++;
    	         {
    	             y = Integer.valueOf(cardsHitArrayList.get(i).toString());
    	             y = x;
    	             while(x++ < Integer.valueOf(cardsHitArrayList.get(i).toString()))
    	             {
    	                 y = Integer.valueOf(cardsHitArrayList.get(i).toString());
    	                 if(x > 2)
    	                     player.ReHitCardsExact(); 
    	             }
    	         x = 0;
    	         player.ReHitCardsExactStay();
    	         }
    	     }
    	 }
}
