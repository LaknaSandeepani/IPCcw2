package lk.com.ipccw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button map, add, btn_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        map = (Button) findViewById(R.id.btn_map);
        add = (Button)findViewById(R.id.btn_addcar);
        btn_history = (Button) findViewById(R.id.btn_history);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carintent = new Intent();
                carintent.setClass(getApplicationContext(),AddCarActivity.class);
                startActivity(carintent);
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carintent = new Intent();
                carintent.setClass(getApplicationContext(),SensorActivity.class);
                startActivity(carintent);
            }
        });
    }
}