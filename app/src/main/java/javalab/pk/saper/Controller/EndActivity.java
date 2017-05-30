package javalab.pk.saper.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.MessageFormat;

import javalab.pk.saper.R;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent myintent = getIntent();
        int count = myintent.getIntExtra("timeCount",0);
        int wincond = myintent.getIntExtra("wincond",0);

        TextView time2 = (TextView) findViewById(R.id.time2);
        TextView win = (TextView) findViewById(R.id.win);
        Button start = (Button) findViewById(R.id.replay);
        Button exit = (Button) findViewById(R.id.exit1);
        MediaPlayer winSound = MediaPlayer.create(this, R.raw.tada);
        MediaPlayer sadSound = MediaPlayer.create(this, R.raw.sad1);
        String typ;
        time2.setText(MessageFormat.format("{0} {1}s", getString(R.string.yourTime), String.valueOf(count)));
        if(wincond>=30){
            winSound.start();
            win.setText(R.string.youwin);
            typ = getString(R.string.youwin);
        }
        else
        {
            sadSound.start();
            if(wincond<2){
                win.setText(R.string.easteregg);
            }
            else{
                win.setText(R.string.youlose);
            }
            typ = getString(R.string.youlose);
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setPriority( NotificationCompat.PRIORITY_DEFAULT )
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(typ + " - " + getString(R.string.time) + " " + String.valueOf(count) + "s");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(count, mBuilder.build());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });
    }
}
