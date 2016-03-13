package blackjack.java;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback {

	public DrawingPanel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override 
    public void onDraw(Canvas canvas) { 
        //do drawing stuff here.
    } 
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	class PanelThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private DrawingPanel _panel;
        private boolean _run = false;


        public PanelThread(SurfaceHolder surfaceHolder, DrawingPanel panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }


        public void setRunning(boolean run) { //Allow us to stop the thread
            _run = run;
        }


        @Override
        public void run() {
            Canvas c;
            while (_run) {     //When setRunning(false) occurs, _run is 
                c = null;      //set to false and loop ends, stopping thread


                try {


                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {


                    //Insert methods to modify positions of items in onDraw()
                    postInvalidate();


                    }
} finally {
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
}
