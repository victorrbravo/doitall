package novo.apps.doitall;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyProjectActivity extends Activity {
	
	private Spinner proyectos;
	
    private String urlimage;
    private ArrayList<ProjectRecord> projects;
    private String urlform;
    private String dataticket;
    private String currentticket;
    private Spinner myselect;
    private DatePicker tentativepicker;
    private ProgressDialog progress;
    ArrayList<String> states;
    ProjectAdapter myadapter;
	private boolean addticket;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_project);
        
        projects = (ArrayList<ProjectRecord>) getIntent().getSerializableExtra("projects");

        
        Log.d("ModifyProjectActivity","count:" + String.valueOf(projects.size()));
        
                
        //tentativepicker.setCalendarViewShown(true);
        states = new ArrayList<String>();
        for(int i=0;i<projects.size();i++){
//        	states.add(projects.get(i).getTitle());
        	Log.d("ModifyProjectActivity","project:" + projects.get(i).getTitle());
        }

        ListView mylist = (ListView) findViewById(R.id.listCheckProject);
        
//      Spinner myselecttype = (Spinner) findViewById(R.id.selecttypeticket);
      
		
      myadapter = 
	            new ProjectAdapter(this);
      
      myadapter.setProjects(projects);
		

      mylist.setAdapter(myadapter);
      
//		myadapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1,states);
		
//		myselect.setAdapter(myadapter);
//		myadapter.notifyDataSetChanged();
			
      mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
          @Override  
          public void onItemClick( AdapterView parent, View item,   
                                   int position, long id) {
        
        	  Log.d("item select","item select + pos:" + String.valueOf(position));
        	  
        	  //ProjectAdapter.ViewContainer1 holder = (ProjectAdapter.ViewContainer1) item;
        	  
        	  
        	  //Toast.makeText(getApplicationContext(), projects.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        	  
              
          }  
        });
      
      
       
    }
    
    

    


    
                

            
    public void onClick(View view) {
        //---use an Intent object to return data---
        Intent i = new Intent();            

        //---use the putExtra() method to return some 
        // value---
        
       // String T1 = "T1 descripcion: T2  proyecto: T3  tipo: T4 tentativedate: 1401510540";
        
        finish();
    }    
    public void onClickCancel(View view) {
        //---use an Intent object to return data---
               
        finish();
    }    
    
}
