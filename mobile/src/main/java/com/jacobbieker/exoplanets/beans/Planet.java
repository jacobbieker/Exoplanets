/*
 * The MIT License (MIT)
 *
 * Copyright (c) $year. Jacob Bieker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jacobbieker.exoplanets.beans;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by Bieker on 11/15/13.
 */


public class Planet extends SugarRecord<Planet> {

    //Planet Info
    public String planet;
    public String planetName;
    public String listStatus;//gives the status of Confrimed or not Confrimed planet
    public String planetMass;
    public String planetPeriod;
    public String planetSemiMajorAxis;
    public String planetEccentricity;
    public String planetDiscoveryMethod;//TODO Expand on What abbreviation means
    public String planetLastUpdate;
    public String planetDiscoveryYear;
    public String planetTemp;

    Context context;
    System system;
    Star star;

    /**
     *
     * @param planetName The Planet's name
     * @param listStatus The list, confirmed or not confirmed, that the exoplanet is on
     * @param planetMass The mass of the planet
     * @param planetPeriod The period of the planet
     * @param planetSemiMajorAxis The semimajor axis of the planet
     * @param planetEccentricity The eccentricity of the planet's orbit
     * @param planetDiscoveryMethod How the planet was discovered
     * @param planetLastUpdate The last time data on this planet was updated
     * @param planetDicoveryYear The year the planet was discovered
     * @param planetTemp The temperature of the planet
     */
    public Planet(Context context, System system, Star star, String planetName, String listStatus, String planetMass, String planetPeriod, String planetSemiMajorAxis, String planetEccentricity, String planetDiscoveryMethod, String planetLastUpdate, String planetDicoveryYear, String planetTemp) {
        this.context = context;
        this.system = system;
        this.star = star;
        this.planetName = planetName;
        this.listStatus = listStatus;
        this.planetMass = planetMass;
        this.planetPeriod = planetPeriod;
        this.planetSemiMajorAxis = planetSemiMajorAxis;
        this.planetEccentricity = planetEccentricity;
        this.planetDiscoveryMethod = planetDiscoveryMethod;
        this.planetLastUpdate = planetLastUpdate;
        this.planetDiscoveryYear = planetDicoveryYear;
        this.planetTemp = planetTemp;
    }

    public Planet() {

    }

    /**
     *
     * @return planet
     */
        public String getPlanet() {
        return planet;
    }

    /**
     *
     * @param planet
     */
    public void setPlanet(String planet) {
        this.planet = planet;
    }

    /**
     *
     * @return Name of Planet
     */
    public String getPlanetName() {
        return planetName;
    }

    /**
     *
     * @param planetName The name of the planet
     */
    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getListStatus() {
        return listStatus;
    }
    public void setListStatus(String listStatus) {
        this.listStatus = listStatus;
    }

    public String getPlanetMass() {
        return planetMass;
    }
    public void setPlanetMass(String planetMass) {
        this.planetMass = planetMass;
    }

    public String getPlanetPeriod() {
        return planetPeriod;
    }
    public void setPlanetPeriod(String planetPeriod) {
        this.planetPeriod = planetPeriod;
    }

    public String getPlanetSemiMajorAxis() {
        return planetSemiMajorAxis;
    }
    public void setPlanetSemiMajorAxis(String planetSemiMajorAxis) {
        this.planetSemiMajorAxis = planetSemiMajorAxis;
    }

    public String getPlanetEccentricity() {
        return planetEccentricity;
    }
    public void setPlanetEccentricity(String planetEccentricity) {
        this.planetEccentricity = planetEccentricity;
    }

    public String getPlanetDiscoveryMethod() {
        return planetDiscoveryMethod;
    }
    public void setPlanetDiscoveryMethod(String planetDiscoveryMethod) {
        this.planetDiscoveryMethod = planetDiscoveryMethod;
    }

    public String getPlanetDiscoveryYear() {
        return planetDiscoveryYear;
    }
    public void setPlanetDiscoveryYear(String planetDiscoveryYear) {
        this.planetDiscoveryYear = planetDiscoveryYear;
    }

    public String getPlanetLastUpdate() {
        return planetLastUpdate;
    }
    public void setPlanetLastUpdate(String planetLastUpdate) {
        this.planetLastUpdate = planetLastUpdate;
    }

    public String getPlanetTemp() {
        return planetTemp;
    }
    public void setPlanetTemp(String planetTemp) {
        this.planetTemp = planetTemp;
    }

    /**
     *
     * @return The information on the planet
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (planetName == null && listStatus == null && planetMass == null && planetPeriod == null && planetSemiMajorAxis == null && planetEccentricity == null && planetDiscoveryMethod == null && planetLastUpdate == null && planetDiscoveryYear == null && planetTemp == null) {
            return "";
        }
        if(planetName != null) {
            builder.append(" Planet Name:");
            builder.append(planetName);
            builder.append("\n");
        }
        if(listStatus != null) {
            builder.append(" Status:");
            builder.append(listStatus);
            builder.append("\n");
        }
        if(planetLastUpdate != null) {
            builder.append(" Last Update:");
            builder.append(planetLastUpdate);
            builder.append("\n");
        }
        if(planetDiscoveryYear != null) {
            builder.append(" Discovery Year:");
            builder.append(planetDiscoveryYear);
            builder.append("\n");
        }
        if(planetDiscoveryMethod != null) {
            builder.append(" Discovery Method:");
            builder.append(planetDiscoveryMethod);
            builder.append("\n");
        }
        if(planetMass != null) {
            builder.append(" Mass:");
            builder.append(planetMass);
            builder.append("\n");
        }
        if(planetPeriod != null) {
            builder.append(" Period:");
            builder.append(planetPeriod);
            builder.append("\n");
        }
        if(planetTemp != null) {
            builder.append(" Temperature:");
            builder.append(planetTemp);
            builder.append("\n");
        }
        if(planetEccentricity != null) {
            builder.append(" Eccentricity:");
            builder.append(planetEccentricity);
            builder.append("\n");
        }
        if(planetSemiMajorAxis != null) {
            builder.append(" Semi-Major Axis:");
            builder.append(planetSemiMajorAxis);
            builder.append("\n");
        }
        return builder.toString().substring(1);
    }
}

