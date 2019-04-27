package au.edu.utas.auvde.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import static au.edu.utas.auvde.assignment2.R.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Button locationPageButton = (Button) findViewById(id.btnLogtimeline);
        locationPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TimeLine.class);
                startActivity(i);
            }


        });

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
      ///  String time = "Current Time;" + format.format(calendar.getTime());



        TextView textView = (TextView) findViewById(id.txtdate);
      //  TextView textshow = (TextView) findViewById(id.time);

        TextView textViewDate = findViewById(R.id.txtdate);
        textViewDate.setText(currentDate);


    }
}