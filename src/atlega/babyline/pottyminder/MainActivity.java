package atlega.babyline.pottyminder;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Handler myHandler = new Handler();
	private long startTime = 0l;
	private long finalTime = 0l;
	private long timeInMillies = 0l;
	private long timeSwap = 0l;
	private TextView txtTimer;
	private int direction;
	private final int DIRECTION_UP = 0;
	private final int DIRECTION_DOWN = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        direction = DIRECTION_DOWN;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onStart(View view){
    	startTime = SystemClock.uptimeMillis();
    	myHandler.postDelayed(updateTimerMethod, 0);
    }
    
    public void onStop(View view){
    	timeSwap += timeInMillies;
    	myHandler.removeCallbacks(updateTimerMethod);
    }
    
    private Runnable updateTimerMethod = new Runnable() {
    	public void run() {

    	if (direction == DIRECTION_UP){
    		timeInMillies = SystemClock.uptimeMillis() - startTime;
    		finalTime = timeSwap + timeInMillies;
    	} else if (direction == DIRECTION_DOWN) {
    		timeInMillies = SystemClock.uptimeMillis() - startTime + 25000;
    		finalTime = timeSwap + timeInMillies;
    	}
    	
    	int seconds = (int) (finalTime / 1000);
    	int minutes = seconds / 60;
    	seconds = seconds % 60;
    	int milliseconds = (int) (finalTime % 1000);
    	txtTimer.setText(""+ minutes + ":"
    	+ String.format("%02d", seconds) + ":"
    	+ String.format("%03d", milliseconds));
    	myHandler.postDelayed(this, 0);
    	}

    	};
}
