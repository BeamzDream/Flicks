package Flicks.codepath.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import Flicks.codepath.com.models.Config;
import Flicks.codepath.com.models.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

/*    // the base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3/movies";
    // parameter for API
    public final static String API_KEY_PARAM = "api_key";
    // tag for logging from this activity
    public final static String TAG = "MovieListActivity";
    // a numeric code to identify the edit activity
    public static final int EDIT_REQUEST_CODE = 20;

    public static final String ID_KEY = "Key";*/

/*    AsyncHttpClient client;*/

    public String id;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.rbVoteAverage) RatingBar rbVoteAverage;
    @BindView(R.id.ivPosterImage) ImageView ivPosterImage;
    @Nullable
    @BindView(R.id.ivBackdropImage) ImageView ivBackdropImage;

    Config config;
    public void setConfig(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        // unwrap movie in via intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        Log.d("MovieDetailsActivity", "Loaded orientation");

        Intent catcher = getIntent();
        String imageUrl = catcher.getStringExtra(MovieAdapter.IMAGE_URL);
        Log.i("Info", "Correct Url is" + imageUrl);


        ImageView imageView = ivPosterImage;
        Glide.with(this)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 30, 0))
                .into(imageView);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

/*        tvOverview.setOnClickListener(new View.OnClickListener) {
            // first parameter is the context, second is the class of the activity to launch
            Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
            // put "extras" into the bundle for access in the edit activity
            i.putExtra(ID_KEY, id);
            // brings up the edit activity with the expectation of a result
            startActivityForResult(i, EDIT_REQUEST_CODE);
        }*/

    }

/*    // get the list of currently playing movies
    private void getTrailer() {
        String url = API_BASE_URL + "/movie/{movie_id}/videos";
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

    }*/

/*

    private void logError(String message, Throwable error, boolean alertUser) {
        Log.e(TAG, message, error);
        // alert users for silent error
        if (alertUser) {
            // show long toast
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
*/


}

