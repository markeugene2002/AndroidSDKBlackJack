package blackjack.java;

import java.util.*;
import java.io.*;

public class Shoe {
    
    private ArrayList<PlayingCard> deck = new ArrayList<PlayingCard>();
    private ArrayList<PlayingCard> shoe = new ArrayList<PlayingCard>();
    private ArrayList<PlayingCard> discards = new ArrayList<PlayingCard>();
    private Stack<PlayingCard> gameDeal = new Stack<PlayingCard>();
    private boolean extDiscard = false;
    private Random rand = new Random();
    
    public Shoe(int ndecks)
    {
        MakeDeck();
        MakeShoe(ndecks);
    }
    
    public void newGame()
    {
    	gameDeal.clear();  // Testing:  gameDeal is a Stack used for storing all dealt cards, to redeal
                            // if requested by the user/developer
    }

    private void MakeDeck()
    {
        PlayingCard pc;
        for (Value v : Value.values())
            for (Suit s : Suit.values())
            {
                pc = new PlayingCard(s, v);
                deck.add(pc);
            }
    }
    private void MakeShoe(int ndecks)
    {
        ArrayList<PlayingCard> templist = new ArrayList<PlayingCard>();
        for (int i = 0; i < ndecks; i++)
            discards.addAll(deck);
        RandomizeDiscardsIntoShoe();
    }
    public void RandomizeDiscardsIntoShoe()  // Shuffle the deck/decks
    {
        int ct = discards.size();
        while (ct != 0)
            shoe.add(discards.remove(rand.nextInt(ct--)));
    }
    
    public PlayingCard GetCard()
    {
        PlayingCard card;
    
        if (shoe.size() == 0)
            return null;
        card = shoe.remove(0);
        if (!extDiscard)
            discards.add(card);
        if (shoe.size() < 15)
            RandomizeDiscardsIntoShoe();

        TestCards(shoe);
        gameDeal.push(card);
        return card;
    }
    
    public void reDeal()  // Testing:  Redeal the cards exactly as they were originally dealt.
    {
    	Stack<PlayingCard> tmpstack = (Stack<PlayingCard>) gameDeal.clone();
    	while (!tmpstack.empty())
    		shoe.add(0, tmpstack.pop());
    }
    public void DisCard(PlayingCard pc)
    {
	if (pc.IsTestCard())
		return;
        /* If first external use of DisCard(), clear discard list and
         * prevent GetCard() from adding to discards. */
       if (!extDiscard)
       {
           discards.clear();
           extDiscard = true;
       }
       discards.add(pc);  // Adds card to "discards" arraylist.  Where it is stored until deck is
                          // shuffled again in "RandomizeDiscardsIntoShoe();"
    }
    
    public int DiscardsCt()
    {
        return discards.size();
    }
    
    public int ShoeCt()
    {
        return shoe.size();
    }
    
    public void RedealHands(BufferedReader file)
    {
        String l;
	ArrayList<ArrayList<PlayingCard>> hands = new ArrayList<ArrayList<PlayingCard>>();
	ArrayList<PlayingCard> hand = null;
    
        try {
            while ((l = file.readLine()) != null)
            {
                if (l.length() < 3)
                    continue;
                if (l.startsWith("player")) {
                } else if (l.startsWith("hand")) {
                    hand = new ArrayList<PlayingCard>();
                    hands.add(hand);
                } else {
                    Value v;
                    Suit s;
                    try {
                        v = Value.valueOf(l.substring(0, l.indexOf(" ")));
                        s = Suit.valueOf(l.substring(1 + l.lastIndexOf(" ")));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Bad card: " + l);
                        return;
                    }
                    PlayingCard c = new PlayingCard(s, v);
                    c.SetTestCard(true);
                    hand.add(c);
                }
            }
            // Redeal all but first two cards, backwards
            for (int i = hands.size() - 1; i >= 0; i--)
                for (int j = hands.get(i).size() - 1; j >= 2; j--)
                    shoe.add(0, hands.get(i).remove(j));
            // Redeal the second dealt card, backwards
            for (int i = hands.size() - 1; i >= 0; i--)
                shoe.add(0, hands.get(i).remove(1));
            // Redeal the first dealt card, backwards
            for (int i = hands.size() - 1; i >= 0; i--)
                shoe.add(0, hands.get(i).remove(0));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    private void TestCards(List<PlayingCard> shoe)
    {
        int[] cc = new int[52];
        int[] totals = new int[10];
        
        for (int i = 0; i < 10; i++) totals[i] = 0;
    
        // In array cc[52] count how many cards of each
        // suit/value the shoe currently holds 
        for(PlayingCard pc : shoe)
            cc[pc.CardValue() + pc.CardSuit()*13]++;
        // Now make an array totals[10] to contain how many
        // card faces have N occurrences.  For instance, in a
        // three-deck shoe totals[3] will be 52 and the other
        // array elements of totals[] will be zero. 
        for (int j = 0; j < 52; j++)
            if (cc[j] < 10) totals[cc[j]]++;
        return;
    }
}
