package javalab.pk.saper.Model;


import javalab.pk.saper.R;

/**
 * Created by khaeemm on 27.04.2017.
 */

public class PoleNumer extends Pole {
    private int number;

    public PoleNumer(int nr) {
        number = nr;
    }

    @Override
    public int action() {

        int value = 0;

        switch (number) {
            case 1:
                value = R.mipmap.number_1;
                break;
            case 2:
                value = R.mipmap.number_2;
                break;
            case 3:
                value = R.mipmap.number_3;
                break;
            case 4:
                value = R.mipmap.number_4;
                break;
            case 5:
                value = R.mipmap.number_5;
                break;
            case 6:
                value = R.mipmap.number_6;
                break;
            case 7:
                value = R.mipmap.number_7;
                break;
            case 8:
                value = R.mipmap.number_8;
                break;
        }

        return value;
    }
}



