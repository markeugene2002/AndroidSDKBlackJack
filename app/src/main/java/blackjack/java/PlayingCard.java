

package blackjack.java;

import blackjack.java.R;

public class PlayingCard {



	/**
	 *
	 * @author Mark-Laptop
	 */

	private Suit suit;
	private Value value;
	private boolean testCard = false;

	public PlayingCard(Suit s, Value v)
	{
		suit = s;
		value = v;
	}

	public int CardSuit()
	{
		return this.suit.ordinal();
	}

	public Suit CardSuitEnum()
	{
		return this.suit;
	}

	public int CardValue()
	{
		return this.value.ordinal();
	}

	public Value CardValueEnum()
	{
		return this.value;
	}

	public void SetTestCard(boolean what)
	{
		testCard = what;
	}

	public boolean IsTestCard()
	{
		return testCard;
	}

	public String ToString()
	{
		String result = String.format("%s of %s", value, suit);
		return result;
	}

	public int ToRes()
	{
		switch (suit) {
			case Clubs:
				switch (value) {
					case Two:	return R.drawable.clubs_2;
					case Three:	return R.drawable.clubs_3;
					case Four:	return R.drawable.clubs_4;
					case Five:	return R.drawable.clubs_5;
					case Six:	return R.drawable.clubs_6;
					case Seven:	return R.drawable.clubs_7;
					case Eight:	return R.drawable.clubs_8;
					case Nine:	return R.drawable.clubs_9;
					case Ten:	return R.drawable.clubs_10;
					case Jack:	return R.drawable.clubs_j;
					case Queen:	return R.drawable.clubs_q;
					case King:	return R.drawable.clubs_k;
					case Ace:	return R.drawable.clubs_a;
				}
			case Diamonds:
				switch (value) {
					case Two:	return R.drawable.diamonds_2;
					case Three:	return R.drawable.diamonds_3;
					case Four:	return R.drawable.diamonds_4;
					case Five:	return R.drawable.diamonds_5;
					case Six:	return R.drawable.diamonds_6;
					case Seven:	return R.drawable.diamonds_7;
					case Eight:	return R.drawable.diamonds_8;
					case Nine:	return R.drawable.diamonds_9;
					case Ten:	return R.drawable.diamonds_10;
					case Jack:	return R.drawable.diamonds_j;
					case Queen:	return R.drawable.diamonds_q;
					case King:	return R.drawable.diamonds_k;
					case Ace:	return R.drawable.diamonds_a;
				}
			case Hearts:
				switch (value) {
					case Two:	return R.drawable.hearts_2;
					case Three:	return R.drawable.hearts_3;
					case Four:	return R.drawable.hearts_4;
					case Five:	return R.drawable.hearts_5;
					case Six:	return R.drawable.hearts_6;
					case Seven:	return R.drawable.hearts_7;
					case Eight:	return R.drawable.hearts_8;
					case Nine:	return R.drawable.hearts_9;
					case Ten:	return R.drawable.hearts_10;
					case Jack:	return R.drawable.hearts_j;
					case Queen:	return R.drawable.hearts_q;
					case King:	return R.drawable.hearts_k;
					case Ace:	return R.drawable.hearts_a;
				}
			case Spades:
				switch (value) {
					case Two:	return R.drawable.spades_2;
					case Three:	return R.drawable.spades_3;
					case Four:	return R.drawable.spades_4;
					case Five:	return R.drawable.spades_5;
					case Six:	return R.drawable.spades_6;
					case Seven:	return R.drawable.spades_7;
					case Eight:	return R.drawable.spades_8;
					case Nine:	return R.drawable.spades_9;
					case Ten:	return R.drawable.spades_10;
					case Jack:	return R.drawable.spades_j;
					case Queen:	return R.drawable.spades_q;
					case King:	return R.drawable.spades_k;
					case Ace:	return R.drawable.spades_a;
				}
		}
		return 0;
	}

}
