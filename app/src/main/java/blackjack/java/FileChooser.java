package blackjack.java;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooser extends Activity 
{
    private ListView lv;
    // ArrayList to store file names to populate ListView
    private ArrayList<String> arrayOfFilenames = new ArrayList<String>();
    
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	Button button1 = new Button(this);
    	ListView lv = new ListView(this);
        setContentView(R.layout.fb);
        lv = (ListView) findViewById(R.id.listView1);
        button1 = (Button) findViewById(R.id.button1);
        
        arrayOfFilenames.add("foo");
        arrayOfFilenames.add("bar");
        
        // Call method to populate "arrayOfFilenames" ArrayList
        getFilesForList();
        
        // This is the array adapter, it takes the context of the activity as a first 
        // parameter, the type of ListView as a second parameter and your array as a third parameter
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
        		 arrayOfFilenames);

        lv.setAdapter(arrayAdapter); 
        lv.setOnItemClickListener(new ListView.OnItemClickListener() 
 		{
        	public void onItemClick(AdapterView<?> arg0, View arg1,
 					int arg2, long arg3) {
 				
        		String myFile = arg0.getItemAtPosition(arg2).toString();
				
				finish();
 				toast("The file selected is: " + myFile);
 				clearArray();
 				
 			}

 			public void onNothingSelected(AdapterView<?> arg0) {
 				// TODO Auto-generated method stub
 				
 			}         //add some code here     
 			} ); 
        
        button1.setBackgroundResource(R.drawable.cardback);
        button1.setOnClickListener(new OnClickListener() {
 			public void onClick(View arg0)
 			{
 				// Go back to previous activity
 				finish();
 			}
 		});
    }

    	private void clearArray()
	    {
	    	arrayOfFilenames.clear();
	    }    
        
        // Method to populate "arrayOfFilenames" ArrayList with file names from directory
        private void getFilesForList()
        {
	        String path = "DCIM/";
	        File sdCardRoot = Environment.getExternalStorageDirectory();
			File yourDir = new File(sdCardRoot, path);
			for (File f : yourDir.listFiles()) 
			{
			    if (f.isFile())
			    {
			        String name = f.getName();
			        // Do your stuff
			        arrayOfFilenames.add(name);
			    }
			}
        }
        
	    public void toast(String text)
	    {
	    	Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	    }
    
}

