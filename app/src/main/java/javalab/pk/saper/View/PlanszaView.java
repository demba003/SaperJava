package javalab.pk.saper.View;

import android.app.Activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.Vector;

import javalab.pk.saper.Model.Plansza;
import javalab.pk.saper.R;


public class PlanszaView {
    private Vector<Vector<ImageButton>> buttons;
    private Plansza plansza;

    public PlanszaView(Activity activity, Plansza pl, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        plansza = pl;
        buttons = new Vector<>();

        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.game_layout);

        for (int i = 0; i < plansza.getHeight(); i++) {
            LinearLayout row = new LinearLayout(activity);
            buttons.add(new Vector<ImageButton>());
            for (int j = 0; j < plansza.getWidth(); j++) {
                buttons.get(i).add(new ImageButton(activity));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.weight = 1;

                buttons.get(i).get(j).setLayoutParams(p);
                buttons.get(i).get(j).setBackground(null);
                buttons.get(i).get(j).setImageResource(R.mipmap.squareblank);

                buttons.get(i).get(j).setId(10*i + j);
                buttons.get(i).get(j).setOnClickListener(onClickListener);
                buttons.get(i).get(j).setOnLongClickListener(onLongClickListener);
                row.addView(buttons.get(i).get(j));
            }
            layout.addView(row);
        }
    }

    public void open(int x, int y) {
        buttons.get(x).get(y).setImageResource(plansza.getField(x,y).action());
    }

    public void markAsBomb(int x, int y) {
        buttons.get(x).get(y).setImageResource(R.mipmap.flag);
    }
}
