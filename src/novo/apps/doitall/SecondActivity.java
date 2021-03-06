package novo.apps.doitall;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.xy.*;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {
	
	private ProgressDialog progress;
	private ImageView imagegraph;
    private String urlimage;
    private String graphtitle;
    private String alljson;
    private PieChart graficoPartidos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        urlimage = getIntent().getStringExtra("str1");
        graphtitle= getIntent().getStringExtra("str2");
                
        alljson= getIntent().getStringExtra("alljson");
        
        imagegraph = (ImageView) findViewById(R.id.imageGraph);
        Log.d("SecondActivity","urlimage:" + urlimage);
        
		progress = new ProgressDialog(this);
		TextView mytitle = (TextView) findViewById(R.id.graphTitle);
		mytitle.setText(graphtitle);
		
        
	  	new DownloadImageTask( (ImageView) imagegraph).execute(urlimage);

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
    
    public void	onBarsClick(View view) {
    	Log.d("BarActivity","Set result");
    	Intent i = new Intent("novo.apps.doitall.BarPlotExampleActivity");
       	
    	//i.putExtra("graphtype", "bars");
    	i.putExtra("alljson", alljson);
    	startActivity(i);
    	
    	
    }
    public void onPieClick(View view) {
        
    	Log.d("PieActivity","Set result");
    	Intent i = new Intent("novo.apps.doitall.SimplePieChartActivity");
   	
    	i.putExtra("graphtype", "pie");
    	i.putExtra("alljson", alljson);
    	startActivity(i);


    }    

    
    
    
    
    
    
    
    
    
    
}
