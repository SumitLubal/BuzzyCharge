package dharmasoftware.com.buzzycharge;

/**
 * Created by sam on 11/05/16.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;
import android.os.IBinder;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class BatteryService extends Service {
    private static final int NOTIFICATION_ID = 1;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        runAsForeground();
    }
    private void runAsForeground(){
        Intent notificationIntent = new Intent(this, BatteryService.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0,
                notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notification=new NotificationCompat.Builder(this)
                .setContentText(getString(R.string.app_name))
                .setContentIntent(pendingIntent).build();

        startForeground(NOTIFICATION_ID, notification);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        HeartBeat heartBeat = new HeartBeat(this);
        heartBeat.execute("Nothing");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
    class HeartBeat extends AsyncTask<String,Integer,String>{
        private Context mContext;
        public HeartBeat (Context context){
            mContext = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            int i=0;
            while(true){
                try {
                    Thread.sleep(1000);
                    //Toast.makeText(mContext,"This is ping after : "+i+" sec",Toast.LENGTH_SHORT).show();
                    Log.d("BatteryService","This is ping after : "+i+" sec");
                    i++;
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    return "Problem in interruption";
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}