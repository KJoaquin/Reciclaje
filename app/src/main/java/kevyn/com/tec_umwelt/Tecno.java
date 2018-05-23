package kevyn.com.tec_umwelt;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Tecno extends AppCompatActivity {

    private RelativeLayout ducha;
    private RelativeLayout consejos;
    private RelativeLayout recordatorio;
    private String alarma,descripcion,titulo,mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecno);

        ducha = (RelativeLayout) findViewById(R.id.lucha);
        consejos = (RelativeLayout) findViewById(R.id.consejos);
        recordatorio = (RelativeLayout) findViewById(R.id.recordatorios);

        ducha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDucha();
            }
        });
        consejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abirConsejos();
            }
        });
        recordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abirRecordatorio();
            }
        });
        //notificacion();
        servicio();
    }

    private void abirRecordatorio() {
        Intent intent = new Intent(Tecno.this, Recordatorio.class);
        startActivity(intent);
        finish();
    }

    private void abirConsejos() {
        Intent intent = new Intent(Tecno.this, Ambiente.class);
        startActivity(intent);
        finish();
    }

    private void abrirDucha() {
        Intent intent = new Intent(Tecno.this, Ducha.class);
        startActivity(intent);
        finish();

    }

    private void notificacion() {

        alarma= "Recordatorio";
        titulo= "Tomar Agua";
        descripcion = "Necesitas contaminar menos";
        mensaje = "No olvides tirar la basura en su lugar, separa la basura y tirara en su respectivo recipiente";

        Bitmap icono1 = BitmapFactory.decodeResource(getApplication().getResources(),R.mipmap.ic_launcher);

        Intent notificationIntent = new Intent(getApplicationContext(), Tecno.class);
        notificationIntent.putExtra("NOT",2);
        notificationIntent.putExtra("ID",alarma);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        long[] pattern = new long[]{2000, 1000, 2000};

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(mensaje);
        bigText.setBigContentTitle(descripcion);
        bigText.setSummaryText(titulo);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setLargeIcon(icono1)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setSound(defaultSound)
                .setStyle(bigText);

        NotificationManager mNotificationMAnager = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationMAnager.notify(0, mBuilder.build());


    }


    public void servicio() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        int intervalMillis = 60000 * 90;
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,firstMillis , intervalMillis, pIntent);
    }
}