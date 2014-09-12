package novo.apps.doitall;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	 Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
    	 Log.d("BootReceiver","Boot_receiver...(1)");
    	 	//Intent i=new Intent("novo.apps.doitall.PrincipalActivity");
    	 context.sendBroadcast(new Intent("ALARM_RECEIVED"));
    	 Log.d("BootReceiver","Boot_receiver...(2)");
    	 
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//        	
//        	
//        	
//        	alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        //	Intent intent = new Intent(context, BootReceiver.class);
//        	alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//
//        	// Set the alarm to start at 8:30 a.m.
//        	Calendar calendar = Calendar.getInstance();
//        	calendar.setTimeInMillis(System.currentTimeMillis());
//        	calendar.set(Calendar.HOUR_OF_DAY, 15);
//        	calendar.set(Calendar.MINUTE, 52);
//
//        	alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//        			alarmIntent);
//        	Log.d("Alarm","BootReceiver....(1)");

        }
        
        
        
        
    }
}