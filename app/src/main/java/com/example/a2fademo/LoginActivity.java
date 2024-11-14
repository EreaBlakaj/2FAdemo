package com.example.a2fademo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, otpInput;
    private Button submitButton;
    private TextView signupPrompt;

    private boolean isSigningUp = false;  // Track whether the user is signing up or logging in
    private FirebaseAuth mAuth;  // Firebase authentication instance
    private GoogleAuthenticator gAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        otpInput = findViewById(R.id.otp_input);
        submitButton = findViewById(R.id.submit_button);
        signupPrompt = findViewById(R.id.signup_prompt);

        // Handle the click event for the Submit Button
        submitButton.setOnClickListener(v -> handleSubmit());

        // Toggle between Sign-Up and Login
        signupPrompt.setOnClickListener(v -> toggleSignUp());
    }

    private void handleSubmit() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (isSigningUp) {
            // Handle sign-up process
            signUpUser(email, password);
        } else {
            // Handle login process
            loginUser(email, password);
        }
    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // If sign-up is successful, proceed to OTP screen
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
                        otpInput.setVisibility(View.VISIBLE);
                        submitButton.setText("Verify OTP");
                    } else {
                        // If sign-up fails, show an error message
                        Toast.makeText(LoginActivity.this, "Sign-up failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // If login is successful, proceed to OTP screen
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        otpInput.setVisibility(View.VISIBLE);
                        submitButton.setText("Verify OTP");
                    } else {
                        // If login fails (wrong email/password), show error message
                        Toast.makeText(LoginActivity.this, "Invalid email or password. Please sign up.", Toast.LENGTH_SHORT).show();
                        otpInput.setVisibility(View.GONE);  // Hide OTP field if login fails
                    }
                });
    }

    private void toggleSignUp() {
        // Toggle between login and signup state
        isSigningUp = !isSigningUp;
        if (isSigningUp) {
            signupPrompt.setText("Already have an account? Log in");
            submitButton.setText("Sign Up");
        } else {
            signupPrompt.setText("Don't have an account? Sign up");
            submitButton.setText("Log In");
        }
    }

    private boolean validateOtp(String otp) {
        // Implement OTP validation logic here (e.g., using a TOTP library)
        return true;  // Placeholder for actual validation logic
    }

    // Check if user is already signed in when the activity starts
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, navigate to the main activity or dashboard
            // Redirect to the home screen or the appropriate activity
        }
    }
}
