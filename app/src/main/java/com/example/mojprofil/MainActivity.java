package com.example.mojprofil;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private Button submitButton;
    private TextView msgField;
    private TextView emailField;
    private TextView passwordField;
    boolean sw = false;

    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = findViewById(R.id.submit);
        msgField = findViewById(R.id.formMessage);
        emailField = findViewById(R.id.emailInput);
        passwordField = findViewById(R.id.passwordInput);

        submitButton.setOnClickListener(v ->
        {
            if(!sw)
            {
                changeMail();
            }
            else
            {
                changePassword();
            }
            sw = false;
        });
    }

    private void changeMail()
    {
        EditText emailInputText = new EditText(this);

        @SuppressLint("ResourceAsColor") AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Zmień email")
                .setMessage("Podaj nowy email")
                .setView(emailInputText)
                .setPositiveButton("Zapisz", (dialog, which) ->
                {
                    if(!emailInputText.getText().toString().contains("@"))
                    {
                        log("Nieprawidłowy format emaila", "red");

                        return;
                    }
                    changePassword();
                    mail = emailInputText.getText().toString();
                    sw = false;
                })
                .setNegativeButton("Anuluj", (dialog, which) ->
                {
                    sw = true;

                    log("Edycja profilu anuluowana", "gray");
                });
        builder.show();
    }

    private void changePassword()
    {
        EditText field1 = new EditText(this);
        field1.setHint("Wpisz haslo");
        EditText field2 = new EditText(this);
        field2.setHint("Powtorz haslo");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(field1);
        layout.addView(field2);

        @SuppressLint("ResourceAsColor") AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Zmień haslo")
                .setMessage("Podaj nowe haslo")
                .setView(layout)
                .setPositiveButton("Zapisz", (dialog, which) ->
                {
                    String password = field1.getText().toString();
                    String conf = field2.getText().toString();

                    if(password.equals(conf))
                    {
                        String psswrd = "";
                        for(int i = 0; i < password.length(); i++)
                        {
                            psswrd += "*";
                        }

                        emailField.setText(mail);
                        passwordField.setText(psswrd);


                        log("Profil zaktualizowany! Nowy email: " + emailField.getText().toString(), "green");
                    }
                })
                .setNegativeButton("Anuluj", (dialog, which) ->
                {
                    sw=false;
                    log("Edycja profilu anuluowana", "gray");
                });
        builder.show();
    }

    private void log(String msg, String col)
    {
        msgField.setText(msg);

        switch(col)
        {
            case "green":
                msgField.setTextColor(Color.parseColor("#4CAF50"));
                break;
            case "gray":
                msgField.setTextColor(Color.parseColor("#00BCD4"));
                break;
            case "red":
                msgField.setTextColor(Color.parseColor("#FF3C3C"));
                break;
        }
    }
}