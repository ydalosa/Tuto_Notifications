package com.samuelvialle.tutonotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_01 extends AppCompatActivity {

    // Les vars globales
    String CHANNEL_ID = "1";
    PendingIntent pendingIntent;
    Bitmap bitmap, bitmapPlage;
    String message;

    // Les widgets
    Button btnSend;
    EditText etMessage;
    TextView tvNotification;

    // Init des widgets
    public void init() {
        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etMessage);
        tvNotification = findViewById(R.id.tvNotification);

        // 4 Ajout d'image pour la personnalisation
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.satgiaire);
        bitmapPlage = BitmapFactory.decodeResource(getResources(), R.drawable.plage);
    }

    // 1 La méthode pour la gestion de la notification sur les API 25 et -
    public void sendNotification() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = etMessage.getText().toString();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Notification_01.this,
                        CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_chat) // On utilise un petit icône (à ajouter depuis new > Vector asset ...
                        .setContentTitle("Ma notification") // Le titre de la notification
                        .setContentText("Le texte de ma prmière notification est " + message) // Le contenu de la notification
                        .setContentIntent(pendingIntent) // la gestion du clic pour afficher la page souhaitée (3)
                        .setAutoCancel(true) // Que l'on clic ou non sur la notification disparîtra toute seule au bout d'un moment (3)
                        .setLargeIcon(bitmap) // On ajoute l'image en bas à droite (4)

                        // 4 Différents exemples de style
                        // a1 Affichage de l'icone en grand
//                        .setStyle(new NotificationCompat.BigPictureStyle()
//                                    .bigPicture(bitmap) // L'image en grand
//                                    .bigLargeIcon(null)) // l'icone en null pour que large icone (3) ne soit afficher que lorsque la notification est collapse
//                                        // Noter que pour réduire la notification il faut cliquer sur la flèche à côté de l'heure
                        // a2 Affichage par exemple d'une photo envoyée avec l'icone de l'expéditeur
//                        .setStyle(new NotificationCompat.BigPictureStyle()
//                                        .bigPicture(bitmapPlage) // L'image envoyé par le destinataire
//                                        .bigLargeIcon(bitmap)) // L'avatar de l'expéditeur
                        // a3 Affiche d'un text en grand
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                          .bigText(getString(R.string.lorem)))
                        // a4 Afficher un certain nombre de ligne
//                        .setStyle(new NotificationCompat.InboxStyle() // Pratique pour envoyer plusieurs informations en même temps par exemple
//                                .addLine("Line 1")
//                                .addLine("Line 2")
//                                .addLine("Line 3")
//                                .addLine("Line 4"))

                        // 5 Ajout d'un bouton dans la notification
                        .addAction(R.mipmap.ic_launcher, "View more !", pendingIntent) // On commence par une image ? puis le texte et enfin l'action à entreprendre
                        .addAction(R.mipmap.ic_launcher, "option 2", pendingIntent) // On peux ajouter un second 'bouton' ici on utilisera les mêmes options que pour le 1er
                        // 6 Ajout de couleur pour le texte de l'action button et pour changer la couleur de notre icone
                        .setColor(getResources().getColor(R.color.teal_200))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); // La priorité de la notification en général DEFAULT

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Notification_01.this);
                notificationManagerCompat.notify(1, builder.build()); // ATTENTION l'id est UNIQUE pour chacune des notifications que vous allez créer
            }
        });
    }

    // 2 Pour afficher les notifications sur les versions supérieures à Android 8 // Api26+
    public void createNotificationChannel() {
        // On créé des Notification channel seulement pour les versions API 26 et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //O = Oreo 8.0
            String name = "My channel name";
            String description = "My channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Enregistrement de la channel avec le service d'android OS
            // Il est à noter qu'aucune modification d'importance ou dans les paramètres de ce channel ne pourra être modifié ensuite
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    // 3 Création d'un clic sur la notification pour ouvrir une activité, par exemple ouvrir l'application avec le message du chat
    // On l'appelera dans la méthode du clic sur le bouton et plus exactement dans le builder de la notification
    public void creaIntent() {
        Intent intent = new Intent(Notification_01.this, Response_01.class);
//        pendingIntent = PendingIntent.getActivity(
//                Notification_01.this, // Le context
//                0, // Le request code
//                intent, // l'intent à lancer
//                0); // Le flag de l'intent

        // Version 2 avec envoi de data et mise à jour du pendingIntent
        intent.putExtra("message", message);

        pendingIntent = PendingIntent.getActivity(
                Notification_01.this, // Le context
                0, // Le request code
                intent, // l'intent à lancer
                PendingIntent.FLAG_UPDATE_CURRENT); // Sans ce flag la valeur transférée ne sera pas mise à jour dans la seconde activité
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification01);

        init();
        tvNotification.setText("Notficication Simple");
        creaIntent(); // Appel de l'intent avant de lancer la notification (3)
        sendNotification();
        createNotificationChannel();

    }
}