package Flicks.codepath.com.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {

    // values from API
    public String title;
    public String overview;
    public String posterPath; //only the path
    public String backdropPath;
    public Double voteAverage;
    public Integer id;

    //no arg constructor
    public Movie() {}

    // initialize from JSON data
    public Movie(JSONObject movie) throws JSONException {
        title = movie.getString("title");
        overview = movie.getString("overview");
        posterPath = movie.getString("poster_path");
        backdropPath = movie.getString("backdrop_path");
        voteAverage = movie.getDouble("vote_average");
        id = movie.getInt("id");


    }

    public Integer getId() {
        return id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
