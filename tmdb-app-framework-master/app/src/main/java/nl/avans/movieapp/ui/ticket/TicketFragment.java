package nl.avans.movieapp.ui.ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.avans.movieapp.R;
import nl.avans.movieapp.db.SqlManager;
import nl.avans.movieapp.db.TicketDAO;

public class TicketFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_film_ticket,container,false);
        SqlManager sqlManager = new SqlManager();
        TicketDAO ticketDAO = new TicketDAO();
        List<Map<String, Object>> rows = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            ResultSet result = sqlManager.executeSql(ticketDAO.getAllTickets());
            ResultSetMetaData md = result.getMetaData();
            int columns = md.getColumnCount();

            while(result.next()) {
                Map<String, Object> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; i++) {
                    row.put(md.getColumnName(i), result.getObject(i));
                }
                rows.add(row);
            }

            sb.append("\n");

            for (Map<String, Object> row : rows) {
                row.forEach((column, value) -> {
                    sb.append(column + ": " + value + "\n");
                });
                sb.append("\n");
                sb.append("-------------------------------------------");
                sb.append("\n");
                sb.append("\n");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        TextView textView = view.findViewById(R.id.textView2);

        textView.setText(sb.toString());

        return view;
    }
}
