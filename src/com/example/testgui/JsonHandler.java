package com.example.testgui;

import org.json.simple.JSONArray;
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
    public static JSONObject updateContact(JSONObject allJsonObject, int id, String contact, String detail)
    {
        JSONArray contactArray = (JSONArray) allJsonObject.get("contact");
        JSONObject currentContact = (JSONObject) contactArray.get(id);
        currentContact.put("contact",contact);
        currentContact.put("detail",detail);
        contactArray.set(id,currentContact);
        allJsonObject.put("contact",contactArray);

        return allJsonObject;
    }
    public static JSONObject deleteContact(JSONObject allJsonObject, int id)
    {
        JSONArray contact = (JSONArray) allJsonObject.get("contact");
        contact.remove(id);
        allJsonObject.put("contact", contact);
        return allJsonObject;
    }

    public static JSONObject addContact(JSONObject allJsonObject, String contact, String contactDetail)
    {
        JSONArray jsonArray = (JSONArray) allJsonObject.get("contact");
        JSONObject newContact = new JSONObject();
        newContact.put("contact", contact);
        newContact.put("detail",contactDetail);
        jsonArray.add(newContact);

        allJsonObject.put("contact",jsonArray);
        return allJsonObject;
    }

    public static String[] getAllContactDetails(JSONArray contactJsonArray)
    {
        String[] contactDetails = new String[contactJsonArray.toArray().length];

        for(int i=0;i<contactDetails.length;i++)
        {
            JSONObject jsonObject = (JSONObject) contactJsonArray.get(i);
            contactDetails[i] = (String) jsonObject.get("detail");
        }
        return contactDetails;
    }

    public static String[] getAllContacts(JSONArray contactJsonArray)
    {
        String[] contacts = new String[contactJsonArray.toArray().length];

        for (int i=0;i<contacts.length;i++)
        {
            JSONObject jsonObject = (JSONObject) contactJsonArray.get(i);
            contacts[i] = (String) jsonObject.get("contact");
        }
        return contacts;
    }

    public static JSONObject deleteWorkItem(JSONObject allJsonObject, int index)
    {
        JSONArray workPageArray = (JSONArray) allJsonObject.get("work_page");
        JSONArray workJsonArrayNew = new JSONArray();

        for (int i=0;i<workPageArray.toArray().length;i++)
        {
            if (i!=index)
            {
                workJsonArrayNew.add(workPageArray.get(i));
            }
        }
        allJsonObject.put("work_page", workJsonArrayNew);
        return allJsonObject;
    }

    public static JSONObject updateWorkJsonArray(JSONObject allJsonObject, int index, String imageLink,
                                                 String workDescription, String price)
    {
        //Check Price
        char[] priceChar = price.toCharArray();
        boolean moneyInPrice = false;
        JSONArray workPageArray = (JSONArray) allJsonObject.get("work_page");
        JSONArray workJsonArrayNew = new JSONArray();

        if (priceChar[0]!='£')
        {
            price = "£"+price;
        }

        for (int i=0;i<workPageArray.toArray().length;i++)
        {
            if (i==index)
            {
                JSONObject newWorkJson = new JSONObject();
                newWorkJson.put("src",imageLink);
                newWorkJson.put("price_text",price);
                newWorkJson.put("text",workDescription);
                workJsonArrayNew.add(newWorkJson);
            }
            else
            {
                workJsonArrayNew.add(workPageArray.get(i));
            }
        }

        allJsonObject.put("work_page",workJsonArrayNew);
        return allJsonObject;
    }

    public static JSONObject addElementWorkJsonArray(JSONObject allJsonObject, String imageLink, String workDescription,
                                              String price)
    {
        //Check Price
        char[] priceChar = price.toCharArray();
        boolean moneyInPrice = false;

        if (priceChar[0]!='£')
        {
            price = "£"+price;
        }

        JSONArray workJsonArray = (JSONArray) allJsonObject.get("work_page");
        JSONObject newWorkJson = new JSONObject();
        newWorkJson.put("src",imageLink);
        newWorkJson.put("price_text",price);
        newWorkJson.put("text",workDescription);
        workJsonArray.add(newWorkJson);
        allJsonObject.put("work_page",workJsonArray);

        return allJsonObject;


    }

    public static String[] getWorkPrices(JSONArray jsonArray)
    {
        String[] workPrices = new String[jsonArray.toArray().length];
        JSONObject currentWork;
        for (int i=0;i< workPrices.length;i++)
        {
            currentWork = (JSONObject)jsonArray.get(i);
            workPrices[i] = (String) currentWork.get("price_text");
        }
        return workPrices;
    }

    public static String[] getWorkDescriptions(JSONArray jsonArray)
    {
        String[] workDescriptions = new String[jsonArray.toArray().length];
        JSONObject currentWork;

        for (int i=0;i< workDescriptions.length;i++)
        {
            currentWork = (JSONObject) jsonArray.get(i);
            workDescriptions[i] = (String) currentWork.get("text");
        }
        return workDescriptions;
    }

    public static String[] getWorkImagesLinks(JSONArray jsonArray) throws IOException
    {
        String[] works = new String[jsonArray.toArray().length];
        String link;
        JSONObject currentWork;

        for (int i=0;i< works.length;i++)
        {
            currentWork = (JSONObject) jsonArray.get(i);
            link = (String) currentWork.get("src");
            works[i] = link;
        }
        return works;
    }

    public static BufferedImage[] getWorkImages(JSONArray jsonArray) throws IOException
    {
        BufferedImage[] works = new BufferedImage[jsonArray.toArray().length];
        String link;
        JSONObject currentWork;
        BufferedImage bufferedImage;

        for (int i=0;i< works.length;i++)
        {
            currentWork = (JSONObject) jsonArray.get(i);
            link = (String) currentWork.get("src");
            URL url = new URL(link);
            bufferedImage = ImageIO.read(url);
            works[i] = bufferedImage;
        }
        return works;
    }

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
