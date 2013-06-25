package com.proj.mapshlpr;

import java.util.ArrayList;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.PathOverlay;

public class Hlpr extends Thread{
	RoadManager roadmanager;
	MapView maps;
	GeoPoint startPoint;
	PathOverlay overlay;
	public void doSmth(MapView map,GeoPoint startPoint){
		roadmanager=new OSRMRoadManager();
        this.maps=map;
        this.startPoint=startPoint;
		
	}
	public Overlay getOverLay(){
		return overlay;
		
	}
	Road road ;
	@Override
	public void run() {
	ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(startPoint);
        waypoints.add(new GeoPoint(28.20,83.95)); //end point
       road = roadmanager.getRoad(waypoints);
        this.overlay= RoadManager.buildRoadOverlay(road, maps.getContext());
        
	
	}
	public Road getroad(){
		return this.road;
	}
	

}
