package com.example.whoworteit;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for using the Google Book Search API to download book
 * information.
 */
public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String HTTP="http";
    private static final String HTTPS="https";


    /**
     * Static method to make the actual query to the Books API.
     *
     * @param queryString the query string.
     * @return the JSON response string from the query.
     */
    static String getCode(Context context, String queryString, String protocol) {

        // Set up variables for the try block that need to be closed in the
        // finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String code = null;

        // Use a StringBuilder to hold the incoming response.
        StringBuilder builder = new StringBuilder();
        try {
            //构建一URL对象
            Uri builderUri;
            builderUri= Uri.parse(queryString).buildUpon().scheme(HTTPS).build();
            URL url = new URL(builderUri.toString());
            //使用openStream得到一输入流并由此构造一个BufferedReader对象
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            code = builder.toString();
            return code;
        } catch (Exception ex) {
            System.err.println(ex);
        }finally {
            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return builder.toString();
    }
}