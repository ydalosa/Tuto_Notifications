package com.samuelvialle.tutonotifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    // Définition d'un objet btnClic pour la gestion globales de tous les boutons de l'activité
    private Button btnClic;
    private Class activiteDestination;

    // Méthode de gestion sur tout les boutons de l'activité à ajouter à l'attribut onClick dans le xml pour chaque bouton, sans oublié le tag
    public void clicButton(View view) throws ClassNotFoundException {
        btnClic = (Button) view;

        // Utilisation du tag pour d'une part récupérer le nom de l'activité courante, et avoir la classe
        // de destination en vue de créer un intent pour afficher l'activité correspondante au bouton cliqué

        // Récupération du tag, ce tag est IDENTIQUE au nom de la classe de destination lors du clic et sera utilisé dans l'intent
        String tagBouton = btnClic.getTag().toString();

        // Pour transformer un String en class il faut le nom complet du package donc
        String nomClasseDestination = "com.samuelvialle.tutonotifications." + tagBouton; // Le nom de votre package global
        // Transformation du string nomClasseDestination en classe pour l'injecter dans l'intent
        activiteDestination = Class.forName(nomClasseDestination);

        // Création de l'intent activiteDepart -> activiteDestination
        startActivity(new Intent(MainActivity.this, activiteDestination));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}