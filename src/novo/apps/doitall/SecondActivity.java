package novo.apps.doitall;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SecondActivity extends Activity {
	
	private ProgressDialog progress;
	private ImageView imagegraph;
    private String urlimage; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        //---get the data passed in using getStringExtra()---
        urlimage = getIntent().getStringExtra("str1");
        
//        Toast.makeText(this,urlimage, 
//            Toast.LENGTH_SHORT).show();

        //---get the data passed in using getIntExtra()---
                
        
        imagegraph = (ImageView) findViewById(R.id.imageGraph);
        Log.d("SecondActivity","urlimage:" + urlimage);
        
        
        
	  	new DownloadImageTask( (ImageView) imagegraph).execute(urlimage);

        //---get the Bundle object passed in---
        //Bundle bundle = getIntent().getExtras();

        //---get the data using the getString()---         
        //Toast.makeText(this, bundle.getString("str2"), 
        //    Toast.LENGTH_SHORT).show();

        //---get the data using the getInt() method---
        //Toast.makeText(this,Integer.toString(bundle.getInt("age2")), 
        //    Toast.LENGTH_SHORT).show();
        
        //---get the custom object passed in---
//        MyCustomClass obj = (MyCustomClass) getIntent().getSerializableExtra("MyObject");
 //       Toast.makeText(this, obj.Name(), Toast.LENGTH_SHORT).show();
 //       Toast.makeText(this, obj.Email(), Toast.LENGTH_SHORT).show();
    }


    final class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	  
		protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	        		Log.d("DownloadImage","urldisplay:" + urldisplay);
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);  
	    }
	}

        
    public void onClick(View view) {
        //---use an Intent object to return data---
        Intent i = new Intent();            

        //---use the putExtra() method to return some 
        // value---
        i.putExtra("age3", 45);

        //---use the setData() method to return some value---
        i.setData(Uri.parse(
            "http://www.learn2develop"));                            

        //---set the result with OK and the Intent object---
        setResult(RESULT_OK, i);   
        
        finish();
    }    
    
}
