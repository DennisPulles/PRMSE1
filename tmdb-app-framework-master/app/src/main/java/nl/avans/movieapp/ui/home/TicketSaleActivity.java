package nl.avans.movieapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import nl.avans.movieapp.db.SqlManager;
import nl.avans.movieapp.domain.Chair;

public class TicketSaleActivity extends AppCompatActivity {

    LinearLayout layoutList;
    Button addbutton;

    List<Integer> chairNrList = new ArrayList<>();
    List<String> showList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sale);

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

        showList.add("Show1");
        showList.add("Show2");

        Intent ticketIntent = getIntent();
        String movieTitle = ticketIntent.getStringExtra("movieTitle");
        String moviePoster = ticketIntent.getStringExtra("moviePoster");

        ImageView posterView = findViewById(R.id.image_view_film_ticket_image);
        TextView filmTitle = findViewById(R.id.text_view_film_ticket_title);

        Picasso.get().load(moviePoster).resize(1000,1200).centerInside().into(posterView);
        filmTitle.setText(movieTitle);

        addbutton = findViewById(R.id.ticket_add_button);
        layoutList = findViewById(R.id.ticket_layout);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });



    }

    private void addView() {

        final View ticketView = getLayoutInflater().inflate(R.layout.row_add_ticket, null, false);

        AppCompatSpinner showSpinner = (AppCompatSpinner)ticketView.findViewById(R.id.show_spinner);
        AppCompatSpinner chairSpinner = (AppCompatSpinner)ticketView.findViewById(R.id.chair_spinner);
        ImageView image = (ImageView)ticketView.findViewById(R.id.ticket_remove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, chairNrList);
        chairSpinner.setAdapter(arrayAdapter);

        ArrayAdapter showAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, showList);
        showSpinner.setAdapter(showAdapter);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(ticketView);
            }
        });

        layoutList.addView(ticketView);

    }

    private void removeView(View view) {

        layoutList.removeView(view);

    }
}