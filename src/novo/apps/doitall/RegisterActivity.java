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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import novo.apps.doitall.*;


public class RegisterActivity extends Activity {
	
	private GenTicket mygentask;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    	mygentask = new GenTicket(getApplicationContext());

    }


    public void onRegisterClick(View view) {
    	
    	
    	Log.d("Register","Register");
        
    }    


        
    public void onClick(View view) { 
    	Log.d("OnClick","OnClick");
    	ArrayMap<String,String> mymap = new ArrayMap<String,String>();
    	
        EditText account = (EditText) findViewById(R.id.accountname);
        EditText fullname = (EditText) findViewById(R.id.fullname);
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass1 = (EditText) findViewById(R.id.password1);
        EditText pass2 = (EditText) findViewById(R.id.password2);
    	mymap.put("account", account.getText().toString().trim());
    	mymap.put("email", email.getText().toString().trim());
    	mymap.put("fullname", fullname.getText().toString().trim());
    	mymap.put("passwordone", pass1.getText().toString().trim());
    	mymap.put("passwordtwo", pass2.getText().toString().trim());

    	
    	String selectuser = account.getText().toString().trim();
    	String selectauth  = mygentask.generateNewTicket();
    	mygentask.setCurrentUser(selectuser);
    	Log.d("NewTicket","user:|" + selectuser+"|");
    	Log.d("NewTicket","auth:|" + selectauth +"|");
    	
    	mymap.put("ticket", selectauth);
    	
    	Log.d("RegisterResult","mymap.count():" + mymap.size());
    	
    	HttpUtilsTask mytask =  new HttpUtilsTask("");
    	
    	mytask.setMapfields(mymap);
    	
    	mytask.execute("");
    	
    	
    	Log.d("RegisterResult","outing(2)");
        
    }    
    public void onCancelClick(View view) {
        
    	Log.d("SecondActivity","Set result");
//    	Intent i = new Intent();
//    	
//    	setResult(1, i);
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
    	        HttpPost httppost = new HttpPost(PrincipalActivity.URL_SERVER);
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
        	Log.d("Resultado","responseString:" + responseString );
        	
        	if (responseString.startsWith("Se ha enviado") ) {
       
        		
        		mygentask.saveTicket();
        		
            	Intent i = new Intent();
            	i.putExtra("selectuser", mygentask.getCurrentUser());
            	i.putExtra("selectauth", mygentask.getCurrentTicket());
            	setResult(1, i);
                finish();

        	}
        	
        	else {
	    	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"Error en los datos:" + responseString, Toast.LENGTH_LONG);
	    	toast.show();
	    	
        	}

        }

    }


    
}


