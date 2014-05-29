package novo.apps.doitall;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AboutActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        

    }



        
    public void onAboutClick(View view) {
         
    	Log.d("onAboutClick","Set result");
//    	Intent i = new Intent();
//    	
//    	setResult(5, i);
        finish();
    }    
    
}
