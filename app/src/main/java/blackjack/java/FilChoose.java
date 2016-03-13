package blackjack.java;

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class FilChoose extends Spinner {
	BlackJackActivity that;
	private String[] array_spinnerC = new String[4];
	private ArrayList<String> array_spinner = new ArrayList<String>();
	private boolean firstFired = false;
	private ListView lv;
	
	public FilChoose(RelativeLayout layout, Context context,  
			BlackJackActivity myThat) 
	{ 
		super(context);
		that = myThat;
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, 
				android.R.layout.simple_spinner_item, array_spinnerC);
		setAdapter(adapter);
		setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(firstFired)
				{
					String myFile = getSelectedItem().toString();
					that.toast("The file selected is: " + myFile);
					removeAllViewsInLayout();
				}
				firstFired = true;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				that.toast("No file was selected");
			}         //add some code here     
			} ); 
	}
    
	public void show(RelativeLayout layout)
	{
		
		for(int a = 0; a < 4; a++)
		{
			String myString = "";
			myString = Integer.toString(a);
			array_spinnerC[a] = myString;
			array_spinner.add(myString); 			
		}
		
		layout.addView(this, getRL(50,150));
		
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
