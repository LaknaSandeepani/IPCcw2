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







public class RegisterActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView,nameTextView;
    private Button Btn;
    private  TextView loginRedirectText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
//    private String messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Firestore instance
        db = FirebaseFirestore.getInstance();

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();


        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        nameTextView = findViewById(R.id.name);
        Btn = findViewById(R.id.btnregister);
        loginRedirectText = findViewById(R.id.loginRedirectText);


        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegister();
            }

        });
        // Set on Click Listener on Registration button
        Btn.setOnClickListener(v -> registerNewUser());
    }


    private void registerNewUser() {
        String email, password,name;
        name = nameTextView.getText().toString();
        email = emailTextView.getText().toString().trim(); // remove any leading/trailing spaces
        password = passwordTextView.getText().toString();

        // Validations for input name
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter your name!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;}
//        } else if (!isValidEmail(email)) {
//            Toast.makeText(getApplicationContext(),
//                    "Invalid email format!!",
//                    Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Check if password is exactly 6 characters long
        if (password.length() != 6) {
            Toast.makeText(getApplicationContext(),
                    "Password must be exactly 6 characters long!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get the device token for the current user
                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(tokenTask -> {
                                    if (tokenTask.isSuccessful()) {
                                        String token = tokenTask.getResult();
                                        // Create a Map object with the device token for the current user
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("deviceToken", token);
                                        user.put("name", name);

                                        // Write the user data to Firestore database
                                        db.collection("users")
                                                .document(mAuth.getCurrentUser().getUid())
                                                .set(user)
                                                .addOnCompleteListener(deviceTokenTask -> {
                                                    if (deviceTokenTask.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(),
                                                                "Registration successful!",
                                                                Toast.LENGTH_LONG)
                                                                .show();

                                                        // Clear the input fields
                                                        nameTextView.setText("");
                                                        emailTextView.setText("");
                                                        passwordTextView.setText("");

                                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        // Failed to store device token
                                                        Toast.makeText(getApplicationContext(),
                                                                "Failed to store device token!! Please try again later",
                                                                Toast.LENGTH_LONG)
                                                                .show();
                                                        Log.e("RegisterActivity", "Failed to store device token", deviceTokenTask.getException());
                                                    }
                                                });
                                    } else {
                                        // Failed to get device token
                                        Toast.makeText(getApplicationContext(),
                                                "Failed to get device token!! Please try again later",
                                                Toast.LENGTH_LONG)
                                                .show();
                                        Log.e("RegisterActivity", "Failed to get device token", tokenTask.getException());
                                    }
                                });
                    } else {
                        String errorMessage = task.getException().getMessage();
                        if (errorMessage.contains("email address is already in use")) {
                            Toast.makeText(getApplicationContext(),
                                    "This email address is already registered. Please use a different email address.",
                                    Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            // Registration failed
                            Toast.makeText(getApplicationContext(),
                                    "Registration failed!! Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();
                            Log.e("RegisterActivity", "Registration failed: " + errorMessage, task.getException());
                        }
                    }
                });
    }


//    private boolean isValidEmail(CharSequence email) {
//        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }


    private void OpenRegister() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
