package blackjack.java;
/*
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class CreateCard {
	private Hand myHand;
	
	public CreateCard(Hand h)
	{
		myHand = h;
	}
	
	public void getTheButton(BlackJackDevActivity that, Bitmap bm, PicBox p) {
		RelativeLayout layout = that.Layout();
		Context ctx = layout.getContext();

		//ibArray.add(b1);
		p.setImageBitmap(bm);
		p.setPadding(0, 0, 0, 0);
		p.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				myHand.Hand_Click('L');
				//doClick(arg0);
			}
		});
		layout.addView(p, getRL(p.GetX(), p.GetY()));
		((Activity) ctx).setContentView(layout);
	}
	
	private ImageButton getButton(Context ctx, Bitmap bm){
		ImageButton ib = new ImageButton(ctx);
		ib.setImageBitmap(bm);
		ib.setPadding(0, 0, 0, 0);
		ib.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				myHand.Hand_Click('L');
				//doClick(arg0);
			}
		});
		return ib;
	}
	
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
	
	private void doClick(View arg0)
	{
		//ImageButton ib = getButton(arg0.getContext());
		//layout.addView(ib, getRL());
	}
}
*/