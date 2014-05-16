package novo.apps.doitall;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import novo.apps.doitall.PrincipalActivity.GetGraphTask;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddTicketActivity extends Activity {
	
	private Spinner proyectos;
	
    private String urlimage;
    private ArrayList<ProjectRecord> projects;
    private String urlform;
    private Spinner myselect;
    private DatePicker tentativepicker;
    private ProgressDialog progress;
    ArrayList<String> states;
	ArrayAdapter<String> myadapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ticket);
        projects = (ArrayList<ProjectRecord>) getIntent().getSerializableExtra("projects");

        tentativepicker = (DatePicker) findViewById(R.id.tentativedate);
        
        //tentativepicker.setCalendarViewShown(true);
        
        Log.d("projects AddTicket",String.valueOf(projects.size()));

        myselect = (Spinner) findViewById(R.id.selectproject);
        states = new ArrayList<String>();
        
//        Spinner myselecttype = (Spinner) findViewById(R.id.selecttypeticket);
        

        for(int i=0;i<projects.size();i++){
        	states.add(projects.get(i).getTitle());
        }

		
		myadapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1,states);
		
		
		myselect.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();
		
		
		urlform = "operacion:agregar_ticket%20resumen:";
		
		 
		progress = new ProgressDialog(this);
		
		addListenerButton();
        
    }

	final class GetGraphTask extends AsyncTask<String, Void, String> {

		private String consult;
		private String resulting;
		
		public GetGraphTask(String c) {
			
	        super();
	        consult = c;
	    }
		
		 protected void onPreExecute() {
			  progress.setMessage(getString(R.string.downloading));
		      progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		      progress.setIndeterminate(true);
		      progress.show();
            
         }
	  
		 
		private  String callSAFETAPI(String urldisplay) {
			String result = "";
			 URL url;
			    try {
			        //url = new URL("https://gestion.cenditel.gob.ve/intranet/api/f3bf4ca25e666d70d6f847b87f448fefba5f2fda/?tipoaccion=console&aplicacion=victorrbravo&accion=operacion:Generar_grafico_coloreado%20Cargar_archivo_flujo:/home/victorrbravo/.safet/flowfiles/flujogeneralsesiones.xml%20configurekey.Plugins.Graphviz/plugins.graphviz.graphtype:png");
			        url = new URL(urldisplay);
			        //		url = new URL("http://www.google.com");
			        
			        progress.setProgress(10);
			        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			                return null;
			            }
			            public void checkClientTrusted(X509Certificate[] certs, String authType) {
			            }
			            public void checkServerTrusted(X509Certificate[] certs, String authType) {
			            }
			        } };
			        // Install the all-trusting trust manager
			        final SSLContext sc = SSLContext.getInstance("SSL");
			        sc.init(null, trustAllCerts, new java.security.SecureRandom());
			        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			        // Create all-trusting host name verifier
			        HostnameVerifier allHostsValid = new HostnameVerifier() {
			            public boolean verify(String hostname, SSLSession session) {
			                return true;
			            }
			        };

			        // Install the all-trusting host verifier
			        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			        
			        HttpsURLConnection urlConnection = (HttpsURLConnection) url
			                .openConnection();

			        InputStream in = urlConnection.getInputStream();
			        
			        BufferedReader reader =new BufferedReader(new InputStreamReader(in, "Latin-1"));
			        String webPage = "",data="";
			        while ((data = reader.readLine()) != null){
			           webPage += data;
			        }
			        

			        result = webPage;
			        Log.d("callSAFETAPI","result:|"+ webPage + "|");
			    } catch (Exception e) {
			        // TODO Auto-generated catch block
			    	Log.e("LLamada a SAFET",e.toString());
			      
			    }
			    
			return result;
		}
			    
		protected String doInBackground(String... urls) {
			String urldisplay = urls[0];



			resulting = callSAFETAPI(urldisplay);
			progress.setProgress(50);
			if (consult.contentEquals("agregar_proyecto")) {
				String myconsult = PrincipalActivity.URL_API 
						+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/proyectos.xml"+
		    			"%20Variable:vProyectos";
				resulting = callSAFETAPI(myconsult);
				consult = "agregar_proyecto_relistar_proyectos";
			}
			else if (consult.contentEquals("borrar_proyecto")) {
				String myconsult = PrincipalActivity.URL_API 
						+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/proyectos.xml"+
		    			"%20Variable:vProyectos";
				resulting = callSAFETAPI(myconsult);
				consult = "borrar_proyecto_relistar_proyectos";
			}

			Log.d("AddTicket resulting", resulting);
			if ( consult.endsWith("listar_proyectos") ) {		
				
				projects.clear();
				
				try {	
					JSONObject jall = new JSONObject(resulting);

					JSONArray jArray = jall.getJSONArray("safetlist");
					for(int i=0;i<jArray.length();i++){

						JSONObject json_data = jArray.getJSONObject(i);
						ProjectRecord  project = new ProjectRecord();
						//String summary = URLDecoder.decode(json_data.getString("resumen"), "UTF-8");
						String projectid = json_data.getString("projectid");
						String title = json_data.getString("title");
						String desc = json_data.getString("description");
						String type = json_data.getString("type");
						
						project.setProjectid(projectid);
						project.setDescription(desc);
						project.setTitle(title);
						project.setType(type);
						
						urlimage = "";
						projects.add(project);
					}
					states.clear();
			        for(int i=0;i<projects.size();i++){
			        	states.add(projects.get(i).getTitle());
			        }


				} catch(Exception e) {
					Log.e("JSON", "Ocurrio el error:"+ e.toString());
					//e.printStackTrace();

				}
			}
		
			//new DownloadImageTask( (ImageView) imagegraph).execute(mygraph);

			//Intent openBrowser =  new Intent(Intent.ACTION_VIEW, Uri.parse(mygraph));
			//	startActivity(openBrowser);
	        return resulting;
	    }
		
		

	    protected void onPostExecute(String result) {
	    	Log.d("Resultado","Hecho");
	    	

	    	progress.setProgress(100);
	    	progress.dismiss();
	    	
	    	Log.d("GetGraphTask result",consult);
	        Log.d("ProjectCount:",String.valueOf(projects.size()));
	        
	    	if (consult.startsWith("agregar_proyecto")) {
	    		
	    		myadapter.notifyDataSetChanged();
	    		
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Se agregó el proyecto a su lista", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    	
	    		
	    	}
	    	else if (consult.startsWith("borrar_proyecto")) {
	    		myadapter.notifyDataSetChanged();
	    		
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Se eliminó el proyecto de la lista", Toast.LENGTH_SHORT);
		    	toast.show();
	    		
	    	}

	    	else {
	    		Log.d("else","else");
	    	}
	          
	    }
	}

    
    public void makeAddProjectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setMessage(getString(R.string.add_project_name));
		final EditText input = new EditText(this);
		builder.setView(input);


		builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here

		    	
		    	String title  = input.getText().toString().trim();
				String myconsult = PrincipalActivity.URLFORM_API + 
						"operacion:agregar_proyecto Titulo:"+ title +" descripcion: "+ title +" tipo:General";
				
				myconsult = myconsult.replace(" ","%20");
				Log.d("agregar proyecto...consult:","|"+myconsult+"|");
				
				new GetGraphTask("agregar_proyecto").execute(myconsult);
				
//
		    	Log.d("makeDeleteOptionsDialog","Yes");
		        dialog.dismiss();
		    }

		});

		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // I do not need any action here you might
		    	Log.d("makeDeleteOptionsDialog","no");
		        dialog.dismiss();
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

    public void makeDeleteProjectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.delete_project_dialog));
		String currproject = myselect.getSelectedItem().toString();
		
		String text = getString(R.string.delete_project_text) + " \"" + currproject + "\"?";
		builder.setMessage(text);



		builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here
		    	int pos = myselect.getSelectedItemPosition();
		    	String projectid = String.valueOf(projects.get(pos).getProjectid());
		    	

				String myconsult = PrincipalActivity.URLFORM_API + 
						"operacion:borrar_proyecto id:" + projectid;
				Log.d("borrar_proyecto...consult:","|"+myconsult+"|");
				
				myconsult = myconsult.replace(" ", "%20");
				
			   new GetGraphTask("borrar_proyecto").execute(myconsult);
//
		    	Log.d("makeDeleteOptionsDialog","Yes");
		        dialog.dismiss();
		    }

		});

		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // I do not need any action here you might
		    	Log.d("makeDeleteOptionsDialog","no");
		        dialog.dismiss();
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

    
    
    private void addListenerButton() {
		ImageButton addbutton = (ImageButton) findViewById(R.id.buttonaddproject);
		ImageButton delbutton = (ImageButton) findViewById(R.id.buttondeleteproject);
		
		addbutton.setOnClickListener(new OnClickListener() {
						
				
			@Override
			public void onClick(View view) {
				Log.d("add project","project 1");
				makeAddProjectDialog();
								
			}
		});
				
		delbutton.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View view) {
				Log.d("delete project","project 1");
				makeDeleteProjectDialog();
			}
		});

    	
    }

        
    public void onClick(View view) {
        //---use an Intent object to return data---
        Intent i = new Intent();            

        //---use the putExtra() method to return some 
        // value---
        
       // String T1 = "T1 descripcion: T2  proyecto: T3  tipo: T4 tentativedate: 1401510540";
        
        EditText summary = (EditText) findViewById(R.id.summary);
        EditText desc = (EditText) findViewById(R.id.desc);
        Spinner typeproject = (Spinner) findViewById(R.id.selectproject);
        Spinner typeticket = (Spinner) findViewById(R.id.selecttypeticket);
        
        

        int year = tentativepicker.getYear();
        Log.d("(1)Date year:",String.valueOf(year));
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(year,tentativepicker.getMonth(),tentativepicker.getDayOfMonth(),23,59,59);
        
        Log.d("Date",calendar.toString());
        
        Log.d("(2)Date:",calendar.toString());
        String seldate = String.valueOf(calendar.getTimeInMillis()/1000) ;
        urlform = urlform + summary.getText().toString().trim();
        urlform = urlform + "%20descripcion:" + desc.getText().toString().trim();
        int pos = typeproject.getSelectedItemPosition();
        urlform = urlform + "%20proyecto:" + String.valueOf(projects.get(pos).getProjectid());
        pos = typeticket.getSelectedItemPosition();
        urlform = urlform + "%20tipo:" + typeticket.getSelectedItem().toString();
        urlform = urlform + "%20Fecha_tentativa:" + seldate;
        
        
        
        
        
        urlform = urlform.replace(" ", "%20");
        
        
        Log.d("addTicket urlform", urlform);
        
        
        i.putExtra("urlform", urlform);

        //---set the result with OK and the Intent object---
        
        setResult(3, i);   
        
        finish();
    }    
    public void onClickCancel(View view) {
        //---use an Intent object to return data---
               
        finish();
    }    
    
}
