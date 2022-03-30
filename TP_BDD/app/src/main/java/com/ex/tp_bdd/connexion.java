package com.ex.tp_bdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connexion extends AppCompatActivity {

    private TextView textView;
    private EditText editText, editText1;
    private Button button, button1;
    private Modele modele = new Modele();
    private Connection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //infos
        textView = (TextView) findViewById(R.id.textView12);
        //pseudo
        editText = (EditText) findViewById(R.id.editTextTextPersonName2);
        //password
        editText1 = (EditText) findViewById(R.id.editTextTextPassword3);
        //confirmer
        button = (Button) findViewById(R.id.button2);
        //quitter
        button1 = (Button) findViewById(R.id.button3);


        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog() // Enregistre un message à logcat
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath() //l'application se bloque, fonctionne à //la fin de toutes les sanctions permises
                .build());
        try {
            conn = modele.MysqlConnexion(this, conn);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String log = editText.getText().toString();
                String pass = editText1.getText().toString();
                String req = "Select count(*) FROM utilisateur WHERE pseudo='" + log + "' AND mdp ='" + pass + "'";
                try{
                    if(modele.Verif(req, conn) == 1){
                        textView.setText("Vous êtes bien inscris");
                    }
                    else{
                        textView.setText("Vous n'êtes pas inscris, cliquez ici pour inscrire");
                        textView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                Intent intent = new Intent(connexion.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                return false;
                            }
                        });
                        editText1.setText("");
                        editText.setText("");
                    }
                    }
                catch (Exception se){
                    se.printStackTrace();
                }

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }


}
