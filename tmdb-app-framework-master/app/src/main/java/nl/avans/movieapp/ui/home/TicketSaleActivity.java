package nl.avans.movieapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.avans.movieapp.R;
import nl.avans.movieapp.db.ChairDAO;
import nl.avans.movieapp.db.ShowDAO;
import nl.avans.movieapp.db.SqlManager;
import nl.avans.movieapp.db.TicketDAO;
import nl.avans.movieapp.domain.Chair;
import nl.avans.movieapp.domain.Show;

public class TicketSaleActivity extends AppCompatActivity {

//    LinearLayout layoutList;
//    Button addbutton;
    Button buybutton;

    List<Integer> chairNrList = new ArrayList<>();
    List<Show> showList = new ArrayList<>();
    List<String> showDateTimeList = new ArrayList<>();
    List<Integer> roomNrList = new ArrayList<>();
    List<Integer> showIdList = new ArrayList<>();

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sale);


        Intent ticketIntent = getIntent();
        int movieId = ticketIntent.getIntExtra("movieId", 0);
        String movieTitle = ticketIntent.getStringExtra("movieTitle");
        String moviePoster = ticketIntent.getStringExtra("moviePoster");

        TicketDAO tickets = new TicketDAO();
        ChairDAO chairs = new ChairDAO();
        SqlManager sqlManager = new SqlManager();
        ResultSet resultSet;

        resultSet = sqlManager.executeSql(chairs.selectAllChairNrs());

        try {
            while (resultSet.next()){
                int chairNr = resultSet.getInt(1);
                chairNrList.add(chairNr);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ShowDAO showDAO = new ShowDAO();
        ResultSet resultSet1;

        resultSet1 = sqlManager.executeSql(showDAO.selectShow(movieId));

        try {
            while (resultSet1.next()){
                Show show = new Show(resultSet1.getInt(1), resultSet1.getInt(2), resultSet1.getInt(3), resultSet1.getString(4));
                showList.add(show);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ImageView posterView = findViewById(R.id.image_view_film_ticket_image);
        TextView filmTitle = findViewById(R.id.text_view_film_ticket_title);

        Picasso.get().load(moviePoster).resize(1000,1200).centerInside().into(posterView);

        filmTitle.setText(movieTitle);

        AppCompatSpinner showSpinner = findViewById(R.id.show_spinner);
        AppCompatSpinner chairSpinner = findViewById(R.id.chair_spinner);

        ArrayAdapter chairAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, chairNrList);

        for (int i = 0; i<showList.size(); i++) {
            showDateTimeList.add(showList.get(i).getDateTime());
        }

        ArrayAdapter showAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, showDateTimeList);

        showSpinner.setAdapter(showAdapter);
        chairSpinner.setAdapter(chairAdapter);

        buybutton = findViewById(R.id.ticket_buy_button);

        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqlManager.executeSql(tickets.insertTicket(Integer.parseInt(chairSpinner.getSelectedItem().toString()),showList.get(showSpinner.getSelectedItemPosition()).getShowId(),showList.get(showSpinner.getSelectedItemPosition()).getRoom()));

            }
        });

    }
}
//        addbutton = findViewById(R.id.ticket_add_button);

//        layoutList = findViewById(R.id.ticket_layout);

//        addbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addView();
//            }
//        });



//    private void addView() {
//
//        final View ticketView = getLayoutInflater().inflate(R.layout.row_add_ticket, null, false);
//
//        AppCompatSpinner showSpinner = (AppCompatSpinner)ticketView.findViewById(R.id.show_spinner);
//        AppCompatSpinner chairSpinner = (AppCompatSpinner)ticketView.findViewById(R.id.chair_spinner);
//        ImageView image = (ImageView)ticketView.findViewById(R.id.ticket_remove);
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, chairNrList);
//        chairSpinner.setAdapter(arrayAdapter);
//
//        showSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                ticketShowList.add(adapterView.getItemAtPosition(position).toString());
//                Log.d(TAG, "onItemClick: " + ticketShowList.toString());
//            }
//        });
//
//        ArrayAdapter showAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, showList);
//        showSpinner.setAdapter(showAdapter);
//
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeView(ticketView);
//            }
//        });
//
//        layoutList.addView(ticketView);
//
//    }
//
//    private void removeView(View view) {
//
//        layoutList.removeView(view);
//
//    }
