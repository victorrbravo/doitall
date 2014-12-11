package novo.apps.doitall;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.mikephil.charting.charts.LineChart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.interfaces.OnChartGestureListener;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.LimitLine;
import com.github.mikephil.charting.utils.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.utils.XLabels;
import java.util.ArrayList;






public class WebActivity  extends FragmentActivity  implements OnSeekBarChangeListener,
OnChartGestureListener, OnChartValueSelectedListener {
	
	private ProgressDialog progress;
	private ImageView imagegraph;
    private String urlimage;
    private String graphtitle;
    private String alljson;
    private LineChart mChart;
    private TextView tvX, tvY;

    private SeekBar mSeekBarX, mSeekBarY;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        Log.d("WebActivity",".....1....");
        setContentView(R.layout.activity_web);
        

        Log.d("WebActivity",".....2....");


        urlimage = getIntent().getStringExtra("str1");
        graphtitle= getIntent().getStringExtra("str2");
                
        alljson= getIntent().getStringExtra("alljson");
        Log.d("WebActivity",".....3....");
        mChart = (LineChart) findViewById(R.id.chart1);        

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);

        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);
        
        Log.d("WebActivity",".....4....mchart = null:" + String.valueOf(mChart == null));
        
        setData(45, 100);
        mChart.setStartAtZero(false);
     // disable the drawing of values into the chart
        mChart.setUnit(" U");
        mChart.setDrawUnitsInChart(true);
        // enable value highlighting
        mChart.setHighlightEnabled(true);
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        
        
     mChart.setDrawYValues(false);
     mChart.setDrawBorder(true);
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");
        Log.d("WebActivity",".....5....");
        //mChart.animateX(2500);


Legend l = mChart.getLegend();
// modify the legend ...
// l.setPosition(LegendPosition.LEFT_OF_CHART);
l.setForm(LegendForm.LINE);

        

    }
    
    private void setData(int count, float range) {
    	ArrayList<String> xVals = new ArrayList<String>();
    	for (int i = 0; i < count; i++) {
    	xVals.add((i) + "");
    	}
    	ArrayList<Entry> yVals = new ArrayList<Entry>();
    	for (int i = 0; i < count; i++) {
    	float mult = (range + 1);
    	float val = (float) (Math.random() * mult) + 3;// + (float)
    	// ((mult *
    	// 0.1) / 10);
    	yVals.add(new Entry(val, i));
    	}
    	// create a dataset and give it a type
    	LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
    	// set1.setFillAlpha(110);
    	// set1.setFillColor(Color.RED);
    	// set the line to be drawn like this "- - - - - -"
    	set1.enableDashedLine(10f, 5f, 0f);
    	set1.setColor(Color.BLACK);
    	set1.setCircleColor(Color.BLACK);
    	set1.setLineWidth(1f);
    	set1.setCircleSize(4f);
    	set1.setFillAlpha(65);
    	set1.setFillColor(Color.BLACK);
    	// set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
    	// Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));
    	ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    	dataSets.add(set1); // add the datasets
    	// create a data object with the datasets
    	LineData data = new LineData(xVals, dataSets);
    	LimitLine ll1 = new LimitLine(130f);
    	ll1.setLineWidth(4f);
    	ll1.enableDashedLine(10f, 10f, 0f);
    	ll1.setDrawValue(true);
    	ll1.setLabelPosition(LimitLabelPosition.RIGHT);
    	LimitLine ll2 = new LimitLine(-30f);
    	ll2.setLineWidth(4f);
    	ll2.enableDashedLine(10f, 10f, 0f);
    	ll2.setDrawValue(true);
    	ll2.setLabelPosition(LimitLabelPosition.RIGHT);
    	data.addLimitLine(ll1);
    	data.addLimitLine(ll2);
    	// set data
    	mChart.setData(data);
    	}
    
	private void loadData() {
		// TODO Auto-generated method stub
		
	}










	final class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	ImageView bmImage;

	    
    	public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

		 protected void onPreExecute() {
			  progress.setMessage(getString(R.string.downloading));
		      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		      progress.setIndeterminate(true);
		      progress.show();
           
        }

	    
		protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        progress.setProgress(10);
	        try {
	        		Log.d("DownloadImage","urldisplay:" + urldisplay);
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        progress.setProgress(50);
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);  
	    	progress.setProgress(100);
	    	progress.dismiss();
   	
	    	

	    }
	}

        
    public void onClick(View view) {
         
    	Log.d("SecondActivity","Set result");
    	Intent i = new Intent();
    	
    	setResult(1, i);
        finish();
    }

	@Override
	public void onValueSelected(Entry e, int dataSetIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartLongPressed(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartDoubleTapped(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartSingleTapped(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		
	}

	 @Override
	 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	 tvX.setText("" + (mSeekBarX.getProgress() + 1));
	 tvY.setText("" + (mSeekBarY.getProgress()));
	 Log.i("SetData","SetData....1...");
	 setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
	 // redraw
	 mChart.invalidate();
	 Log.i("SetData","SetData....invalidate...");
	 }

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}    
    
        
    
    
    
    
    
    
    
    
    
}
