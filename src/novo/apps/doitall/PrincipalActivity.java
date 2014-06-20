package novo.apps.doitall;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import novo.apps.doitall.AddTicketActivity.GetGraphTask;




import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class PrincipalActivity extends ActionBarActivity {

	private ImageButton newticketbutton;
	private ImageButton button;
	private ImageButton graphbutton;

	private ProgressDialog progress;
	private ActionBar actionbar;
	AdvancedCustomArrayAdapter adapter; 
	ArrayList<TicketRecord> tickets;
	ArrayList<ProjectRecord> projects;
	private String currenttitle;
	private String urlimage;
	private ListView listtickets;
	private String currentticket;
	private String currentdate;
	private String currentuser;
	private String currentauth;
	private String lastlisttickets;
	private String currfile; 
	private String currreport;
	AlertDialog task_dialog; 	
	private ArrayList<String>  newstates;

	
	public static String URL_SERVER = "http://XXXX/intranet/register";
	public static String URL_SERVER_LOGIN = "http://XXXX/intranet/login";
	public static String FIRST_URL_GRAPH = "http://XXXXX/media/";
	public static String FIRST_URL_API = "http://XXXX/intranet/api/";
	public static String SECOND_URL_API = "/?tipoaccion=console&aplicacion=panelapp&accion=";
	public static String SECOND_URLFORM_API = "/?tipoaccion=form&aplicacion=panelapp&accion=";
	
	public static String PARAMETER_BY_PROJECT;
	public static String PARAMETER_BY_TYPE;
	public static String PARAMETER_BY_SEARCH;
	public static String DESCRIPTION_PROJECT_PARAMS;
	public static String DESCRIPTION_TYPE_PARAMS;
	
	public static int isclosing;
	public static String URL_API; 			
	public static String URLFORM_API;
	public static final String CURRENTDATEFORMAT = "dd/MMM/yyyy hh:mma";
	Map<String, String> usersmap;
			

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PARAMETER_BY_PROJECT = "";
		PARAMETER_BY_TYPE = "";		
		PARAMETER_BY_SEARCH = "";
		DESCRIPTION_PROJECT_PARAMS = "";
		DESCRIPTION_TYPE_PARAMS = "";
		
		isclosing = 0;
		Log.d("PrincipalActivity","OnCreate");
		usersmap = new HashMap<String, String>();		
//		usersmap.put("vbravo", "f3bf4ca25e666d70d6f847b87f448fefba5f2fda");
		usersmap.put("ssole", "aa004ca25e666d70d6f847b87f448fefba5f2aa0");
		usersmap.put("goapps0", "bb004ca25e666d70d6f847b87f448fefba5fbb00");
		usersmap.put("goapps1", "bb004ca25e666d70d6f847b87f448fefba5fcc00");		
		usersmap.put("goapps2", "bb004ca25e666d70d6f847b87f448fefba5fdd00");
	//	 usersmap.put("mlara","d893b97f6944b2fe1de3f014d34104c3b2cdfbe3");
		
		 actionbar = getSupportActionBar();
		 actionbar.setHomeButtonEnabled(true);

		 
		 currentuser = getIntent().getStringExtra("selectuser");
		 String ticket = getIntent().getStringExtra("selectauth");
		 String pass  = getIntent().getStringExtra("selectpass");
		 currentauth = ticket;
		 
//		 if (pass.isEmpty() ) {
//			 if (!usersmap.containsKey(currentuser)) {
//				 currentauth = ticket;			 
//			 }
//			 else {
//				 currentauth = usersmap.get(currentuser);
//			 }
//		 }
//		 else {
//			 
//			 
//		 }
		 
		 Log.d("PrincipalActivity","currentuser (pass):"+currentuser);
		 Log.d("PrincipalActivity","currentauth(pass):"+currentauth);
		 URL_API = FIRST_URL_API + currentauth + SECOND_URL_API;
		 URLFORM_API = FIRST_URL_API + currentauth + SECOND_URLFORM_API;
		 Log.d("PrincipalActivity","URL_API"+URL_API);
		 Log.d("PrincipalActivity","URLFORM_API"+URLFORM_API);
		 
	        
		// actionBar.setSubtitle("Tareas pendientes");
		 
		 actionbar.setDisplayShowTitleEnabled(true);
	//	 actionbar.setDisplayHomeAsUpEnabled(true);
		
		 currentticket = "";
		 currentdate = "";
		 urlimage = "";
		 lastlisttickets = "";
				 	 
		 Log.d("PrincipalActivity","1");
		setContentView(R.layout.activity_principal);
		Log.d("PrincipalActivity","2");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		tickets = new ArrayList<TicketRecord>();
		projects = new ArrayList<ProjectRecord>();
		adapter = 
	            new AdvancedCustomArrayAdapter(this);
		listtickets = (ListView) findViewById(R.id.listTasks);
		listtickets.setAdapter(adapter);

		progress = new ProgressDialog(this);

		
		addListenerOnButton();
		loadSafetReport("Por_hacer");
	        
	}
//	@Override
//	public void finish() {
//	    System.out.println("finish activity");          
//	    System.runFinalizersOnExit(true) ;          
//	    super.finish();
//	    android.os.Process.killProcess(android.os.Process.myPid());
//	}
//    
//	@Override
//	public void onBackPressed() {
//		
//		 if (isclosing == 0) {
//			 Toast toast = Toast.makeText(getApplicationContext(), 
// 	    			"Debe presionar de nuevo el botón para cerrar Doitall", Toast.LENGTH_SHORT);
//     		toast.show();
//     		isclosing = isclosing  +1;
//			 return;
//		 }
//		
//		 this.finish();
//		 
//	}
    
	final class GetGraphTask extends AsyncTask<String, Void, String> {

		private String consult;
		private String resulting;
		private String dataticket;
		
		public String getDataticket() {
			return dataticket;
		}

		public void setDataticket(String dataticket) {
			this.dataticket = dataticket;
		}



		private boolean addticket;
		
		public boolean isAddticket() {
			return addticket;
		}

		public void setAddticket(boolean addticket) {
			this.addticket = addticket;
		}

		public GetGraphTask(String c) {
			
	        super();
	        consult = c;
	        isclosing = 0;
	    }
		
		 protected void onPreExecute() {
			  progress.setMessage(getString(R.string.downloading));
		      progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		      progress.setIndeterminate(true);
		      progress.show();
            
         }
	  

		 private  String callPlainSAFETAPI(String urldisplay) {
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpResponse response;
		        String responseString = null;
		        try {
		        	
		            response = httpclient.execute(new HttpGet(urldisplay));
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
			    	Toast toast = Toast.makeText(getApplicationContext(), 
			    			"Error en la conexión:" + e.getMessage(), Toast.LENGTH_SHORT);
			    	toast.show();
		        
		        } catch (IOException e) {
			    	Toast toast = Toast.makeText(getApplicationContext(), 
			    			"Error de lectura de datos:" + e.getMessage(), Toast.LENGTH_SHORT);
			    	toast.show();
		        
		        
		 		} catch (Exception e) {
			    	Toast toast = Toast.makeText(getApplicationContext(), 
			    			"Error:" + e.getMessage(), Toast.LENGTH_SHORT);
			    	toast.show();
		 			
		 		}
		        return responseString;
		    } 
		 
		private  String callSAFETAPI(String urldisplay) {
			String result = "";
			 URL url;
			    try {
			        //url = new URL("https://gestion.cenditel.gob.ve/intranet/api/f3bf4ca25e666d70d6f847b87f448fefba5f2fda/?tipoaccion=console&aplicacion=victorrbravo&accion=operacion:Generar_grafico_coloreado%20Cargar_archivo_flujo:/home/victorrbravo/.safet/flowfiles/flujogeneralsesiones.xml");
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



			Log.d("mygraph","callPlainSAFETAPI...(1)");
			urldisplay = urldisplay.replace("á", "%E1");
			urldisplay = urldisplay.replace("é", "%E9");
			urldisplay = urldisplay.replace("í", "%ED");
			urldisplay = urldisplay.replace("ó", "%F3");
			urldisplay = urldisplay.replace("ú", "%FA");
			urldisplay = urldisplay.replace("ñ", "%F1");
			urldisplay = urldisplay.replace("Ñ", "%D1");
			Log.d("urldisplay",urldisplay);
			resulting = callPlainSAFETAPI(urldisplay);
			Log.d("mygraph","callPlainSAFETAPI...(2)");
			//resulting = callSAFETAPI(urldisplay);
			progress.setProgress(50);

			if (consult.contentEquals("cambiar_estado")) {
				Log.d("LastListTickets",lastlisttickets);
				
				//resulting = callPlainSAFETAPI(lastlisttickets);
				//consult = "cambiar_estado_relistar_ticket";
			}
			else if (consult.contentEquals("agregar_ticket")) {
				Log.d("**agregar ticket LastListTickets",lastlisttickets);
				
				
				//resulting = callPlainSAFETAPI(lastlisttickets);
				//consult = "agregar_ticket_relistar_ticket";
				
			}
			else if (consult.contentEquals("borrar_ticket")) {
				//Log.d("borrar_ticket",lastlisttickets);
				
				//resulting = callPlainSAFETAPI(lastlisttickets);
				//consult = "borrar_ticket_relistar_ticket";
			}
			else if (consult.contentEquals("modificar_ticket")) {
				Log.d("modificar_ticket",lastlisttickets);
				
				//resulting = callPlainSAFETAPI(lastlisttickets);
				//consult = "modificar_ticket_relistar_ticket";				
			}



			Log.d("SAFETCALLAPI from execute()",resulting);
			if ( consult.endsWith("ver_ticket") ) {
				Log.d("ver_ticket","resulting");
				
			}
			else if (consult.endsWith("siguientes_estados") ) {
				newstates = loadStatus(resulting);
				Log.d("changeStatusTask","newstates.count:"+ String.valueOf(newstates.size()));
				//showStatusDialog(newstates);

			}
			else if ( consult.endsWith("listar_ticket") ) {

				tickets.clear();
				Log.d("PrincipalActivity","after SAFETCALLAPI");
				
				lastlisttickets = urldisplay;
				
				try {	
					JSONObject jall = new JSONObject(resulting);

					JSONArray jArray = jall.getJSONArray("safetlist");
					for(int i=0;i<jArray.length();i++){

						JSONObject json_data = jArray.getJSONObject(i);
						TicketRecord ticket  = new TicketRecord();
						//String summary = URLDecoder.decode(json_data.getString("resumen"), "UTF-8");
						String summary = json_data.getString("resumen");

						Log.i("jsonarray","resumen:"+ summary);
						String desc = json_data.getString("descripcion");
						Log.i("jsonarray","descripcion:"+ desc);
						String id = json_data.getString("id");
						ticket.setId(id);
						ticket.setSummary(summary);
						ticket.setDescription(desc);
						ticket.setProject(json_data.getString("proyecto"));
						ticket.
						setTentativedate(PrincipalActivity.convertDateEpochToFormat(json_data.getString("tentativedate")));
						String newfinish = json_data.getString("finishdate");
						if (newfinish.contentEquals("0")) {
							ticket.
							setFinishdate(getString(R.string.nocomplete));
						}
						else  {
							ticket.
							setFinishdate(PrincipalActivity.convertDateEpochToFormat(newfinish));

						}
						ticket.setOwner(json_data.getString("owner"));
						ticket.setType(json_data.getString("type"));
						ticket.setStatus(json_data.getString("status"));
						
						
						tickets.add(ticket);
						urlimage = "";

					}
					currenttitle = jall.getString("safetvariable");
					
					
					
					Log.d("checkpoint","1");
					adapter.setTickets(tickets);
					Log.d("checkpoint","2");

				} catch(Exception e) {
					Log.e("JSON", "Ocurrio el error:"+ e.toString());
					//e.printStackTrace();

				}
			}
			else if ( consult.endsWith("listar_proyectos") ) {

				
				
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
					

				} catch(Exception e) {
					Log.e("JSON", "Ocurrio el error:"+ e.toString());
					//e.printStackTrace();

				}
			}
			else if (consult.contentEquals("graficar")) {
				String mygraph = PrincipalActivity.FIRST_URL_GRAPH + resulting;
				Log.d("mygraph",mygraph);
				resulting = mygraph;
				urlimage = mygraph;
				Log.d("example",mygraph);
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
	    	
	    	Log.d("onPostExecute result",consult);
	        Log.d("**consult:",consult);
	        
	    	if (consult.startsWith("cambiar_estado")) {
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Cambio de estado realizado", Toast.LENGTH_SHORT);
		    	toast.show();
	    		String myconsultdel = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
		    			"Cargar_archivo_flujo:%20"+currfile+"%20Variable:"+currreport
		    			+PrincipalActivity.PARAMETER_BY_PROJECT
		    			+PrincipalActivity.PARAMETER_BY_TYPE;
		    			;
		        new GetGraphTask("listar_ticket").execute(myconsultdel);

	    		
	    	}
	    	else if (consult.startsWith("siguientes_estados")) {
	    		Log.d("siguientes_estados",String.valueOf(newstates.size()));
				showStatesNameDialog(newstates,getString(R.string.state_list_title));
				Log.d("showstatus","showstatus");		

	    	}
	    	else if (consult.startsWith("ver_ticket")) {
	    		if (isAddticket() ) {
					Intent i = new 
			                Intent("novo.apps.doitall.ViewTicketActivity");
					
					i.putExtra("resulting", resulting);
					startActivity(i);
	    		}
	    		else {
	    			callPreListprojects(false,resulting);
	    		}
	    		
	    	}
	    	else if (consult.startsWith("agregar_ticket")) {
	    		Log.d("ticket agregado",consult);
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Tarea agregada", Toast.LENGTH_SHORT);
		    	adapter.notifyDataSetChanged();
		    	toast.show();

	    		String myconsultdel = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
		    			"Cargar_archivo_flujo:%20"+currfile+"%20Variable:"+currreport
		    			+PrincipalActivity.PARAMETER_BY_PROJECT
		    			+PrincipalActivity.PARAMETER_BY_TYPE;
		    			;

		        new GetGraphTask("listar_ticket").execute(myconsultdel);
	    		
	    	}

	    	else if (consult.startsWith("borrar_ticket")) {
	    		Log.d("postExecute","Tarea eliminada");
	    		
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Tarea eliminada", Toast.LENGTH_SHORT);
		    	adapter.notifyDataSetChanged();
		    	toast.show();
				String myconsultdel = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
		    			"Cargar_archivo_flujo:%20"+currfile+"%20Variable:"+currreport
		    			+PrincipalActivity.PARAMETER_BY_PROJECT
		    			+PrincipalActivity.PARAMETER_BY_TYPE;
		    			;

		        new GetGraphTask("listar_ticket").execute(myconsultdel);
		    	
	    		
	    	}
	    	else if (consult.startsWith("modificar_ticket")) {
	    		Log.d("postExecute","Tarea modificada");
	    		
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Se modificó la fecha planeada de la tarea", Toast.LENGTH_SHORT);
		    	adapter.notifyDataSetChanged();
		    	toast.show();

	    		String myconsultdel = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
		    			"Cargar_archivo_flujo:%20"+currfile+"%20Variable:"+currreport
		    			+PrincipalActivity.PARAMETER_BY_PROJECT
		    			+PrincipalActivity.PARAMETER_BY_TYPE;
		    			;

		        new GetGraphTask("listar_ticket").execute(myconsultdel);

		    	
	    		
	    	}
	    	
	    	else if  (consult.contentEquals("graficar")) {
			 
			      Log.d("urlimage",urlimage);

					Log.d("graph","GraphButton");
			        Intent i = new 
			                Intent("novo.apps.doitall.SecondActivity");

			        //---use putExtra() to add new key/value pairs---            
			        i.putExtra("str1", urlimage);
			        
			        
			        startActivityForResult(i,1);

		    	  
		      }
	    	else if (consult.contentEquals("listar_proyectos")) {
				Log.d("**ButtonNew","executing addTicket");

				callAddTicketActivity(isAddticket(),getDataticket());
	    	}
	    	
	    	else if (consult.contentEquals("para_filtro_listar_proyectos")) {

	    		Log.d("makeFilterProjectDialog","makeFilterProjectDialog");		
	    		makeFilterProjectDialog();	    			
	    		

	    	}
	    	else {
		        actionbar.setTitle(currenttitle + PrincipalActivity.DESCRIPTION_PROJECT_PARAMS
		        		+ PrincipalActivity.DESCRIPTION_TYPE_PARAMS.replace("%20"," "));
		    	adapter.notifyDataSetChanged();
	    	}
	          
	    }
	}
	
	public void callAddTicketActivity(boolean addticket, String dataticket) {
		
        Intent i = new 
                Intent("novo.apps.doitall.AddTicketActivity");

        //---use putExtra() to add new key/value pairs---
    	i.putExtra("projectlist", urlimage);		
    	i.putExtra("projects", projects);
    	i.putExtra("addticket", addticket);
       	i.putExtra("dataticket", dataticket);


        startActivityForResult(i, 2);
		
	}
	
	public void makeFilterProjectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.filter_project_dialog));
	
		final Spinner input = new Spinner(this);
		
		ArrayList<String> mylist = new ArrayList();
		for(int i=0; i < projects.size(); i++) {		
			mylist.add(projects.get(i).getTitle());
		}		 
		
		ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mylist);

		input.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();
		
		builder.setView(input);


		builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here
		    	
		    	String selproject = input.getSelectedItem().toString();
		    
		    	PrincipalActivity.DESCRIPTION_PROJECT_PARAMS =  "-" + selproject;
		    			
		    	Log.d("....makeFilter","selproject" + selproject);
		    	
		    	int pos = input.getSelectedItemPosition();
		    	
		    	if ( pos >= 0 && pos < projects.size() ) {
		    		PrincipalActivity.PARAMETER_BY_PROJECT = "%20parameters.ByProject:" + String.valueOf(projects.get(pos).getProjectid());
		    		Log.d("PrincipalActivity.PARAMETER_BY_PROJECT", PrincipalActivity.PARAMETER_BY_PROJECT);
		    		//boxproject.setText(selproject);
		    	}
		        
		    	actionbar.setTitle(currenttitle + PrincipalActivity.DESCRIPTION_PROJECT_PARAMS
		    			+ PrincipalActivity.DESCRIPTION_TYPE_PARAMS.replace("%20"," "));
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			getString(R.string.need_refresh), Toast.LENGTH_SHORT);
		    	toast.show();

		    }

		});

		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // I do not need any action here you might
		    	Log.d("makeFilterOptionsDialog...(1)","...(1)");
		        dialog.dismiss();
		        ToggleButton boxproject = ((ToggleButton) findViewById(R.id.toggleProject));
		      
		        boxproject.setChecked(false);
		        PrincipalActivity.DESCRIPTION_PROJECT_PARAMS = "";
		        
		        Log.d("makeFilterOptionsDialog...(1)","...(2)");

	        		        
		        
		        
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void makeFiltertTypeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.filter_type_dialog));
	
		final Spinner input = new Spinner(this);
		
		String[] mytypes = getResources().getStringArray(R.array.names_type_ticket);
		
						
		
		ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mytypes);

		input.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();
		
		builder.setView(input);


		builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here
		    	ToggleButton boxtype = (ToggleButton) findViewById(R.id.toggleType);
		    	
		    	String seltype = input.getSelectedItem().toString();
		    		
		    	Log.d("makeFilterType","seltype" + seltype);
		    	
		    	int pos = input.getSelectedItemPosition();
		    	
		    	//boxtype.setText(seltype);
		    	seltype = seltype.replace(" ", "%20");
		    	PrincipalActivity.PARAMETER_BY_TYPE = "%20parameters.ByType:" + seltype;
		    	PrincipalActivity.DESCRIPTION_TYPE_PARAMS = "-" + seltype;
		        Log.d("BoxType","seltype:" + seltype);
		        
		    	actionbar.setTitle(currenttitle + PrincipalActivity.DESCRIPTION_PROJECT_PARAMS
		    			+ PrincipalActivity.DESCRIPTION_TYPE_PARAMS.replace("%20"," "));
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			getString(R.string.need_refresh), Toast.LENGTH_SHORT);
		    	toast.show();

		    }

		});

		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // I do not need any action here you might
		    	Log.d("makeFilterOptionsDialog...(1)","...(1)");
		        dialog.dismiss();
		        ToggleButton boxtype = ((ToggleButton) findViewById(R.id.toggleType));
		      
		        boxtype.setChecked(false);
		        
		        PrincipalActivity.PARAMETER_BY_TYPE = "";
		        PrincipalActivity.DESCRIPTION_TYPE_PARAMS = "";
		        Log.d("makeFilterOptionsDialog...(1)","...(2)");

	        		        
		        
		        
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();
	}
	
	

	private void showGraphChooseDialog(Context context) {
    	
    	final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.choose_graph_report);
		dialog.setTitle(getString(R.string.report_type));

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(getString(R.string.select_report_graph));
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);

		
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String myconsult = "";
				Spinner typegraph = (Spinner) dialog.findViewById(R.id.graphtype);
				if (typegraph.getSelectedItemPosition() == 0 ) {
					myconsult = PrincipalActivity.URL_API +"operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratres.xml"
							+PrincipalActivity.PARAMETER_BY_PROJECT
							+PrincipalActivity.PARAMETER_BY_TYPE;
				}
				else if (typegraph.getSelectedItemPosition() == 1 ) {
					myconsult = PrincipalActivity.URL_API +"operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleraproximos.xml"
							+PrincipalActivity.PARAMETER_BY_PROJECT
							+PrincipalActivity.PARAMETER_BY_TYPE;
				}
				else if (typegraph.getSelectedItemPosition() == 2 ) {
					myconsult = PrincipalActivity.URL_API +"operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/cartelerapormes.xml"
							+PrincipalActivity.PARAMETER_BY_PROJECT
							+PrincipalActivity.PARAMETER_BY_TYPE;
				}
				else if (typegraph.getSelectedItemPosition() == 3 ) {
					myconsult = PrincipalActivity.URL_API +"operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/cartelerafinalizadosporsemana.xml"
							+PrincipalActivity.PARAMETER_BY_PROJECT
							+PrincipalActivity.PARAMETER_BY_TYPE;
				}
				else if (typegraph.getSelectedItemPosition() == 4 ) {
					myconsult = PrincipalActivity.URL_API +"operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/cartelerafinalizados.xml"
							+PrincipalActivity.PARAMETER_BY_PROJECT
							+PrincipalActivity.PARAMETER_BY_TYPE;
				}
				else if (typegraph.getSelectedItemPosition() == 5 ) {
					myconsult = PrincipalActivity.URL_API +
							"operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratodos.xml%20Autofiltro:%20por_proyecto";
				}
				
				else {
					Log.d("GraphType","Selected none");
					return;
				}


				Log.d("showGraph *consult:",myconsult);
				
			GetGraphTask mytask = new GetGraphTask("graficar");
			
				mytask.execute(myconsult);
				
				dialog.dismiss();
			}
		});

		Log.d("showInputNameDialog","entrando 8");
		Button dialogCancel= (Button) dialog.findViewById(R.id.dialogButtonCancel);
		// if button is clicked, close the custom dialog
		dialogCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				dialog.dismiss();
			}
		});
		dialog.show();
		Log.d("show","show");
    }
	
	
	public void onClickedProject(View view) {
		Log.d("OnClickProject","OnClickProject");
		ToggleButton boxproject = ((ToggleButton) view); 
		if (boxproject.isChecked()) {
			Log.d("boxproject","entering...1");
			
			String myconsult = PrincipalActivity.URL_API 
					+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/proyectos.xml"+
	    			"%20Variable:vProyectos";

	    	GetGraphTask mytask = new GetGraphTask("para_filtro_listar_proyectos");
	    	
	    	mytask.execute(myconsult);
	    	Log.d("boxproject","entering...2");

			
					
			
		}
		else {
			Log.d("OnClickProject","OnClickProject Off");
			//boxproject.setText(getString(R.string.filter_any_project));
			PrincipalActivity.PARAMETER_BY_PROJECT = "";
			PrincipalActivity.DESCRIPTION_PROJECT_PARAMS = "";
			
		}
		//view.refreshDrawableState();
	}
	
	public void onClickedType(View view) {
		Log.d("OnClickType","OnClickType");
		ToggleButton boxtype = ((ToggleButton) view);
		if (boxtype.isChecked()) {
			//boxtype.setText("Trabajo");
			makeFiltertTypeDialog();
			
		}
		else {
			PrincipalActivity.PARAMETER_BY_TYPE = "";
			PrincipalActivity.DESCRIPTION_TYPE_PARAMS = "";
			//boxtype.setText(getString(R.string.filter_any_type));
		}
		
	}

	private void showInputNameDialog(Context context,ArrayList<String> states, String title) {
    	
    	final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.choose_report);
		dialog.setTitle(getString(R.string.report_type));

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(getString(R.string.select_report_ticket));
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);


		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog


		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner myselect = (Spinner)dialog.findViewById(R.id.selecttype);				
				String myreport = myselect.getSelectedItem().toString();
				Log.d("Spinner",myreport);
				dialog.dismiss();
				loadSafetReport(myreport);
			}
		});


		
		Log.d("showInputNameDialog() 1","entrando **");
		Button dialogCancel= (Button) dialog.findViewById(R.id.dialogButtonCancel);
		Log.d("showInputNameDialog() 2","entrando **");
		// if button is clicked, close the custom dialog
		dialogCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		Log.d("showInputNameDialog() 3","entrando **");
		Log.d("showInputNameDialog() 4","entrando **");
		dialog.show();
		Log.d("showInputNameDialog() 5","entrando **");
		Log.d("show","show");
    }


	private void showStatesNameDialog(ArrayList<String> states, String title) {
    	
    	final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.choose_report);
		dialog.setTitle(getString(R.string.report_type));

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(getString(R.string.select_report_ticket));
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);


		Log.d("states size","size() > 0");
		Spinner myselect = (Spinner)dialog.findViewById(R.id.selecttype);

		ArrayAdapter<String> myadapter;
		myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,states);

		myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		myselect.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();
		Log.d("states size","size() > 0**");

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog


		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner myselect = (Spinner)dialog.findViewById(R.id.selecttype);				
				String mystatus = myselect.getSelectedItem().toString();
				Log.d("cambiar_estado",mystatus);
				dialog.dismiss();
				String idticket = currentticket;
				String myconsult = PrincipalActivity.URLFORM_API + "operacion:Siguiente_estado%20id:"+ idticket +
						"%20Siguiente_Estado:"+  mystatus;
				Log.d("CambiandoEstado...consult:","|"+myconsult+"|");
				new GetGraphTask("cambiar_estado").execute(myconsult);
			}
		});

			
	
	
		
		Log.d("showInputNameDialog() 1","entrando **");
		Button dialogCancel= (Button) dialog.findViewById(R.id.dialogButtonCancel);
		Log.d("showInputNameDialog() 2","entrando **");
		// if button is clicked, close the custom dialog
		dialogCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		Log.d("showInputNameDialog() 3","(3)entrando **");
		Log.d("showInputNameDialog() 4","(4)entrando **");
		dialog.show();
		Log.d("showInputNameDialog() 5","entrando **");
		Log.d("show","show");
    }

	public void makeDeleteDataConnectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setMessage(getString(R.string.ask_delete_data_connect));

		builder.setPositiveButton(getString(R.string.yes_option), new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here

		    	GenTicket mytask = new GenTicket(getBaseContext());

		    	mytask.deleteTicket();
		    	Log.d("makeDeleteDataConnectDialog","Yes");
		        dialog.dismiss();
		    	
		    }

		});

		builder.setNegativeButton(getString(R.string.no_option), new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // I do not need any action here you might
		    	Log.d("makeDeleteDataConnectDialog","no");
		        dialog.dismiss();
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();

		
		
	}

	
	public void makeModifyOptionsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setMessage(getString(R.string.modify_date_task));
    	Calendar calendar = Calendar.getInstance();
    	
    	
    	Log.d("makeModifyOptionsDialog", "Seltext:currentdate:" + currentdate);
    	
    	
    	
    	SimpleDateFormat sf = new SimpleDateFormat(PrincipalActivity.CURRENTDATEFORMAT);

    	try {
    		calendar.setTime(sf.parse(currentdate));
    	}
    	catch(Exception e){
    		
	    	Toast toast = Toast.makeText(getApplicationContext(), 
	    			getString(R.string.error_dateparse), Toast.LENGTH_SHORT);

	    	Log.d("Exception",e.getMessage());
	    	
    		return;
    	}
    	
    	
		
		final DatePicker input = new DatePicker(this);
    	Log.d("makeModifyOptionsDialog", "Seltext:year:" + String.valueOf(calendar.get(Calendar.YEAR)));
    	input.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
    			calendar.get(Calendar.DAY_OF_MONTH), null);

		builder.setView(input);

		builder.setPositiveButton(getString(R.string.yes_option), new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here

		        int year = input.getYear();
		        
		        Calendar calendar = Calendar.getInstance();
		        
		        calendar.set(year,input.getMonth(),input.getDayOfMonth(),23,59,59);
		        
		        String fechatentativa = String.valueOf(calendar.getTimeInMillis()/1000) ;

		    	
				String myconsult = PrincipalActivity.URLFORM_API + 
						"operacion:modificar_ticket%20id:"+ currentticket+"%20Fecha_tentativa:"
						+fechatentativa;
				Log.d("Modificar_ticket...consult:","|"+myconsult+"|");
				
				new GetGraphTask("modificar_ticket").execute(myconsult);

		    	Log.d("makeModifyOptionsDialog","Yes");
		        dialog.dismiss();
		    }

		});

		builder.setNegativeButton(getString(R.string.no_option), new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // I do not need any action here you might
		    	Log.d("makeDeleteOptionsDialog","no...(1)");
		        dialog.dismiss();
		        

		        Log.d("makeDeleteOptionsDialog","no...(2)");
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();
		
		
	}
	
	public void makeDeleteOptionsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setMessage(getString(R.string.confirm_delete));

		builder.setPositiveButton(getString(R.string.yes_option), new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here

				String myconsult = PrincipalActivity.URLFORM_API + 
						"operacion:borrar_ticket%20id_ticket:"+ currentticket;
				Log.d("Borrar_ticket...consult:","|"+myconsult+"|");
				
				new GetGraphTask("borrar_ticket").execute(myconsult);

		    	Log.d("makeDeleteOptionsDialog","Yes");
		        dialog.dismiss();

		    	
		    }

		});

		builder.setNegativeButton(getString(R.string.no_option), new DialogInterface.OnClickListener() {

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
	
	public AlertDialog makeTaskOptionsDialog(final Context context) {
		final String[] option = new String[] {getString(R.string.change_status), 
				getString(R.string.show_task), getString(R.string.delete_task), getString(R.string.modify_date_task),
				getString(R.string.modify_data_task) };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

		
			public void onClick(DialogInterface dialog, int which) {
				Log.d("makeTaskOptionsDialog","currentticket:"+currentticket);

				switch (which) {
				case 0: // Cambiar estado
				Log.d("makeTaskOptionsDialog", "changeStatusTask");
				changeStatusTask();
				break;
				case 1: //Ver
	
					loadViewTicketActivity(true);
					break;
				case 2: // Elimina
					makeDeleteOptionsDialog();
					break;
				case 3: // Modifica fecha de la tarea
					makeModifyOptionsDialog();
					break;
				case 4: // Modifica datos de la tarea
					loadViewTicketActivity(false);
					break;
					
				default:
					break;
				}
			}

		});     

		return builder.create();
	}
	
    protected void loadViewTicketActivity(boolean addticket) {
		// TODO Auto-generated method stub
        

    	String myconsult = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
    			"Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratodos.xml%20Variable:vTodas_las_tareas"
    			+"%20parameters.nro_ticket:"+currentticket;

    	Log.d("loadViewTicketActivity","myconsult:"+myconsult);

    	
    	GetGraphTask mytask = new GetGraphTask("ver_ticket");
    	mytask.setAddticket(addticket);

    	mytask.execute(myconsult);



	}


	public void loadSafetReport(String report) {
    	currreport = "vPor_hacer";
    	currfile = "/home/panelapp/.safet/flowfiles/carteleratres.xml";
    	
    	Log.d("Spinner","2:|" + report+"|");
    	if ( report.contentEquals("En progreso")) {
    		Log.d("Spinner","3:" + report);
    		currreport = "vProgress";
    	}
    	else if (report.contentEquals("Pospuestos") ) {
    		currreport = "vPostponed";	
    	}
    	else if (report.contentEquals(getString(R.string.completed_this_week)) ) {
    		currreport = "vEsta_semana";	
    		currfile = "/home/panelapp/.safet/flowfiles/cartelerafinalizadosporsemana.xml";
    	}
    	else if (report.contentEquals(getString(R.string.all_tickets)) ) {
    		currreport = "Todos";	
    		currfile = "/home/panelapp/.safet/flowfiles/cartelerabusqueda.xml";
    	}
    	
    	else if (report.contentEquals(getString(R.string.delayed)))  {
    		currreport = "vDelayed";	
    	}
    	
    	else if (report.contentEquals(getString(R.string.next_week)) ) {
    		Log.d("loadSafetReport","nextweek");
    		currfile = "/home/panelapp/.safet/flowfiles/carteleraproximos.xml";
    		currreport = "vProxima_semana";
    		
    	}
    	else if (report.contentEquals(getString(R.string.this_week)) ) {
    		Log.d("loadSafetReport","nextweek");
    		currfile = "/home/panelapp/.safet/flowfiles/carteleraproximos.xml";
    		currreport = "vEsta_semana";
    		
    	}
    	else if (report.contentEquals(getString(R.string.this_month)) ) {
    		Log.d("loadSafetReport","nextweek");
    		currfile = "/home/panelapp/.safet/flowfiles/cartelerapormes.xml";
    		currreport = "vEste_mes";
    		
    	}
    	else if (report.contentEquals(getString(R.string.next_month)) ) {
    		Log.d("loadSafetReport","nextweek");
    		currfile = "/home/panelapp/.safet/flowfiles/cartelerapormes.xml";
    		currreport = "vProximo_mes";
    		
    	}
    	else if (report.contentEquals(getString(R.string.after_month)) ) {
    		Log.d("loadSafetReport","nextweek");
    		currfile = "/home/panelapp/.safet/flowfiles/cartelerapormes.xml";
    		currreport = "vSuperior_proximo_mes";
    		
    	}
    	else if (report.contentEquals(getString(R.string.after_week)) ) {
    		Log.d("loadSafetReport","nextweek");
    		currfile = "/home/panelapp/.safet/flowfiles/carteleraproximos.xml";
    		currreport = "vSuperior_proxima_semana";    		
    	}

    	else if (report.contentEquals(getString(R.string.finished)) ) {
    		currreport = "vFinished";
    	}
    	
    	String myconsult = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
    			"Cargar_archivo_flujo:%20"+currfile+"%20Variable:"+currreport
    			+PrincipalActivity.PARAMETER_BY_PROJECT
    			+PrincipalActivity.PARAMETER_BY_TYPE;
    			;

    	    	

    	GetGraphTask mytask = new GetGraphTask("listar_ticket");

    	mytask.execute(myconsult);
    	
    }
    

    protected String changePlainTicketStatus(String urldisplay) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
        	
            response = httpclient.execute(new HttpGet(urldisplay));
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
	    	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"Error en la conexión:" + e.getMessage(), Toast.LENGTH_SHORT);
	    	toast.show();
        
        } catch (IOException e) {
	    	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"Error de lectura de datos:" + e.getMessage(), Toast.LENGTH_SHORT);
	    	toast.show();
        
        
 		} catch (Exception e) {
	    	Toast toast = Toast.makeText(getApplicationContext(), 
	    			"Error:" + e.getMessage(), Toast.LENGTH_SHORT);
	    	toast.show();
 			
 		}
        return responseString;

        
        
    }

	
    protected String changeTicketStatus(String urldisplay) {
        String webPage = "";
        
		 URL url;
		    try {

		        url = new URL(urldisplay);	        

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
		        String data="";
		        while ((data = reader.readLine()) != null){
		           webPage += data;
		        }
		        Log.d("ChangeStatusTicket","DATA---WEBPAGE:|"+ webPage + "|");		        
	  	
		        
		       } catch (Exception e) {
		        // TODO Auto-generated catch block
		    	Log.d("Exception",e.toString());
		        e.printStackTrace();
		    }
        
        

		    return webPage;
    }
    
	private ArrayList<String> loadStatus(String resulting) {
    	
		ArrayList<String> states = new ArrayList<String>();

		Log.d("loadStatus","resulting:"+resulting);
		states.clear();
		try {
			JSONObject jall = new JSONObject(resulting);
			JSONArray jarray = jall.getJSONArray("Status");
			  for(int i=0;i<jarray.length();i++){
				  JSONArray json_data = jarray.getJSONArray(i);
				  String myvalue = json_data.get(0).toString();
				  Log.d("json","myvalue:|" + myvalue+"|");					  
				  states.add(myvalue);
			  }
						  			 
		} catch (Exception e) {
			Log.d("exception",e.toString());
			e.printStackTrace();
			
		}
		
		return states;
    }

	

    // Agregando botón de cargar datos
	
	public void changeStatusTask() {
	
		
		String myconsult = PrincipalActivity.URL_API +
		"operacion:Siguientes_estados%20Cargar_archivo_flujo:"+
				"/home/panelapp/.safet/flowfiles/carteleratresf.xml%20Clave:" + currentticket;

		Log.d("changeStatusTask..myconsult:",myconsult);
		
        new GetGraphTask("siguientes_estados").execute(myconsult);		

	}
	
    public void addListenerOnButton() {

        //Select a specific button to bundle it with the action you want
		
		listtickets.setOnItemLongClickListener(new OnItemLongClickListener()  {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				TicketRecord ticket = (TicketRecord) listtickets.getItemAtPosition(position);
				String idticket = ticket.getId();
				currentticket = idticket;
				currentdate = ticket.getTentativedate();
				
				Log.d("Selecting","IdTicket:|" + currentticket + "|");
				Log.d("Selecting","Tentativedate:|" + currentdate + "|");
				
				task_dialog = makeTaskOptionsDialog(view.getContext());
				task_dialog.show();
				
						
				return true;
			}

		});

		
		button = (ImageButton) findViewById(R.id.buttonload);
		
		button.setOnClickListener(new OnClickListener() {

						
				
			@Override
			public void onClick(View view) {
				
				
				Log.d("DialogLog", "1");
				ArrayList<String> mylist = new ArrayList<String>();
				showInputNameDialog(view.getContext(),mylist,getString(R.string.ticket_list_title));	
				 Log.d("DialogLog", "2");
		
				
						
			}
			
			

		});

		
		newticketbutton  = (ImageButton) findViewById(R.id.buttonnewticket);

		Log.d("newticketbutton","newticketbutton");
		
		newticketbutton.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View view) {

				callPreListprojects(true,"");
			}
		});
		

		
		
		graphbutton = (ImageButton) findViewById(R.id.buttongraph);
		
		graphbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				showGraphChooseDialog(view.getContext());

			}
			
		});
		
		
	}

    public void callPreListprojects(boolean addticket, String dataticket) {
		String myconsult = PrincipalActivity.URL_API 
				+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/proyectos.xml"+
    			"%20Variable:vProyectos";

    	GetGraphTask mytask = new GetGraphTask("listar_proyectos");

    	mytask.setDataticket(dataticket);
    	mytask.setAddticket(addticket);
    	mytask.execute(myconsult);
    	

    }

	public void onActivityResult(int requestCode, 
            int resultCode, Intent data)
    {
        //---check if the request code is 1---
		Log.d("requestCode:",String.valueOf(requestCode));
		Log.d("requestCode:",String.valueOf(resultCode));
        if (resultCode == 1) {

        	Log.d("requestCode:",String.valueOf(1));
        	adapter.notifyDataSetChanged();
            //---if the result is OK--- 
            if (resultCode == RESULT_OK) {
            }            
        }
        else if (requestCode == 2) {
        	if (resultCode == 3) {
        		
        		String urlform = PrincipalActivity.URLFORM_API + data.getStringExtra("urlform");
        		Log.d("OnActivityResult...","*Principal");
        		
        		Log.d("urlform...",urlform);
            	GetGraphTask mytask = new GetGraphTask("agregar_ticket");
            	mytask.execute(urlform);
            	
        	}
        	
        }
        else if (requestCode == 5) {
        	Log.d("return from About", "return from About");
        	
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
			
		 
		    

		    // When using the support library, the setOnActionExpandListener() method is
		    // static and accepts the MenuItem object as an argument
		  MenuItem searchItem = menu.findItem(R.id.action_search);

		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


	    
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
	        @Override
	        public boolean onQueryTextChange(String newText) {
	            if (newText.length() > 0) {
	                // Search
	            	Log.d("Search","Search (1)");

	            } else {
	                // Do something when there's no input
	            	
	            }
	            return false;
	        }
	        @Override
	        public boolean onQueryTextSubmit(String query) { 

	        	Log.d("Search","Search (3)");
	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

	            Toast.makeText(getBaseContext(), "Búsqueda realida con éxito", Toast.LENGTH_SHORT).show();
	            setSupportProgressBarIndeterminateVisibility(true);

	            Log.d("Search","Search (4)");

	            currfile = "/home/panelapp/.safet/flowfiles/cartelerabusqueda.xml";
	            currreport = "vBusqueda";
	            String newquery = query.replace(" ","%20");
				PrincipalActivity.PARAMETER_BY_SEARCH = "%20parameters.ByPattern:" + newquery;
	            
	    		String myconsult = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
		    			"Cargar_archivo_flujo:%20"+currfile+"%20Variable:"+currreport
		    			+"%20"+PrincipalActivity.PARAMETER_BY_SEARCH
		    			+"%20"+PrincipalActivity.PARAMETER_BY_PROJECT
		    			+"%20"+PrincipalActivity.PARAMETER_BY_TYPE;

		    			
		    			
	    		Log.d("SEARCH CONSULT:",myconsult);
		        new GetGraphTask("listar_ticket").execute(myconsult);

	            return false; 
	        }
	    });
	    
	    
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
			Log.d("About","About");
			Intent i = new 
	                Intent("novo.apps.doitall.AboutActivity");
	        startActivityForResult(i,5);
			return true;
		} 
		else if (id == R.id.delete_data_connect) {
			
			makeDeleteDataConnectDialog();
			
		}
		else if (id == R.id.help_action_link) {
			 Intent intent = new Intent();
			 intent.setAction(Intent.ACTION_VIEW);
			 intent.addCategory(Intent.CATEGORY_BROWSABLE);
			 intent.setData(Uri.parse("https://github.com/victorrbravo/doitall/wiki/Ayuda-de-Doitall"));
			 startActivity(intent);
			
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_principal,
					container, false);
			return rootView;
		}
	}

    public static String convertDateEpochToFormat(String epoch) {    	
    	String result = epoch.trim();
    	if ( result.equalsIgnoreCase("0")) {
    		result =   "n/a";
    		return result;
    	}
    	long dv = Long.valueOf(epoch)*1000;// its need to be in milisecond
    	Date df = new java.util.Date(dv);
    	result = new SimpleDateFormat(PrincipalActivity.CURRENTDATEFORMAT).format(df);
    	
    	return result;
    	
    }

}

