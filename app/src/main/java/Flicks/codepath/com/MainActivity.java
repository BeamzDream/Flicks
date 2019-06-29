package Flicks.codepath.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Flicks.codepath.com.models.Config;
import Flicks.codepath.com.models.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    // the base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    // parameter for API
    public final static String API_KEY_PARAM = "api_key";
    // tag for logging from this activity
    public final static String TAG = "MovieListActivity";

    //instance field
    AsyncHttpClient client;

    ArrayList<Movie> movies;

    @BindView(R.id.rvMovies) RecyclerView rvMovies;
    MovieAdapter adapter;

    //image config
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the client
        client = new AsyncHttpClient();
        // initialize the configuration on ap  p creation
        movies = new ArrayList<>();
        // initialize the adapter - movies array not reinitialized after
        adapter = new MovieAdapter(movies);

        ButterKnife.bind(this);

        // resolve recycler view and connect a layout manager and adapter
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        getConfiguration();
    }

    // get the list of currently playing movies
    private void getNowPlaying() {
        String url = API_BASE_URL + "/movie/now_playing";
        // Set up request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key)); //API Key Required
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the result
                try {
                    JSONArray results = response.getJSONArray("results");
                    // iterate through the array
                    for (int i=0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);
                        adapter.notifyItemInserted(movies.size()-1 );
                    }
                    Log.i(TAG, String.format("loaded %s movies", results.length()));


                } catch (JSONException e) {
                    logError("Failed to parse on now playing data", e, true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now playing", throwable, true);
            }
        });

    }
    // Get configuration from the API
    private void getConfiguration() {
        String url = API_BASE_URL + "/configuration";
        // Set up request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key)); //API Key Required
        // execute a GET request with JSON response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);
                    Log.i(TAG, String.format("loaded configuration with imageBaseUrl %s and posterSize %s", config.getImageBaseUrl(), config.getPosterSize()));
                    // pass config to adapter
                    adapter.setConfig(config);
                    getNowPlaying();

                } catch (JSONException e) {
                    logError("Failed Parsing Configuration", e, true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting Configuration", throwable, true);
            }
        });
    }
    // handle errors (even silent errors that user is not normally aware of
    private void logError(String message, Throwable error, boolean alertUser) {
        Log.e(TAG, message, error);
        // alert users for silent error
        if (alertUser) {
            // show long toast
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
