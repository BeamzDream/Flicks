package Flicks.codepath.com.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    // base URL for instancing messages
    String imageBaseUrl;
    // poster size
    String posterSize;
    // backdrop size
    String backdropSize;


    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        // get the image base url
        imageBaseUrl = images.getString("secure_base_url");
        // get the poster sizes
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        // select the right size index 3
        posterSize = posterSizeOptions.optString(3, "w342");
        // parse backdrop options
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");

    }
    // helper method
    public String getImageUrl (String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path); // concatenate all three
    }
    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
