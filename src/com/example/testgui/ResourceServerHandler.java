package com.example.testgui;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public final class ResourceServerHandler
{
    public static void delete(String filePath)
    {
        File file = new File(filePath);
        file.delete();
    }

    public static String resizeImage(String imagePath) throws IOException {
        int MAXS = 1500;
        int aimHeight,aimWidth;
        File outputFile;
        String filePath = "resizedImage.JPG";

        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (width < MAXS && height < MAXS)
        {
            outputFile = new File(filePath);
            ImageIO.write(bufferedImage,"JPG",outputFile);
            return filePath;
        }

        float multiplier = ((float)height / width);

        if (width<height)
        {
            aimHeight = MAXS;
            aimWidth = Math.round(aimHeight * multiplier);
        }
        else
        {
            aimWidth = MAXS;
            aimHeight = Math.round(aimWidth * multiplier);
        }

        BufferedImage resizedImage = new BufferedImage(aimWidth,aimHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(bufferedImage, 0,0,aimWidth,aimHeight,null);
        graphics2D.dispose();

        outputFile = new File(filePath);
        ImageIO.write(resizedImage,"JPG",outputFile);
        return filePath;
    }

    public static String StoreImage(boolean urlImage, String imagePath, String public_id)
    {
        if (urlImage)
        {
            return StoreUrlImage(imagePath,public_id);
        }
        else
        {
            return StoreLocalImage(imagePath,public_id);
        }

    }

    public static String StoreLocalImage(String imagePath, String public_id)
    {
        try {
            imagePath = resizeImage(imagePath);

            Date now = new Date();
            Long longTime = now.getTime() / 1000;
            String timestamp = longTime.toString();

            BufferedImage bimg = bimg = ImageIO.read(new File(imagePath));

            String width = Integer.toString(bimg.getWidth());
            String height = Integer.toString(bimg.getHeight());

            //Need to find out the exact size of the image
            String eagerURL = "w_"+width+",h_"+height;//+",c_pad"+vertical+"w_"+width+",h_"+height+",c_crop";
            String eager = URLDecoder.decode(eagerURL, StandardCharsets.UTF_8.name());

            String data = "eager="+eager+"&public_id="+public_id+"&timestamp="+timestamp;
            String api_secret = "Wnzk_vp_FU2eSw_pqVVTgl9dn74";

            String signature = DigestUtils.sha1Hex(data+api_secret);

            String URLstring = "https://api.cloudinary.com/v1_1/dl8uqh2na/image" +
                    "/upload?api_key=589668529957739&timestamp="+timestamp+"&signature="+signature+"&eager="+eagerURL
                    +"&public_id="+public_id;

            //System.out.println(URLstring);
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = (HttpResponse<String>) Unirest.post(URLstring)
                    .field("file", new File(imagePath)).asString();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(response.getBody());
            delete(imagePath);

            return (String) jsonObject.get("secure_url");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String StoreUrlImage(String imagePath, String public_id)
    {
        try {
            Date now = new Date();
            Long longTime = now.getTime() / 1000;
            String timestamp = longTime.toString();

            URL url = new URL(imagePath);
            BufferedImage bimg = ImageIO.read(url);
            String width = Integer.toString(bimg.getWidth());
            String height = Integer.toString(bimg.getHeight());

            //Need to find out the exact size of the image
            String eagerURL = "w_"+width+",h_"+height;//+",c_pad"+vertical+"w_"+width+",h_"+height+",c_crop";
            String eager = URLDecoder.decode(eagerURL, StandardCharsets.UTF_8.name());

            String data = "eager="+eager+"&public_id="+public_id+"&timestamp="+timestamp;
            String api_secret = "Wnzk_vp_FU2eSw_pqVVTgl9dn74";

            String signature = DigestUtils.shaHex(data+api_secret);

            String URLstring = "https://api.cloudinary.com/v1_1/dl8uqh2na/image" +
                    "/upload?api_key=589668529957739&timestamp="+timestamp+"&signature="+signature+"&eager="+eagerURL
                    +"&public_id="+public_id+"&file="+imagePath;

            //System.out.println(URLstring);
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = (HttpResponse<String>) Unirest.post(URLstring).asString();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(response.getBody());
            return (String) jsonObject.get("secure_url");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
