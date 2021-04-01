package nl.avans.movieapp.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import nl.avans.movieapp.R;
import nl.avans.movieapp.controller.MovieController;
import nl.avans.movieapp.domain.Movie;

/**
 *
 */
public class HomeGridAdapter
        extends RecyclerView.Adapter<HomeGridAdapter.MoviesGridViewHolder>
        implements MovieController.MovieControllerListener {

    private final String TAG = this.getClass().getSimpleName();
    private final ArrayList<Movie> moviesArrayList;
    private OnMovieSelectionListener listener;

    public HomeGridAdapter(ArrayList<Movie> moviesArrayList, OnMovieSelectionListener listener) {
        Log.d(TAG, "Constructor aangeroepen");
        this.moviesArrayList = moviesArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MoviesGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreate aangeroepen");

        int layoutIdForListItem = R.layout.home_movies_grid_item;
        final boolean shouldAttachToParentImmediately = false;

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MoviesGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesGridViewHolder holder, int position) {
        Movie movie = moviesArrayList.get(position);
        Log.d(TAG, movie.toString());

//        holder.mMovieName.setText(movie.getTitle());
        Picasso.get()
                .load(movie.getPoster_path())
                .resize(700, 700)
                .centerInside()
                .into(holder.mMovieImage);
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    @Override
    public void onMoviesAvailable(List<Movie> movies) {
        Log.d(TAG, "We have " + movies.size() + " items");

        this.moviesArrayList.clear();
        this.moviesArrayList.addAll(movies);
        notifyDataSetChanged();
    }

    public class MoviesGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mMovieName;
        public ImageView mMovieImage;

        public MoviesGridViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieName = (TextView) itemView.findViewById(R.id.home_movies_griditem_name);
            mMovieImage = (ImageView) itemView.findViewById(R.id.home_movies_griditem_imageurl);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick on item " + getAdapterPosition());
            listener.onMovieSelected(getAdapterPosition());
        }
    }

    public interface OnMovieSelectionListener {
        void onMovieSelected(int position);
    }
}
