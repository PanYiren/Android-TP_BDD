package com.ex.tp_bdd;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Modele {
    public Connection MysqlConnexion(Context context, Connection conn) {
        String jdbcURL = "jdbc:mysql://192.168.1.13:3306/android";
        String user = "username";
        String passwd = "password";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, user, passwd);

        } catch (ClassNotFoundException e) {
            Toast.makeText(context, "Driver manquant." + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        } catch (java.sql.SQLException ex) {
            Toast.makeText(context, "Connexion au serveur impossible." + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.d("error", "SQLException: " + ex.getMessage());
            Log.d("error", "SQLState: " + ex.getSQLState());
            Log.d("error", "VendorError: " + ex.getErrorCode());
        }
        Toast.makeText(context, "Connexion réussi", Toast.LENGTH_LONG).show();
        return conn;
    }

    public int Verif(String req, Connection conn) {
        int nombre = 0;

        try{
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet result = stmt.executeQuery(req);
            while(result.next()){
                nombre = result.getInt(1);
            }
        }
        catch (SQLException e){
            System.out.println("Connexion erreur");
        }
        return nombre;
    }
}
