package novo.apps.doitall;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

	private String selectuser;
	private String selectpass;
	private String selectauth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        selectuser = "";
        selectauth = "";
        selectpass = "";
        
        GenTicket mytask = new GenTicket(getApplicationContext());
        
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
		Log.d("requestCode:",String.valueOf(requestCode));
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
    }



	private void callPrincipal() {
		Intent i=new Intent("novo.apps.doitall.PrincipalActivity");
    	Log.d("LoginActivity","seluser:" + selectuser);
    	i.putExtra("selectuser", selectuser);
    	i.putExtra("selectauth", selectauth);
    	i.putExtra("selectpass", selectpass);
        startActivity(i);
	}
        
    public void onClick(View view) {            	    
    	EditText wselect = (EditText) findViewById(R.id.selectuser);    		
    	selectuser = wselect.getText().toString().trim();
    	
    	EditText wpass = (EditText) findViewById(R.id.selectpass);
    	selectpass = wpass.getText().toString().trim();
    	
    	callPrincipal();
    	
        
    }    
    public void onCancelClick(View view) {
        
    	Log.d("RegisterActivity","Set result");
        finish();
    }    
    
}
