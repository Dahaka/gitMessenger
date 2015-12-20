package dahakashow.com.messangerapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Dahakashow on 12/13/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    Communicator1 comm ;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        Log.v("onMessageRecieved","onMessageRecieved");

        String message = data.getString("message");
        String username = data.getString("username");
        Log.v("From ONusername=", data.getString("username"));

        String fullMessage= username+":"+message;

        //comm.addToListview("ADDED ???");

        if(!MainActivity.username123.equals(username))
        sendNotification(message);

        Communicator comm1 = null;
        publishResults();
        sendMessage(message,username);

        insertToDb(fullMessage);









    }

    private void insertToDb(String message) {
        SqlDB sql = new SqlDB(this);
        sql.insert(message);

    }

    private void sendMessage(String message,String username) {
        Intent intent = new Intent("my-event");
        // add data
        intent.putExtra("username", username);
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void publishResults() {

    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_play_light)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    public  void setComm(Communicator1 comm){
        this.comm = comm;

    }

    public interface Communicator1 {

        public void addToListview(String message);
    }

}
