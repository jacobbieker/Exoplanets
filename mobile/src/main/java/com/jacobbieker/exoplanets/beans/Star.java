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
public class Star extends SugarRecord<Star> {

    //Star Info
    public String starName;
    public String starMass;
    public String starRadius;
    public String starMagV;
    //TODO Add support for plus or minus error in measurement
    public String starMagB;
    public String starMagJ;
    public String starMagH;
    public String starMagK;
    public String starMetallicity;
    public String starSpectralType;
    public String starTemp;

    Context context;
    System system;

    /**
     *
     * @param starName The name
     * @param starMass The mass
     * @param starRadius The radius
     * @param starMagV The V magnitude
     * @param starMagB The B magnitude
     * @param starMagJ The J magnitude
     * @param starMagH The H magnitude
     * @param starMagK The K magnitude
     * @param starMetallicity The Metallicity
     * @param starSpectralType The Spectral Type
     * @param starTemp The Temperature
     */
    public Star(Context context, System system, String starName, String starMass, String starRadius, String starMagV, String starMagB, String starMagJ, String starMagH, String starMagK, String starMetallicity, String starSpectralType, String starTemp) {
        this.context = context;
        this.system = system;
        this.starName = starName;
        this.starMass = starMass;
        this.starRadius = starRadius;
        this.starMagV = starMagV;
        this.starMagB = starMagB;
        this.starMagJ = starMagJ;
        this.starMagH = starMagH;
        this.starMagK = starMagK;
        this.starMetallicity = starMetallicity;
        this.starSpectralType = starSpectralType;
        this.starTemp = starTemp;
    }

    public Star() {
        this.context = null;
        this.system = null;
        this.starName = null;
        this.starMass = null;
        this.starRadius = null;
        this.starMagV = null;
        this.starMagB = null;
        this.starMagJ = null;
        this.starMagH = null;
        this.starMagK = null;
        this.starMetallicity = null;
        this.starSpectralType = null;
        this.starTemp = null;
    }
	   /*
	    * Star Getters and Setters
	    */

    public String getStarName() {
        return starName;
    }
    public void setStarName(String starName) {
        this.starName = starName;
    }

    public String getStarMass() {
        return starMass;
    }
    public void setStarMass(String starMass) {
        this.starMass = starMass;
    }

    public String getStarRadius() {
        return starRadius;
    }
    public void setStarRadius(String starRadius) {
        this.starRadius = starRadius;
    }

    //Star magnitude Setter and Getters

    public String getStarMagV() {
        return starMagV;
    }
    public void setStarMagV(String starMagV) {
        this.starMagV = starMagV;
    }

    public String getStarMagB() {
        return starMagB;
    }
    public void setStarMagB(String starMagB){
        this.starMagB = starMagB;
    }

    public String getStarMagH() {
        return starMagH;
    }
    public void setStarMagH(String starMagH){
        this.starMagH = starMagH;
    }

    public String getStarMagJ() {
        return starMagJ;
    }
    public void setStarMagJ(String starMagJ){
        this.starMagJ = starMagJ;
    }

    public String getStarMagK() {
        return starMagK;
    }
    public void setStarMagK(String starMagK){
        this.starMagK = starMagK;
    }

    /**
     *
     * @return The Metallicity
     */
    public String getStarMetallicity() {
        return starMetallicity;
    }

    /**
     *
     * @param starMetallicity The Metallicity
     */
    public void setStarMetallicity(String starMetallicity) {
        this.starMetallicity = starMetallicity;
    }

    /**
     *
     * @return Returns Spectral Type
     */
    public String getStarSpectralType() {
        return starSpectralType;
    }

    /**
     *
     * @param starSpectralType The Spectral Type
     */
    public void setStarSpectralType(String starSpectralType) {
        this.starSpectralType = starSpectralType;
    }

    /**
     *
     * @return Returns the Temperature
     */
    public String getStarTemp() {
        return starTemp;
    }

    /**
     *
     * @param starTemp The Temperature
     */
    public void setStarTemp(String starTemp) {
        this.starTemp = starTemp;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (starName == null && starMass == null && starRadius == null && starMagV == null && starMagB == null && starMagJ == null && starMagH == null && starMagK == null && starMetallicity == null && starSpectralType == null && starTemp == null) {
            return "";
        }

        if (starName != null) {
            builder.append(" Star Name: ");
            builder.append(starName);
            builder.append("\n");
        }
        if (starMass!= null) {
            builder.append(" Mass: ");
            builder.append(starMass);
            builder.append("\n");
        }
        if (starRadius != null) {
            builder.append(" Radius: ");
            builder.append(starName);
            builder.append("\n");
        }
        if (starMetallicity != null) {
            builder.append(" Metallicity: ");
            builder.append(starMetallicity);
            builder.append("\n");
        }
        if (starSpectralType!= null) {
            builder.append(" Spectral Type: ");
            builder.append(starSpectralType);
            builder.append("\n");
        }
        if (starTemp != null) {
            builder.append(" Temperature: ");
            builder.append(starTemp);
            builder.append("\n");
        }
        if (starMagV != null) {
            builder.append(" Mag V: ");
            builder.append(starMagV);
            builder.append("\n");
        }
        if (starMagB != null) {
            builder.append(" Mag B: ");
            builder.append(starMagB);
            builder.append("\n");
        }
        if (starMagH != null) {
            builder.append(" Mag H: ");
            builder.append(starMagH);
            builder.append("\n");
        }
        if (starMagJ != null) {
            builder.append(" Mag J: ");
            builder.append(starMagJ);
            builder.append("\n");
        }
        if (starMagK != null) {
            builder.append(" Mag K: ");
            builder.append(starMagK);
            builder.append("\n");
        }
        return builder.toString().substring(1);

    }

}

