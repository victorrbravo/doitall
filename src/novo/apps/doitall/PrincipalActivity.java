package novo.apps.doitall;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



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
	private ArrayList<String> liststatus;
	private String lastlisttickets;
 
	AlertDialog task_dialog; 	
	
	
	public static final String URL_API = "https://gestion.cenditel.gob.ve/intranet/api/"+
			"f3bf4ca25e666d70d6f847b87f448fefba5f2fda/?tipoaccion=console&"+
			"aplicacion=panelapp&accion=";
	
	public static final String URLFORM_API = "https://gestion.cenditel.gob.ve/intranet/api/"+
			"f3bf4ca25e666d70d6f847b87f448fefba5f2fda/?tipoaccion=form&"+
			"aplicacion=panelapp&accion=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 actionbar = getSupportActionBar();
		 actionbar.setHomeButtonEnabled(true);
		 
		// actionBar.setSubtitle("Tareas pendientes");
		 
		 actionbar.setDisplayShowTitleEnabled(true);
	//	 actionbar.setDisplayHomeAsUpEnabled(true);
		
		 currentticket = "";
		 urlimage = "";
		 lastlisttickets = "";
		 
		 
		 liststatus  = new ArrayList<String>();
		 
		setContentView(R.layout.activity_principal);

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

			if (consult.contentEquals("cambiar_estado")) {
				Log.d("LastListTickets",lastlisttickets);
				
				resulting = callSAFETAPI(lastlisttickets);
				consult = "cambiar_estado_relistar_ticket";
			}
			else if (consult.contentEquals("agregar_ticket")) {
				Log.d("agregar ticket LastListTickets",lastlisttickets);
				
				resulting = callSAFETAPI(lastlisttickets);
				consult = "agregar_ticket_relistar_ticket";
				
			}
			else if (consult.contentEquals("borrar_ticket")) {
				Log.d("borrar_ticket",lastlisttickets);
				
				resulting = callSAFETAPI(lastlisttickets);
				consult = "borrar_ticket_relistar_ticket";				
			}

			tickets.clear();

			Log.d("SAFETCALLAPI from execute()",resulting);
			if ( consult.endsWith("listar_ticket") ) {


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
				String mygraph = "https://gestion.cenditel.gob.ve/media/" + resulting;
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
	    	
	    	Log.d("GetGraphTask result",consult);
	        Log.d("ProjectCount:",String.valueOf(projects.size()));
	        
	    	if (consult.startsWith("cambiar_estado")) {
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Cambio de estado realizado", Toast.LENGTH_SHORT);
		    	toast.show();

	    		
	    	}
	    	else if (consult.startsWith("agregar_ticket")) {
//		    	Toast toast = Toast.makeText(getApplicationContext(), 
//		    			"Cambio de estado realizado", Toast.LENGTH_SHORT);
//		    	toast.show();
	    		Log.d("ticket agregado",consult);
	    		
	    	}

	    	else if (consult.startsWith("borrar_ticket")) {
	    		Log.d("postExecute","Tarea eliminada");
	    		
		    	Toast toast = Toast.makeText(getApplicationContext(), 
		    			"Tarea eliminada", Toast.LENGTH_SHORT);
		    	adapter.notifyDataSetChanged();
		    	toast.show();
		    	
	    		
	    	}
	    	else if  (consult.contentEquals("graficar")) {
			 
			      Log.d("urlimage",urlimage);

					Log.d("graph","GraphButton");
			        Intent i = new 
			                Intent("novo.apps.doitall.SecondActivity");

			        //---use putExtra() to add new key/value pairs---            
			        i.putExtra("str1", urlimage);
			        
			        
			        startActivityForResult(i, 1);

		    	  
		      }
	    	else if (consult.contentEquals("listar_proyectos")) {
				Log.d("**ButtonNew","executing addTicket");
		        Intent i = new 
		                Intent("novo.apps.doitall.AddTicketActivity");

		        //---use putExtra() to add new key/value pairs---            
		        i.putExtra("projectlist", urlimage);		  

		        i.putExtra("projects", projects);
		        
		        startActivityForResult(i, 2);

	    	}
	    	else {
		        actionbar.setTitle(currenttitle);
		    	adapter.notifyDataSetChanged();
	    	}
	          
	    }
	}
	

	
	

	private void showInputNameDialog(Context context,ArrayList<String> states) {
    	
		Log.d("showInputNameDialog","entrando 1");
    	final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.choose_report);
		dialog.setTitle(getString(R.string.report_type));

		Log.d("showInputNameDialog","entrando 2");
		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(getString(R.string.select));
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);
		Log.d("showInputNameDialog","entrando 3");
		if (states.size() > 0 ) {

			Spinner myselect = (Spinner)dialog.findViewById(R.id.selecttype);
			
			ArrayAdapter<String> myadapter;
			myadapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,states);
			
			myselect.setAdapter(myadapter);
			myadapter.notifyDataSetChanged();
			Log.d("showInputNameDialog","entrando 4");
		}

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		Log.d("showInputNameDialog","entrando 5");
		if (states.size() > 0 ) {
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("showInputNameDialog","entrando 6");
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
			
			
		}
		else {
			Log.d("showInputNameDialog","entrando 7");
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
		}

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
				getString(R.string.show_task), getString(R.string.delete_task) };
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
				changeStatusTask(context);
				break;
				case 1: //Eliminar
	
					
					break;
				case 2: // Ver
					makeDeleteOptionsDialog();
					break;
				default:
					break;
				}
			}

		});     

		return builder.create();
	}
	
    public void loadSafetReport(String report) {
    	String currreport = "vToDo";
    	Log.d("Spinner","2:|" + report+"|");
    	if ( report.contentEquals("En progreso")) {
    		Log.d("Spinner","3:" + report);
    		currreport = "vProgress";
    	}
    	else if (report.contentEquals("Pospuestos") ) {
    		currreport = "vPostponed";	
    	}
    	else if (report.contentEquals("Finalizados") ) {
    		currreport = "vFinished";
    	}
    	

    	
    	String myconsult = PrincipalActivity.URL_API + "operacion:Listar_datos%20"+
    			"Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratres.xml%20Variable:"+currreport;


    	GetGraphTask mytask = new GetGraphTask("listar_ticket");

    	mytask.execute(myconsult);
    	
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
	
	public void changeStatusTask(Context context) {
	
		
		String myconsult = PrincipalActivity.URL_API +
		"operacion:Siguientes_estados%20Cargar_archivo_flujo:"+
				"/home/panelapp/.safet/flowfiles/carteleratresf.xml%20Clave:" + currentticket;

		Log.d("setOnItemLongClickListener..myconsult:",myconsult);
		
		ArrayList<String> newstates = loadStatus(changeTicketStatus(myconsult));
		Log.d("ItemLong","newstates.count:"+ String.valueOf(newstates.size()));
		//showStatusDialog(newstates);
		showInputNameDialog(context,newstates);
		Log.d("showstatus","showstatus");		

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
				showInputNameDialog(view.getContext(),mylist);	
				 Log.d("DialogLog", "2");
		
				
						
			}
			
			

		});

		
		newticketbutton  = (ImageButton) findViewById(R.id.buttonnewticket);

		Log.d("newticketbutton","newticketbutton");
		
		newticketbutton.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View view) {
				String myconsult = PrincipalActivity.URL_API 
						+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/proyectos.xml"+
		    			"%20Variable:vProyectos";

		    	GetGraphTask mytask = new GetGraphTask("listar_proyectos");
		    	
		    	mytask.execute(myconsult);
		    	

							
			}
		});
		

		
		
		graphbutton = (ImageButton) findViewById(R.id.buttongraph);
		
		graphbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String myconsult = PrincipalActivity.URL_API +"operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratres.xml"
						+ "%20configurekey.Plugins.Graphviz/plugins.graphviz.graphtype:png";

					GetGraphTask mytask = new GetGraphTask("graficar");
					
						mytask.execute(myconsult);

			}
			
		});
		
		
	}


	public void onActivityResult(int requestCode, 
            int resultCode, Intent data)
    {
        //---check if the request code is 1---
		Log.d("requestCode:",String.valueOf(requestCode));
		Log.d("requestCode:",String.valueOf(resultCode));
        if (resultCode == 1) {

        	
            //---if the result is OK--- 
            if (resultCode == RESULT_OK) {

                //---get the result using getIntExtra()---
//                Toast.makeText(this, Integer.toString(
//                        data.getIntExtra("age3", 0)), 
//                        Toast.LENGTH_SHORT).show();      
//
//                //---get the result using getData()---
//                Uri url = data.getData();
//                Toast.makeText(this, url.toString(), 
//                        Toast.LENGTH_SHORT).show();
            }            
        }
        else if (requestCode == 2) {
        	if (resultCode == 3) {
        		String urlform = PrincipalActivity.URLFORM_API + data.getStringExtra("urlform");
        		Log.d("PrincipalActivity urlform:",urlform);
            	GetGraphTask mytask = new GetGraphTask("agregar_ticket");

            	mytask.execute(urlform);

        		 
        		
        	}
        	
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
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

}
