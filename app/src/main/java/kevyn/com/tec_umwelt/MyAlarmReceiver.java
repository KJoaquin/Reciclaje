package kevyn.com.tec_umwelt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    private String alarma,descripcion,titulo,mensaje;


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyTestService.class);
        context.startService(i);
        Calendar calendario = Calendar.getInstance();
        int hora, min,dia,mes,ano;
        String fecha_sistema,hora_sistema;

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        fecha_sistema=mes+"-"+dia+"-"+ano+" ";
        hora_sistema=hora+":"+min;;

        alarma= "Recordatorio";
        titulo= "Tomar Agua";
        descripcion = "Necesitas contaminar menos";
        mensaje = "No olvides tirar la basura en su lugar, separa la basura y tirara en su respectivo recipiente";

        Bitmap icono1 = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);

        Intent notificationIntent = new Intent(context, Tecno.class);
        notificationIntent.putExtra("NOT",2);
        notificationIntent.putExtra("ID",alarma);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TITLE_COLUMN_INDEX);
        long[] pattern = new long[]{2000, 1000, 2000};

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(mensaje);
        bigText.setBigContentTitle(descripcion);
        bigText.setSummaryText(titulo);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context);
        mBuilder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setLargeIcon(icono1)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setSound(defaultSound)
                .setStyle(bigText);

        NotificationManager mNotificationMAnager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationMAnager.notify(0, mBuilder.build());
                
                

    }



}
