package novo.apps.doitall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class GenTicket {
	private final String filename = "currentinfoaboutauth.conf";
	private String currentTicket;
	private String currentUser;
	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	Context context;
	
	GenTicket(Context c) {
		
		currentTicket = "";
		context = c;
		
	}
	
    public String getCurrentTicket() {
		return currentTicket;
	}
    
	public  String generateNewTicket() {
        Random r=new Random();
        long l0 = r.nextLong();
        
        long l1 = r.nextLong();
        
        
        try {
        	currentTicket = GenTicket.SHA1(String.valueOf(l0) + " " + String.valueOf(l1));
        }
        catch(UnsupportedEncodingException e) {
        	Log.d("GenTicket","No no soporta el encoding");
        	e.printStackTrace();
        	
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
        	Log.d("GenTicket","No no soporta el algoritmo");
			e.printStackTrace();
		}
        
        return currentTicket;
    }
    public void saveTicket() {
    	
    	Log.d("GenTicket","saveTicket...ok(1)");
    	FileOutputStream outputStream;

    	File file = new File(context.getFilesDir(),filename);
    	Log.d("saveTicket","path:" + file.getAbsolutePath());
    	
    	
    	String newuser = currentUser + "\n";
    	try {
    		  outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
    		  outputStream.write(newuser.getBytes());
    		  Log.d("saving","user:" + newuser);
    		  Log.d("saving","ticket:" + currentTicket);
    		  
    		  outputStream.write(currentTicket.getBytes());
    		  outputStream.close();
    		} catch (Exception e) {
    			Toast toast = Toast.makeText(context, 
    	    			"Ocurrio el siguiente error al escribir un archivo:" + e.getMessage(), Toast.LENGTH_LONG);
    	    	toast.show();
    		}
    	Log.d("GenTicket","saveTicket...ok(2)");
    	
    }
    
    
    public void deleteTicket() {
    	File file = new File(context.getFilesDir(),filename);
    	boolean deleted = file.delete();
    	
    	Log.d("DeleteTicket","borrado:" + String.valueOf(deleted));
    	
    	if (deleted) {
    		
    		Toast toast = Toast.makeText(context, 
	    			"Se borraron los datos de acceso a su cuenta", Toast.LENGTH_LONG);
	    	toast.show();
    	}
    	else {
    		Toast toast = Toast.makeText(context, 
	    			"No fue posible eliminar los datos de acceso a su cuenta. Intente instalar de nuevo Doitall",
	    			Toast.LENGTH_LONG);
	    	toast.show();
    		
    	}
    }
    
    public String readTicket() {

    	FileInputStream inputStream;

    	File file = new File(context.getFilesDir(),filename);
    	Log.d("saveTicket","path:" + file.getAbsolutePath());
    	
    	if (!file.exists()) {
    		Log.d("readTicket","no exist:" + file.getAbsolutePath());
    		return "";
    		
    	}
    	
    	try {
    		inputStream = context.openFileInput(filename);
    		
    		 char current;
    		 String result = "";
             while (inputStream.available() > 0) {
                 current = (char) inputStream.read();
                 if (current == '\n') {
                	 currentUser = result;
                	 result = "";
                 }
                 else {
                	 result = result + String.valueOf(current);
                 }

             }
    		 currentTicket = result;
    		} catch (Exception e) {
    			Toast toast = Toast.makeText(context, 
    	    			"Ocurrio el siguiente error al leer un archivo:" + e.getMessage(), Toast.LENGTH_LONG);
    	    	toast.show();
    		}
    	
    	Log.d("readTicket","currentUser:|"+currentUser+"|");
    	Log.d("readTicket","currentTicket:|"+currentTicket+"|");
    	return currentTicket;
    }


    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}