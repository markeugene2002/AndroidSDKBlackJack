package blackjack.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import blackjack.java.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class AndroidFileBrowser extends ListActivity{
	 private List<String> items = null;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.file_browser);
	        getFiles(new File("/").listFiles());
	        

	    }	       
	    
	   public void onDestroy()
	   {
		   super.onDestroy();
	   }
	   
	   @Override
	    public void onStop()
        {  
		// After a pause OR at startup
        super.onStop();
        finish();
        }
	    
	    public void doShow()
	    {
	    	showDialog(1);
	    }
	    
	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id)
	    {
	        int selectedRow = (int)id;
	        if(selectedRow == 0)
	        {
	            getFiles(new File("/").listFiles());
	        }
	        else
	        {
	            File file = new File(items.get(selectedRow));
	            if(file.isDirectory()){
	                getFiles(file.listFiles());
	            }
	            else
	            {
	                 new AlertDialog.Builder(this)
	                 .setTitle("This file is not a directory")
	                 .setNeutralButton("OK", new DialogInterface.OnClickListener(){
	                     public void onClick(DialogInterface dialog, int button){
	                         //do nothing
	                     }
	                 })
	                 .show();
	            }
	        }
	    }

	    private void getFiles(File[] files)
	    {
	        items = new ArrayList<String>();
	        items.add("Goto Root");
	        for(File file : files)
	        {
	            items.add(file.getPath());
	        }
	        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.file_list_row, items);
	        setListAdapter(fileList);
	    }

	}