package com.jacobbieker.exoplanets.beans;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by Bieker on 11/15/13.
 */
public class System extends SugarRecord<System> {
    //System info
    public String systemName;
    public String rightAscension;
    public String declination;
    public String distance;
    public String system;

    //Link Info

    /**
     *
     * @param id The id used to find the system in the SQLite table
     * @param systemName The name of the system
     * @param rightAscension The right ascension of the system with respect to Earth
     * @param declination The declination of the system with respect to Earth
     * @param distance The distance from Earth
     */
    public System(Context context, String systemName, String rightAscension, String declination, String distance) {
        this.systemName = systemName;
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.distance = distance;
    }

    public System() {
    }

   /*
    * System Getters and Setters
    */

    /*
    Getters and Setters for Database
     */


    /**
     *
     * @param id Takes the id provided and sets it for the current System
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return A System object
     */
    public String getSystem() {
        return system;
    }

    /**
     *
     * @param system A System object
     */
    public void setSystem(String system) {
        this.system = system;
    }
    /*
    End of getters and setter for Database
     */

    /**
     *
     * @return The name of the System
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     *
     * @param systemName The name of the system
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     *
     * @return The right ascension value
     */
    public String getRightAscension() {
        return rightAscension;
    }

    /**
     *
     * @param rightAscension The right ascension of the system with respect to Earth
     */
    public void setRightAscension(String rightAscension) {
        this.rightAscension = rightAscension;
    }

    /**
     *
     * @return Declination of System with respect to Earth
     */
    public String getDeclination() {
        return declination;
    }

    /**
     *
     * @param declination The declination of the system with respect to Earth
     */
    public void setDeclination(String declination) {
        this.declination = declination;
    }

    /**
     *
     * @return Distance of System from Earth
     */
    public String getDistance() {
        return distance;
    }

    /**
     *
     * @param distance The distance from Earth
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String toString() {
       StringBuilder builder = new StringBuilder();
        if (systemName == null && rightAscension == null && declination == null && distance == null) {
           return " )";
        }
        if (systemName != null) {
            builder.append(" System Name: ");
            builder.append(systemName);
            builder.append("\n");
        }
        if (rightAscension != null) {
            builder.append(" Right Ascension: ");
            builder.append(rightAscension);
            builder.append("\n");
        }
        if (declination != null) {
            builder.append(" Declination: ");
            builder.append(declination);
            builder.append("\n");
        }
        if (distance != null) {
            builder.append(" Distance: ");
            builder.append(distance);
            builder.append("\n");
        }
        builder.append("Hello");

            return builder.toString().substring(1);
        //return system;
    }

}

