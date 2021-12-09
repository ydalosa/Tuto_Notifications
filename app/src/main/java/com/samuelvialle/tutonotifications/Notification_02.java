package com.samuelvialle.tutonotifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

// Il est possible d'utiliser un custom layout pour le design de la notification
// cependant il y a beaucoup de contraintes pour commencer la hauteur : en collapse = 64dp max
// ouverte on peut aller jusqu'à 256dp. De plus il faut vérifier que la notification s'affiche bien sur
// tous les différents types de terminaux ou d'orientation cependant pour l'exemple voici comment faire.
public class Notification_02 extends AppCompatActivity {
    // 1 Commencer par ajouter 2 layout avec obligatoirement des linearLayout car nous allons utiliser une RemoteViews
    //  - un pour la vue collapse
    //  - un pour la vue expend

    // Les vars globales
    String CHANNEL_ID = "1";
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
    }

    public void sendNotification() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = etMessage.getText().toString();

                // Ajout de la vue custom avec RemoteViews
                RemoteViews collapseLayout = new RemoteViews(getPackageName(), R.layout.notification_collapse);
                RemoteViews expendLayout = new RemoteViews(getPackageName(), R.layout.notification_expend);
                expendLayout.setImageViewResource(R.id.ivAvatarExpendNotification, R.drawable.satgiaire); // Ajout de l'image
                expendLayout.setTextViewText(R.id.tvExpendNotification, "Du texte ajouter par la programmation ;) avec le message => "
                            + message);


                NotificationCompat.Builder builder = new NotificationCompat.Builder(Notification_02.this,
                        CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_chat)
                        .setCustomContentView(collapseLayout)
                        .setCustomBigContentView(expendLayout)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle()) // Ajoute les infos types icone // nom de l'app // heure de l'envoi
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); // La priorité de la notification en général DEFAULT

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Notification_02.this);
                notificationManagerCompat.notify(2, builder.build()); // ATTENTION l'id est UNIQUE pour chacune des notifications que vous allez créer
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification01); // Pour plus de dsimplicité on utilise le layout de la première activité

        init();
        tvNotification.setText("Notficication Custom");

        sendNotification();
    }
}