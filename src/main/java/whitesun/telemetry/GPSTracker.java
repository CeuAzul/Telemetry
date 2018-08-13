package whitesun.telemetry;

        import android.app.AlertDialog;
        import android.app.Service;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.os.IBinder;
        import android.provider.Settings;
        import android.util.Log;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;


    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    double altitude;
    double speed;
    double cOg;
    double distX;
    double distY;
    double distTotal;

    double latitudeReference;
    double longitudeReference;
    boolean temReferencia = false;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 500; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;


    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);


            if (!isGPSEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                speed = location.getSpeed();
                                altitude = location.getAltitude();
                                cOg = location.getBearing();
                                setReferencePoint();
                                setDistancias(latitude, longitude);

                            }
                        }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    private void setDistancias(double latB, double lngB) {
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");

        locationA.setLatitude(latitudeReference);
        locationA.setLongitude(longitudeReference);
        locationB.setLatitude(latitudeReference);
        locationB.setLongitude(lngB);

        distX = locationA.distanceTo(locationB);

        if(lngB > longitudeReference){
            distX = distX*(-1);
        }

        locationA.setLatitude(latitudeReference);
        locationA.setLongitude(longitudeReference);
        locationB.setLatitude(latB);
        locationB.setLongitude(longitudeReference);

        distY = locationA.distanceTo(locationB);

        if(latB < latitudeReference){
            distY = distY*(-1);
        }

        locationA.setLatitude(latitudeReference);
        locationA.setLongitude(longitudeReference);
        locationB.setLatitude(latB);
        locationB.setLongitude(lngB);

        distTotal = locationA.distanceTo(locationB);
    }

    public void setReferencePoint(){
        if(!temReferencia) {
            if((getLatitude() != 0)&&(getLongitude() != 0) ) {
                Log.d("latitude " ,latitude+"");
                latitudeReference = latitude;
                longitudeReference = longitude;
                temReferencia = true;
            }else{
                temReferencia = false;
            }
        }
    }

    public boolean hasReference(){
        return temReferencia;
    }
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to get altitude
     * */
    public double getAltitude(){
        if(location != null){
            altitude = location.getAltitude();
        }

        return altitude;
    }

    /**
     * Function to get speed
     * */
    public double getSpeed(){
        if(location != null){
            speed = location.getSpeed();
        }

        // return longitude
        return speed;
    }

    /**
     * Function to get cog
     * */
    public double getCoG(){
        if(location != null){
            cOg = location.getBearing();
        }
        return cOg;
    }


    public double getDistX(){
        return distX;
    }

    public double getDistY(){
        return distY;
    }

    public double getDistTotal(){
        return distTotal;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle(mContext.getString(R.string.gpsnotdetected));

        // Setting Dialog Message
        alertDialog.setMessage(mContext.getString(R.string.gostariadeativargps));

        // On pressing Settings button
        alertDialog.setPositiveButton(mContext.getString(R.string.configuracoes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton(mContext.getString(R.string.naoquerousargps), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}