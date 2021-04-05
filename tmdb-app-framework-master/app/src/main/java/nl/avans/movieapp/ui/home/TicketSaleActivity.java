package nl.avans.movieapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.avans.movieapp.R;

public class TicketSaleActivity extends AppCompatActivity {

    LinearLayout layoutList;
    Button addbutton;

    List<String> ticketList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sale);

        ticketList.add("Ticket1");
        ticketList.add("Ticket2");
        ticketList.add("Ticket3");
        ticketList.add("Ticket4");

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

        EditText editText = (EditText)ticketView.findViewById(R.id.ticket_edit_text);
        AppCompatSpinner spinner = (AppCompatSpinner)ticketView.findViewById(R.id.ticket_spinner);
        ImageView image = (ImageView)ticketView.findViewById(R.id.ticket_remove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ticketList);
        spinner.setAdapter(arrayAdapter);

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