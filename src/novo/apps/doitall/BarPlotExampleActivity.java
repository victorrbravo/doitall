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

import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.androidplot.LineRegion;
import com.androidplot.pie.Segment;
import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.TextOrientationType;
import com.androidplot.ui.widget.TextLabelWidget;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;

/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class BarPlotExampleActivity extends Activity
{

    private static final String NO_SELECTION_TXT = "Seleccione un punto";
    private XYPlot plot;

    private Spinner spRenderStyle, spWidthStyle;
    private SeekBar sbFixedWidth, sbVariableWidth;
    
    private ArrayList<String> mydatax;
    private ArrayList<String> mydataextra;
    private ArrayList<Integer> mydatay;
    
    
    private XYSeries series1;

    // Create a couple arrays of y-values to plot:
        

    private String alljson;
    
    
    //private MyBarFormatter formatter1;

    private LineAndPointFormatter formatter1;
    
    private MyBarFormatter selectionFormatter;

    private TextLabelWidget selectionWidget;
    private TextLabelWidget valueWidget;

    private Pair<Integer, XYSeries> selection;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        mydatax = new ArrayList<String>();
        mydataextra = new ArrayList<String>();
        mydatay = new ArrayList<Integer>();
        
        
        setContentView(R.layout.bar_plot_example);

        alljson = getIntent().getStringExtra("alljson");
        
        Log.d("BarPlotChart","...before process alljson");        
        
        Log.d("BarPlotChart","...after process alljson");
        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        
        
        processJSON();
        Log.d("BarPlot","............(1)...");

        //formatter1 = new MyBarFormatter(Color.argb(200, 100, 150, 100), Color.LTGRAY);
        
        formatter1 = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.TRANSPARENT, null);
        
        selectionFormatter = new MyBarFormatter(Color.YELLOW, Color.WHITE);

        selectionWidget = new TextLabelWidget(plot.getLayoutManager(), NO_SELECTION_TXT,
                new SizeMetrics(
                        PixelUtils.dpToPix(100), SizeLayoutType.ABSOLUTE,
                        PixelUtils.dpToPix(100), SizeLayoutType.ABSOLUTE),
                TextOrientationType.HORIZONTAL);


        valueWidget = new TextLabelWidget(plot.getLayoutManager(), getString(R.string.without_value),
                new SizeMetrics(
                        PixelUtils.dpToPix(100), SizeLayoutType.ABSOLUTE,
                        PixelUtils.dpToPix(100), SizeLayoutType.ABSOLUTE),
                TextOrientationType.HORIZONTAL);

        selectionWidget.getLabelPaint().setTextSize(PixelUtils.dpToPix(16));
        valueWidget.getLabelPaint().setTextSize(PixelUtils.dpToPix(16));
        
        Log.d("BarPlot","............(2)...");

        // add a dark, semi-transparent background to the selection label widget:
        Paint p = new Paint();
        p.setARGB(100, 0, 0, 0);
        selectionWidget.setBackgroundPaint(p);
        valueWidget.setBackgroundPaint(p);

        selectionWidget.position(
                0, XLayoutStyle.RELATIVE_TO_CENTER,
                PixelUtils.dpToPix(45), YLayoutStyle.ABSOLUTE_FROM_TOP,
                AnchorPosition.TOP_MIDDLE);
        selectionWidget.pack();

        valueWidget.position(
                0, XLayoutStyle.RELATIVE_TO_CENTER,
                PixelUtils.dpToPix(45), YLayoutStyle.ABSOLUTE_FROM_CENTER,
                AnchorPosition.TOP_MIDDLE);
        valueWidget.pack();

        
        Log.d("BarPlot","............(3)...");

        // reduce the number of range labels
       //plot.setTicksPerRangeLabel(1);
        plot.setRangeLowerBoundary(0, BoundaryMode.FIXED);

        plot.getGraphWidget().setGridPadding(30, 10, 30, 0);

         
        
         plot.setTicksPerDomainLabel(1);

         plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);

         plot.setDomainBoundaries(0, mydatax.size()-1, BoundaryMode.FIXED);
         
        // setup checkbox listers:

        plot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    onPlotClicked(new PointF(motionEvent.getX(), motionEvent.getY()));
                }
                return true;
            }
        });
        
        Log.d("BarPlot","............(5)...");
        
        Log.d("BarPlot","............(6)...");

        Log.d("BarPlot","............(7)...");
                
        Log.d("BarPlot","............(8)...");
       
        
       
        plot.getLayoutManager()
        .remove(plot.getLegendWidget());
        
        Log.d("BarPlot","............(9)...");
        Log.d("BarPlot","............(10)...");

        plot.setDomainValueFormat(new GraphXLabelFormat());
        Log.d("BarPlot","............setDomainValueFormat...");
        updatePlot();
        Log.d("BarPlot","............updatePlot()...");
                

    }
    
    public static JSONArray sortJsonArray(JSONArray array) throws JSONException {
        List<JSONObject> jsons = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            jsons.add(array.getJSONObject(i));
        }
        Collections.sort(jsons, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                int lid = 0;
                int rid = 0;
				try {
					String mynumber1 = lhs.getString("name").replaceAll("[^0-9]", "");
					//Log.d("Compare","mynumber1:" + mynumber1);
					if (mynumber1.isEmpty() ) {
						lid = -100;
					}
					else {
						lid = Integer.parseInt(mynumber1);
					}
					String mynumber2 = rhs.getString("name").replaceAll("[^0-9]", "");
					//Log.d("Compare","mynumber2:" + mynumber2);
					if (mynumber2.isEmpty() ) {
						rid = -100;
					}
					else {
						rid = Integer.parseInt(mynumber2);;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int result = 0;
				if (lid < rid ) {
					result = -1;
				}
				else {
					result = 1;
				}
				
                // Here you could parse string id to integer and then compare.
                return result;
            }
        });
        return new JSONArray(jsons);
    }
    
    public class GraphXLabelFormat extends Format {

		@Override
		public StringBuffer format(Object value, StringBuffer buffer,
				FieldPosition arg2) {
			// TODO Auto-generated method stub
			 Log.d("setDomainValueFormat","myindex(1):");
        	Integer myindex = (int) Math.round(Float.parseFloat(value.toString()));
        	Log.d("setDomainValueFormat","***myindex(2):" + String.valueOf(myindex));
        	
        	Log.d("setDomainValueFormat","***dataxsize:" + String.valueOf(mydatax.size()));
        	
        	String newlabel = "n/a";
        	
        	if (myindex>= 0  && myindex < mydatax.size()) {
        		newlabel = mydatax.get(myindex);
        	}
        	buffer.append(newlabel);
            return buffer;

		}

        @Override
        public Object parseObject(String arg0, ParsePosition arg1) {
            // TODO Auto-generated method stub
            return mydatax.indexOf(arg0);
        }

    }
    
    private void processJSON() {
    	try {
    	    
			JSONObject jall = new JSONObject(alljson);

			JSONObject jdata = jall.getJSONObject("data");
			
			String mytitle = jall.getString("id");
			
			plot.setTitle(mytitle);
			
			JSONArray jArray = jdata.getJSONArray("nodes");
			JSONArray jnewArray = sortJsonArray(jArray);
			Log.d("BarChart","jnewArray:" + String.valueOf(jnewArray.length()));		
			
			
			for (int i = 0; i < jnewArray.length(); i++) {
				JSONObject mynode = jnewArray.getJSONObject(i);
				String namenode = mynode.getString("name");
				String title = "";
				if (mynode.has("title")) {
					title = mynode.getString("title");
				}			

				String parameter1 = mynode.getString("parameter1").trim();
				if (!SimplePieChartActivity.isNumeric(parameter1)) {    				
					Log.d("BarChart", "***");
					continue;
				}
				Log.d("BarChart", "name node:" + namenode);
				
				Log.d("BarChart", "parameter1 node:" + parameter1);
				
				int counttasks = Integer.parseInt(parameter1);
				
    			Log.d("BarChart","counttasks:" + String.valueOf(counttasks));
    			if (counttasks == 0 ) {
    				continue;
    			}
    			
				mydatax.add(namenode.replaceAll("[^0-9]", ""));
				mydataextra.add(title);
    			
    			mydatay.add(counttasks);
	
				
			}
			Log.d("mydatax count", "mydatax:" + String.valueOf(mydatax.size()));
			Log.d("mydatax count", "mydatay:" + String.valueOf(mydatay.size()));
	
	} catch (Exception e) {
		Log.e("JSON", "*Pie Chart:" + e.toString());	
	}

    }
    
    public void onClickClose(View view) {
        
        finish();
    }    

    private void updatePlot() {
    	
    	// Remove all current series from each plot
        Iterator<XYSeries> iterator1 = plot.getSeriesSet().iterator();
        while(iterator1.hasNext()) { 
        	XYSeries setElement = iterator1.next();
        	plot.removeSeries(setElement);
        }

        // Setup our Series with the selected number of elements
        series1 = new SimpleXYSeries(mydatay, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Categorías");

        // add a new series' to the xyplot:
         plot.addSeries(series1, formatter1);


        // Setup the BarRenderer with our selected options
     //   MyBarRenderer renderer = ((MyBarRenderer)plot.getRenderer(MyBarRenderer.class));
     //   renderer.setBarWidthStyle(BarRenderer.BarWidthStyle.FIXED_WIDTH);
     //   renderer.setBarWidth(55);
        
//        renderer.setBarRenderStyle((BarRenderer.BarRenderStyle)spRenderStyle.getSelectedItem());
//        renderer.setBarWidthStyle((BarRenderer.BarWidthStyle)spWidthStyle.getSelectedItem());
//        renderer.setBarWidth(sbFixedWidth.getProgress());
//        renderer.setBarGap(sbVariableWidth.getProgress());
        
//        if (BarRenderer.BarRenderStyle.STACKED.equals(spRenderStyle.getSelectedItem())) {
//        	plot.setRangeTopMin(15);
//        } else {
//        	plot.setRangeTopMin(0);
//        }
//	        
        plot.redraw();
    	
    }  
    
    private void onPlotClicked(PointF point) {

        // make sure the point lies within the graph area.  we use gridrect
        // because it accounts for margins and padding as well. 
        if (plot.getGraphWidget().getGridRect().contains(point.x, point.y)) {
            Number x = plot.getXVal(point);
            Number y = plot.getYVal(point);


            selection = null;
            double xDistance = 0;
            double yDistance = 0;

            // find the closest value to the selection:
            for (XYSeries series : plot.getSeriesSet()) {
                for (int i = 0; i < series.size(); i++) {
                    Number thisX = series.getX(i);
                    Number thisY = series.getY(i);
                    if (thisX != null && thisY != null) {
                        double thisXDistance =
                                LineRegion.measure(x, thisX).doubleValue();
                        double thisYDistance =
                                LineRegion.measure(y, thisY).doubleValue();
                        if (selection == null) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance < xDistance) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance == xDistance &&
                                thisYDistance < yDistance &&
                                thisY.doubleValue() >= y.doubleValue()) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        }
                    }
                }
            }

        } else {
            // if the press was outside the graph area, deselect:
            selection = null;
        }

        if(selection == null) {
            selectionWidget.setText(NO_SELECTION_TXT);
            valueWidget.setText(getString(R.string.without_value));
        } else {
        	if (selection.first < mydatax.size()) {
        		selectionWidget.setText("Selección: " + mydatax.get(selection.first) +
        				" Valor: " + selection.second.getY(selection.first));
                valueWidget.setText(mydataextra.get(selection.first));
        	}
        }
        plot.redraw();
    }



    class MyBarFormatter extends BarFormatter {
        public MyBarFormatter(int fillColor, int borderColor) {
            super(fillColor, borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass() {
            return MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer getRendererInstance(XYPlot plot) {
            return new MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter> {

        public MyBarRenderer(XYPlot plot) {
            super(plot);
        }

        /**
         * Implementing this method to allow us to inject our
         * special selection formatter.
         * @param index index of the point being rendered.
         * @param series XYSeries to which the point being rendered belongs.
         * @return
         */
        @Override
        public MyBarFormatter getFormatter(int index, XYSeries series) { 
            if(selection != null &&
                    selection.second == series && 
                    selection.first == index) {
                return selectionFormatter;
            } else {
                return getFormatter(series);
            }
        }
    }
}
