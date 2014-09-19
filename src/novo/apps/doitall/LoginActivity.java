package novo.apps.doitall;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import novo.apps.doitall.RegisterActivity.HttpUtilsTask;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import novo.apps.doitall.RegisterActivity;

public class LoginActivity extends Activity {

	private String selectuser;
	private String selectpass;
	private String selectauth;
	final public static int mId = 111;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        selectuser = "";
        selectauth = "";
        selectpass = "";
        
        
        GenTicket mytask = new GenTicket(getApplicationContext());
        
        Boolean from_notify = getIntent().getBooleanExtra("from_notify", false);
        
        if(from_notify) {
        	
        	Log.d("LOGIN_ACTIVITY","calling from_notify");
        	NotificationManager mNotificationManager =
        		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        	mNotificationManager.cancel(mId);
        	
        	
        }
        selectauth = mytask.readTicket();
        
        selectuser = mytask.getCurrentUser();
        
        Log.d("LoginActivity","user(read):|" + selectuser + "|");
        Log.d("LoginActivity","currticket (read):|" + selectauth + "|");
        if (!selectauth.isEmpty()) {

        	Log.d("LoginActivity","No Empty");
        	Log.d("LoginActivity","Calling Principal");
        	Log.d("LoginActivity","Deleting ticket");
        	
        	// mytask.deleteTicket();
        	callPrincipal();
        }

    }


    public void onRegisterClick(View view) {
    	
    	
    	Log.d("Register","Register");
    	try {
    		Intent i=new Intent("novo.apps.doitall.RegisterActivity");
    		startActivityForResult(i,1);
    	} catch(Exception e) {
	    	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"Ocurrio el siguiente error:" + e.getMessage(), Toast.LENGTH_SHORT);
	    	toast.show();
    	}

        
    }    
    
	public void onActivityResult(int requestCode, 
            int resultCode, Intent data)
    {
        //---check if the request code is 1---
		Log.d("**requestCode:",String.valueOf(requestCode));
		Log.d("resultCode:",String.valueOf(resultCode));
        if (resultCode == 1) {
        	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"Usuario agregado correctamente", Toast.LENGTH_SHORT);
	    	toast.show();
	    	selectuser = data.getStringExtra("selectuser");
	    	selectauth = data.getStringExtra("selectauth");
	    	Log.d("resultCode:selectuser",selectuser);
	    	Log.d("selectauth",selectauth);
	    	callPrincipal();
	    	
        }
        else if (resultCode == 2) {
        	Log.d("resultCode:","finalizing");
        	finish();
        }
        
    }



	private void callPrincipal() {
		Intent i=new Intent("novo.apps.doitall.PrincipalActivity");
    	Log.d("CallPrincipal","seluser:" + selectuser);
    	Log.d("CallPrincipal","selectauth:" + selectauth);
    	i.putExtra("selectuser", selectuser);
    	i.putExtra("selectauth", selectauth);
    	i.putExtra("selectpass", selectpass);
        startActivityForResult(i, 2 );
	}
        
    public void onClick(View view) {            	    
    	EditText wselect = (EditText) findViewById(R.id.selectuser);    		
    	selectuser = wselect.getText().toString().trim();
    	
    	EditText wpass = (EditText) findViewById(R.id.selectpass);
    	selectpass = wpass.getText().toString().trim();
    	
    	if (selectuser.isEmpty()) {
        	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"El nombre de usuario no puede estar vacìo", Toast.LENGTH_SHORT);
        	toast.show();
    		
    		
    		return;
    	}
    	if (selectpass.isEmpty()) {
        	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"La clave no puede ser vacía", Toast.LENGTH_SHORT);
        	toast.show();    		    		
    		return;
    	}

    	ArrayMap<String,String> mymap = new ArrayMap<String,String>();
    	mymap.put("username", selectuser);
    	mymap.put("password", selectpass);
    	
    	HttpUtilsTask mytask =  new HttpUtilsTask("login");
    	
    	mytask.setMapfields(mymap);
    	
    	mytask.execute("");

//    	callPrincipal();
    	
        
    }    
    public void onCancelClick(View view) {
        
    	Log.d("RegisterActivity","Set result");
        finish();
    }    
    final public class  HttpUtilsTask extends AsyncTask<String, Void, String> {
    	
    	
    	private ArrayMap<String,String> mapfields;
    	private String responseString;

    	
    	public HttpUtilsTask(String c) {
    		
            super();
            responseString = "";

        }

    	 
    	 public ArrayMap<String, String> getMapfields() {
    		return mapfields;
    	}

    	public void setMapfields(ArrayMap<String, String> mapfields) {
    		this.mapfields = mapfields;
    	}

    	public   String callPlainSAFETAPI()  {
    		 
    		   
    		    Log.d("HttpUtils",".callPlainSAFETAPI...entering(1)");
    		    
    	        HttpClient httpclient = new DefaultHttpClient();
    	        HttpPost httppost = new HttpPost(PrincipalActivity.URL_SERVER_LOGIN);
    	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(mapfields.size());
    	        for(String k: mapfields.keySet()) {		        	 
    	        		String currentText = "";
    	             nameValuePairs.add(new BasicNameValuePair(k, mapfields.get(k))); 
    	        }
    	        try {
    	        	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	        }
    	        catch(UnsupportedEncodingException e ) {
    	        	 Log.d("ERROR", e.getMessage());
    	        }
    	        
    	        HttpResponse response;
    	        
    	        
    	        try {
    	        	
    	            response = httpclient.execute(httppost);
    	            StatusLine statusLine = response.getStatusLine();
    	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
    	                ByteArrayOutputStream out = new ByteArrayOutputStream();
    	                response.getEntity().writeTo(out);
    	                out.close();
    	                responseString = out.toString();
    	            } else{
    	                //Closes the connection.
    	                response.getEntity().getContent().close();
    	                throw new IOException(statusLine.getReasonPhrase());
    	            }
    	        } catch (ClientProtocolException e) {
    	        	Log.d("ClientError","Client");
    	        
    	        } catch (IOException e) {
    	        	Log.d("IOException","Client");
    	        
    	        
    	 		} catch (Exception e) {
    	 			Log.d("Exception","Client");
    	 			
    	 		}
    	        return responseString;
    	    }

    	@Override
    	protected String doInBackground(String... params) {
    		// TODO Auto-generated method stub
    		
    		callPlainSAFETAPI();
    		return null;
    	} 

        protected void onPostExecute(String result) {
        	Log.d("Resultado","Hecho");
        	Log.d("Resultado","*responseString:" + responseString );
        	
        	if (responseString.isEmpty()) {
            	Toast toast = Toast.makeText(getApplicationContext(), 
    	    			"Usuario/Clave érronea", Toast.LENGTH_SHORT);

            	toast.show();
        		return;
        	}
        	JSONObject jall = null;
        	String myticket = "";
        	try {        	
        		jall = new JSONObject(responseString);
        		myticket = jall.getString("ticket");
        		
        	}
        	catch(Exception e) {
        		Toast toast = Toast.makeText(getApplicationContext(), 
    	    			"Error en la conexión:" + e.getMessage(), Toast.LENGTH_SHORT);
        		toast.show();
        		return;
        	}
        	
        	
        	
        	GenTicket mygentask = new GenTicket(getApplicationContext());
        	String myuser = mapfields.get("username");
	    	selectuser = myuser;	    	
	    	selectauth = myticket;
	    	selectpass = mapfields.get("password");

        	Log.d("Resultado","myuser:" + myuser);
        	Log.d("Resultado","Ticket:" + myticket);
        	mygentask.setCurrentUser(myuser);
        	mygentask.setCurrentTicket(myticket);
        	mygentask.saveTicket();
        	
	    	Log.d("user selectuser",selectuser);
	    	Log.d("user selectauth",selectauth);
	    	Log.d("user selectpass",selectpass);
	    	callPrincipal();
       	

        }

    }


    
}
