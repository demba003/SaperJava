package javalab.pk.saper.Model;

import android.content.Context;
import android.widget.ImageButton;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class Plansza {
    //private static Plansza instance = ;
    /*public static Plansza getInstance() {
        return instance;
    }*/

    private Context context;
    private Vector<Vector<Pole>> board;
    Vector<Vector<ImageButton>> buttons;
    private int wincond = 0;

    public Iterator getIterator() {
        return board.iterator();
    }

    public Pole getField(int x, int y) {
        return board.get(x).get(y);
    }

    public int getOpened() {
        return wincond;
    }

    public void open(int x, int y){
        getField(x,y).isOpened = true;
        wincond++;
        buttons.get(x).get(y).setImageResource(getField(x,y).action());
    }

    public Plansza(Context ctx, Vector<Vector<ImageButton>> btns) {
        context = ctx;
        board = new Vector<>();
        buttons = btns;
        Random rnd = new Random();

        for (int i=0; i<6; i++){
            board.add(new Vector<Pole>());
            for (int j=0; j<6; j++) {
                board.get(i).add(new PustePole());
            }
        }

        for (int i=0; i<6; i++) {
            int x = rnd.nextInt(6);
            int y = rnd.nextInt(6);
            if(board.get(x).get(y) instanceof Bomba)  {
                i--;
            }
            board.get(x).set(y, (new Bomba()));
        }

        int bombCount = 0;
        for(int i=0; i<6; i++) {
            for(int j=0; j<6; j++) {
                bombCount = 0;
                if(board.get(i).get(j).getClass() != Bomba.class) {
                    for (int ii=-1; ii<2; ii++) {
                        for (int jj=-1; jj<2; jj++) {
                            if (ii==jj && ii==0){}
                            else {
                                try {
                                    if (board.get(i + ii).get(j + jj) instanceof Bomba) bombCount++;
                                } catch (Exception e){}
                            }
                        }
                    }
                    if (bombCount > 0) board.get(i).set(j, (new PoleNumer(context, bombCount)));
                }
            }
        }
    }

    public void floodFill(int x, int y) {
        /* ZA POMOCA KOLEJKI, NA RAZIE NIE DZIALA :(
        Queue<Point> floodQ = new LinkedList<Point>();
        floodQ.add(new Point(x, y));
        while(!floodQ.isEmpty()){
            Point p = floodQ.poll();
            if(p.x > 0 && p.x < 6 && p.y > 0 && p.y < 6 && board.get(p.x).get(p.y) instanceof PustePole && !board.get(x).get(y).isOpened)
            {
                buttons.get(p.x).get(p.y).setImageResource(board.get(p.x).get(p.y).action());
                board.get(x).get(y).isOpened = true;
                floodQ.add(new Point(p.x - 1, p.y));
                floodQ.add(new Point(p.x + 1, p.y));
                floodQ.add(new Point(p.x, p.y - 1));
                floodQ.add(new Point(p.x, p.y + 1));
            }
        }
        */
        if (x >= 0 && x <6 && y >= 0 && y < 6 && !getField(x,y).isOpened) {
            if (getField(x,y) instanceof PustePole) {
                buttons.get(x).get(y).setImageResource(getField(x,y).action());
                getField(x,y).isOpened = true;
                wincond++;
                floodFill(x - 1, y);
                floodFill(x + 1, y);
                floodFill(x, y - 1);
                floodFill(x, y + 1);
                floodFill(x + 1, y + 1);
                floodFill(x - 1, y + 1);
                floodFill(x - 1, y - 1);
                floodFill(x + 1, y - 1);
            }
            if (getField(x,y) instanceof PoleNumer){
                buttons.get(x).get(y).setImageResource(getField(x,y).action());
                getField(x,y).isOpened = true;
                wincond++;
            }
        }
        else{
            return;
        }
    }

}
