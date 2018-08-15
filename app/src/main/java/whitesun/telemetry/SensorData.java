package whitesun.telemetry;

import android.util.Log;

public class SensorData {
    private float acx;
    private float acy;
    private float acz;
    private float gyx;
    private float gyy;
    private float gyz;
    private float gyncx;
    private float gyncy;
    private float gyncz;
    private float driftX;
    private float driftY;
    private float driftZ;
    private float gravX;
    private float gravY;
    private float gravZ;
    private float linaccX;
    private float linaccY;
    private float linaccZ;
    private float rotVecX;
    private float rotVecY;
    private float rotVecZ;
    private float steps;
    private float rotVecGameX;
    private float rotVecGameY;
    private float rotVecGameZ;
    private float geoRotVecX;
    private float geoRotVecY;
    private float geoRotVecZ;
    private float magFieldX;
    private float magFieldY;
    private float magFieldZ;
    private float magFieldUncaX;
    private float magFieldUncaY;
    private float magFieldUncaZ;
    private float ironBiasX;
    private float ironBiasY;
    private float ironBiasZ;
    private float orientationX;
    private float orientationY;
    private float orientationZ;
    private float proximityDist;
    private float ambiTemp;
    private float light;
    private float pressure;
    private float relHum;
    private float deviceTemp;
    private float soundIntensity;
    private float latitude;
    private float longitude;
    private float speed;
    private float altitude;
    private float cog;
    private float distX;
    private float distY;
    private float distTotal;


    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getCog() {
        return cog;
    }

    public void setCog(float cog) {
        this.cog = cog;
    }

    public float getDistX() {
        return distX;
    }

    public void setDistX(float distX) {
        this.distX = distX;
    }

    public float getDistY() {
        return distY;
    }

    public void setDistY(float distY) {
        this.distY = distY;
    }

    public float getDistTotal() {
        return distTotal;
    }

    public void setDistTotal(float distTotal) {
        this.distTotal = distTotal;
    }




    public float getSoundIntensity() { return soundIntensity; }
    public void setSoundIntensity(float soundIntensity) { this.soundIntensity = soundIntensity; }



    public float getAcx() {
        return acx;
    }
    public float getAcy() {
        return acy;
    }
    public float getAcz() {
        return acz;
    }
    public float getGyx() {
        return gyx;
    }
    public float getGyy() {
        return gyy;
    }
    public float getGyz() {
        return gyz;
    }
    public float getGyncx() {
        return gyncx;
    }
    public float getGyncy() {
        return gyncy;
    }
    public float getGyncz() {
        return gyncz;
    }
    public float getDriftX() {
        return driftX;
    }
    public float getDriftY() {
        return driftY;
    }
    public float getDriftZ() {
        return driftZ;
    }
    public float getGravX() {
        return gravX;
    }
    public float getGravY() {
        return gravY;
    }
    public float getGravZ() {
        return gravZ;
    }
    public float getLinaccX() {
        return linaccX;
    }
    public float getLinaccY() {
        return linaccY;
    }
    public float getLinaccZ() {
        return linaccZ;
    }
    public float getRotVecX() {
        return rotVecX;
    }
    public float getRotVecY() {
        return rotVecY;
    }
    public float getRotVecZ() {
        return rotVecZ;
    }
    public float getSteps() {
        return steps;
    }
    public float getRotVecGameX() {
        return rotVecGameX;
    }
    public float getRotVecGameY() {
        return rotVecGameY;
    }
    public float getRotVecGameZ() {
        return rotVecGameZ;
    }
    public float getGeoRotVecX() {
        return geoRotVecX;
    }
    public float getGeoRotVecY() {
        return geoRotVecY;
    }
    public float getGeoRotVecZ() {
        return geoRotVecZ;
    }
    public float getMagFieldX() {
        return magFieldX;
    }
    public float getMagFieldY() {
        return magFieldY;
    }
    public float getMagFieldZ() {
        return magFieldZ;
    }
    public float getMagFieldUncaX() {
        return magFieldUncaX;
    }
    public float getMagFieldUncaY() {
        return magFieldUncaY;
    }
    public float getMagFieldUncaZ() {
        return magFieldUncaZ;
    }
    public float getIronBiasX() {
        return ironBiasX;
    }
    public float getIronBiasY() {
        return ironBiasY;
    }
    public float getIronBiasZ() {
        return ironBiasZ;
    }
    public float getOrientationX() {
        return orientationX;
    }
    public float getOrientationY() {
        return orientationY;
    }
    public float getOrientationZ() {
        return orientationZ;
    }
    public float getProximityDist() {
        return proximityDist;
    }
    public float getAmbiTemp() {
        return ambiTemp;
    }
    public float getLight() {
        return light;
    }
    public float getPressure() {
        return pressure;
    }
    public float getRelHum() {
        return relHum;
    }
    public float getDeviceTemp() {
        return deviceTemp;
    }
    public void setAcx(float acx) {
        this.acx = acx;
    }
    public void setAcy(float acy) {
        this.acy = acy;
    }
    public void setAcz(float acz) {
        this.acz = acz;
    }
    public void setGyx(float gyx) {
        this.gyx = gyx;
    }
    public void setGyy(float gyy) {
        this.gyy = gyy;
    }
    public void setGyz(float gyz) {
        this.gyz = gyz;
    }
    public void setGyncx(float gyncx) {
        this.gyncx = gyncx;
    }
    public void setGyncy(float gyncy) {
        this.gyncy = gyncy;
    }
    public void setGyncz(float gyncz) {
        this.gyncz = gyncz;
    }
    public void setDriftX(float driftX) {
        this.driftX = driftX;
    }
    public void setDriftY(float driftY) {
        this.driftY = driftY;
    }
    public void setDriftZ(float driftZ) {
        this.driftZ = driftZ;
    }
    public void setGravX(float gravX) {
        this.gravX = gravX;
    }
    public void setGravY(float gravY) {
        this.gravY = gravY;
    }
    public void setGravZ(float gravZ) {
        this.gravZ = gravZ;
    }
    public void setLinaccX(float linaccX) {
        this.linaccX = linaccX;
    }
    public void setLinaccY(float linaccY) {
        this.linaccY = linaccY;
    }
    public void setLinaccZ(float linaccZ) {
        this.linaccZ = linaccZ;
    }
    public void setRotVecX(float rotVecX) {
        this.rotVecX = rotVecX;
    }
    public void setRotVecY(float rotVecY) {
        this.rotVecY = rotVecY;
    }
    public void setRotVecZ(float rotVecZ) {
        this.rotVecZ = rotVecZ;
    }
    public void setSteps(float steps) {
        this.steps = steps;
    }
    public void setRotVecGameX(float rotVecGameX) {
        this.rotVecGameX = rotVecGameX;
    }
    public void setRotVecGameY(float rotVecGameY) {
        this.rotVecGameY = rotVecGameY;
    }
    public void setRotVecGameZ(float rotVecGameZ) {
        this.rotVecGameZ = rotVecGameZ;
    }
    public void setGeoRotVecX(float geoRotVecX) {
        this.geoRotVecX = geoRotVecX;
    }
    public void setGeoRotVecY(float geoRotVecY) {
        this.geoRotVecY = geoRotVecY;
    }
    public void setGeoRotVecZ(float geoRotVecZ) {
        this.geoRotVecZ = geoRotVecZ;
    }
    public void setMagFieldX(float magFieldX) {
        this.magFieldX = magFieldX;
    }
    public void setMagFieldY(float magFieldY) {
        this.magFieldY = magFieldY;
    }
    public void setMagFieldZ(float magFieldZ) {
        this.magFieldZ = magFieldZ;
    }
    public void setMagFieldUncaX(float magFieldUncaX) {
        this.magFieldUncaX = magFieldUncaX;
    }
    public void setMagFieldUncaY(float magFieldUncaY) {
        this.magFieldUncaY = magFieldUncaY;
    }
    public void setMagFieldUncaZ(float magFieldUncaZ) {
        this.magFieldUncaZ = magFieldUncaZ;
    }
    public void setIronBiasX(float ironBiasX) {
        this.ironBiasX = ironBiasX;
    }
    public void setIronBiasY(float ironBiasY) {
        this.ironBiasY = ironBiasY;
    }
    public void setIronBiasZ(float ironBiasZ) {
        this.ironBiasZ = ironBiasZ;
    }
    public void setOrientationX(float orientationX) {
        this.orientationX = orientationX;
    }
    public void setOrientationY(float orientationY) {
        this.orientationY = orientationY;
    }
    public void setOrientationZ(float orientationZ) {
        this.orientationZ = orientationZ;
    }
    public void setProximityDist(float proximityDist) {
        this.proximityDist = proximityDist;
    }
    public void setAmbiTemp(float ambiTemp) {
        this.ambiTemp = ambiTemp;
    }
    public void setLight(float light) {
        this.light = light;
    }
    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
    public void setRelHum(float relHum) {
        this.relHum = relHum;
    }
    public void setDeviceTemp(float deviceTemp) {
        this.deviceTemp = deviceTemp;
    }


}
