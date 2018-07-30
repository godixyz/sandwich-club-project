package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Log tag constant
    private final static String TAG = JsonUtils.class.getSimpleName();

    private final static String NAME_KEY = "name";
    private final static String MAIN_NAME_KEY = "mainName";
    private final static String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private final static String DESCRIPTION_KEY = "description";
    private final static String IMAGE_KEY = "image";
    private final static String INGREDIENTS_KEY = "ingredients";

    // Parse a given json string and return a Sandwich object
    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainJsonObject = new JSONObject(json);

            // Extract the JSONObject associated with the key "name"
            JSONObject name = mainJsonObject.getJSONObject(NAME_KEY);

            // Extract the main name associated with the key "mainName"
            String mainName = name.optString(MAIN_NAME_KEY);

            // Get the JSONArray associated with the key "alsoKnownAs"
            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS_KEY);
            List<String> alsoKnownAs = JsonArrayToList(JSONArrayAlsoKnownAs);

            // Extract the place of origin text associated with the key "placeOfOrigin"
            String placeOfOrigin = mainJsonObject.optString(PLACE_OF_ORIGIN_KEY);

            // Extract the description text associated with the key "description"
            String description = mainJsonObject.optString(DESCRIPTION_KEY);

            // Extract the image path associated with the key "image"
            String image = mainJsonObject.optString(IMAGE_KEY);

            JSONArray JSONArrayIngredients = mainJsonObject.getJSONArray(INGREDIENTS_KEY);
            List<String> ingredients = JsonArrayToList(JSONArrayIngredients);

            // return our sandwich object
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Convert a given JSONArray to a list of data
    private static List<String> JsonArrayToList (JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}