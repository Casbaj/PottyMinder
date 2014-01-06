package atlega.babyline.pottyminder;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Handler myHandler = new Handler();
	private long startTime = 0l;
	private long finalTime = 0l;
	private long timeInMillies = 0l;
	private long timeSwap = 0l;
	private TextView txtTimer;
	private BubbleTextView bubbleTimer;
	private int direction;
	private final int DIRECTION_UP = 0;
	private final int DIRECTION_DOWN = 1;
	private GraphicalView mGraphView;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bubbleTimer = (BubbleTextView) findViewById(R.id.bubbleTextView1);
		bubbleTimer.setTimer(0, 0, 0);
		direction = DIRECTION_DOWN;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        LinearLayout layout = (LinearLayout) findViewById(R.id.chartLayout);
        if (mGraphView == null) {
            initGraph();
            addSampleData();
            mGraphView = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer, 0.3f);
            mGraphView.setBackgroundColor(Color.TRANSPARENT);
            mGraphView.setPadding(0, 0, 0, 0);
            layout.addView(mGraphView);
        } else {
        	mGraphView.repaint();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onStart(View view) {
		startTime = SystemClock.uptimeMillis();
		myHandler.postDelayed(updateTimerMethod, 0);
	}

	public void onStop(View view) {
		timeSwap += timeInMillies;
		myHandler.removeCallbacks(updateTimerMethod);
	}

	private Runnable updateTimerMethod = new Runnable() {
		public void run() {

			if (direction == DIRECTION_UP) {
				timeInMillies = SystemClock.uptimeMillis() - startTime;
				finalTime = timeSwap + timeInMillies;
			} else if (direction == DIRECTION_DOWN) {
				timeInMillies = SystemClock.uptimeMillis() - startTime + 25000;
				finalTime = timeSwap + timeInMillies;
			}

			int seconds = (int) (finalTime / 1000);
			int minutes = seconds / 60;
			int hours = minutes % 60;
			seconds = seconds % 60;
			int milliseconds = (int) (finalTime % 1000);
			
			bubbleTimer.setTimer(hours, minutes, seconds);
			
			/*bubbleTimer.setText("" + minutes + ":"
					+ String.format("%02d", seconds) + ":"
					+ String.format("%03d", milliseconds));*/
			
			myHandler.postDelayed(this, 0);
		}

	};

	private void initGraph(){
        mCurrentSeries = new XYSeries("Sample Data");
        mDataset.addSeries(mCurrentSeries);
        mCurrentRenderer = new XYSeriesRenderer();
        mRenderer.addSeriesRenderer(mCurrentRenderer);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
        mRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01, 0x01));
        mRenderer.setLabelsColor(Color.BLUE);
        mRenderer.setAxesColor(Color.BLUE);
        mRenderer.setLabelsTextSize(10);
        mRenderer.setXLabelsColor(Color.BLUE);
        mRenderer.setYLabelsColor(0, Color.BLUE);
	}
	
    private void addSampleData() {
        mCurrentSeries.add(1, 2);
        mCurrentSeries.add(2, 3);
        mCurrentSeries.add(3, 2);
        mCurrentSeries.add(4, 5);
        mCurrentSeries.add(5, 4);
    }
}
