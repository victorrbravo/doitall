package novo.apps.doitall;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import novo.apps.doitall.AddTicketActivity.GetGraphTask;

import novo.apps.doitall.R;

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





//import novo.apps.doitall.PlaceholderFragment;
//import com.example.testmodernmenu.DrawerLayout;
//import com.example.testmodernmenu.FragmentManager;
//import com.example.testmodernmenu.NavigationDrawerFragment;
//import com.example.testmodernmenu.MainActivity.PlaceholderFragment;
//import com.example.testmodernmenu.MainActivity;
//import com.example.testmodernmenu.MainActivity.PlaceholderFragment;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.FontFactory;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;



public class PrincipalActivity extends ActionBarActivity  {

	private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera, R.drawable.composer_music,
		R.drawable.composer_place, R.drawable.composer_sleep, R.drawable.composer_thought, R.drawable.composer_with };

	private ImageButton newticketbutton;
	private ImageButton button;
	private ImageButton graphbutton;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;
	  private ListView mDrawerList;


	private ProgressDialog progress;
	private ActionBar actionbar;
	private android.support.v4.app.FragmentManager fragmentManager;
	private SimpleListFragment fragment;

	AdvancedCustomArrayAdapter adapter;
	ArrayList<TicketRecord> tickets;
	ArrayList<ProjectRecord> projects;
	ArrayList<String> users;
	private String currenttitle;
	private String urlimage;
	private String alljson;
	private String graphtitle;
	private ListView listtickets;
	private String currentticket;
	private TicketRecord recordticket;
	private String currentprojectid;
	private String currentdate;
	private String currentuser;
	private String currentauth;
	private String lastlisttickets;
	private String currfile;
	private String currreport;
	private int posMenuGraph; 
	AlertDialog task_dialog;

	private ArrayList<String> newstates;
	private CanvasView noteView;
	private NotificationManager myNotificationManager;

	private AlarmManagerBroadcastReceiver alarm;

	private ArrayList<TicketRecord> ticketsforNotify;

	public static String URL_SERVER = "http://XXXXXXXXXXXX/intranet/register";
	public static String URL_SERVER_LOGIN = "http://XXXXXXXXXXXX/intranet/login";
	public static String FIRST_URL_GRAPH = "http://XXXXXXXXXXXX/media/";
	public static String FIRST_URL_API = "http://XXXXXXXXXXXX/intranet/apiv2/";
	public static String SECOND_URL_API = "/?tipoaccion=console&aplicacion=panelapp&accion=";
	public static String SECOND_URLFORM_API = "/?tipoaccion=form&aplicacion=panelapp&accion=";
	public static String FLOWFILES_DIR = "/home/panelapp/.safet/flowfiles/";

	public static String PARAMETER_BY_PROJECT;
	public static String PARAMETER_BY_TYPE;
	public static String PARAMETER_BY_DATE1;
	public static String PARAMETER_BY_DATE2;
	public static String PARAMETER_BY_SEARCH;
	public static String DESCRIPTION_PROJECT_PARAMS;
	public static String DESCRIPTION_TYPE_PARAMS;
	public static String DESCRIPTION_DATE1_PARAMS;

	public static int isclosing;
	public static String URL_API;
	public static String URLFORM_API;
	public static final String CURRENTDATEFORMAT = "dd/MMM/yyyy hh:mma";
	public static final String FILE_NOTIFICATIONS = "notifications.txt";
	public static final String ONLYDATEFORMAT = "dd/MMM/yyyy";
	Map<String, String> usersmap;

	private SectionAdapter menuAdapter;
	// Data for notification

	private static int notificationIdOne = 111;
	private int notificationIdTwo = 112;
	private int numMessagesOne = 0;
	private int numMessagesTwo = 0;

	TimePicker myTimePicker;
	TimePickerDialog timePickerDialog;

	final static int RQS_1 = 1;

	private static final int LIMITMENUREPORTS = 6;
	private static final int LIMITMENUGRAPHS = 6;
	// used for register alarm manager
	PendingIntent pendingIntent;
	// used to store running alarmmanager instance
	AlarmManager alarmManager;
	// Callback function for Alarmmanager event
	BootReceiver mReceiver;
	AlarmReceiver aReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PARAMETER_BY_PROJECT = "";
		PARAMETER_BY_TYPE = "";
		PARAMETER_BY_DATE1 = "";
		PARAMETER_BY_DATE2 = "";
		PARAMETER_BY_SEARCH = "";
		DESCRIPTION_PROJECT_PARAMS = "";
		DESCRIPTION_TYPE_PARAMS = "";
		DESCRIPTION_DATE1_PARAMS = "";

		posMenuGraph = 0;
		
		mTitle = getTitle();

		alarm = new AlarmManagerBroadcastReceiver();

		

		
		isclosing = 0;
		Log.d("PrincipalActivity", "OnCreate");

		if (getIntent().getStringExtra("function") != null) {
			Log.d("PrincipalActivity", "OnCreate...YES!");
			displayNotificationOne();
			return;

		}

		usersmap = new HashMap<String, String>();

		actionbar = getSupportActionBar();
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);


		currentuser = getIntent().getStringExtra("selectuser");
		String ticket = getIntent().getStringExtra("selectauth");
		String pass = getIntent().getStringExtra("selectpass");

		currentauth = ticket;

		ticketsforNotify = new ArrayList<TicketRecord>();

		Log.d("PrincipalActivity", "currentuser (pass):" + currentuser);
		Log.d("PrincipalActivity", "currentauth(pass):" + currentauth);
		URL_API = FIRST_URL_API + currentauth + SECOND_URL_API;
		URLFORM_API = FIRST_URL_API + currentauth + SECOND_URLFORM_API;
		Log.d("PrincipalActivity", "URL_API" + URL_API);
		Log.d("PrincipalActivity", "URLFORM_API" + URLFORM_API);

		// actionBar.setSubtitle("Tareas pendientes");

		actionbar.setDisplayShowTitleEnabled(true);
		// actionbar.setDisplayHomeAsUpEnabled(true);

		currentticket = "";
		recordticket = null;

		currentprojectid = "";
		currentdate = "";
		urlimage = "";
		lastlisttickets = "";

		Log.d("PrincipalActivity", "**1");
		setContentView(R.layout.activity_principal);
		Log.d("PrincipalActivity", "**2");

		tickets = new ArrayList<TicketRecord>();
		projects = new ArrayList<ProjectRecord>();
		users = new ArrayList<String>();
		Log.d("Advance", "1");
		adapter = new AdvancedCustomArrayAdapter(this, currentuser);
		Log.d("Advance", "2");
		listtickets = (ListView) findViewById(R.id.listTasks);
		listtickets.setAdapter(adapter);
		listtickets.setEmptyView(findViewById(R.id.emptymessage));

		Log.d("Advance", "3");
		progress = new ProgressDialog(this);
		progress.setIcon(R.drawable.logo);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		addListenerOnButton();
		loadSafetReport("Por_hacer");

		Log.d("noteView", "....(1)..");

		noteView = (CanvasView) findViewById(R.id.viewSticky);

		noteView.setVisibility(View.GONE);
		noteView.setCurrentuser(currentuser);

		Log.d("noteView", "....(2)..");
        final int itemCount = ITEM_DRAWABLES.length;
        
        
        Log.d("create","....1...");
        
        mTitle = mDrawerTitle = getTitle();
        Log.d("create","....2...");
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        Log.d("create","....3...");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        Log.d("create","....4...");
        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        Log.d("create","....4*...");

        loadMenuAdapter();
        
        mDrawerList.setAdapter(menuAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Log.d("create","....5...");
        // enable ActionBar app icon to behave as action to toggle nav drawer

        Log.d("create","....6...");
        mDrawerToggle = new ActionBarDrawerToggle(
        		this, 
        		mDrawerLayout,
                R.drawable.ic_drawer, 
                R.string.drawer_open, 
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            	Log.d("DrawerToggle","...close....");
                super.onDrawerClosed(view);
                actionbar.setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	Log.d("DrawerToggle","...open...." + mDrawerTitle);
                super.onDrawerOpened(drawerView);
                
                actionbar.setTitle(mDrawerTitle);
                Log.d("DrawerToggle","...open...2");
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                Log.d("DrawerToggle","...open...3");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        if (savedInstanceState == null) {
//            selectItem(0);
//        }
	}
	
	private void loadMenuAdapter() {
		// TODO Auto-generated method stub
		menuAdapter = new SectionAdapter(this);
		menuAdapter.addSectionHeaderItem(getString(R.string.tasks_title));
		String[] reports = getResources().getStringArray(R.array.names_report);
		
		posMenuGraph = 1;
		for (int i=0; i< reports.length && i < LIMITMENUREPORTS; i++) {
			menuAdapter.addItem(reports[i]);
			posMenuGraph = posMenuGraph + 1;
		}
		
		
		menuAdapter.addSectionHeaderItem(getString(R.string.graph_type));
		String[] graphs = getResources().getStringArray(R.array.graph_report);
		
		for (int i=0; i< graphs.length && i < LIMITMENUGRAPHS; i++) {
			menuAdapter.addItem(graphs[i]);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);

		// When using the support library, the setOnActionExpandListener()
		// method is
		// static and accepts the MenuItem object as an argument

		MenuItem searchItem = menu.findItem(R.id.action_search);

		final SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				if (newText.length() > 0) {
					// Search
					Log.d("Search", "Search (1)");

				} else {
					// Do something when there's no input

				}
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {

				Log.d("Search", "Search (3)");
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

				Toast.makeText(getBaseContext(), "Búsqueda realida con éxito",
						Toast.LENGTH_SHORT).show();
				setSupportProgressBarIndeterminateVisibility(true);

				Log.d("Search", "Search (4)");

				currfile = FLOWFILES_DIR + "cartelerabusqueda.xml";
				currreport = "vBusqueda";
				String newquery = query.replace(" ", "%20");
				PrincipalActivity.PARAMETER_BY_SEARCH = "%20parameters.ByPattern:"
						+ newquery;

				String myconsult = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport + "%20"
						+ PrincipalActivity.PARAMETER_BY_SEARCH + "%20"
						+ PrincipalActivity.PARAMETER_BY_PROJECT + "%20"
						+ PrincipalActivity.PARAMETER_BY_TYPE;

				Log.d("SEARCH CONSULT:", myconsult);
				new GetGraphTask("listar_ticket").execute(myconsult);

				return false;
			}
		});

		return true;
	}

	
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//    	Log.d("onPrepareOptionsMenu", "....1....");
//    	if (mDrawerLayout != null && mDrawerList != null ) {
//    		Log.d("onPrepareOptionsMenu", "....2....");
//    		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//    		Log.d("onPrepareOptionsMenu", "....3....");
//    		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
//    	}
//    	Log.d("onPrepareOptionsMenu", "....4....");
        return super.onPrepareOptionsMenu(menu);
    }



	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		
	    // ************* Before Screen
	
//		String mytext = "Position:" + String.valueOf(position) + "posMenuGraph:" +
//		String.valueOf(posMenuGraph);		
//    	Toast.makeText(getApplicationContext(), 
//    			mytext, Toast.LENGTH_SHORT).show();
		
		if (position == 0) {
			mDrawerLayout.closeDrawer(mDrawerList);
			if (menuAdapter != null) {
				menuAdapter.setHas_separator(false);
			}
			ArrayList<String> mylist = new ArrayList<String>();
			showInputNameDialog(this, mylist,
					getString(R.string.ticket_list_title));
			
		}    	
    	else if (position> 0 && position <  posMenuGraph ) {
			String[] reports = getResources().getStringArray(R.array.names_report);
			loadSafetReport(reports[position-1]);
			
		}
    	else if (position == posMenuGraph) {
    		mDrawerLayout.closeDrawer(mDrawerList);
    		if (menuAdapter != null) {
				menuAdapter.setHas_separator(false);
			}
    		showGraphChooseDialog(this);

    	}
    	else {
			executeGraph(position-posMenuGraph-1);
    		
    	}

	    // ************* Before Screen
	    // *************Create a new fragment and specify the planet to show based on position
		
//	    Fragment fragment = new PlanetFragment();
//	    Bundle args = new Bundle();
//	    args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//	    fragment.setArguments(args);
//
//	    // Insert the fragment by replacing any existing fragment
//	    FragmentManager fragmentManager = getSupportFragmentManager();
//	    fragmentManager.beginTransaction()
//	                   .replace(R.id.content_frame1, fragment)
//	                   .commit();
//
//	    // Highlight the selected item, update the title, and close the drawer
//	    mDrawerList.setItemChecked(position, true);
//	    setTitle(mPlanetTitles[position]);
	    // *************Create a new fragment and specify the planet to show based on position
	    mDrawerLayout.closeDrawer(mDrawerList);
	    if (menuAdapter != null) {
			menuAdapter.setHas_separator(false);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    actionbar.setTitle(mTitle);
	}

	// @Override
	// protected void onDestroy() {
	// unregisterReceiver(mReceiver);
	// unregisterReceiver(aReceiver);
	// super.onDestroy();
	// }
	//

	public class AlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("Doitall",
					"Yes Yes ahhh!!...BroadcastReceiver::OnReceive() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			// Toast.makeText(context,
			// "Congrats!. Your Alarm time has been reached",
			// Toast.LENGTH_LONG).show();
			Toast.makeText(context, "AlarmReceiver**", Toast.LENGTH_LONG)
					.show();

			displayNotificationOne();
		}

	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	public void displayNotificationOne() {

		// Invoking the default notification service
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this);

		mBuilder.setContentTitle("Tienes una tarea");
		if (recordticket != null) {
			mBuilder.setContentText(recordticket.getSummary());
		} else {
			mBuilder.setContentText("Hay un mensaje");
		}
		mBuilder.setTicker("Nuevo mensaje!");
		mBuilder.setSmallIcon(R.drawable.ic_launcher);

		// Increase notification number every time a new notification arrives
		mBuilder.setNumber(1);

		// Creates an explicit intent for an Activity in your app

		Intent resultIntent = new Intent(this, NotificationOne.class);

		resultIntent.putExtra("notificationId", 111);

		// This ensures that navigating backward from the Activity leads out of
		// the app to Home page
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

		// Adds the back stack for the Intent
		stackBuilder.addParentStack(PrincipalActivity.class);

		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_ONE_SHOT // can only be used once
				);
		// start the activity when the user clicks the notification text
		mBuilder.setContentIntent(resultPendingIntent);

		myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// pass the Notification object to the system
		myNotificationManager.notify(111, mBuilder.build());
	}

	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments

		Log.d("drawer", "drawer");
		if (fragmentManager.findFragmentById(android.R.id.content) == null) {
			fragmentManager.beginTransaction()
					.add(android.R.id.content, new SimpleListFragment())
					.commit();
		} else {
			fragmentManager.beginTransaction()
					.replace(R.id.container, fragment).commit();
		}
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(getString(R.string.close_app))
				.setMessage(getString(R.string.question_close))
				.setPositiveButton(getString(R.string.yes_option),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Log.d("closing", "Closing all");
								Intent i = new Intent();
								setResult(2, i);
								finish();
							}

						})
				.setNegativeButton(getString(R.string.no_option), null).show();
	}

	

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
		private boolean showsticky;

		public boolean isShowsticky() {
			return showsticky;
		}

		public void setShowsticky(boolean showsticky) {
			this.showsticky = showsticky;
		}

		private boolean modifyproject;

		public boolean isModifyproject() {
			return modifyproject;
		}

		public void setModifyproject(boolean modifyproject) {
			this.modifyproject = modifyproject;
		}

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
			modifyproject = false;
			showsticky = false;
			addticket = false;

		}

		protected void onPreExecute() {
			progress.setMessage(getString(R.string.downloading));
			// progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress.setIndeterminate(true);
			progress.show();

		}

		private String callPlainSAFETAPI(String urldisplay) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;
			String result = "";
			try {

				response = httpclient.execute(new HttpGet(urldisplay));
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseString = out.toString();
				} else {
					// Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				result = "SafetError:ClientProtocol:" + e.getMessage();
				return result;

			} catch (IOException e) {
				result = "SafetError:IOException:" + e.getMessage();
				return result;

			} catch (Exception e) {
				result = "SafetError:Exception:" + e.getMessage();
				return result;

			}
			return responseString;
		}

		private String callSAFETAPI(String urldisplay) {
			String result = "";
			URL url;

			try {
				// url = new
				// URL("https://gestion.cenditel.gob.ve/intranet/api/f3bf4ca25e666d70d6f847b87f448fefba5f2fda/?tipoaccion=console&aplicacion=victorrbravo&accion=operacion:Generar_grafico_coloreado%20Cargar_archivo_flujo:/home/victorrbravo/.safet/flowfiles/flujogeneralsesiones.xml");
				url = new URL(urldisplay);
				// url = new URL("http://www.google.com");
				Log.d("SAFETAPI", url.toString());
				progress.setProgress(10);
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs,
							String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs,
							String authType) {
					}
				} };
				// Install the all-trusting trust manager
				final SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc
						.getSocketFactory());
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

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, "Latin-1"));
				String webPage = "", data = "";
				while ((data = reader.readLine()) != null) {
					webPage += data;
				}

				result = webPage;
				Log.d("callSAFETAPI", "result:|" + webPage + "|");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String connerr = "SafetError:" + e.toString();
				Log.d("LLamada SAFET ", connerr);

				return connerr;

			}

			return result;
		}

		protected String doInBackground(String... urls) {
			String urldisplay = urls[0];

			Log.d("mygraph", "*callPlainSAFETAPI...(1)");
			urldisplay = urldisplay.replace("á", "%E1");
			urldisplay = urldisplay.replace("é", "%E9");
			urldisplay = urldisplay.replace("í", "%ED");
			urldisplay = urldisplay.replace("ó", "%F3");
			urldisplay = urldisplay.replace("ú", "%FA");
			urldisplay = urldisplay.replace("ñ", "%F1");
			urldisplay = urldisplay.replace("Ñ", "%D1");
			Log.d("urldisplay", urldisplay);
			try {

				resulting = callPlainSAFETAPI(urldisplay);
				if (resulting.startsWith("SafetError:")) {
					String mysafeterror = "SafetError: ";
					String mymessage = resulting.substring(resulting.indexOf(
							":", mysafeterror.length()));
					Toast.makeText(getApplicationContext(), mymessage,
							Toast.LENGTH_LONG).show();
					return "";

				}
				Log.d("mygraph", "callPlainSAFETAPI...(2)");
				// Log.d("mygraph","resulting:" + resulting);
				// resulting = callSAFETAPI(urldisplay);
				progress.setProgress(50);

				if (consult.contentEquals("cambiar_estado")) {
					Log.d("LastListTickets", lastlisttickets);

					// resulting = callPlainSAFETAPI(lastlisttickets);
					// consult = "cambiar_estado_relistar_ticket";
				} else if (consult.contentEquals("agregar_ticket")) {
					Log.d("**agregar ticket LastListTickets", lastlisttickets);

					// resulting = callPlainSAFETAPI(lastlisttickets);
					// consult = "agregar_ticket_relistar_ticket";

				} else if (consult.contentEquals("borrar_ticket")) {
					// Log.d("borrar_ticket",lastlisttickets);

					// resulting = callPlainSAFETAPI(lastlisttickets);
					// consult = "borrar_ticket_relistar_ticket";
				} else if (consult.contentEquals("modificar_ticket")) {
					Log.d("modificar_ticket", lastlisttickets);

					// resulting = callPlainSAFETAPI(lastlisttickets);
					// consult = "modificar_ticket_relistar_ticket";
				}

				Log.d("SAFETCALLAPI from execute()", resulting);
				if (consult.endsWith("ver_ticket")) {
					Log.d("ver_ticket", "resulting");

				} else if (consult.endsWith("siguientes_estados")) {
					newstates = loadStatus(resulting);
					Log.d("changeStatusTask",
							"newstates.count:"
									+ String.valueOf(newstates.size()));
					// showStatusDialog(newstates);
				} else if (consult.endsWith("listar_usuarios")) {

					users.clear();
					Log.d("listar_usuarios", resulting);
					try {
						JSONArray jArray = new JSONArray(resulting);

						for (int i = 0; i < jArray.length(); i++) {
							String myuser = jArray.getString(i);

							users.add(myuser);

						}
					} catch (Exception e) {
						Log.e("JSON users", "Ocurrio el error:" + e.toString());
						// e.printStackTrace();

					}

					Log.d("users", "count:" + String.valueOf(users.size()));

				} else if (consult.endsWith("listar_ticket")) {

					tickets.clear();
					Log.d("PrincipalActivity", "after SAFETCALLAPI");

					lastlisttickets = urldisplay;

					try {
						JSONObject jall = new JSONObject(resulting);

						JSONArray jArray = jall.getJSONArray("safetlist");
						for (int i = 0; i < jArray.length(); i++) {

							JSONObject json_data = jArray.getJSONObject(i);
							TicketRecord ticket = new TicketRecord();
							// String summary =
							// URLDecoder.decode(json_data.getString("resumen"),
							// "UTF-8");
							String summary = json_data.getString("resumen");

							Log.i("jsonarray", "resumen:" + summary);
							String desc = json_data.getString("descripcion");
							Log.i("jsonarray", "descripcion:" + desc);
							String id = json_data.getString("id");
							ticket.setId(id);
							ticket.setSummary(summary);
							ticket.setDescription(desc);
							ticket.setProject(json_data.getString("proyecto"));
							ticket.setNotifyid(Integer.parseInt(json_data
									.getString("notifyid")));
							ticket.setAssignto(json_data.getString("assignto"));
							ticket.setAssignfrom(json_data
									.getString("assignfrom"));
							ticket.setProjectid(Integer.valueOf(json_data
									.getString("projectid")));
							ticket.setTentativedate(PrincipalActivity
									.convertDateEpochToFormat(json_data
											.getString("tentativedate")));
							ticket.setEpochtentativedate(Long
									.parseLong(json_data
											.getString("tentativedate")));
							String newfinish = json_data
									.getString("finishdate");
							if (newfinish.contentEquals("0")) {
								ticket.setFinishdate(getString(R.string.nocomplete));
								ticket.setEpochfinishdate(0l);
							} else {
								ticket.setFinishdate(PrincipalActivity
										.convertDateEpochToFormat(newfinish));
								ticket.setEpochfinishdate(Long
										.parseLong(newfinish));

							}
							ticket.setOwner(json_data.getString("owner"));
							ticket.setType(json_data.getString("type"));
							ticket.setStatus(json_data.getString("status"));

							tickets.add(ticket);
							urlimage = "";

						}
						currenttitle = jall.getString("safetvariable");

						Log.d("checkpoint", "1");
						adapter.setTickets(tickets);
						Log.d("checkpoint", "2");

					} catch (Exception e) {
						Log.e("JSON", "Ocurrio el error:" + e.toString());
						// e.printStackTrace();

					}
				} else if (consult.endsWith("listar_proyectos")) {

					projects.clear();

					try {
						JSONObject jall = new JSONObject(resulting);

						JSONArray jArray = jall.getJSONArray("safetlist");
						for (int i = 0; i < jArray.length(); i++) {

							JSONObject json_data = jArray.getJSONObject(i);
							ProjectRecord project = new ProjectRecord();
							// String summary =
							// URLDecoder.decode(json_data.getString("resumen"),
							// "UTF-8");
							String projectid = json_data.getString("projectid");
							String title = json_data.getString("title");
							String desc = json_data.getString("description");
							String type = json_data.getString("type");

							project.setProjectid(projectid);
							project.setDescription(desc);
							project.setTitle(title);
							project.setType(type);
							String enable = json_data.getString("enable");
							Log.d("listar_enable", enable);
							if (enable.contentEquals("No")) {
								project.setSelected(false);
							} else {
								project.setSelected(true);
							}

							urlimage = "";
							projects.add(project);
						}

					} catch (Exception e) {
						Log.e("JSON", "Ocurrio el error:" + e.toString());
						// e.printStackTrace();

					}
				} else if (consult.contentEquals("graficar")) {
					JSONObject jall = null;
					String myfilename = "";
					alljson = resulting;

					try {
						jall = new JSONObject(resulting);
						Log.e("JSON", "GRAPHJSON:" + resulting);
						myfilename = jall.getString("filename");
						Log.e("JSON", "myfilename:" + myfilename);
						graphtitle = URLDecoder.decode(jall.getString("id"));
						// graphtitle = jall.getString("id");
						Log.e("Graph title", graphtitle);

					} catch (Exception e) {
						Log.e("JSON",
								"Ocurrio el error grafico:" + e.toString());
					}

					String mygraph = PrincipalActivity.FIRST_URL_GRAPH
							+ myfilename;
					Log.d("mygraph", mygraph);
					resulting = mygraph;
					urlimage = mygraph;
					Log.d("example", mygraph);
				}

				// new DownloadImageTask( (ImageView)
				// imagegraph).execute(mygraph);

				// Intent openBrowser = new Intent(Intent.ACTION_VIEW,
				// Uri.parse(mygraph));
				// startActivity(openBrowser);
			} catch (Exception e) {
				Log.d("...Bk", e.getMessage());
				// Toast.makeText(getApplicationContext(),
				// getString(R.string.no_connect_internet), Toast.LENGTH_LONG)
				// .show();
				return "";

			}
			return resulting;
		}

		protected TicketRecord processResulting(String resulting) {

			TicketRecord ticket = new TicketRecord();
			if (resulting.isEmpty()) {
				return ticket;
			}
			try {
				JSONObject jall = new JSONObject(resulting);

				JSONArray jArray = jall.getJSONArray("safetlist");
				for (int i = 0; i < jArray.length(); i++) {

					JSONObject json_data = jArray.getJSONObject(i);

					// String summary =
					// URLDecoder.decode(json_data.getString("resumen"),
					// "UTF-8");

					ticket.setSummary(json_data.getString("resumen"));
					ticket.setDescription(json_data.getString("descripcion"));
					ticket.setType(json_data.getString("tipo"));
					ticket.setProject(json_data.getString("proyecto"));
					ticket.setStatus(json_data.getString("status"));
					ticket.setOwner(json_data.getString("propietario"));
					ticket.setTentativedate(PrincipalActivity
							.convertDateEpochToFormat(json_data
									.getString("tentativedate")));
					ticket.setFinishdate(PrincipalActivity
							.convertDateEpochToFormat(json_data
									.getString("finishdate")));
					ticket.setAssignto(json_data.getString("assignto"));

				}

			} catch (Exception e) {
				Log.e("JSON View Ticket", "Ocurrio el error:" + e.toString());
				// e.printStackTrace();

			}

			return ticket;
		}

		protected void onPostExecute(String result) {
			Log.d("Resultado", "Hecho");

			if (result.isEmpty()) {

				progress.setProgress(100);
				progress.dismiss();

				Toast.makeText(getApplicationContext(),
						getString(R.string.no_connect_internet),
						Toast.LENGTH_LONG).show();
				return;
			}

			progress.setProgress(100);
			progress.dismiss();

			Log.d("onPostExecute result", consult);
			Log.d("**consult:", consult);

			if (consult.startsWith("cambiar_estado")) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Cambio de estado realizado", Toast.LENGTH_SHORT);
				toast.show();
				String myconsultdel = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;
				;
				new GetGraphTask("listar_ticket").execute(myconsultdel);

			} else if (consult.startsWith("siguientes_estados")) {
				Log.d("siguientes_estados", String.valueOf(newstates.size()));
				showStatesNameDialog(newstates,
						getString(R.string.state_list_title));
				Log.d("showstatus", "showstatus");

			} else if (consult.startsWith("ver_ticket")) {
				if (isShowsticky()) {
					Log.d("resulting", resulting);

					TicketRecord myticket = processResulting(resulting);
					Log.d("ver sticky", "(1)");
					noteView.setMessage(myticket);
					noteView.setVisibility(View.VISIBLE);
					noteView.renderText();
					Log.d("view click", "view click gone");

				} else {
					if (isAddticket()) {
						Intent i = new Intent(
								"novo.apps.doitall.ViewTicketActivity");

						i.putExtra("resulting", resulting);
						startActivity(i);
					} else {
						callPreListprojects(false, resulting, false);
					}
				}

			} else if (consult.startsWith("listar_usuarios")) {

				// Toast.makeText(getApplicationContext(),
				// "asignación creada", Toast.LENGTH_SHORT).show();
				Log.d("assign", "prev list users");

				showAssignToChooseDialog(getApplicationContext());

			} else if (consult.endsWith("agregar_asignacion")) {

				Toast toast = Toast.makeText(getApplicationContext(),
						"Asignación agregada", Toast.LENGTH_SHORT);
				toast.show();

				String myconsultdel = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;
				;

				new GetGraphTask("listar_ticket").execute(myconsultdel);

			} else if (consult.endsWith("aceptar_asignacion")) {

				Toast toast = Toast.makeText(getApplicationContext(),
						"Asignación aceptada", Toast.LENGTH_SHORT);
				toast.show();

				String myconsultdel = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;

				;

				new GetGraphTask("listar_ticket").execute(myconsultdel);

			} else if (consult.endsWith("eliminar_asignacion")) {

				Toast toast = Toast.makeText(getApplicationContext(),
						"Asignación eliminada", Toast.LENGTH_SHORT);
				toast.show();

				String myconsultdel = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;

				;

				new GetGraphTask("listar_ticket").execute(myconsultdel);

			} else if (consult.startsWith("agregar_ticket")) {
				Log.d("ticket agregado", consult);
				Toast toast = Toast.makeText(getApplicationContext(),
						"Tarea agregada", Toast.LENGTH_SHORT);
				adapter.notifyDataSetChanged();
				toast.show();

				String myconsultdel = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;

				;

				new GetGraphTask("listar_ticket").execute(myconsultdel);

			}

			else if (consult.startsWith("borrar_ticket")) {
				Log.d("postExecute", "Tarea eliminada");

				Toast toast = Toast.makeText(getApplicationContext(),
						"Tarea eliminada", Toast.LENGTH_SHORT);
				adapter.notifyDataSetChanged();
				toast.show();
				String myconsultdel = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;

				new GetGraphTask("listar_ticket").execute(myconsultdel);

			} else if (consult.startsWith("modificar_ticket")) {
				Log.d("postExecute", "Tarea modificada");

				Toast toast = Toast.makeText(getApplicationContext(),
						"Se modificó la fecha planeada de la tarea",
						Toast.LENGTH_SHORT);
				adapter.notifyDataSetChanged();
				toast.show();

				String myconsultdel = PrincipalActivity.URL_API
						+ "operacion:Listar_datos%20"
						+ "Cargar_archivo_flujo:%20" + currfile
						+ "%20Variable:" + currreport
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;

				new GetGraphTask("listar_ticket").execute(myconsultdel);

			}

			else if (consult.contentEquals("graficar")) {

				Log.d("urlimage", urlimage);

				Log.d("graph", "GraphButton");
				Intent i = null;
				i = new Intent("novo.apps.doitall.SecondActivity");

//				i = new Intent("novo.apps.doitall.WebActivity");
				// ---use putExtra() to add new key/value pairs---
				Log.d("alljson", alljson);
				i.putExtra("alljson", alljson);
				i.putExtra("str1", urlimage);
				i.putExtra("str2", graphtitle);

				startActivityForResult(i, 1);

			} else if (consult.contentEquals("listar_proyectos")) {
				Log.d("**ButtonNew", "executing addTicket");

				if (isModifyproject()) {
					callModifyProjectActivity();
				} else {
					callAddTicketActivity(isAddticket(), getDataticket());
				}
			}

			else if (consult.contentEquals("para_filtro_listar_proyectos")) {

				Log.d("makeFilterProjectDialog", "makeFilterProjectDialog");
				makeFilterProjectDialog();

			} else {
				actionbar.setTitle(currenttitle
						+ PrincipalActivity.DESCRIPTION_PROJECT_PARAMS
						+ PrincipalActivity.DESCRIPTION_TYPE_PARAMS.replace(
								"%20", " ")
						+ PrincipalActivity.DESCRIPTION_DATE1_PARAMS.replace(
								"%20", " "));
				adapter.notifyDataSetChanged();
			}

		}
	}

	public void callAddTicketActivity(boolean addticket, String dataticket) {

		Intent i = new Intent("novo.apps.doitall.AddTicketActivity");

		// ---use putExtra() to add new key/value pairs---
		i.putExtra("projectlist", urlimage);
		i.putExtra("projects", projects);
		i.putExtra("addticket", addticket);
		i.putExtra("dataticket", dataticket);

		startActivityForResult(i, 2);

	}

	public void callModifyProjectActivity() {

		Intent i = new Intent("novo.apps.doitall.ModifyProjectActivity");

		// ---use putExtra() to add new key/value pairs---
		i.putExtra("projects", projects);

		startActivityForResult(i, 6);

	}

	public void makeFilterProjectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.filter_project_dialog));

		ArrayList<String> mylist = new ArrayList();
		for (int i = 0; i < projects.size(); i++) {
			mylist.add(projects.get(i).getTitle());
		}
		final Spinner input = new Spinner(this);

		ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mylist);

		// ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_multiple_choice,mylist);

		// ProjectAdapter myadapter = new ProjectAdapter(this);
		// myadapter.setProjects(projects);
		input.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();

		builder.setView(input);

		builder.setPositiveButton(getString(R.string.accept),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do do my action here

						String selproject = input.getSelectedItem().toString();

						PrincipalActivity.DESCRIPTION_PROJECT_PARAMS = "-"
								+ selproject;

						Log.d("....makeFilter", "selproject" + selproject);

						int pos = input.getSelectedItemPosition();

						if (pos >= 0 && pos < projects.size()) {
							PrincipalActivity.PARAMETER_BY_PROJECT = "%20parameters.ByProject:"
									+ String.valueOf(projects.get(pos)
											.getProjectid());
							Log.d("PrincipalActivity.PARAMETER_BY_PROJECT",
									PrincipalActivity.PARAMETER_BY_PROJECT);
							// boxproject.setText(selproject);
						}

						actionbar.setTitle(currenttitle
								+ PrincipalActivity.DESCRIPTION_PROJECT_PARAMS
								+ PrincipalActivity.DESCRIPTION_TYPE_PARAMS
										.replace("%20", " ")
								+ PrincipalActivity.DESCRIPTION_DATE1_PARAMS
										.replace("%20", " "));

						currreport = "Todos";
						currfile = FLOWFILES_DIR + "ucartelerabusqueda.xml";

						String myconsultdel = PrincipalActivity.URL_API
								+ "operacion:Listar_datos%20"
								+ "Cargar_archivo_flujo:%20" + currfile
								+ "%20Variable:" + currreport
								+ PrincipalActivity.PARAMETER_BY_PROJECT
								+ PrincipalActivity.PARAMETER_BY_TYPE
								+ PrincipalActivity.PARAMETER_BY_DATE1
								+ PrincipalActivity.PARAMETER_BY_DATE2;

						new GetGraphTask("listar_ticket").execute(myconsultdel);

						// Toast toast = Toast.makeText(getApplicationContext(),
						// getString(R.string.need_refresh),
						// Toast.LENGTH_SHORT);
						// toast.show();

					}

				});

		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// I do not need any action here you might
						Log.d("makeFilterOptionsDialog...(1)", "...(1)");
						dialog.dismiss();
						ToggleButton boxproject = ((ToggleButton) findViewById(R.id.toggleProject));

						boxproject.setChecked(false);
						PrincipalActivity.DESCRIPTION_PROJECT_PARAMS = "";

						Log.d("makeFilterOptionsDialog...(1)", "...(2)");

					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void makeFiltertTypeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.filter_type_dialog));

		final Spinner input = new Spinner(this);

		String[] mytypes = getResources().getStringArray(
				R.array.names_type_ticket);

		ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mytypes);

		input.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();

		builder.setView(input);

		builder.setPositiveButton(getString(R.string.accept),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do do my action here
						ToggleButton boxtype = (ToggleButton) findViewById(R.id.toggleType);

						String seltype = input.getSelectedItem().toString();

						Log.d("makeFilterType", "seltype" + seltype);

						int pos = input.getSelectedItemPosition();

						// boxtype.setText(seltype);
						seltype = seltype.replace(" ", "%20");
						PrincipalActivity.PARAMETER_BY_TYPE = "%20parameters.ByType:"
								+ seltype;
						PrincipalActivity.DESCRIPTION_TYPE_PARAMS = "-"
								+ seltype;
						Log.d("BoxType", "seltype:" + seltype);

						actionbar.setTitle(currenttitle
								+ PrincipalActivity.DESCRIPTION_PROJECT_PARAMS
								+ PrincipalActivity.DESCRIPTION_TYPE_PARAMS
										.replace("%20", " "));
						Toast toast = Toast.makeText(getApplicationContext(),
								getString(R.string.need_refresh),
								Toast.LENGTH_SHORT);
						toast.show();

					}

				});

		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// I do not need any action here you might
						Log.d("makeFilterOptionsDialog...(1)", "...(1)");
						dialog.dismiss();
						ToggleButton boxtype = ((ToggleButton) findViewById(R.id.toggleType));

						boxtype.setChecked(false);

						PrincipalActivity.PARAMETER_BY_TYPE = "";
						PrincipalActivity.DESCRIPTION_TYPE_PARAMS = "";
						Log.d("makeFilterOptionsDialog...(1)", "...(2)");

					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showAssignToChooseDialog(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// builder.setTitle(getString(R.string.assign_to));
		builder.setTitle("Usuario a asignar (" + String.valueOf(users.size())
				+ ")");

		final EditText input = new EditText(this);

		ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, users);

		// ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_multiple_choice,mylist);

		// ProjectAdapter myadapter = new ProjectAdapter(this);
		// myadapter.setProjects(projects);
		// input.setAdapter(myadapter);
		// myadapter.notifyDataSetChanged();

		builder.setView(input);

		builder.setPositiveButton(getString(R.string.accept),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do do my action here
						String touser = input.getText().toString().trim();
						if (touser.isEmpty()) {
							Toast toast = Toast.makeText(
									getApplicationContext(),
									getApplicationContext().getString(
											R.string.error_user, null),
									Toast.LENGTH_SHORT);
							toast.show();

							return;
						}

						if (!users.contains(touser)) {
							Toast toast = Toast.makeText(
									getApplicationContext(), "El usuario  \""
											+ touser
											+ "\" no existe en DoItAll",
									Toast.LENGTH_SHORT);
							toast.show();

							return;
						}

						String idproyecto = currentprojectid;
						String idticket = currentticket;
						String myconsult = PrincipalActivity.URLFORM_API
								+ "operacion:agregar_asignacion%20" + "id:%20"
								+ idticket + "%20" + "para:%20" + touser;

						Log.d("assign_to", "myconsult:" + myconsult);

						new GetGraphTask("agregar_asignacion")
								.execute(myconsult);

					}

				});

		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// I do not need any action here you might
						Log.d("makeFilterOptionsDialog...(1)", "...(1)");
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();

	}
	
	private void executeGraph(int position)  {
		String myconsult;
		String[] reports = getResources().getStringArray(R.array.names_report);
		String myselect = reports[position];
		
		
		Log.d("executeGraph", "position:" + String.valueOf(position));
	
	
		if (position == 0) {

			if (PrincipalActivity.PARAMETER_BY_PROJECT
					.indexOf("ByProject") > 0) {
				myconsult = PrincipalActivity.URL_API
						+ "operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20"
						+ FLOWFILES_DIR + "uocarteleratres.xml"
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;

			} else {
				myconsult = PrincipalActivity.URL_API
						+ "operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20"
						+ FLOWFILES_DIR + "ocarteleratres.xml"
						+ PrincipalActivity.PARAMETER_BY_PROJECT
						+ PrincipalActivity.PARAMETER_BY_TYPE
						+ PrincipalActivity.PARAMETER_BY_DATE1
						+ PrincipalActivity.PARAMETER_BY_DATE2;

			}
			Log.d("**GRAPHFORDATE", myconsult);
		} else if (position == 1) {
			myconsult = PrincipalActivity.URL_API
					+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/cartelerafiltros.xml%20Autofiltro:por_finalizado%20"					
					+ PrincipalActivity.PARAMETER_BY_PROJECT
					+ PrincipalActivity.PARAMETER_BY_TYPE
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;
			

		} else if (position == 2) {
			myconsult = PrincipalActivity.URL_API
//					+ "operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleraproximos.xml"
					+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleraplanificados.xml%20Autofiltro:por_tentativo%20parameters.ByPeriod:week%20"					
					+ PrincipalActivity.PARAMETER_BY_PROJECT
					+ PrincipalActivity.PARAMETER_BY_TYPE
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;

		} else if (position == 3) {
			myconsult = PrincipalActivity.URL_API
//					+ "operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/cartelerapormes.xml"
+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleraplanificados.xml%20Autofiltro:por_tentativo%20parameters.ByPeriod:month%20"					
					+ PrincipalActivity.PARAMETER_BY_PROJECT
					+ PrincipalActivity.PARAMETER_BY_TYPE
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;


		} else if (position == 4) {
			myconsult = PrincipalActivity.URL_API
					+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratodos.xml%20Autofiltro:%20por_proyecto"
					+ PrincipalActivity.PARAMETER_BY_TYPE
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;


		} else if (position == 5) {
			Log.d("Autofiltros", "Autofiltros..(1)..");
			
			myconsult = PrincipalActivity.URL_API
					+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/cartelerafiltros.xml%20Autofiltro:por_finalizado%20parameters.ByPeriod:month%20"									
					+ PrincipalActivity.PARAMETER_BY_PROJECT
					+ PrincipalActivity.PARAMETER_BY_TYPE
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;

		} else if (position == 6) {
			myconsult = PrincipalActivity.URL_API					
					//+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratodos.xml%20Autofiltro:%20por_tipo"
					+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/cartelerafiltros.xml%20Autofiltro:por_finalizado%20parameters.ByPeriod:quarter%20"
					+ PrincipalActivity.PARAMETER_BY_PROJECT
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;


		} else if (position == 7) {
			myconsult = PrincipalActivity.URL_API
					+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleratodos.xml%20Autofiltro:%20por_tipo"
					+ PrincipalActivity.PARAMETER_BY_PROJECT
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;

		} else if (position == 8) {
			myconsult = PrincipalActivity.URL_API
					+ "operacion:Generar_gr%E1fico_coloreado%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleraasignado.xml"
					+ PrincipalActivity.PARAMETER_BY_TYPE
					+ PrincipalActivity.PARAMETER_BY_PROJECT
					+ PrincipalActivity.PARAMETER_BY_DATE1
					+ PrincipalActivity.PARAMETER_BY_DATE2;

		}
		else if (position == 9) {
		myconsult = PrincipalActivity.URL_API
				+ "operacion:Generar_gr%E1fico_con_autofiltro%20Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleraplanificados.xml%20Autofiltro:por_tentativo%20parameters.ByPeriod:quarter%20"				
				+ PrincipalActivity.PARAMETER_BY_TYPE
				+ PrincipalActivity.PARAMETER_BY_PROJECT
				+ PrincipalActivity.PARAMETER_BY_DATE1
				+ PrincipalActivity.PARAMETER_BY_DATE2;

		}

		else {
			Log.d("GraphType", "Selected none");
			return;
		}

		Log.d("showGraph *consult:", myconsult);

		GetGraphTask mytask = new GetGraphTask("graficar");

		mytask.execute(myconsult);

		
	}

	
	private void showGraphChooseDialog(Context context) {

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.choose_graph_report);
		dialog.setTitle(getString(R.string.graph_type));

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(getString(R.string.select_report_graph));
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_action_graph);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String myconsult = "";
				Spinner typegraph = (Spinner) dialog
						.findViewById(R.id.graphtype);				
				dialog.dismiss();
				executeGraph(typegraph.getSelectedItemPosition());
			}
		});
		

		Log.d("showInputNameDialog", "entrando 8");
		Button dialogCancel = (Button) dialog
				.findViewById(R.id.dialogButtonCancel);
		// if button is clicked, close the custom dialog
		dialogCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();
			}
		});
		dialog.show();
		Log.d("show", "show");
	}

	public void onClickedProject(View view) {
		Log.d("OnClickProject", "OnClickProject");
		ToggleButton boxproject = ((ToggleButton) view);
		if (boxproject.isChecked()) {
			Log.d("boxproject", "entering...1");

			String myconsult = PrincipalActivity.URL_API
					+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/proyectos.xml"
					+ "%20Variable:vProyectos";

			GetGraphTask mytask = new GetGraphTask(
					"para_filtro_listar_proyectos");

			mytask.execute(myconsult);
			Log.d("boxproject", "entering...2");

		} else {
			Log.d("OnClickProject", "OnClickProject Off");
			// boxproject.setText(getString(R.string.filter_any_project));
			PrincipalActivity.PARAMETER_BY_PROJECT = "";
			PrincipalActivity.DESCRIPTION_PROJECT_PARAMS = "";

		}
		// view.refreshDrawableState();
	}

	public void onClickedType(View view) {
		Log.d("OnClickType", "OnClickType");
		ToggleButton boxtype = ((ToggleButton) view);
		if (boxtype.isChecked()) {
			// boxtype.setText("Trabajo");
			makeFiltertTypeDialog();

		} else {
			PrincipalActivity.PARAMETER_BY_TYPE = "";
			PrincipalActivity.DESCRIPTION_TYPE_PARAMS = "";
			// boxtype.setText(getString(R.string.filter_any_type));
		}

	}

	public void onClickedDate(View view) {
		Log.d("OnClickType", "OnClickDatee");
		ToggleButton boxdate = ((ToggleButton) view);
		if (boxdate.isChecked()) {
			// boxtype.setText("Trabajo");
			Log.d("Click", "DateFrom button");

			makePutParameterDateDialog();

		} else {
			PrincipalActivity.PARAMETER_BY_DATE1 = "";
			PrincipalActivity.PARAMETER_BY_DATE2 = "";
			PrincipalActivity.DESCRIPTION_DATE1_PARAMS = "";
			// boxtype.setText(getString(R.string.filter_any_type));
		}

	}

	private void showInputNameDialog(Context context, ArrayList<String> states,
			String title) {

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.choose_report);
		dialog.setTitle(getString(R.string.report_type));

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(getString(R.string.select_report_ticket));
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_action_refresh);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner myselect = (Spinner) dialog
						.findViewById(R.id.selecttype);
				String myreport = myselect.getSelectedItem().toString();
				Log.d("Spinner", myreport);
				dialog.dismiss();
				loadSafetReport(myreport);
			}
		});

		Log.d("showInputNameDialog() 1", "entrando **");
		Button dialogCancel = (Button) dialog
				.findViewById(R.id.dialogButtonCancel);
		Log.d("showInputNameDialog() 2", "entrando **");
		// if button is clicked, close the custom dialog
		dialogCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		Log.d("showInputNameDialog() 3", "entrando **");
		Log.d("showInputNameDialog() 4", "entrando **");
		dialog.show();
		Log.d("showInputNameDialog() 5", "entrando **");
		Log.d("show", "show");
	}

	private ArrayList<String> changeNames(ArrayList<String> states) {
		Map inmap = new HashMap<String, String>();
		inmap.put("Finished", "Completada");
		inmap.put("Progress", "En Progreso");
		inmap.put("Postponed", "Pospuesta");
		inmap.put("ToDo", "Por hacer");

		Map outmap = new HashMap<String, String>();
		outmap.put("Completada", "Finished");
		outmap.put("En Progreso", "Progress");
		outmap.put("Pospuesta", "Postponed");
		outmap.put("Por hacer", "ToDo");

		ArrayList<String> result = new ArrayList<String>();
		Stack<String> temporal = new Stack<String>();

		for (String s : states) {
			String news = s;
			if (inmap.containsKey(s)) {
				news = (String) inmap.get(s);
			} else if (outmap.containsKey(s)) {
				news = (String) outmap.get(s);
			}

			temporal.push(news);
		}

		while (!temporal.isEmpty()) {
			result.add(temporal.pop());
		}

		return result;
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

		Log.d("states size", "size() > 0");
		Spinner myselect = (Spinner) dialog.findViewById(R.id.selecttype);

		ArrayAdapter<String> myadapter;
		ArrayList<String> newstates = changeNames(states);
		myadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, newstates);

		myadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		myselect.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();
		Log.d("states size", "size() > 0**");

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner myselect = (Spinner) dialog
						.findViewById(R.id.selecttype);
				String mystatus = myselect.getSelectedItem().toString();
				ArrayList<String> newstates = new ArrayList<String>();
				newstates.add(mystatus);
				newstates = changeNames(newstates);

				mystatus = newstates.get(0);
				Log.d("FULL cambiar_estado", mystatus);
				dialog.dismiss();
				String idticket = currentticket;
				String myconsult = PrincipalActivity.URLFORM_API
						+ "operacion:Siguiente_estado%20id:" + idticket
						+ "%20Siguiente_Estado:" + mystatus;
				Log.d("CambiandoEstado...consult:", "|" + myconsult + "|");
				new GetGraphTask("cambiar_estado").execute(myconsult);
			}
		});

		Log.d("showInputNameDialog() 1", "entrando **");
		Button dialogCancel = (Button) dialog
				.findViewById(R.id.dialogButtonCancel);
		Log.d("showInputNameDialog() 2", "entrando **");
		// if button is clicked, close the custom dialog
		dialogCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		Log.d("showInputNameDialog() 3", "(3)entrando **");
		Log.d("showInputNameDialog() 4", "(4)entrando **");
		dialog.show();
		Log.d("showInputNameDialog() 5", "entrando **");
		Log.d("show", "show");
	}

	public void makeDeleteDataConnectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setMessage(getString(R.string.ask_delete_data_connect));

		builder.setPositiveButton(getString(R.string.yes_option),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do do my action here

						GenTicket mytask = new GenTicket(getBaseContext());

						mytask.deleteTicket();
						Log.d("makeDeleteDataConnectDialog", "Yes");
						dialog.dismiss();

						boolean result = deleteNotifications(getApplicationContext());
						Log.d("DELETINGCONNECT:", String.valueOf(result));
					}

				});

		builder.setNegativeButton(getString(R.string.no_option),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// I do not need any action here you might
						Log.d("makeDeleteDataConnectDialog", "no");
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();

	}

	@SuppressLint("NewApi")
	public void makePutParameterDateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.filter_by_date1));
		// builder.setMessage(getString(R.string.parameter_datefrom));
		Calendar calendar = Calendar.getInstance();

		// LinearLayout item = (LinearLayout)findViewById(R.id.daterange);
		View child = getLayoutInflater().inflate(R.layout.daterange, null);

		builder.setView(child);


		final DatePicker input = (DatePicker) child.findViewById(R.id.dateFrom);

		Log.d("makePutParameterDateDialog",
				"Seltext:year:" + String.valueOf(calendar.get(Calendar.YEAR)));
		// input.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DAY_OF_MONTH), null);

		final DatePicker inputto = (DatePicker) child.findViewById(R.id.dateTo);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {

			input.setCalendarViewShown(false);
			inputto.setCalendarViewShown(false);

		}

		inputto.setEnabled(false);

		final CheckBox checkto = (CheckBox) child
				.findViewById(R.id.checkDateto);

		checkto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkto.isChecked()) {

					inputto.setEnabled(true);
				} else {
					inputto.setEnabled(false);
				}
			}
		});

		builder.setPositiveButton(getString(R.string.yes_option),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do do my action here

						Log.d("set positive", "...(1)");
						int year = input.getYear();

						Log.d("set positive", "...(2)");
						Calendar calendar = Calendar.getInstance();

						calendar.set(year, input.getMonth(),
								input.getDayOfMonth(), 0, 0, 0);

						Log.d("set positive", "...(3)");
						// String fechatentativa = String.valueOf(calendar
						// .getTimeInMillis() / 1000 - 86399);

						String fechatentativa = String.valueOf(calendar
								.getTimeInMillis() / 1000); // Mayor que

						PrincipalActivity.PARAMETER_BY_DATE1 = "%20parameters.ByDate1:"
								+ fechatentativa;

						PrincipalActivity.DESCRIPTION_DATE1_PARAMS = "-"
								+ convertDateEpochToOnlyFormat(fechatentativa);
						Log.d("BoxDate1", "seldate1:" + fechatentativa);

						if (checkto.isChecked()) {
							Calendar calendarto = Calendar.getInstance();
							int yearto = input.getYear();
							calendarto.set(yearto, inputto.getMonth(),
									inputto.getDayOfMonth(), 0, 0, 0);
							String fechahasta = String.valueOf((calendarto
									.getTimeInMillis() / 1000) + 86399 - 3600 * 4); // Mayor
																					// que

							PrincipalActivity.PARAMETER_BY_DATE2 = "%20parameters.ByDate2:"
									+ fechahasta;
							Log.d("fecha hasta", "seldate2:"
									+ PrincipalActivity.PARAMETER_BY_DATE2);
							PrincipalActivity.DESCRIPTION_DATE1_PARAMS = PrincipalActivity.DESCRIPTION_DATE1_PARAMS
									+ "-"
									+ convertDateEpochToOnlyFormat(fechahasta);
						}

						dialog.dismiss();
						Log.d("set positive", "...(5)");
					}

				});

		builder.setNegativeButton(getString(R.string.no_option),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// I do not need any action here you might
						Log.d("makeDeleteOptionsDialog", "no...(1)");
						dialog.dismiss();
						ToggleButton boxdate1 = ((ToggleButton) findViewById(R.id.toggleDateFrom));

						boxdate1.setChecked(false);

						PrincipalActivity.PARAMETER_BY_DATE1 = "";
						PrincipalActivity.PARAMETER_BY_DATE2 = "";
						PrincipalActivity.DESCRIPTION_DATE1_PARAMS = "";

						Log.d("makePutParametersDialog", "no...(2)");
					}
				});

		AlertDialog alert = builder.create();
		Log.d("show", "1");
		alert.show();
		Log.d("show", "2");

	}

	@SuppressLint("NewApi")
	public void makeModifyOptionsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setMessage(getString(R.string.modify_date_task));
		Calendar calendar = Calendar.getInstance();


		Log.d("makeModifyOptionsDialog", "Seltext:currentdate:" + currentdate);

		SimpleDateFormat sf = new SimpleDateFormat(
				PrincipalActivity.CURRENTDATEFORMAT);

		try {
			calendar.setTime(sf.parse(currentdate));
		} catch (Exception e) {

			Toast toast = Toast.makeText(getApplicationContext(),
					getString(R.string.error_dateparse), Toast.LENGTH_SHORT);

			Log.d("Exception", e.getMessage());

			return;
		}

		View child = getLayoutInflater().inflate(R.layout.dateadvance, null);

		
		Log.d("makeModifyOptionsDialog",
				"Seltext:year:" + String.valueOf(calendar.get(Calendar.YEAR)));
		final DatePicker input = (DatePicker) child.findViewById(R.id.datePlan);
		
		input.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), null);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {

			input.setCalendarViewShown(false);

		}

		

		final CheckBox checkmiddleweek = (CheckBox) child
				.findViewById(R.id.checkmiddleweek);

		final CheckBox checkstartweek = (CheckBox) child
				.findViewById(R.id.checkstartweek);

		final CheckBox checkendweek = (CheckBox) child
				.findViewById(R.id.checkendweek);

		final CheckBox checkmiddlemonth = (CheckBox) child
				.findViewById(R.id.checkmiddlemonth);
		
		checkmiddleweek.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 
				Calendar calendar = Calendar.getInstance();

				if (checkmiddleweek.isChecked()) {
					Log.d("check","check 1");
					//input.setEnabled(true);
					Long nw = Utils.dayOfNextWeek(1)*1000;
					calendar.setTimeInMillis(nw);
					checkstartweek.setChecked(false);
					checkendweek.setChecked(false);
					checkmiddlemonth.setChecked(false);
				}
				input.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), null);
			}
		});
		
		checkstartweek.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 
				Calendar calendar = Calendar.getInstance();

				if (checkstartweek.isChecked()) {
					Log.d("check","check 1");
					//input.setEnabled(true);
					Long nw = Utils.dayOfNextWeek(0)*1000;
					calendar.setTimeInMillis(nw);
					checkmiddleweek.setChecked(false);
					checkendweek.setChecked(false);
					checkmiddlemonth.setChecked(false);

				}
				input.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), null);
			}
		});

		checkendweek.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 
				Calendar calendar = Calendar.getInstance();

				if (checkendweek.isChecked()) {
					Log.d("check","check 1");
					//input.setEnabled(true);
					Long nw = Utils.dayOfNextWeek(2)*1000;
					calendar.setTimeInMillis(nw);
					checkmiddleweek.setChecked(false);
					checkstartweek.setChecked(false);
					checkmiddlemonth.setChecked(false);

				}
				input.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), null);
			}
		});
		
		checkmiddlemonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 
				Calendar calendar = Calendar.getInstance();

				if (checkmiddlemonth.isChecked()) {
					Log.d("check","check 1");
					//input.setEnabled(true);
					Long nw = Utils.dayOfNextMonth(1)*1000;
					calendar.setTimeInMillis(nw);
					checkmiddleweek.setChecked(false);
					checkstartweek.setChecked(false);
					checkendweek.setChecked(false);

				}
				input.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), null);
			}
		});

		builder.setView(child);

		Log.d("makeModifyOptionsDialog","set child");
		
		builder.setPositiveButton(getString(R.string.yes_option),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do do my action here

						int year = input.getYear();

						Calendar calendar = Calendar.getInstance();

						calendar.set(year, input.getMonth(),
								input.getDayOfMonth(), 23, 59, 59);

						String fechatentativa = String.valueOf(calendar
								.getTimeInMillis() / 1000);

						String myconsult = PrincipalActivity.URLFORM_API
								+ "operacion:modificar_ticket%20id:"
								+ currentticket + "%20Fecha_tentativa:"
								+ fechatentativa;
						Log.d("Modificar_ticket...consult:", "|" + myconsult
								+ "|");

						new GetGraphTask("modificar_ticket").execute(myconsult);

						Log.d("makeModifyOptionsDialog", "Yes");
						dialog.dismiss();
					}

				});

		builder.setNegativeButton(getString(R.string.no_option),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// I do not need any action here you might
						Log.d("makeDeleteOptionsDialog", "no...(1)");
						dialog.dismiss();

						Log.d("makeDeleteOptionsDialog", "no...(2)");
					}
				});

		AlertDialog alert = builder.create();
		alert.show();

	}

	public void makeDeleteOptionsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setMessage(getString(R.string.confirm_delete));

		builder.setPositiveButton(getString(R.string.yes_option),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do do my action here

						String myconsult = PrincipalActivity.URLFORM_API
								+ "operacion:borrar_ticket%20id_ticket:"
								+ currentticket;
						Log.d("Borrar_ticket...consult:", "|" + myconsult + "|");

						deleteAlarmTask(getApplicationContext(),
								recordticket.getEpochtentativedate());
						new GetGraphTask("borrar_ticket").execute(myconsult);

						Log.d("makeDeleteOptionsDialog", "Yes");
						dialog.dismiss();

					}

				});

		builder.setNegativeButton(getString(R.string.no_option),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// I do not need any action here you might
						Log.d("makeDeleteOptionsDialog", "no");
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public boolean deleteAlarmTask(Context context, Long currdatetime) {
		boolean result = false;
		ArrayList<TicketRecord> mytickets = PrincipalActivity
				.readTicketForNotify(context);
		if (mytickets == null) {
			Log.e("PRINCIPAL deleteAlarm", "no hay tickets");
			return false;
		}

		Log.d("mostrando", "ticket!!");

		TicketRecord myticket = null;

		int pos = 0;

		for (TicketRecord theticket : mytickets) {

			Log.d("PRINCIPAL deleteAlarm",
					"currdatetime:" + String.valueOf(currdatetime));
			Log.d("PRINCIPAL deleteAlarm",
					"getEpochfinishdate():"
							+ String.valueOf(theticket.getEpochtentativedate()));

			if (currdatetime <= theticket.getEpochtentativedate() + 10
					&& currdatetime >= theticket.getEpochtentativedate()) {

				break;

			}
			pos = pos + 1;
		}
		if (pos < mytickets.size()) {
			mytickets.remove(pos);

			PrincipalActivity.saveTicketForNotify(context, mytickets);
			Log.d("PRINCIPAL deleteAlarm", "DELETED: " + String.valueOf(pos));
			result = true;
		}

		return result;
	}

	public AlertDialog makeTaskOptionsDialog(final Context context) {

		final int isdelassign;

		final String[] option;

		Log.d("record status", recordticket.getStatus());
		Log.d("record assignto", recordticket.getAssignto());

		if (recordticket == null) {

			Log.d("recordticket", "noinfo");
			return null;
		}

		Log.d("LongClick (1)", recordticket.getStatus());
		if (recordticket.getStatus().contentEquals("AssignTo")) {

			Log.d("record status", "...(2)...");

			if (recordticket.getAssignto().contentEquals(currentuser)) {
				option = new String[] { getString(R.string.show_task),
						getString(R.string.assign_accept),
						getString(R.string.desassign), };
				isdelassign = 2;

				Log.d("record status", "...(3)...");
			} else {
				option = new String[] {
						// getString(R.string.change_status),
						getString(R.string.show_task),
						getString(R.string.delete_task),
						getString(R.string.modify_date_task),
						getString(R.string.modify_data_task),
						getString(R.string.desassign), };
				isdelassign = 1;
			}

		} else if (recordticket.getStatus().contentEquals("Finished")) {
			Log.d("LongClick (2)", recordticket.getStatus());
			option = new String[] { getString(R.string.show_task),
					getString(R.string.delete_task), };
			isdelassign = 0;
		}

		else if (recordticket.getAssignfrom().contentEquals(currentuser)
				&& !recordticket.getAssignto().contentEquals(currentuser)) {
			Log.d("isdelassign", "4");
			option = new String[] { getString(R.string.show_task),
					getString(R.string.desassign),
					getString(R.string.modify_date_task), };
			isdelassign = 4;
		} else if (recordticket.getAssignto().contentEquals(currentuser)
				&& !recordticket.getAssignfrom().contentEquals(currentuser)) {
			option = new String[] { getString(R.string.change_status),
					getString(R.string.show_task),
					getString(R.string.desassign), };
			isdelassign = 3;
		} else {
			option = new String[] { getString(R.string.change_status),
					getString(R.string.show_task),
					getString(R.string.delete_task),
					getString(R.string.modify_date_task),
					getString(R.string.modify_data_task),
					getString(R.string.assign_action),
			// getString(R.string.task_notify),
			};
			isdelassign = 0;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.select));
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Log.d("makeTaskOptionsDialog", "currentticket:" + currentticket);

				if (recordticket.getStatus().contentEquals("Finished")) {
					switch (which) {
					case 0: // Cambiar estado
						Log.d("makeTaskOptionsDialog", "changeStatusTask");
						loadViewTicketActivity(true, false);
						break;
					case 1: // Elimina
						makeDeleteOptionsDialog();
						break;
					default:
						Log.d("Error switch", "Error switch");
						break;
					}

				} else if (isdelassign == 4) {
					switch (which) {
					case 0: // Ver
						loadViewTicketActivity(true, false);
						break;
					case 1: // Modifica datos de la tarea
						callAllocTasks(isdelassign);
						break;
					case 2:
						makeModifyOptionsDialog();
						break;
					default:
						break;
					}
				}

				else if (isdelassign == 3) {
					switch (which) {
					case 0: // Cambiar estado
						Log.d("makeTaskOptionsDialog", "changeStatusTask");
						changeStatusTask();
						break;
					case 1: // Ver
						loadViewTicketActivity(true, false);
						break;
					case 2: // Modifica datos de la tarea
						callAllocTasks(isdelassign);
						break;

					default:
						break;
					}
				} else if (isdelassign == 2) {
					switch (which) {
					case 0: // Ver

						loadViewTicketActivity(true, false);
						break;
					case 1: // asignacion
						callAllocTasks(isdelassign);
						break;
					case 2: // desasignacion
						callAllocTasks(3);
						break;
					default:
						break;
					}

				} else if (isdelassign == 1) {

					switch (which) {
					case 0: // Ver

						loadViewTicketActivity(true, false);
						break;
					case 1: // Elimina
						makeDeleteOptionsDialog();
						break;
					case 2: // Modifica fecha de la tarea
						makeModifyOptionsDialog();
						break;
					case 3: // Modifica datos de la tarea
						loadViewTicketActivity(false, false);
						break;
					case 4: // Modifica datos de la tarea
						callAllocTasks(isdelassign);
						break;

					default:
						break;
					}

				}

				else {
					switch (which) {
					case 0: // Cambiar estado
						Log.d("makeTaskOptionsDialog", "changeStatusTask");
						changeStatusTask();
						break;
					case 1: // Ver

						loadViewTicketActivity(true, false);
						break;
					case 2: // Elimina
						makeDeleteOptionsDialog();
						break;
					case 3: // Modifica fecha de la tarea
						makeModifyOptionsDialog();
						break;
					case 4: // Modifica datos de la tarea
						loadViewTicketActivity(false, false);
						break;
					case 5: // Modifica datos de la tarea
						callAllocTasks(isdelassign);
						break;

					case 6: // Modifica datos de la tarea
						notifytaskalarm();
						break;

					default:
						break;
					}
				}
			}

		});

		return builder.create();
	}

	protected void notifytaskalarm() {
		Log.d("notifytaskalarm", "notifytaskalarm...(1)...");

		openTimePickerDialog(false);
		Log.d("notifytaskalarm", "notifytaskalarm...(2)...");

	}

	private void openTimePickerDialog(boolean is24r) {
		Calendar calendar = Calendar.getInstance();

		timePickerDialog = new TimePickerDialog(PrincipalActivity.this,
				onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), is24r);
		timePickerDialog.setTitle("Set Alarm Time");

		timePickerDialog.show();

	}

	OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			Calendar calNow = Calendar.getInstance();
			Calendar calSet = (Calendar) calNow.clone();

			calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calSet.set(Calendar.MINUTE, minute);
			calSet.set(Calendar.SECOND, 0);
			calSet.set(Calendar.MILLISECOND, 0);

			if (calSet.compareTo(calNow) <= 0) {
				// Today Set time passed, count to tomorrow
				calSet.add(Calendar.DATE, 1);
			}

			setAlarm(calSet);
			// for this

		}
	};

	private static final int ALARM_REQUEST_CODE = 1;

	private void setAlarm(Calendar targetCal) {

		Context context = this.getApplicationContext();
		if (alarm != null) {
			alarm.setTargetCal(targetCal);
			alarm.setOnetimeTimer(context, generateNumber(0, 500));
			Toast.makeText(context, "setting alarm", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
		}

		Log.d("setAlarm", "...(1)...");
	}

	private void RegisterAlarmBroadcast() {
		Log.i("Alarm Example:RegisterAlarmBroadcast()",
				"Going to register Intent.RegisterAlramBroadcast");

		// This is the call back function(BroadcastReceiver) which will be call
		// when your
		// alarm time will reached.
		mReceiver = new BootReceiver();
		aReceiver = new AlarmReceiver();

		// register the alarm broadcast here
		registerReceiver(mReceiver, new IntentFilter(
				"novo.apps.doitall.PrincipalActivity"));
		registerReceiver(aReceiver, new IntentFilter("ALARM_RECEIVED"));

		pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				"novo.apps.doitall.PrincipalActivity"), 0);
		alarmManager = (AlarmManager) (this
				.getSystemService(Context.ALARM_SERVICE));
	}

	// private void UnregisterAlarmBroadcast()
	// {
	// alarmManager.cancel(pendingIntent);
	// getBaseContext().unregisterReceiver(mReceiver);
	// //getBaseContext().unregisterReceiver(aReceiver);
	// }
	//

	protected void loadViewTicketActivity(boolean addticket, boolean showstick) {
		// TODO Auto-generated method stub

		String myconsult = PrincipalActivity.URL_API
				+ "operacion:Listar_datos%20"
				+ "Cargar_archivo_flujo:%20/home/panelapp/.safet/flowfiles/carteleravertarea.xml%20Variable:vTodas_las_tareas"
				+ "%20parameters.nro_ticket:" + currentticket;

		Log.d("loadViewTicketActivity", "myconsult:" + myconsult);

		GetGraphTask mytask = new GetGraphTask("ver_ticket");
		mytask.setAddticket(addticket);
		mytask.setShowsticky(showstick);

		mytask.execute(myconsult);

	}

	public void loadSafetReport(String report) {
		currreport = "vPor_hacer";
		currfile = FLOWFILES_DIR + "ocarteleratres.xml";


		if (PrincipalActivity.PARAMETER_BY_PROJECT
				.indexOf("ByProject") > 0) {
			currfile = FLOWFILES_DIR + "uocarteleratres.xml";			
		}
		
		Log.d("**", "2:|" + report + "|");
		if (report.contentEquals("En progreso")) {
			Log.d("Spinner", "3:" + report);
			currreport = "vProgress";
		} else if (report.contentEquals(getString(R.string.assign_to_others))) {
			currfile = FLOWFILES_DIR + "carteleraasignado.xml";
			currreport = "vAsignadosOtros";

		} else if (report.contentEquals(getString(R.string.assign_to_me))) {
			currfile = FLOWFILES_DIR + "carteleraasignado.xml";
			currreport = "vAsignadosMi";

		} else if (report.contentEquals("Pospuestas")) {
			currreport = "vPostponed";
		} else if (report
				.contentEquals(getString(R.string.completed_this_week))) {

			currreport = "vEsta_semana";
			currfile = FLOWFILES_DIR + "cartelerafinalizadosporsemana.xml";
			Log.d("Completadas esta semana", currreport);
			Log.d("Completadas esta semana", currfile);
		} else if (report
				.contentEquals(getString(R.string.completed_after_week))) {
			currreport = "vSemana_anterior";
			currfile = FLOWFILES_DIR + "cartelerafinalizadosporsemana.xml";
		}

		else if (report.contentEquals(getString(R.string.all_tickets))) {

			currreport = "Todos";
			if (PrincipalActivity.PARAMETER_BY_PROJECT.indexOf("ByProject") > 0) {
				currfile = FLOWFILES_DIR + "ucartelerabusqueda.xml";
			} else {
				currfile = FLOWFILES_DIR + "cartelerabusqueda.xml";
			}

		}

		else if (report.contentEquals(getString(R.string.delayed))) {
			currreport = "vDelayed";
		}

		else if (report.contentEquals(getString(R.string.next_week))) {
			Log.d("loadSafetReport", "nextweek");
			currfile = FLOWFILES_DIR + "carteleraproximos.xml";
			currreport = "vProxima_semana";

		} else if (report.contentEquals(getString(R.string.this_week))) {
			Log.d("loadSafetReport", "nextweek");
			currfile = FLOWFILES_DIR + "carteleraproximos.xml";
			currreport = "vEsta_semana";

		} else if (report.contentEquals(getString(R.string.this_month))) {
			Log.d("loadSafetReport", "nextweek");
			currfile = FLOWFILES_DIR + "cartelerapormes.xml";
			currreport = "vEste_mes";

		} else if (report.contentEquals(getString(R.string.next_month))) {
			Log.d("loadSafetReport", "nextweek");
			currfile = FLOWFILES_DIR + "cartelerapormes.xml";
			currreport = "vProximo_mes";

		} else if (report.contentEquals(getString(R.string.after_month))) {
			Log.d("loadSafetReport", "nextweek");
			currfile = FLOWFILES_DIR + "cartelerapormes.xml";
			currreport = "vSuperior_proximo_mes";

		} else if (report.contentEquals(getString(R.string.after_week))) {
			Log.d("loadSafetReport", "nextweek");
			currfile = FLOWFILES_DIR + "carteleraproximos.xml";
			currreport = "vSuperior_proxima_semana";
		}

		else if (report.contentEquals(getString(R.string.finished))) {
			currreport = "vFinished";
		}

		String myconsult = PrincipalActivity.URL_API
				+ "operacion:Listar_datos%20" + "Cargar_archivo_flujo:%20"
				+ currfile + "%20Variable:" + currreport
				+ PrincipalActivity.PARAMETER_BY_PROJECT
				+ PrincipalActivity.PARAMETER_BY_TYPE
				+ PrincipalActivity.PARAMETER_BY_DATE1
				+ PrincipalActivity.PARAMETER_BY_DATE2;

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
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Error en la conexión:" + e.getMessage(),
					Toast.LENGTH_SHORT);
			toast.show();

		} catch (IOException e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Error de lectura de datos:" + e.getMessage(),
					Toast.LENGTH_SHORT);
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

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };
			// Install the all-trusting trust manager
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
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

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "Latin-1"));
			String data = "";
			while ((data = reader.readLine()) != null) {
				webPage += data;
			}
			Log.d("ChangeStatusTicket", "DATA---WEBPAGE:|" + webPage + "|");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Exception", e.toString());
			e.printStackTrace();
		}

		return webPage;
	}

	private ArrayList<String> loadStatus(String resulting) {

		ArrayList<String> states = new ArrayList<String>();

		Log.d("loadStatus", "resulting:" + resulting);
		states.clear();
		try {
			JSONObject jall = new JSONObject(resulting);
			JSONArray jarray = jall.getJSONArray("Status");
			for (int i = 0; i < jarray.length(); i++) {
				JSONArray json_data = jarray.getJSONArray(i);
				String myvalue = json_data.get(0).toString();
				Log.d("json", "myvalue:|" + myvalue + "|");
				states.add(myvalue);
			}

		} catch (Exception e) {
			Log.d("exception", e.toString());
			e.printStackTrace();

		}

		return states;
	}

	// Agregando botón de cargar datos

	public void changeStatusTask() {

		String myconsult = PrincipalActivity.URL_API
				+ "operacion:Siguientes_estados%20Cargar_archivo_flujo:"
				+ FLOWFILES_DIR + "carteleratresf.xml%20Clave:" + currentticket;

		Log.d("changeStatusTask..myconsult:", myconsult);

		new GetGraphTask("siguientes_estados").execute(myconsult);

	}

	public void addListenerOnButton() {

		// Select a specific button to bundle it with the action you want

		listtickets.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("view **click", "view click");
				recordticket = (TicketRecord) listtickets
						.getItemAtPosition(position);

				String idticket = recordticket.getId();
				currentticket = idticket;
				currentprojectid = String.valueOf(recordticket.getProjectid());
				currentdate = recordticket.getTentativedate();

				Log.d("View TicketSelecting", "IdTicket:|" + currentticket
						+ "|");
				Log.d("View Ticket Selecting", "Tentativedate:|" + currentdate
						+ "|");

				if (noteView.isShown()) {
					noteView.setVisibility(View.GONE);
					Log.d("view click", "view click gone");
					return true;
				}

				Log.d("ver sticky", "(1)");
				noteView.setMessage(recordticket);
				noteView.setVisibility(View.VISIBLE);
				noteView.renderText();
				Log.d("view click", "view click gone");

				// loadViewTicketActivity(false, true);
				return true;

			}
		});

		listtickets.setOnItemClickListener(new OnItemClickListener() {
		

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (noteView.isShown()) {
					noteView.setVisibility(View.GONE);
					Log.d("view click", "view click gone");
					return;
				}

				recordticket = (TicketRecord) listtickets
						.getItemAtPosition(position);

				if ((!recordticket.getOwner().contentEquals(currentuser) && !recordticket
						.getAssignfrom().contentEquals(currentuser))
						&& !recordticket.getStatus().contentEquals("AssignTo")) {

					Toast.makeText(getBaseContext(),
							getString(R.string.ticket_other_user),
							Toast.LENGTH_SHORT).show();

					return;
				}
				String idticket = recordticket.getId();
				currentticket = idticket;
				currentprojectid = String.valueOf(recordticket.getProjectid());
				currentdate = recordticket.getTentativedate();

				Log.d("Selecting", "IdTicket:|" + currentticket + "|");
				Log.d("Selecting", "Tentativedate:|" + currentdate + "|");

				task_dialog = makeTaskOptionsDialog(view.getContext());
				if (task_dialog != null)
					task_dialog.show();

				return;
			}

		});

		button = (ImageButton) findViewById(R.id.buttonload);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				Log.d("DialogLog", "1");
				ArrayList<String> mylist = new ArrayList<String>();
				showInputNameDialog(view.getContext(), mylist,
						getString(R.string.ticket_list_title));
				Log.d("DialogLog", "2");

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

	public void callPreListprojects(boolean addticket, String dataticket,
			boolean modifyproject) {
		String myconsult = "";

		if (modifyproject) {
			myconsult = PrincipalActivity.URL_API
					+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/todosproyectos.xml"
					+ "%20Variable:vProyectos";
		} else {
			myconsult = PrincipalActivity.URL_API
					+ "operacion:Listar_datos%20Cargar_archivo_flujo:/home/panelapp/.safet/flowfiles/proyectos.xml"
					+ "%20Variable:vProyectos";

		}

		GetGraphTask mytask = new GetGraphTask("listar_proyectos");

		mytask.setModifyproject(modifyproject);
		mytask.setDataticket(dataticket);
		mytask.setAddticket(addticket);
		mytask.execute(myconsult);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ---check if the request code is 1---
		try {

			Log.d("requestCode:", String.valueOf(requestCode));
			Log.d("requestCode:", String.valueOf(resultCode));
			if (resultCode == 1) {

				Log.d("requestCode:", String.valueOf(1));
				adapter.notifyDataSetChanged();
				// ---if the result is OK---
				if (resultCode == RESULT_OK) {
				}
			} else if (requestCode == 2) {

				if (resultCode == 3) {

					String urlform = PrincipalActivity.URLFORM_API
							+ data.getStringExtra("urlform");
					Log.d("OnActivityResult...", "*Principal");

					Boolean isnotify = data
							.getBooleanExtra("notifytask", false);

					Log.d("agregar_ticket", urlform);
					Log.d("notifytask", "notify:" + isnotify.toString());

					if (isnotify) {

						Calendar currdatetime = (Calendar) data
								.getSerializableExtra("currdatetime");

						Log.d("PrincipalActivity DATETIME", "CALENDAR:"
								+ currdatetime.toString());
						TicketRecord myticket = (TicketRecord) data
								.getSerializableExtra("currticket");

						int currnumber = generateNumber(10, 100000);

						Log.d("My Ticket",
								"summary:" + String.valueOf(currnumber));

						myticket.setId(String.valueOf(currnumber));

						ArrayList<TicketRecord> tempnotify = readTicketForNotify(getApplicationContext());
						if (tempnotify != null) {
							ticketsforNotify = tempnotify;

						}

						ticketsforNotify.add(myticket);

						saveTicketForNotify(getApplicationContext(),
								ticketsforNotify);

						setAlarm(currdatetime);
					} else {
						Long currepoch = data.getLongExtra("currepoch", 0);

						Log.d("DelAlarm", String.valueOf(currepoch));

						deleteAlarmTask(getApplicationContext(), currepoch);

					}

					GetGraphTask mytask = new GetGraphTask("agregar_ticket");
					mytask.execute(urlform);

				}

			} else if (requestCode == 6) {

				Log.d("Cerrando", "ModifyProject");
			} else if (requestCode == 5) {
				Log.d("return from About", "return from About");

			}
		} catch (Exception e) {
			Log.d("**Bk", e.getMessage());
			Toast.makeText(getApplicationContext(),
					getString(R.string.no_connect_internet), Toast.LENGTH_LONG)
					.show();
		}
	}

	public static int generateNumber(int min, int max) {

		Random r = new Random();
		int i1 = r.nextInt(max - min + 1) + min;

		return i1;
	}

	public static void saveTicketForNotify(Context context,
			ArrayList<TicketRecord> mytickets) {

		ObjectOutputStream outputStream;

		File file = new File(context.getFilesDir(), FILE_NOTIFICATIONS);
		Log.d("saveTicket", "path:" + file.getAbsolutePath());

		try {

			outputStream = new ObjectOutputStream(context.openFileOutput(
					FILE_NOTIFICATIONS, Context.MODE_PRIVATE));
			outputStream.writeObject(mytickets);
			outputStream.close();
		} catch (Exception e) {
			Toast toast = Toast.makeText(context,
					"Ocurrio el siguiente error al escribir la notificaciòn:"
							+ e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
		}

	}

	static public boolean deleteNotifications(Context context) {

		boolean result = false;
		ObjectInputStream inputStream;

		ArrayList<TicketRecord> mytickets = null;
		File file = new File(context.getFilesDir(), FILE_NOTIFICATIONS);
		Log.d("saveTicket", "path:" + file.getAbsolutePath());

		try {
			result = file.delete();

		} catch (Exception e) {
			Log.d("readTicketForNotify", "no tickets");
			return result;
		}

		return result;

	}

	static public ArrayList<TicketRecord> readTicketForNotify(Context context) {

		ObjectInputStream inputStream;

		ArrayList<TicketRecord> mytickets = null;
		File file = new File(context.getFilesDir(), FILE_NOTIFICATIONS);
		Log.d("saveTicket", "path:" + file.getAbsolutePath());

		try {

			inputStream = new ObjectInputStream(
					context.openFileInput(FILE_NOTIFICATIONS));
			mytickets = (ArrayList<TicketRecord>) inputStream.readObject();
			inputStream.close();
		} catch (Exception e) {
			Log.d("readTicketForNotify", "no tickets");
			return null;
		}

		return mytickets;

	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}


	
	public void createPDF() {
		Document doc = new Document();

		String path = "";
		try {

			path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/DoItAll";

			// File file = new File(getBaseContext().getFilesDir(),filename);
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();

			File file = new File(dir, "sample.pdf");
			FileOutputStream fOut = new FileOutputStream(file);

			PdfWriter.getInstance(doc, fOut);

			// open the document
			doc.open();

			ByteArrayOutputStream streamlogo = new ByteArrayOutputStream();
			Bitmap bitmaplogo = BitmapFactory.decodeResource(getBaseContext()
					.getResources(), R.drawable.ic_launcher);
			bitmaplogo.compress(Bitmap.CompressFormat.JPEG, 100, streamlogo);
			Image myImglogo = Image.getInstance(streamlogo.toByteArray());
			myImglogo.setAlignment(Image.LEFT);

			// add image to document
			doc.add(myImglogo);

			String newtitle = getString(R.string.title_pdf)
					+ currenttitle
					+ PrincipalActivity.DESCRIPTION_PROJECT_PARAMS
					+ PrincipalActivity.DESCRIPTION_TYPE_PARAMS.replace("%20",
							" ")
					+ PrincipalActivity.DESCRIPTION_DATE1_PARAMS.replace("%20",
							" ");
			Paragraph p1 = new Paragraph(newtitle);

			Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 28,
					Font.BOLD);
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p1.setFont(paraFont);

			// add paragraph to document
			doc.add(p1);

			int counter = 1;
			String newstate = "(Por hacer)";
			for (TicketRecord ticket : tickets) {
				if (ticket.getStatus().contentEquals("Finished")) {
					newstate = "(Completado)";
				} else if (ticket.getStatus().contentEquals("Postponed")) {
					newstate = "(Pospuesto)";
				} else {
					newstate = "(Por hacer)";

				}
				// bitmap =
				// BitmapFactory.decodeResource(getBaseContext().getResources(),
				// R.drawable.ic_launcher);

				String newentry = String.valueOf(counter) + ". " + newstate
						+ " " + ticket.getSummary() + ":"
						+ ticket.getDescription() + " (" + ticket.getProject()
						+ "/" + ticket.getType() + ") "
						+ "/ Tarea planificada para:"
						+ ticket.getTentativedate() + "/ Fue completada:"
						+ ticket.getFinishdate() + ".";
				Paragraph p2 = new Paragraph(newentry);
				Font paraFont2 = new Font(Font.HELVETICA, 16.0f, Color.BLACK);
				p2.setAlignment(Paragraph.ALIGN_LEFT);
				p2.setFont(paraFont2);
				doc.add(p2);
				counter = counter + 1;
			}

			// set footer
			// Phrase footerText = new Phrase("This is an example of a footer");
			// HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
			// doc.setFooter(pdfFooter);

		} catch (DocumentException de) {
			Log.e("PDFCreator", "DocumentException:" + de);
		} catch (IOException e) {
			Log.e("PDFCreator", "ioException:" + e);
		} finally {
			doc.close();
		}

		String uripath = path + "/sample.pdf";

		// Toast.makeText(getApplicationContext(),
		// getString(R.string.path_file_pdf)+"\""+
		// path+"/sample.pdf\"", Toast.LENGTH_SHORT).show();

		File file = new File(uripath);
		Uri uri = Uri.fromFile(file);
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.setType("application/pdf");
		startActivity(Intent.createChooser(shareIntent,
				getResources().getText(R.string.send_to)));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
//        
//        return super.onOptionsItemSelected(item);
        
// *************Anterior
		switch (item.getItemId()) {
		case android.R.id.home:
				return super.onOptionsItemSelected(item);				
			default:
					break;
		}

		int id = item.getItemId();
		if (id == R.id.action_about) {
			Log.d("About", "About");
			Intent i = new Intent("novo.apps.doitall.AboutActivity");
			startActivityForResult(i, 5);
			return true;
		} else if (id == R.id.delete_data_connect) {

			makeDeleteDataConnectDialog();

		} else if (id == R.id.disable_projects) {

			callPreListprojects(false, "", true);

		} else if (id == R.id.export_pdf) {

			Log.d("exportlist", "exportlist");
			createPDF();
			Log.d("exportlist", "after exportlist");
		} else if (id == R.id.buttonnewticket) {

			callPreListprojects(true, "", false);

		} else if (id == R.id.help_action_link) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri
					.parse("https://github.com/victorrbravo/doitall/wiki/Ayuda-de-Doitall"));
			startActivity(intent);

		}
		return super.onOptionsItemSelected(item);
	}

	public void callAllocTasks(int isdelassign) {
		String idticket = currentticket;

		if (isdelassign == 1 || isdelassign == 3 || isdelassign == 4) {
			String myconsult = PrincipalActivity.URLFORM_API
					+ "operacion:eliminar_asignacion%20" + "id:" + idticket
					+ "%20" + "propietario:" + recordticket.getAssignfrom();

			Log.d("eliminar_asignacion", "myconsult:" + myconsult);

			new GetGraphTask("eliminar_asignacion").execute(myconsult);

		} else if (isdelassign == 2) {
			String userto = "";
			String owner = currentuser;
			String myconsult = PrincipalActivity.URLFORM_API
					+ "operacion:aceptar_asignacion%20" + "%20id:" + idticket
					+ "%20propietario:" + owner;

			Log.d("aceptar_asignacion", "myconsult:" + myconsult);

			new GetGraphTask("aceptar_asignacion").execute(myconsult);

			Log.d("callAllocTasks", "aceptar_asignacion");
		} else if (isdelassign == 0) {
			String myconsult = PrincipalActivity.URL_API
					+ "operacion:listar_usuarios%20" + "Rol:all";

			Log.d("callAllocTasks", "myconsult:" + myconsult);

			new GetGraphTask("listar_usuarios").execute(myconsult);
		}

	}

	public static class SimpleListFragment extends
			android.support.v4.app.ListFragment {

		String[] numbers_text = new String[] { "one", "two", "three", "four",
				"five", "six", "seven", "eight", "nine", "ten", "eleven",
				"twelve", "thirteen", "fourteen", "fifteen" };
		String[] numbers_digits = new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15" };

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// new Toast(getActivity(), numbers_digits[(int) id]);
			Log.d("items", "items");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					inflater.getContext(), android.R.layout.simple_list_item_1,
					numbers_text);
			setListAdapter(adapter);
			return super.onCreateView(inflater, container, savedInstanceState);
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_principal,
	// container, false);
	// return rootView;
	// }
	// }

	public static String convertDateEpochToFormat(String epoch) {
		String result = epoch.trim();
		if (result.equalsIgnoreCase("0")) {
			result = "n/a";
			return result;
		}
		long dv = Long.valueOf(epoch) * 1000;// its need to be in milisecond
		Date df = new java.util.Date(dv);
		result = new SimpleDateFormat(PrincipalActivity.CURRENTDATEFORMAT)
				.format(df);

		return result;

	}

	public static String convertDateEpochToOnlyFormat(String epoch) {
		String result = epoch.trim();
		if (result.equalsIgnoreCase("0")) {
			result = "n/a";
			return result;
		}
		long dv = Long.valueOf(epoch) * 1000;// its need to be in milisecond
		Date df = new java.util.Date(dv);
		result = new SimpleDateFormat(PrincipalActivity.ONLYDATEFORMAT)
				.format(df);

		return result;

	}
    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }

	
}

