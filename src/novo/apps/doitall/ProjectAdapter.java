package novo.apps.doitall;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

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

import novo.apps.doitall.AddTicketActivity.GetGraphTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.*;


public class ProjectAdapter extends BaseAdapter {
    private final Activity context;
    private  ArrayList<ProjectRecord> projects;
    private ProgressDialog progress;

    public ProjectAdapter(Activity context) {        
       this.context = context; 
		progress = new ProgressDialog(context);
		progress.setIcon(R.drawable.logo);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    static class ViewContainer1 {
        public TextView txtProject;
        public CheckBox selected;
    }
    public void setProjects(ArrayList<ProjectRecord> t) {
    	projects = t;
    }
    
    
    

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewContainer1 viewContainer;
        View rowView = view;

        //---print the index of the row to examine---
        Log.d("ProjectAdapter",String.valueOf(position));

        //---if the row is displayed for the first time---
        if (rowView == null) {

            Log.d("ProjectAdapter", "New");
            
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.checkrow, null, true);

            //---create a view container object---
            viewContainer = new ViewContainer1();        

            //---get the references to all the views in the row---
            viewContainer.txtProject = (TextView) 
                rowView.findViewById(R.id.labelproject); 
            viewContainer.selected = (CheckBox) 
                rowView.findViewById(R.id.checkproject); 
            
            //---assign the view container to the rowView---
            rowView.setTag(viewContainer);
            viewContainer.selected.setOnClickListener( new View.OnClickListener() {  
                public void onClick(View v) {  
                  CheckBox cb = (CheckBox) v ;
                  if (cb.isChecked()) {
                	  Log.d("Check","on");
                	  callModifyProject(true,projects.get(position).getProjectid());
                  }
                  else {
                	  Log.d("Check","off");
                	  callModifyProject(false,projects.get(position).getProjectid());
                  }
                }  
              });         
        } else { 

            //---view was previously created; can recycle---            
            Log.d("ProjectAdapter", "Recycling");
            //---retrieve the previously assigned tag to get
            // a reference to all the views; bypass the findViewByID() process,
            // which is computationally expensive---
            viewContainer = (ViewContainer1) rowView.getTag();
        }

        
        //---customize the content of each row based on position---
        viewContainer.txtProject.setText(projects.get(position).getTitle());
        
        viewContainer.selected.setChecked(projects.get(position).isSelected());
       
       // viewContainer.txtDescription.setText(presidents[position] + 
       //     " ...Some descriptions here...");
        //viewContainer.imageView.setImageResource(imageIds[position]);
        return rowView;
    }


   public void callModifyProject(boolean state, String currid) {
	    String realstate = "true";
	    
	    if (!state) {
	    	realstate = "false";
	    }
		String myconsult = PrincipalActivity.URLFORM_API + 
				"operacion:modificar_proyecto id:"+currid +" disponible:"+ realstate;
		
		myconsult = myconsult.replace(" ","%20");
		Log.d("modificar proyecto...consult:","|"+myconsult+"|");
		
		new GetGraphTask("modificar_proyecto").execute(myconsult);

	   
   }
	final class GetGraphTask extends AsyncTask<String, Void, String> {

		private String consult;
		private String resulting;
		
		public GetGraphTask(String c) {
			
	        super();
	        consult = c;
	    }
		
		 protected void onPreExecute() {
			  
			 	progress.setMessage(context.getString(R.string.downloading));
		      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
			    	Toast toast = Toast.makeText(context, 
			    			"Error en la conexión:" + e.getMessage(), Toast.LENGTH_SHORT);
			    	toast.show();
		        
		        } catch (IOException e) {
			    	Toast toast = Toast.makeText(context, 
			    			"Error de lectura de datos:" + e.getMessage(), Toast.LENGTH_SHORT);
			    	toast.show();
		        
		        
		 		} catch (Exception e) {
			    	Toast toast = Toast.makeText(context, 
			    			"Error:" + e.getMessage(), Toast.LENGTH_SHORT);
			    	toast.show();
		 			
		 		}
		        return responseString;
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



			resulting = callPlainSAFETAPI(urldisplay);
			progress.setProgress(50);
	        return resulting;
	    }
		
		

	    protected void onPostExecute(String result) {
	    	Log.d("Resultado","Hecho");
	    	

	    	progress.setProgress(100);
	    	progress.dismiss();
	    	
	    	Log.d("GetGraphTask result",consult);
	        Log.d("ProjectCount:",String.valueOf(projects.size()));
	        
	    	if (consult.startsWith("modificar_proyecto")) {
	    		
		    	Toast toast = Toast.makeText(context, 
		    			"Se modificó el estado del proyecto", Toast.LENGTH_SHORT);
		    	toast.show();
	    		
	    	}

	    	else {
	    		Log.d("No option","else");
	    	}
	          
	    }
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (projects == null ) 
			return 0;
		
		return projects.size();
	}




	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (projects == null ) 
			return null;

		return projects.get(position);
	}




	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}


