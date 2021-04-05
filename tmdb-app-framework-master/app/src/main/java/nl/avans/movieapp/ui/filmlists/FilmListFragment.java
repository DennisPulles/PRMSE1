package nl.avans.movieapp.ui.filmlists;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.avans.movieapp.R;
import nl.avans.movieapp.db.FilmListDAO;
import nl.avans.movieapp.db.SqlManager;

public class FilmListFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SqlManager manager = new SqlManager();
        FilmListDAO dao = new FilmListDAO();
        ResultSet result;

        result = manager.executeSql(dao.selectAllFilmLists());

        try {
            while (result.next()){
                Log.d(TAG, "onCreateView: " + result.getString("Listname"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_film_list,container,false);
    }
}
