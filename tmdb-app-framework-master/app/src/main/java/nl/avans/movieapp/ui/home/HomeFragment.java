package nl.avans.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.movieapp.R;
import nl.avans.movieapp.controller.MovieController;
import nl.avans.movieapp.db.FilmDAO;
import nl.avans.movieapp.db.ShowDAO;
import nl.avans.movieapp.db.SqlManager;
import nl.avans.movieapp.domain.Movie;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class HomeFragment
        extends Fragment
        implements HomeGridAdapter.OnMovieSelectionListener {

    private final String TAG = this.getClass().getSimpleName();

    private final ArrayList<Movie> mMovies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private HomeGridAdapter mMoviesGridAdapter;
    private EditText searchText;

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
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        // myUpdateOperation();

                        // Stop the spinner from spinning
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        searchText = root.findViewById(R.id.editText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toLowerCase());
            }
        });
        return root;
    }

    private void filter(String text) {
        Log.d(TAG, "filter: Called");
        ArrayList<Movie> filteredList = new ArrayList<>();
        for (Movie bbCharacter: mMovies){
            if (bbCharacter.getTitle().toLowerCase().contains(text)){
                filteredList.add(bbCharacter);
            }
        }
        mMoviesGridAdapter.filterList(filteredList);
    }

    @Override
    public void onMovieSelected(int position) {
        Log.d(TAG, "onMovieSelected at pos " + position);

        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("moviePoster", mMovies.get(position).getPoster_path());
        intent.putExtra("movieTitle", mMovies.get(position).getTitle());
        intent.putExtra("movieId", mMovies.get(position).getId());
        intent.putExtra("movieAdult", mMovies.get(position).getAdult());
        intent.putExtra("movieRelease", mMovies.get(position).getReleaseDate());
        intent.putExtra("movieDescription", mMovies.get(position).getOverview());
        intent.putExtra("movieLanguage", mMovies.get(position).getOriginal_language());
        intent.putExtra("movieGenreIds", mMovies.get(position).getGenreIds());

        SqlManager sqlManager = new SqlManager();
        FilmDAO filmDAO = new FilmDAO();
        sqlManager.executeSql(filmDAO.insertFilm(mMovies.get(position).getId(), mMovies.get(position).getTitle()));


        ShowDAO showDAO = new ShowDAO();
        Log.d(TAG, "onCreate: Dao made");

        ResultSet resultSet = sqlManager.executeSql(showDAO.selectMovieIdShows());
        Log.d(TAG, "onCreate: resultset made");
        ResultSet resultSet1 = sqlManager.executeSql(showDAO.selectAmountShows());
        Log.d(TAG, "onCreate: resultset1 made");
        int showId = -1;
        Log.d(TAG, "onCreate: showid -1");

        try {
            Log.d(TAG, "onCreate: in try");
            ArrayList<Integer> movieIds = new ArrayList<>();
            Log.d(TAG, "onCreate: movieids made");

            while (resultSet.next()) {
                Log.d(TAG, "onCreate: in while");
                movieIds.add(resultSet.getInt(1));
                Log.d(TAG, "onCreate: " + movieIds.size() + " movieIds size");
            }

            Log.d(TAG, "onCreate: out of while");

            if (!movieIds.contains(mMovies.get(position).getId())) {
                Log.d(TAG, "onCreate: in if");
                String dateTime1 = "21:00, 6th of April 2021";
                String dateTime2 = "19:00, 12th of April 2021";
                String dateTime3 = "14:00, 17th of April 2021";
                Log.d(TAG, "onCreate: strings made");
                int roomNr1 = 1;
                int roomNr2 = 2;
                int roomNr3 = 3;
                Log.d(TAG, "onCreate: ints made");
                while (resultSet1.next()) {
                    Log.d(TAG, "onCreate: in while rs1");
                    showId = (resultSet1.getInt(1) + 1);
                    Log.d(TAG, "onCreate: showid = " + showId);
                }
                Log.d(TAG, "onCreate: uit while");
                //int showId = sqlManager.executeSql(showDAO.selectAmountShows()).getInt(1) + 1;
                sqlManager.executeSql(showDAO.insertShow(showId, mMovies.get(position).getId(), roomNr1, dateTime3));
                sqlManager.executeSql(showDAO.insertShow((showId+1), mMovies.get(position).getId(), roomNr2, dateTime1));
                sqlManager.executeSql(showDAO.insertShow((showId+2), mMovies.get(position).getId(), roomNr3, dateTime2));
                Log.d(TAG, "onCreate: executed sql");
            }
            Log.d(TAG, "onCreate: out if");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        startActivity(intent);
    }
}