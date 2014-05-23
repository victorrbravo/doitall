package novo.apps.doitall;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import novo.apps.doitall.SecondActivity.DownloadImageTask;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewTicketActivity extends Activity {
	
	public String dataform;
	String resulting;
	//Button closebutton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ticket);
      //  closebutton = (Button) findViewById(R.id.buttonclose);
        resulting = getIntent().getStringExtra("resulting");
        
        Log.d("ViewTicketActivity","Create..resulting:"+resulting);
        
        processResulting();
    
    }
    
    public void onClick(View view) {
        
        finish();
    }
    
    protected void processResulting() {
    	TextView summary = (TextView) findViewById(R.id.text_summary);
    	TextView desc = (TextView) findViewById(R.id.text_desc);
    	TextView project = (TextView) findViewById(R.id.text_project);
    	TextView type = (TextView) findViewById(R.id.text_type);
    	TextView tentative = (TextView) findViewById(R.id.text_tentative);
    	TextView finishdate = (TextView) findViewById(R.id.text_finishdate);
    	TextView owner = (TextView) findViewById(R.id.text_owner);
    	TextView status = (TextView) findViewById(R.id.text_status);
    	
		try {	
			JSONObject jall = new JSONObject(resulting);

			JSONArray jArray = jall.getJSONArray("safetlist");
			for(int i=0;i<jArray.length();i++){

				JSONObject json_data = jArray.getJSONObject(i);
				TicketRecord ticket  = new TicketRecord();
				//String summary = URLDecoder.decode(json_data.getString("resumen"), "UTF-8");
				SpannableString spanString = new SpannableString("Resumen: " + json_data.getString("resumen"));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, 0);				
				summary.setText(spanString);
				spanString = new SpannableString("Descripción: " + json_data.getString("descripcion"));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, 0);
				desc.setText(spanString);
				spanString = new SpannableString("Tipo de tarea: " + json_data.getString("tipo"));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 13, 0);				
				type.setText(spanString);
				spanString = new SpannableString("Proyecto al que pertenece: " + json_data.getString("proyecto"));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 25, 0);								
				project.setText(spanString);
				spanString = new SpannableString("Estado de la tarea: " + json_data.getString("status"));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 18, 0);												
				status.setText(spanString);
				spanString = new SpannableString("Propietario: " + json_data.getString("propietario"));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, 0);																
				owner.setText(spanString);
				spanString = new SpannableString("Fecha planificada:" 
						+ PrincipalActivity.convertDateEpochToFormat(json_data.getString("tentativedate")));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 17, 0);																
				
				tentative.setText(spanString);
				spanString = new SpannableString("Fecha de completación:" + 
						PrincipalActivity.convertDateEpochToFormat(json_data.getString("finishdate")));
				spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 21, 0);																				
				finishdate.setText(spanString);				

			}

		} catch(Exception e) {
			Log.e("JSON", "Ocurrio el error:"+ e.toString());
			//e.printStackTrace();

		}

    	    	
    }
    


}
