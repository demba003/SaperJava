package javalab.pk.saper.Model;

import android.content.Context;

/**
 * Created by khaeemm on 27.04.2017.
 */

public class PoleNumer extends Pole {
    private int number;
    private Context context;

    public PoleNumer(Context ctx, int nr) {
        context = ctx;
        number = nr;
    }

    @Override
    public int action() {
        return context.getResources().getIdentifier("number_" + number, "mipmap", context.getPackageName());
    }
}



