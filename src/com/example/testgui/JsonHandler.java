package com.example.testgui;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public final class JsonHandler
{
    public static void updateJsonOnline(JSONObject jsonObject) throws IOException {

        String urlAddress = "https://api.jsonbin.io/b/5f22f458250d377b5dc700da";
        String key = "$2b$10$n/istDwcPLjoT1Sc10mNA.rmT3.ScWeXA3/PZQY/SgGz/YeBvEQGu";

        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("secret-key",key);
        connection.setRequestProperty("versioning","false");

        connection.setRequestMethod("PUT");
        //connection.connect();

        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
        outputStreamWriter.write(String.valueOf(jsonObject));
        outputStreamWriter.flush();
        outputStreamWriter.close();

        System.err.println(connection.getResponseCode());
    }

    public static JSONObject UpdateAllJson(String id, JSONObject object, JSONObject allJson) throws Exception {
        JSONObject test = (JSONObject) allJson.get(id);
        System.out.println(allJson.toString());
        if (test==null)throw new Exception("JSON Object Exception");
        allJson.put(id,object);
        return allJson;
    }

    public static JSONObject UpdateImageLinksMainMenuJson(JSONObject mainJson, String[] imageLinks) throws Exception {
        //imageLinks main,contact,blog,work,news
        String[] label = new String[]{"main","contact","blog","work","news"};

        if (imageLinks.length != 5)throw new IOException("Incorrect amount of imageLinks");
        String src = "_logo_src";
        String test;
        for (int i=0;i<imageLinks.length;i++)
        {
            test = (String) mainJson.get(label[i]+src);
            if (test==null)throw new Exception("JSON Object Exception");
            mainJson.put(label[i]+src,imageLinks[i]);
        }
        return mainJson;
    }

    public static ImageIcon getMainMenuImage(JSONObject json, String category) throws IOException {
        String search = category + "_logo_src";
        String imgURL = (String)json.get(search);
        URL url = new URL(imgURL);
        BufferedImage bufferedImage = ImageIO.read(url);
        return new ImageIcon(bufferedImage);
    }

    public static String getMainMenuImageLink(JSONObject json, String category) throws IOException {
        String search = category + "_logo_src";
        String imgURL = (String)json.get(search);
        return imgURL;
    }



    public static JSONObject getJsonFromUrl(String u, String key)
    {
        try {
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("secret-key",key);
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                }
                in.close();

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject)parser.parse(String.valueOf(response));
                return json;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }
    public static JSONObject getJsonAll()
    {
        String URL = "https://api.jsonbin.io/b/5f22f458250d377b5dc700da/latest";
        String key = "$2b$10$n/istDwcPLjoT1Sc10mNA.rmT3.ScWeXA3/PZQY/SgGz/YeBvEQGu";
        JSONObject jsonObject = getJsonFromUrl(URL, key);
        //System.out.println(jsonObject.toString());
        return jsonObject;
    }

}
