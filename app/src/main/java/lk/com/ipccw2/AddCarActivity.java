package lk.com.ipccw2;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class AddCarActivity extends AppCompatActivity {

    private EditText riderEditText, vehicle_noEditText;
    private Button add;


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
//    private String messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Initialize Firebase Firestore instance
        db = FirebaseFirestore.getInstance();

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();


        riderEditText = findViewById(R.id.rider);
        vehicle_noEditText = findViewById(R.id.vehicle_no);
        add = findViewById(R.id.add);


        // Set on Click Listener on Registration button
        add.setOnClickListener(v -> addNewCar());
    }


    private void addNewCar() {
        String rider, vehicle_no;
        rider = riderEditText.getText().toString();
        vehicle_no = vehicle_noEditText.getText().toString(); // remove any leading/trailing spaces

        // Validations for input name
        if (TextUtils.isEmpty(rider)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter Rider Name!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        // Validations for input name
        if (TextUtils.isEmpty(vehicle_no)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter Vehicle Number!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Create a Map object with the data to be added
        Map<String, Object> car = new HashMap<>();
        car.put("rider", rider);
        car.put("vehicle_no", vehicle_no);

        // Add the car data to the "cars" collection in Firestore database
        db.collection("cars")
                .add(car)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(),
                            "Car added successfully!!",
                            Toast.LENGTH_LONG)
                            .show();
                    riderEditText.setText("");
                    vehicle_noEditText.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(),
                            "Failed to add car!! Please try again later",
                            Toast.LENGTH_LONG)
                            .show();
                    Log.e("AddCarActivity", "Failed to add car", e);
                });
    }




}
