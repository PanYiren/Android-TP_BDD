package com.ex.tp_bdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText, editText1, editText2, editText3;
    private Spinner spinner;
    private CheckBox checkBox, checkBox2, checkBox3, checkBox4, checkBox5;
    private RadioGroup radioGroup;
    private RadioButton radioButton, radioButton1, radioButton2;
    private Button button;
    public static Connection conn = null;
    private Modele modele = new Modele();
    private ResultSet rst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pseudo
        editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //password
        editText1 = (EditText) findViewById(R.id.editTextTextPassword);
        //confirm password
        editText2 = (EditText) findViewById(R.id.editTextTextPassword2);
        //textarea
        editText3 = (EditText) findViewById(R.id.editTextTextMultiLine);
        //association
        spinner = (Spinner) findViewById(R.id.spinner);
        //lundi
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        //mardi
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        //mercredi
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        //jeudi
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        //vendredi
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        //radiogroup
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //debutant
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        //intermediaire
        radioButton1 = (RadioButton) findViewById(R.id.radioButton2);
        //haut niveau
        radioButton2 = (RadioButton) findViewById(R.id.radioButton4);
        //ok
        button = (Button) findViewById(R.id.button);

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

        //appel de la connexion
        try {
            conn = modele.MysqlConnexion(this, conn);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        RequeteAssos();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pseudo = editText.getText().toString();
                String mdp = editText1.getText().toString();
                String confirmmdp = editText2.getText().toString();
                String textarea = editText3.getText().toString();
                if (textarea.equals(null)){
                    textarea = "";
                }
                String association = spinner.getSelectedItem().toString();
                String lundi = " ";
                String mardi = " ";
                String mercredi = " ";
                String jeudi = " ";
                String vendredi = " ";
                if (checkBox.isChecked()){
                    lundi = checkBox.getText().toString();
                }
                if (checkBox2.isChecked()){
                    mardi = checkBox2.getText().toString();
                }
                if (checkBox3.isChecked()){
                    mercredi = checkBox3.getText().toString();
                }
                if (checkBox4.isChecked()){
                    jeudi = checkBox4.getText().toString();
                }
                if (checkBox5.isChecked()){
                    vendredi = checkBox5.toString();
                }
                String dispo = lundi + " " + mardi + " " + mercredi + " " + jeudi + " " + vendredi;
                String niveau = "";
                if (radioButton.isChecked()){
                    niveau = "Debutant";
                }
                if (radioButton1.isChecked()){
                    niveau = "Intermediaire";
                }
                if (radioButton2.isChecked()){
                    niveau = "HautNiveau";
                }
                if (!pseudo.equals("") && !mdp.equals("") && !confirmmdp.equals("") && !association.equals("") && !niveau.equals("")) {
                    if (mdp.equals(confirmmdp)) {
                            try {
                                String req = "Select count(*) FROM utilisateur WHERE pseudo='" + pseudo + "'";
                                if (modele.Verif(req, conn) == 1) {
                                    Toast.makeText(MainActivity.this, "Pseudo existe déjà", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(getIntent());
                                } else {
                                    String sqlAssos = "SELECT id FROM association WHERE association = '" + association + "'";
                                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    ResultSet resultSet = statement.executeQuery(sqlAssos);
                                    String idAssos = "";
                                    while (resultSet.next()) {
                                        idAssos = resultSet.getString(1);
                                    }
                                    String sqlUtili = "INSERT INTO utilisateur VALUES (NULL, ?, ?)";
                                    PreparedStatement preparedStatement = conn.prepareStatement(sqlUtili);
                                    preparedStatement.setString(1, pseudo);
                                    preparedStatement.setString(2, mdp);
                                    preparedStatement.executeUpdate();
                                    String sqlsport = "SELECT id FROM utilisateur WHERE pseudo = '" + pseudo + "' AND mdp = '" + mdp + "'";
                                    Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    ResultSet resultSet1 = statement1.executeQuery(sqlsport);
                                    String idUtili = "";
                                    while (resultSet1.next()) {
                                        idUtili = resultSet1.getString(1);
                                    }
                                    String sqltout = "INSERT INTO sport VALUES (NULL, ?, ?, ?, ?, ?)";
                                    PreparedStatement pstmins = conn.prepareStatement(sqltout);
                                    pstmins.setString(1, dispo);
                                    pstmins.setString(2, niveau);
                                    pstmins.setString(3, textarea);
                                    pstmins.setString(4, idAssos);
                                    pstmins.setString(5, idUtili);
                                    pstmins.executeUpdate();
                                    Intent intent = new Intent(MainActivity.this, connexion.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            catch(SQLException se){
                                    se.printStackTrace();
                                }
                        }
                    else{
                            Toast.makeText(MainActivity.this, "Veillez entrer les mêmes mot de passe", Toast.LENGTH_LONG).show();
                        }
                    }
                else{
                        Toast.makeText(MainActivity.this, "Veillez remplir tous les champs", Toast.LENGTH_LONG).show();
                    }
            }
        });

    }


    public void RequeteAssos() {
        try {
            String req = "SELECT association FROM association";
            Statement pstm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rst1 = pstm.executeQuery(req);
            List<String> list = new ArrayList<>();;
            while (rst1.next()) {
                list.add(rst1.getString(1));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    }
