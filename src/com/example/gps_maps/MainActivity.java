package com.example.gps_maps;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;

public class MainActivity extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	private Button btn;
	private Button btn2;
	private Button btn3;
	private TextView txt;
	private TextView txt2;
	boolean sw=false;
	boolean pres1=false;
	boolean pres2=false;
	boolean pres3=false;
	boolean vis1= false;
	boolean vis2=false;
	LatLng punto1;
	LatLng punto2;
	float bat1=0;
	float bat2=0;
	 float batteryPct =0;
	LocationClient loc ;
	LocationRequest locr;
	IntentFilter ifilter;
	Intent batteryStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        loc = new LocationClient(this,this,this);
        loc.connect();
        btn2=(Button)findViewById(R.id.presi);
        btn=(Button)findViewById(R.id.iniciar);
        btn3=(Button)findViewById(R.id.vis);
        // Create the LocationRequest object
        locr = LocationRequest.create();
        // Use high accuracy
        locr.setInterval(5);
        locr.setFastestInterval(1);
        pres1=true;
        locr.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = this.registerReceiver(null, ifilter);
        
  
        
    	btn.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v){
				
			
            if(pres1==true){
					
					Toast.makeText(getApplicationContext(), "Nivel de presicion:BALANCED POWER ACCURACY",
							   Toast.LENGTH_SHORT).show();
				}
			
	          if(pres2==true){
					
					Toast.makeText(getApplicationContext(), "Nivel de presicion: PRIORITY HIGH ACCURACY",
							   Toast.LENGTH_SHORT).show();
				}
	          if(pres3==true){
					
					Toast.makeText(getApplicationContext(), "Nivel de presicion: LOW POWER",
							   Toast.LENGTH_SHORT).show();
				}
				
				
					
				
				
				
				if(sw==false){
					
					btn.setText("Finalizar");
					
				}
				else{
					
					btn.setText("Iniciar");
					
				}
				
				
				
			
				localizar.run();
		 
				segundoplano.run();
				
			
			
			
				
			}
		
			
			
		});
        
    
    	btn2.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v){
				
              if(pres1==true && pres2==false && pres3==false){
					
            	  
            	  locr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            	  
            	  
					Toast.makeText(getApplicationContext(), "Nivel de presicion:  HIGH ACCURACY",
							   Toast.LENGTH_SHORT).show();
					pres1=false;
					pres2=true;
					pres3=false;
					
					
				}
              
              else{
            	  if(pres2==true&&pres1==false&&pres3==false){
  					
            		  locr.setPriority(LocationRequest.PRIORITY_LOW_POWER);  
            		  
  					Toast.makeText(getApplicationContext(), "Nivel de presicion: LOW POWER",
  							   Toast.LENGTH_SHORT).show();
  					
  					pres1=false;
  					pres2=false;
  					pres3=true;
  				}
            	  else{
            		  if(pres3==true&&pres1==false&&pres2==false){
            		  
            		  locr.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);  
            		  
    				  Toast.makeText(getApplicationContext(), "Nivel de presicion: BALANCED POWER ACCURACY",
    							   Toast.LENGTH_SHORT).show();
            		  
            		  pres1=true;
            		  pres2=false;
            		  pres3=false;
            		  }
            		  
            		  
            	  }
            	  
            	  
              }
			
	         
	       
				
			}
			});
        
    	
     	btn3.setOnClickListener(new OnClickListener() 
    		{
    			
    			@Override
    			public void onClick(View v){
    	     if(vis1==true){
    	    	vis1=false;
    	    	vis2=true;
    	    	 try{
    	    	 GoogleMap mMap= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    	    	 
    	    	  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
    	          punto1.latitude, punto1.longitude), 15));
    	    	
    	    	 }
    	    	 catch(Exception ex){
    	    		 
    	    		 
    	    	 }
    	     }
    	     
    	     else{
    	    	if(vis2=true){
    	    		
    	    		vis2=false;
    	    		vis1=true;
    	    	
    	    	 
    	    	 try{
        	    	 GoogleMap mMap= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	    	 
        	    	  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
        	          punto2.latitude, punto2.longitude), 15));
        	    	
        	    	 }
        	    	 catch(Exception ex){
        	    		 
        	    		 
        	    	 }
    	     }
    	    	 
    	     }
    				
    			}
    			});
    }
 

    
    
    Thread segundoplano = new Thread(new Runnable() {
    	
    
        
    	@SuppressWarnings("null")
		@Override

    	public void run() {
    		actualizar();
    		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    	    float batteryPct = (level / (float)scale)*100;
    		
    		if(sw==false){
    			
    		
    			bat1=batteryPct;
    			sw=true;
    		
  		  
    			
    		}
    		else{
    			
    		
        		
    			float resul;
    			sw=false;
    			bat2=batteryPct;
    		
		  
    			
    			txt=(TextView)findViewById(R.id.bats);
    			txt2=(TextView)findViewById(R.id.dist);
    			resul= bat1-bat2;
    			txt.setText(Float.toString(resul) + "%");
    		
    			Location a = new Location("A");
    			a.setLatitude(punto1.latitude);
    			a.setLongitude(punto1.longitude);
    			Location b= new Location("B");
    			b.setLatitude(punto2.latitude);
    			b.setLongitude(punto2.longitude);
    			 double distancia = a.distanceTo(b);
    			 
    			 if(distancia >=1000){
    				 
    				 distancia=distancia/1000;
    				 txt2.setText(String.format("%.2f", distancia)+ " Km");
    			 }
    			 else{
    			 
    			 
    			  txt2.setText(String.format("%.2f",distancia)+ " metros");
    			 }
    			 
    			
    			
    			bat1=0;
    			bat2=0;
    			
    		}
    	    
    	    
    	
    		}	
    
	});
    
    
    
    Thread localizar = new Thread(new Runnable() {
    	@Override
    	
    	
    	public void run(){
    		
    		GoogleMap mMap= null;
    		  Location act = loc.getLastLocation();
    	    
    	    	if(act!=null){
    	    		
    	    		if (mMap == null) {
    	         	     
    	       	      mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    	      	
    	      	   
    	      	   
    	      	   if (mMap != null) {
    	       	      LatLng pos = new LatLng(act.getLatitude(),act.getLongitude());
    	     	        
    	       	      
    	     	        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	     	        	
    	     	         mMap.setMyLocationEnabled(true);
    	     	         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
    	                  act.getLatitude(), act.getLongitude()), 15));
    	     	         
    	     	         if(sw==false){
    	     	        	 
    	     	                Marker myMaker = mMap.addMarker(new MarkerOptions()
    	    	     	       .position(pos)
    	    	     	       .title("Inicio")  
    	    	     	       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); //Color del marcador
    	    	     	         punto1=pos;
    	    	     	         vis2=true;
    	     	        	 
    	     	         }
    	     	         else{
	    	     	         punto2=pos;
                             vis1=true;
    	     	        	 
    	     	            Marker myMaker = mMap.addMarker(new MarkerOptions()
    	    	     	       .position(punto2)
    	    	     	       .title("Llegada")  
    	    	     	       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))); //Color del marcador
    	     	        	 
    	     	            
    	     	            
    	     	           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
    	     	                  punto2.latitude, punto2.longitude), 15));
    	     	           
    	     	          
    	     	         }
    	     	         
    	     	    
    	     	  
    	     	         
    	     	        ;
    	     	      }
    	      	   }
    	    		
    	    		
    	    		
    	    		
    	    	}
    		
    		
    	}
    	
    });
    
    public void actualizar(){
    
    	ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = this.registerReceiver(null, ifilter);
    }
    
    
    
 
    

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
    
    
}

 
