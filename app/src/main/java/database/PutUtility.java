package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class PutUtility {

    private Map<String, String> params = new HashMap<>();
    private static HttpURLConnection httpConnection;
    private static BufferedReader reader;
    private static String Content;
    private StringBuffer sb1;
    private StringBuffer response;

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setParam(String key, String value) {
        params.put(key, value);
    }

    public String getData(String Url) {


        StringBuilder sb = new StringBuilder();

        try {
            // Defined URL  where to send data

            URL url = new URL(Url);

            URLConnection conn = null;
            conn = url.openConnection();

            // Send POST data request
            httpConnection = (HttpURLConnection) conn;
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            response = new StringBuffer();


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }

        return response.toString();
    }


    public String postData(String Url) {


        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            String value = null;
            value = params.get(key);


            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }

        try {
            // Defined URL  where to send data

            URL url = new URL(Url);

            URLConnection conn = null;
            conn = url.openConnection();

            // Send POST data request
            httpConnection = (HttpURLConnection) conn;
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            OutputStreamWriter wr = null;

            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(sb.toString());
            wr.flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }


        return response.toString();
    }


    public String putData(String Url) {


        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
                if (value.contains("+"))
                    value = value.replace("+", "%20");

                //return sb.toString();


                // Get the server response

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }

        try {
            // Defined URL  where to send data

            URL url = new URL(Url);

            URLConnection conn = null;
            conn = url.openConnection();

            // Send PUT data request
            httpConnection = (HttpURLConnection) conn;
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setRequestMethod("PUT");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(false);
            OutputStreamWriter wr = null;

            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(sb.toString());
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            ;
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb1.append(line + " ");
            }

            // Append Server Response To Content String
            Content = sb.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }
        // Send PUT data request
        return Url;

    }


    public String deleteData(String Url) {


        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {

            try {
                // Defined URL  where to send data

                URL url = new URL(Url);

                URLConnection conn = null;
                conn = url.openConnection();

                // Send POST data request
                httpConnection = (HttpURLConnection) conn;
                httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpConnection.setRequestMethod("DELETE");
                httpConnection.connect();


                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb1.append(line + " ");
                }

                // Append Server Response To Content String
                Content = sb.toString();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }


        }
        return Url;

    }
}
