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

package com.jacobbieker.exoplanets.xml;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.JsonReader;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jacob on 12/20/2014.
 */
public class JsonPullerService extends IntentService {

    public JsonPullerService() {
        super("JsonPullerService");
    }

    public JsonPullerService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String urlString = intent.getStringExtra("URL1");
        URL url = null;
        try {
            url = new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStream stream = null;
            stream = connection.getInputStream();
            ArrayList<JsonSystem> systemsUrls = readJsonStream(stream);//Gets the URLs of all the files

            for (int i = 0; i < systemsUrls.size(); i++) {//Goes through ArrayList
                //Connect to each URL
                URL systemUrl = new URL(systemsUrls.get(i).getUrl());
                URLConnection urlConnection = systemUrl.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputSource inputSource = new InputSource(inputStream);
                InputStream urlInput = new BufferedInputStream(inputSource.getByteStream());
                FileOutputStream outputStream = openFileOutput(systemsUrls.get(i).getName(), Context.MODE_PRIVATE);
                //Log.i("JsonPullerService", "File created at: " + getFilesDir());
                byte data[] = new byte[10971520];
                long total = 0;
                int count;

                while ((count = urlInput.read(data)) != -1) {
                    total+= count;
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
                outputStream.close();
                urlInput.close();
                //End of creating file sequence

                File file = new File(getFilesDir(), systemsUrls.get(i).getName());
                FileInputStream fileInputStream = new FileInputStream(file);
                DatabaseXMLparser databaseXMLparser = new DatabaseXMLparser();
                databaseXMLparser.parse(fileInputStream);
                fileInputStream.close();//End of reading file sequence
                //file.delete();//deletes files so it does not take up space, and for next time Intent runs, file is not there
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<JsonSystem> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readUrlArray(reader);
        } finally{
            reader.close();
        }
    }

    public ArrayList<JsonSystem> readUrlArray(JsonReader reader) throws IOException {
        ArrayList urls = new ArrayList<JsonSystem>();

        reader.beginArray();
        while (reader.hasNext()) {
            urls.add(readUrls(reader));
        }
        reader.endArray();
        return urls;
    }

    public JsonSystem readUrls(JsonReader reader) throws IOException {
        String url = null;
        String systemName = null;


        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            if (name.equalsIgnoreCase("download_url")) {
                url = reader.nextString();
            } else if (name.equalsIgnoreCase("name")) {
                systemName = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        JsonSystem system = new JsonSystem(systemName, url);
        return system;
    }


}
