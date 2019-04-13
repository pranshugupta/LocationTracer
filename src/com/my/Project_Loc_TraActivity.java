package com.my;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Project_Loc_TraActivity extends MapActivity implements LocationListener {
    /** Called when the activity is first created. */
    TextView lat,lng;
	MapView mv;
	LocationManager myManager;  //This class provides access to the system location services.
	Location location;
	double latPoint;
    double lngPoint;
    MapController myMapController;
    private String provider;
    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
      
        
        lat=(TextView)findViewById(R.id.latVal);
        lng=(TextView)findViewById(R.id.lngVal);
        mv=(MapView)findViewById(R.id.mv);
        
/*Instantiating the LocationManager object to allow use of System Service,
   and in that, the LocationService*/
        myManager = (LocationManager)
        getSystemService(Context.LOCATION_SERVICE);
        
 /*Location variable stores the location pulled, from myManager,
  and from the best_Provider in myManager, and specify the criteria of the provider required(here none)
  and store that provider in a string called provider*/       
        
        Criteria criteria = new Criteria();
        provider = myManager.getBestProvider(criteria, false);
        location = myManager.getLastKnownLocation(provider);
        
       
        
        mv.setBuiltInZoomControls(true);
        mv.displayZoomControls(true);
        mv.getZoomButtonsController();
        
        
        if(location!=null)
        {
        	/*Getting the Latitude and Longitude
        	   and storing into double variables*/
        	latPoint = location.getLatitude();
            lngPoint = location.getLongitude();
            
 /*This method for the above purpose didn't work out, maybe depreciated:*/
            //Double latPoint = myManager.getCurrentLocation("gps").getLatitude();
            //Double lngPoint = myManager.getCurrentLocation("gps").getLongitude();

/*Setting this value over the TextView*/            
        lat.setText(Double.toString(latPoint));
        lng.setText(Double.toString(lngPoint));
       
 /*Putting the latitude and longitude values in a Point 
  which can be plotted on the MapView*/   
        GeoPoint myGeoLocation = new GeoPoint((int)(latPoint*1E6),(int)(lngPoint*1E6));
        
/*MapController is used to move the focus of the
Google Map to the location you just defined in the Point. 
Use of getController( ) method
from the MapView to establish a controller in the specific Map*/        
        myMapController = mv.getController();

/*Setting this point onto the map and zooming a little*/        
        myMapController.setCenter(myGeoLocation);
        //myMapController.zoomToSpan(200, 200);  
        myMapController.setZoom(16);
        
	}
        else
        {
        	lat.setText("could not be determined");
            lng.setText("could not be determined");
        	Toast.makeText(getApplicationContext(), "No Location Found\nTurn on GPS service", Toast.LENGTH_LONG).show();
        }
}
	
	/*TO display traffic*/
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}
	
/*Since we implemented the LocationListener interface, 
 we need to declare the following methods*/	
	
	@Override
    public void onLocationChanged(Location location) {
        latPoint = (double) (location.getLatitude());
        lngPoint = (double) (location.getLongitude());
        lat.setText(Double.toString(latPoint));
        lng.setText(Double.toString(lngPoint));
        }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disenabled provider " + provider,
                Toast.LENGTH_SHORT).show();

        
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onResume() {
        myManager.requestLocationUpdates(provider, 500, 1, this);
        super.onResume();
    }
    
    /* Remove the mylistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        myManager.removeUpdates(this);
    }
    
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	Toast.makeText(getApplicationContext(), "See you soon!", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }

}

	
