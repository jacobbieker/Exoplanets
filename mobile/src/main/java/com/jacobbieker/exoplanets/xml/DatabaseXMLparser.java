package com.jacobbieker.exoplanets.xml;

import android.content.Context;

import com.jacobbieker.exoplanets.beans.*;
import com.jacobbieker.exoplanets.beans.System;
import com.jacobbieker.exoplanets.database.GitHubHeadless;
import com.orm.Database;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jacob on 8/25/14.
 */
public class DatabaseXMLparser {

    private static final String ns = null;
    private static final String BINARY = "binary";
    private static final String NAME = "name";
    private static final String RIGHTASCENSION = "rightascension";
    private static final String DECLINATION = "declination";
    private static final String DISTANCE = "distance";
    private static final String STAR = "star";
    private static final String PLANET = "planet";
    File databaseXML = new File(DatabaseStrings.ASSETS_SYSTEMS_XML);
    private Context context;

    public List parse(InputStream is) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, ns);
            parser.nextTag();
            return readDatabase(parser);
        } finally {
            is.close();
        }
    }

    private List readDatabase(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList systems = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "systems");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("system")) {
                systems.add(readSystems(parser));
            } else {
                skip(parser);
            }
        }
        return systems;
    }


    private System currentSystem;
    private Star currentStar;
    //Process System tags in the file
    private List readSystems(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "system");
        List binary = null;
        List star = null;
        List planet = null;
        System system = new System(context, null, null, null, null);
        while (parser.next() != XmlPullParser.END_TAG) {
            continue;
        }
        String entry = parser.getName();
        if (entry.equals(NAME)) {
            system.systemName = parser.getText();
            system.save();
        } else if (entry.equals(RIGHTASCENSION)) {
            system.rightAscension = parser.getText();
            system.save();
        } else if (entry.equals(DECLINATION)) {
            system.declination = parser.getText();
            system.save();
        } else if (entry.equals(DISTANCE)) {
            system.distance = parser.getText();
            system.save();
            currentSystem = system; //Only works if Distance is always last before Star
        } else if (entry.equals(BINARY)) {
            binary = readBinary(parser);
        } else if (entry.equals(STAR)) {
            star = readStar(parser);
        } else {
            skip(parser);
        }
        system.save();
        //TODO This won't work b/c current system not in existence until after readStar and readPlanet
        currentSystem = system;
        return null;
    }

    private List readBinary(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, BINARY);
        List star = null;
        List planet = null;
        List binary = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            continue;
        }
        String entry = parser.getName();
        if (entry.equals(STAR)) {
            star = readStar(parser);
        } else if (entry.equals(PLANET)) {
            planet = readPlanet(parser);
        } else if (entry.equals(BINARY)) {
            binary = readBinary(parser);
        } else {
            skip(parser);
        }
        return null;
    }

    private List readStar(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, STAR);
        List planet = null;
        Star star = new Star(context, currentSystem, null, null, null, null, null, null, null, null, null, null, null);
        while (parser.next() != XmlPullParser.END_TAG) {
            continue;
        }
        String entry = parser.getName();
        if (entry.equals("name")) {
            star.starName = parser.getText();
        } else if (entry.equals("mass")) {
            star.starMass = parser.getText();
        } else if (entry.equals("radius")) {
            star.starRadius = parser.getText();
        } else if (entry.equals("magV")) {
            star.starMagV = parser.getText();
        } else if (entry.equals("magB")) {
            star.starMagB = parser.getText();
        } else if (entry.equals("magJ")) {
            star.starMagJ = parser.getText();
        } else if (entry.equals("magH")) {
            star.starMagH = parser.getText();
        } else if (entry.equals("magK")) {
            star.starMagK = parser.getText();
        } else if (entry.equals("metallicity")) {
            star.starMetallicity = parser.getText();
        } else if (entry.equals("spectraltype")) {
            star.starSpectralType = parser.getText();
        } else if (entry.equals("temperature")) {
            star.starTemp = parser.getText();
        } else if (entry.equals("planet")) {
            star.save();
            currentStar = star;
            planet = readPlanet(parser);
        } else {
            skip(parser);
        }

        return null;
    }

    private List readPlanet(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, PLANET);
        Planet planet = new Planet(context, currentSystem, currentStar, null, null, null, null, null, null, null, null, null, null);
        while (parser.next() != XmlPullParser.END_TAG) {
            continue;
        }
        String entry = parser.getName();
        if (entry.equals("name")) {
            planet.planetName = parser.getText();
        } else if (entry.equals("list")) {
            planet.listStatus = parser.getText();
        } else if (entry.equals("mass")) {
            planet.planetMass = parser.getText();
        } else if (entry.equals("period")) {
            planet.planetPeriod = parser.getText();
        } else if (entry.equals("semimajoraxis")) {
            planet.planetSemiMajorAxis = parser.getText();
        } else if (entry.equals("eccentricity")) {
            planet.planetEccentricity = parser.getText();
        } else if (entry.equals("discoverymethod")) {
            planet.planetDiscoveryMethod = parser.getText();
        } else if (entry.equals("lastupdate")) {
            planet.planetLastUpdate = parser.getText();
        } else if (entry.equals("discoveryyear")) {
            planet.planetDiscoveryYear = parser.getText();
        } else if (entry.equals("temperature")) {
            planet.planetTemp = parser.getText();
        } else {
            skip(parser);
        }
        planet.save();
        return null;
    }



    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if(parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
