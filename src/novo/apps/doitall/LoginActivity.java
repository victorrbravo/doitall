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
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        

    }



        
    public void onClick(View view) {            	    
    	Spinner wselect = (Spinner) findViewById(R.id.selectuser);    		
    	String selectuser = wselect.getSelectedItem().toString();
    	Log.d("LoginActivity","seluser:" + selectuser);
    	Intent i=new Intent("novo.apps.doitall.PrincipalActivity");
    	Log.d("LoginActivity","seluser:" + selectuser);
    	i.putExtra("selectuser", selectuser);
        startActivity(i);
        
    }    
    public void onCancelClick(View view) {
        
    	Log.d("SecondActivity","Set result");
//    	Intent i = new Intent();
//    	
//    	setResult(1, i);
        finish();
    }    
    
}
