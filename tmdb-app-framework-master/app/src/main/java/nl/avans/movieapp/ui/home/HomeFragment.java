package nl.avans.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import nl.avans.movieapp.R;
import nl.avans.movieapp.controller.MovieController;
import nl.avans.movieapp.domain.Movie;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class HomeFragment
        extends Fragment
        implements HomeGridAdapter.OnMovieSelectionListener {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private final ArrayList<Movie> mMovies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private HomeGridAdapter mMoviesGridAdapter;

    private static final int ONE_COLUMN = 1;
    private static final int TWO_COLUMNS = 2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.home_movies_grid);

        int numGridColumns = 0;
        if(this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            numGridColumns = TWO_COLUMNS;
        } else if(this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            numGridColumns = TWO_COLUMNS;
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(container.getContext(), numGridColumns);
        mRecyclerView.setLayoutManager(layoutManager);
        mMoviesGridAdapter = new HomeGridAdapter(mMovies, this);
        mRecyclerView.setAdapter(mMoviesGridAdapter);

        // Call API request
        MovieController movieController = new MovieController(mMoviesGridAdapter);
        movieController.loadTrendingMoviesPerWeek();

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        final SwipeRefreshLayout mSwipeRefreshLayout = root.findViewById(R.id.home_movies_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        // myUpdateOperation();

                        // Stop the spinner from spinning
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return root;
    }

    @Override
    public void onMovieSelected(int position) {
        Log.d(LOG_TAG, "onMovieSelected at pos " + position);

        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("moviePoster", mMovies.get(position).getPoster_path());
        intent.putExtra("movieTitle", mMovies.get(position).getTitle());
        intent.putExtra("movieId", mMovies.get(position).getId());
        intent.putExtra("movieAdult", mMovies.get(position).getAdult());
        intent.putExtra("movieRelease", mMovies.get(position).getReleaseDate());
        intent.putExtra("movieDescription", mMovies.get(position).getOverview());
        intent.putExtra("movieLanguage", mMovies.get(position).getOriginal_language());
        intent.putExtra("movieGenreIds", mMovies.get(position).getGenreIds());
        startActivity(intent);
    }
}