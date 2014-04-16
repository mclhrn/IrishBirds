package com.mickhearne.irishbirds.birds.utilities;

import android.content.Context;

import com.mickhearne.irishbirds.birds.model.Bird;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONPullParser {

	private List<Bird> birds = new ArrayList<Bird>();

	public List<Bird> parseJSON(Context context) throws JSONException, IOException {

        JSONObject obj = parseJSONData(context);
        JSONArray mArray = obj.getJSONArray("birds");

        for (int i = 0; i < mArray.length(); i++) {

            Bird bird = new Bird();

            bird.setName(mArray.getJSONObject(i).getString("name"));
            bird.setLatinName(mArray.getJSONObject(i).getString("latin"));
            bird.setStatus(mArray.getJSONObject(i).getString("status"));
            bird.setIdentification(mArray.getJSONObject(i).getString("identification"));
            bird.setDiet(mArray.getJSONObject(i).getString("diet"));
            bird.setBreeding(mArray.getJSONObject(i).getString("breeding"));
            bird.setWinteringHabits(mArray.getJSONObject(i).getString("wintering_habits"));
            bird.setWhereToSee(mArray.getJSONObject(i).getString("where_to_see"));
            bird.setConservation(mArray.getJSONObject(i).getString("conservation"));
            bird.setImageLarge(mArray.getJSONObject(i).getString("image_large"));
            bird.setImageThumb(mArray.getJSONObject(i).getString("image_thumb"));

            birds.add(bird);
        }

	return birds;
	}


    //Method that will parse the JSON file and will return a JSONObject
    public JSONObject parseJSONData(Context context) {
        String JSONString = null;
        JSONObject JSONObject = null;
        try {

            //open the inputStream to the file
            InputStream inputStream = context.getAssets().open("birds.json");

            int sizeOfJSONFile = inputStream.available();

            //array that will store all the data
            byte[] bytes = new byte[sizeOfJSONFile];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            JSONString = new String(bytes, "UTF-8");
            JSONObject = new JSONObject(JSONString);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (JSONException x) {
            x.printStackTrace();
            return null;
        }
        return JSONObject;
    }
}