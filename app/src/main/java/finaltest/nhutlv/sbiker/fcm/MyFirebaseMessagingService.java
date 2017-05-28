package finaltest.nhutlv.sbiker.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.activities.ApprovedActivity;
import finaltest.nhutlv.sbiker.gcm.GcmActivity;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 29/05/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG,"OKKKKKKKKKKK");
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String,String> data = remoteMessage.getData();
            String message = data.get("message");
            String placeTo = data.get("place_to");
            String placeFrom = data.get("place_from");
            String idHistory = data.get("id_history");
            String isApproved = data.get("is_approved");
            String title = data.get("title");
            Log.d(TAG, "onMessageReceived: "+message);
            Log.d(TAG, "onMessageReceived: "+placeFrom);
            Log.d(TAG, "onMessageReceived: "+placeTo);
            Log.d(TAG, "onMessageReceived: "+idHistory);
            Log.d(TAG, "onMessageReceived: "+isApproved);
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData());
            if(isApproved!=null){
                Log.d(TAG,"Approved");
                sendNotification(message,title, isApproved);
            }else{
                Log.d(TAG,"GCM");
                sendNotification(message,title,placeTo,placeFrom,idHistory);
            }
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }else{
            Log.d(TAG,"CCCCCCCCCCCCC");
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());
            String title = remoteMessage.getNotification().getTitle();

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        Log.d(TAG,"Job");
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(String message, String title, String placeTo, String placeFrom, String idHistory) {
        Intent intent = new Intent(this, GcmActivity.class);
        intent.putExtra("message",message);
        intent.putExtra("id_history",idHistory);
        intent.putExtra("place_to",placeTo);
        intent.putExtra("place_from",placeFrom);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
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

    private void sendNotification(String message, String title, String isApproved) {
        UserLogin.getUserLogin().setIsApproved(Integer.parseInt(isApproved));
        Intent intent = new Intent(this, ApprovedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 1;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent,PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_lauche)
                .setContentText(message)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, noBuilder.build()); //0 = ID of notification
    }
}
