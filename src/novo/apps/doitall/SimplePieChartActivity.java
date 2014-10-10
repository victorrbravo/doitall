/*
 * Copyright 2012 AndroidPlot.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package novo.apps.doitall;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class SimplePieChartActivity extends Activity
{

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    private String alljson;
    private String graphtype;
    private PieChart pie;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);

        // initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.mySimplePieChart);

        
        alljson = getIntent().getStringExtra("alljson");
        graphtype = getIntent().getStringExtra("graphtype");
        
        Log.d("PieChart*","alljson:" + alljson);
        
        // detect segment clicks:
        if (graphtype.contentEquals("pie")) {


        	pie.setOnTouchListener(new View.OnTouchListener() {
        		@Override
        		public boolean onTouch(View view, MotionEvent motionEvent) {
        			PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
        			if(pie.getPieWidget().containsPoint(click)) {
        				Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
        				if(segment != null) {
        					// handle the segment click...for now, just print
        					// the clicked segment's title to the console:
        					System.out.println("Segmento: " + segment.getTitle());
        				}
        			}
        			return false;
        		}
        	});





        	donutSizeSeekBar = (SeekBar) findViewById(R.id.donutSizeSeekBar);

        	donutSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        		@Override
        		public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}

        		@Override
        		public void onStartTrackingTouch(SeekBar seekBar) {}

        		@Override
        		public void onStopTrackingTouch(SeekBar seekBar) {
        			pie.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress()/100f,
        					PieRenderer.DonutMode.PERCENT);
        			pie.redraw();
        			updateDonutText();
        		}
        	});

        	donutSizeTextView = (TextView) findViewById(R.id.donutSizeTextView);
        	updateDonutText();

        	updateSegments();
        }
        else if ( graphtype.contentEquals("bars" )) {
        	Log.d("Bars","graphing bars");
        }


        //pie.getBorderPaint().setColor(Color.WHITE);
        //pie.getBackgroundPaint().setColor(Color.WHITE);
    }

    public static boolean isNumeric(String str)
    {
       return str.matches("-?\\d+(.\\d+)?");
     }
    
    
    protected void updateSegments() {
    	SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);

    	SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);

        SegmentFormatter sf4 = new SegmentFormatter();
        sf4.configure(getApplicationContext(), R.xml.pie_segment_formatter4);

        SegmentFormatter sf5 = new SegmentFormatter();
        sf5.configure(getApplicationContext(), R.xml.pie_segment_formatter5);

        ArrayList<SegmentFormatter> mylist = new ArrayList<SegmentFormatter>();
        mylist.add(sf1);
        mylist.add(sf2);
        mylist.add(sf3);
        mylist.add(sf4);        
        mylist.add(sf5);
        
        
    	try {
    
    			JSONObject jall = new JSONObject(alljson);

    			JSONObject jdata = jall.getJSONObject("data");
    			
    			String mytitle = jall.getString("id");
    			pie.setTitle(mytitle);
    			
    			JSONArray jArray = jdata.getJSONArray("nodes");
    			Log.d("PieChart","jArray:" + String.valueOf(jArray.length()));
    			
    			
    			int sf = 0;
    			for (int i = 0; i < jArray.length(); i++) {
    				JSONObject mynode = jArray.getJSONObject(i);
    				String namenode = mynode.getString("name");
    				Log.d("PieChart", "name node:" + namenode);
    				
    				String parameter1 = mynode.getString("parameter1").trim();
    				if (!isNumeric(parameter1)) {    				
    					Log.d("PieChart", "***");
    					continue;
    				}
    				
    				Log.d("PieChart", "parameter1 node:" + parameter1);
    				
    				int counttasks = Integer.parseInt(parameter1);
    				
        			Log.d("PieChart","counttasks:" + String.valueOf(counttasks));
    				if (counttasks > 0 ) {
    				   				
    					Segment newsegment  = new Segment(namenode + " "
    							+ String.format("%.1f", Float.parseFloat(mynode.getString("index"))*100) + "%", 
    							counttasks);
    					
    			        

    			        EmbossMaskFilter emf = new EmbossMaskFilter(
    			                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

    			        sf1.getFillPaint().setMaskFilter(emf);

    			        Log.d("sf", "name:" + mynode.getString("name") );
    			        
    			        Log.d("sf", "*sf:" + String.valueOf(sf));
    			        Log.d("", "");
    			        
    			        pie.addSeries(newsegment, mylist.get(sf));
    				}
		
    				sf++;
    				if (sf > 4 ) {
    					sf = 0;
    				}
    			}
    	
    	} catch (Exception e) {
    		Log.e("JSON", "*Pie Chart:" + e.toString());	
    	}
    	



    	
    }

    protected void updateDonutText() {
        donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
    }
}
