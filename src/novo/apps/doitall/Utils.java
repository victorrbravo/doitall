package novo.apps.doitall;

import java.util.Calendar;

import android.util.Log;

public class Utils {

	static public Long lastSecondTomorrow() {
		Long result = 0l;
        Calendar calendar = Calendar.getInstance();
                               
//        Log.d("Utils Date:",calendar.toString());
        long currdate = calendar.getTimeInMillis()/1000 ;
        Log.d("Utils Currdate:",String.valueOf(currdate));
        long newdate = currdate + 86400;
        
        if ( (newdate % 86400) > 16199) {
        	newdate = newdate + (86400-(newdate % 86400))+16199;
        }
        else {
        	
        	newdate = newdate + (16199-(newdate % 86400));
        	
        }
        Log.d("Utils Newdate:",String.valueOf(newdate));

		return newdate;
	}
	
	static public Long now() {
		
        Calendar calendar = Calendar.getInstance();
        long currdate = calendar.getTimeInMillis()/1000 ;
        
        Log.d("Utils now", "Now currdate:" + String.valueOf(currdate));
        return currdate; 
		
	}
}
