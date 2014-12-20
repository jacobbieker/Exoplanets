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

package com.jacobbieker.exoplanets.database;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jacobbieker.exoplanets.xml.DatabaseStrings;
import com.jacobbieker.exoplanets.xml.DatabaseXMLparser;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.BufferOverflowException;
import java.util.Date;
import java.util.zip.GZIPInputStream;


public class GitHub_Service extends IntentService {
    private final String TAG = "Github Service";
    private String mRepositoryName = "open_exoplanet_catalogue";
    private String mOrganizationName = "OpenExoplanetCatalogue";
    private String mRepositoryNameGzip = "oec_gzip";
    private String FILENAME = "systems";
    private Date mLastUpdate;
    private Date mCurrentUpdate;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GitHub_Service(String name) {
        super(name);
    }

    public GitHub_Service() {
        super("GitHub_Service");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        /*if ((new File(DatabaseStrings.ASSETS_SYSTEMS_XML).exists())) {
            File f = new File(DatabaseStrings.ASSETS_SYSTEMS_XML);
            f.delete();
            Log.i(TAG, "File removed");
        }*/
        try {
            if (checkUpdateTime()) {
                try {
                    String urlString = intent.getStringExtra("URL1");
                    URL url = new URL(urlString);
                    URLConnection connection = url.openConnection();
                    InputStream stream = connection.getInputStream();
                    stream = new GZIPInputStream(stream);
                    InputSource is = new InputSource(stream);
                    InputStream input = new BufferedInputStream(is.getByteStream());
                    //FileOutputStream output = new FileOutputStream(DatabaseStrings.ASSETS_SYSTEMS_XML);//TODO Make it actually fine the file
                    FileOutputStream output = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    Log.i(TAG, "File created at:" + getFilesDir());
                    byte data[] = new byte[2097152];
                    long total = 0;
                    int count;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileInputStream inputStream = openFileInput(FILENAME);
                    //FileInputStream fileInputStream = new FileInputStream(new File(DatabaseStrings.ASSETS_SYSTEMS_XML));//Takes outputted file from previous try/catch TODO Make it actually find the file
                    DatabaseXMLparser databaseXMLparser = new DatabaseXMLparser();
                    databaseXMLparser.parse(inputStream);//parses new file into database
                    inputStream.close();
                    Log.i(TAG, "Intent Service Done");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "checkUpdate failed");
            e.printStackTrace();
        }
    }

    private void connectToRepo(String organizationName, String repositoryName) throws IOException {
        //GitHubClient client = new GitHubClient();//Authenticate?
        RepositoryService service = new RepositoryService();
        for (Repository repository : service.getOrgRepositories(organizationName)) {
            if (repository.getName().equalsIgnoreCase(repositoryName)) {
                mCurrentUpdate = repository.getUpdatedAt();
            }
        }
    }

    private boolean checkUpdateTime() throws IOException {
        connectToRepo(mOrganizationName, mRepositoryNameGzip);
        if (mCurrentUpdate.after(mLastUpdate)) {
            return true;
        } else {
            return false;
        }
    }

}
