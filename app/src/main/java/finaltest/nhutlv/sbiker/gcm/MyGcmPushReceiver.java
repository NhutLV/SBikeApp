package finaltest.nhutlv.sbiker.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import finaltest.nhutlv.sbiker.R;


public class MyGcmPushReceiver extends GcmListenerService {

    //This method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data) {
        //Getting the message from the bundle
        String message = data.getString("message");
        String title = data.getString("title");
        String placeTo = data.getString("place_to");
        String placeFrom = data.getString("place_from");
        String idHistory = data.getString("id_history");

        //Displaying a notiffication with the message
        sendNotification(message,title,placeTo,placeFrom,idHistory);
    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(String message, String title, String placeTo, String placeFrom, String idHistory) {
        Intent intent = new Intent(this, GcmActivity.class);
        intent.putExtra("message",message);
        intent.putExtra("id_history",idHistory);
        intent.putExtra("place_to",placeTo);
        intent.putExtra("place_from",placeFrom);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_lauche)
                .setContentText(message)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }
}