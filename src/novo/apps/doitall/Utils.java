package novo.apps.doitall;

import java.util.ArrayList;
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
	/*
	 * 0 start of week
	 * 1 middle of week
	 * 2 end of week
	 */
	static public Long dayOfNextWeek(int posweek) {
		Long result = 0l;
        Calendar calendar = Calendar.getInstance();
        int factorday = 9;
        
        if (posweek == 0 ) {
        	factorday = 7;
        }
        else if (posweek == 2 ) {
        	 factorday = 11;
        }
        
//        Log.d("Utils Date:",calendar.toString());
        long currdate = calendar.getTimeInMillis()/1000 ;
        Log.d("Utils Currdate:",String.valueOf(currdate));
        long newdate = currdate + 86400;
        
        int dow = calendar.get(calendar.DAY_OF_WEEK)-1;
        
        Log.d("dow",String.valueOf(dow));        
       	newdate = newdate + (86400*(factorday-dow));
        Log.d("Utils middleNextWeek:",String.valueOf(newdate));
        
		return newdate;
	}
	static public Long dayOfNextMonth(int posweek) {
		Long newdate = 0l;
        Calendar calendar = Calendar.getInstance();
      
        
//        Log.d("Utils Date:",calendar.toString());
    
        int currmonth = calendar.get(Calendar.MONTH);
    
        int curryear = calendar.get(Calendar.YEAR);
        int nextmonth = 0;
        if ( currmonth == Calendar.DECEMBER) {
        	nextmonth = Calendar.JANUARY;
        	
        	 curryear =  curryear + 1;
        }
        else {
        	nextmonth = currmonth +1;
        }
        
        calendar.set(Calendar.MONTH, nextmonth);
        calendar.set(Calendar.YEAR, curryear);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("dow",String.valueOf(dow));
                
        newdate = calendar.getTimeInMillis()/1000;
        
        int nextmonday = (8-dow);
        if ( nextmonday > 6 ) {
        	nextmonday = nextmonday - 7;
        }
        newdate = newdate + (86400*nextmonday);
        
       	
        Log.d("Utils middleNextWeek:",String.valueOf(newdate));
        
		return newdate;
	}
	
	static public Long now() {
		
        Calendar calendar = Calendar.getInstance();
        long currdate = calendar.getTimeInMillis()/1000 ;
        
        Log.d("Utils now", "Now currdate:" + String.valueOf(currdate));
        return currdate; 
		
	}
	
	static public ArrayList<String> splitString(String in, int margin) {
		ArrayList<String> result = new ArrayList<String>();
		
		int mleft = 0;
		int mright = margin;
		int length = in.length();

		if (mright >= length ) {
			mright = length;
		}
		boolean isfinish = false;		
		while( !isfinish ) {			
			if ( mright >= length ) {
				mright = length;
				isfinish = true;
			}
			String mytemp = in.substring(mleft,mright);
			Log.d("splitString","add:" + mytemp);
			result.add(mytemp);			
			mleft = mleft + margin;
			mright = mright + margin; 
		}
		
		return result;
	}
}
