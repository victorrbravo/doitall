package novo.apps.doitall;


import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;;



public class CanvasView extends View implements OnClickListener {
	
	private static final float DWIDTH = 480, DHEIGHT = 800;
	
	private static final float MLEFT = 35;
	 
	private Resources resources;
	 
	private Paint paintObject, paintPath;
	 
	private Bitmap bmpIcon;
	 
	private RectF rect01;
	 
	private Path path01;
	
	private  String currentuser;
	
	public String getCurrentuser() {
		return currentuser;
	}


	public void setCurrentuser(String currentuser) {
		this.currentuser = currentuser;
	}

	private TicketRecord message;
	
	@Override
    public void onClick(View arg0) {
        setVisibility(View.GONE);
    }
	

	public TicketRecord getMessage() {
		return message;
	}

	public void setMessage(TicketRecord message) {
		this.message = message;
	}

	public CanvasView(Context context, AttributeSet attrs) {
		 
		super(context, attrs);
		 
		resources = context.getResources();
		 
		_init();
		 
		}
	
	public void renderText() {
		
	}
	
	
	private void _init() {
		
		paintObject = new Paint();
		 
		paintObject.setARGB(255, 255, 0, 0);
		 
		bmpIcon = BitmapFactory.decodeResource(resources,
		 
		R.drawable.sticky);
		 
//		rect01 = new RectF(250, 500, 350, 600);
//		 
//		path01 = new Path();
//		 
//		path01.moveTo(50, 400);
//		 
//		path01.lineTo(225, 650);
//		 
//		paintPath = new Paint();
//		 
//		paintPath.setColor(0xFF00FF00);
//		 
//		paintPath.setStyle(Paint.Style.STROKE);
//		 
//		paintPath.setStrokeWidth(3);
		 
		}
		 
		@Override
		 
		public void onDraw(Canvas canvas) {
		 
		super.onDraw(canvas);
		 
		 
		// Este código sirve para escalar la pantalla y dibujar en la pantalla.
		 
		canvas.save();
		 
		//canvas.scale(canvas.getWidth() / DWIDTH, canvas.getHeight() / DHEIGHT);
		 
		canvas.drawColor(0x00000000);
		 
	//	canvas.drawCircle(100, 100, 50, paintObject);
		 
		if (bmpIcon != null) {
		 
		canvas.drawBitmap(bmpIcon, 0, 0, null);
		 
		}
		 
//		canvas.drawRect(rect01, paintObject);
		 
//		canvas.drawPath(path01, paintPath);
		paintObject.setColor(Color.BLACK); 
		paintObject.setTextSize(22);

		float linetext = 35;
		float lsp = 22;
		if (!message.getSummary().isEmpty()) {
				//paintObject.setTextAlign(Paint.Align.CENTER);
				Typeface tf4 = Typeface.createFromAsset(resources.getAssets(), "gloriahallelujah.ttf");
				
				
				
				paintObject.setTypeface(tf4);				
			
				ArrayList<String> mysumms  = Utils.splitString(message.getSummary(), 26);
				
				
				for(String l: mysumms) {
					
					canvas.drawText(l, MLEFT, linetext, paintObject);
					linetext = linetext + lsp;
					
				}
				linetext = linetext + lsp;


				ArrayList<String> mydescs  = Utils.splitString(message.getDescription(), 25);
				
				linetext = linetext + lsp;				

				
				for(String l: mydescs) {
					
					canvas.drawText(l, MLEFT, linetext, paintObject);
					linetext = linetext + lsp;
					
				}
							

				canvas.drawText(message.getProject(), MLEFT, linetext, paintObject);
				linetext = linetext + lsp;
				canvas.drawText(message.getTentativedate(), MLEFT, linetext, paintObject);
				linetext = linetext + lsp;
				canvas.drawText(message.getFinishdate(), MLEFT, linetext, paintObject);
				linetext = linetext + lsp;
				canvas.drawText(message.getType(), MLEFT, linetext, paintObject);
				linetext = linetext + lsp;
				canvas.drawText(message.getOwner(), MLEFT, linetext, paintObject);
				linetext = linetext + lsp;
				canvas.drawText(message.getStatus(), MLEFT, linetext, paintObject);

				linetext = linetext + lsp;
				if (!message.getAssignto().isEmpty() && !message.getAssignto().contentEquals(resources.getString(R.string.not_applicable)) ) {
					paintObject.setColor(Color.RED);
					linetext = linetext + lsp;					
					canvas.drawText(resources.getString(R.string.assign_to) +  " " + message.getAssignto(), MLEFT, linetext, paintObject);					
				}
				
				if (!message.getAssignfrom().isEmpty() && !message.getAssignfrom().contentEquals(resources.getString(R.string.not_applicable)) ) {
					paintObject.setColor(Color.RED);
					linetext = linetext + lsp;					
					canvas.drawText(resources.getString(R.string.assign_from) +  " " +   message.getAssignfrom(), MLEFT, linetext, paintObject);					
				}

				
		}
		
		 
		canvas.restore();
		 
		}

}
