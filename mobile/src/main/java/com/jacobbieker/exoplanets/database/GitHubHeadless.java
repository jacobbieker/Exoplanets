package com.jacobbieker.exoplanets.database;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;

import com.jacobbieker.exoplanets.xml.DatabaseStrings;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.BufferOverflowException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GitHubHeadless extends Fragment {
    private final String TAG = "Github Headless";
    private String mRepositoryName = "open_exoplanet_catalogue";
    private String mOrganizationName = "OpenExoplanetCatalogue";
    private String mRepositoryNameGzip = "oec_gzip";


    public GitHubHeadless() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        try {
            connectToRepository(mOrganizationName, mRepositoryName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setRetainInstance(true);
    }

    public void connectToRepository(String organizationName, String repositoryName) throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();
        GHRepository repository = gitHub.getOrganization(organizationName).getRepository(repositoryName);
        List<GHContent> directory = repository.getDirectoryContent("systems");
        for (int i = 0; i < directory.size(); i++) {
            Log.i(TAG, directory.get(i).getName());
            if (directory.get(i).isFile()) {
                directory.get(i).getContent();
            }
        }
    }

    private void connectToDatabase() {

    }


    private class DownloadDatabaseTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);
                URLConnection connection = url.openConnection();
                InputStream stream = connection.getInputStream();
                stream = new GZIPInputStream(stream);
                InputSource is = new InputSource(stream);
                InputStream input = new BufferedInputStream(is.getByteStream());
                OutputStream output = new FileOutputStream(DatabaseStrings.ASSETS_SYSTEMS_XML);
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
            } catch (BufferOverflowException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }
    }



}
