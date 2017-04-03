package javalab.pk.saper;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.Vector;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    Vector<ImageButton> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buttons = new Vector<>();

        LinearLayout layout = (LinearLayout) findViewById(R.id.game_layout);

        for (int i = 0; i < 6; i++) {
            LinearLayout row = new LinearLayout(this);

            for (int j = 0; j <6; j++) {
                buttons.add(new ImageButton(this));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.weight = 1;

                buttons.get(j + 6*i).setLayoutParams(p);
                buttons.get(j + 6*i).setImageResource(R.mipmap.square);
                buttons.get(j + 6*i).setId(j + 6*i);
                buttons.get(j + 6*i).setOnClickListener(this);
                row.addView(buttons.get(j + 6*i));
            }

            layout.addView(row);
        }
    }

    @Override
    public void onClick(View view) {
        buttons.get(view.getId()).setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
