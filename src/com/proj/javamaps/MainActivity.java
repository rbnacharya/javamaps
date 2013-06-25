package com.proj.javamaps;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;
import org.osmdroid.bonuspack.overlays.ItemizedOverlayWithBubble;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;

import com.proj.mapshlpr.Hlpr;
import com.rbn.javamaps.R;

public class MainActivity extends Activity {

	MapView map=null;
	 GeoPoint startPoint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		   startPoint = new GeoPoint(27.71, 85.31);
	       
		    map = (MapView) findViewById(R.id.mapview);
		    map.setTileSource(TileSourceFactory.MAPNIK);

		    map.setBuiltInZoomControls(true);
		    map.setMultiTouchControls(true);
		    map.getController().setZoom(16);
		    map.getController().setCenter(startPoint);
		    map.setBuiltInZoomControls(true);
		    map.setClickable(true);
		    map.invalidate();
		    this.doSmthng();
		    
		    
		   
		    
	}
	Hlpr aahl;
	public void doSmthng(){
	    DummyOverlay dumOverlay ;
        dumOverlay=new DummyOverlay(this) {
		};
        List<Overlay> listOfOverlays = map.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(dumOverlay);

		aahl=new Hlpr();
		aahl.doSmth(map, startPoint);
		aahl.start();
		while(aahl.isAlive());
		map.getOverlays().add(aahl.getOverLay());
		this.getMarked();

	}
	public void getMarked(){
		final ArrayList<ExtendedOverlayItem> roadItems = 
				  new ArrayList<ExtendedOverlayItem>();
				ItemizedOverlayWithBubble<ExtendedOverlayItem> roadNodes = 
				  new ItemizedOverlayWithBubble<ExtendedOverlayItem>(this, roadItems, map);
				map.getOverlays().add(roadNodes);
				Road road=aahl.getroad();
				
		 Drawable marker = getResources().getDrawable(R.drawable.marker_node);
	        for (int i=0; i<road.mNodes.size(); i++){
	                RoadNode node = road.mNodes.get(i);
	                ExtendedOverlayItem nodeMarker = new ExtendedOverlayItem("Step "+i, "", node.mLocation, this);
	                nodeMarker.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
	                nodeMarker.setMarker(marker);
	                nodeMarker.setDescription(node.mInstructions);
	                nodeMarker.setSubDescription(road.getLengthDurationText(node.mLength, node.mDuration));
	                
	                Drawable icon = getResources().getDrawable(R.drawable.ic_continue);
	                nodeMarker.setImage(icon);
	                
	                roadNodes.addItem(nodeMarker);
	                
	                
	                
	    }
	    

	                
	        
	        
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public abstract class DummyOverlay extends Overlay {
		 	Context ctt;
	        public DummyOverlay(Context ctx) {
	            super(ctx); // TODO Auto-generated constructor stub
	            ctt=ctx;
	        }
	        

	        @Override
	        protected void draw(Canvas c, MapView osmv, boolean shadow) {}

	        @Override
	        public boolean onDoubleTap(MotionEvent e, MapView mapView) {
	            // This stops the 'jump to, and zoom in' of the default behaviour
	            int zoomLevel = map.getZoomLevel();
	            map.getController().setZoom(zoomLevel + 3);
	        	this.getDialog(e, mapView);

	            return super.onDoubleTap(e, mapView);// This stops the double tap being passed on to the mapview
	        }
	        @Override
	        public boolean onTouchEvent(MotionEvent event, MapView mapView) {
	        	this.getDialog(event, mapView);
	        	return super.onTouchEvent(event, mapView);
	        }
	        @Override
	        public boolean onSingleTapConfirmed(MotionEvent e, MapView mapView) {
	        // TODO Auto-generated method stub
	        	this.getDialog(e,mapView);
		        
	        	return super.onSingleTapConfirmed(e, mapView);
	        }
	        public void getDialog(MotionEvent e,MapView mapView){
	        	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(ctt);
                Projection proj = mapView.getProjection();
                IGeoPoint p = proj.fromPixels(e.getX(), e.getY());
                



	        	dlgAlert.setMessage("Lat: "+p.getLatitudeE6()+"Long:"+p.getLongitudeE6());
	        	dlgAlert.setTitle("Touch Event");
	        	dlgAlert.setPositiveButton("Ok", new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// TODO Auto-generated method stub
						
					}
				} );
	        	dlgAlert.setCancelable(true);
	        	dlgAlert.create().show();
	        	
	        }
	    };

}
