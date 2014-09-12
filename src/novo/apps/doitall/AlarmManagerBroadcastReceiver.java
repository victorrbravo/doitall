package novo.apps.doitall;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

 final public static String ONE_TIME = "onetime";
 final public static int mId = 111;
 
 private Calendar targetCal;
 
 public Calendar getTargetCal() {
	return targetCal;
}

public void setTargetCal(Calendar targetCal) {
	this.targetCal = targetCal;
}

@Override
 public void onReceive(Context context, Intent intent) {
   PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         //Acquire the lock
         wl.acquire();

         //You can do the processing here.
         Bundle extras = intent.getExtras();
         StringBuilder msgStr = new StringBuilder();
         
         if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
          //Make sure this intent has been sent by the one-time timer button.
          msgStr.append("One time Timer : ");
         }
         Format formatter = new SimpleDateFormat("hh:mm:ss a");
         msgStr.append(formatter.format(new Date()));

         //Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
         Log.d("AlarmManagerBoradcast", "(1)" + msgStr.toString());

         displayNotificationOne(context);
         
         Log.d("AlarmManagerBoradcast", "(2)" + msgStr.toString());

         //Release the lock
         wl.release();
 }

private void displayNotificationOne(Context context) {
	NotificationCompat.Builder mBuilder =
	        new NotificationCompat.Builder(context)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setContentTitle("Tarea por hacer")
	        .setContentText("Consulte DoitAll por tareas pendientes");
	// Creates an explicit intent for an Activity in your app
	Intent resultIntent = new Intent(context, LoginActivity.class);

	// The stack builder object will contain an artificial back stack for the
	// started Activity.
	// This ensures that navigating backward from the Activity leads out of
	// your application to the Home screen.
	TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
	// Adds the back stack for the Intent (but not the Intent itself)
	stackBuilder.addParentStack(PrincipalActivity.class);
		//Adds the Intent that starts the Activity to the top of the stack
	stackBuilder.addNextIntent(resultIntent);
	PendingIntent resultPendingIntent =
	        stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_UPDATE_CURRENT
	        );
	mBuilder.setContentIntent(resultPendingIntent);
	NotificationManager mNotificationManager =
	    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	// mId allows you to update the notification later on.
	mNotificationManager.notify(mId, mBuilder.build());
	
}

 private void callActivity(Context context) {
     Intent ilogin = new Intent(context, LoginActivity.class);
     ilogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
     context.startActivity(ilogin);

	 
 }

 public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); 
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeTimer(Context context){
     AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pi);
    }
}
