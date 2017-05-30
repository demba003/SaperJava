package javalab.pk.saper.Model;

import java.util.Random;
import java.util.Vector;

import javalab.pk.saper.View.BoardView;

public class Board {
    private static Board instance = null;

    public static Board get(int w, int h, int maxBombs) {
        if (instance == null) instance = new Board(w, h, maxBombs);
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    private Vector<Vector<Field>> board;

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    private BoardView boardView;
    private int wincond = 0;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field getField(int x, int y) {
        return board.get(x).get(y);
    }

    public int getOpened() {
        return wincond;
    }

    public void bombSetOpened(){
        if (wincond >= 30) wincond = 29;
    }

    public void open(int x, int y){
        getField(x,y).isOpened = true;
        wincond++;
        boardView.open(x, y);
    }

    private Board(int w, int h, int maxBombs) {
        width = w;
        height = h;
        board = new Vector<>();
        Random rnd = new Random();

        for (int i = 0; i < height; i++){
            board.add(new Vector<Field>());
            for (int j = 0; j < width; j++) {
                board.get(i).add(new BlankField());
            }
        }

        for (int i = 0; i < maxBombs; i++) {
            int x = rnd.nextInt(height);
            int y = rnd.nextInt(width);
            if(board.get(x).get(y) instanceof Bomb)  {
                i--;
            }
            board.get(x).set(y, (new Bomb()));
        }

        int bombCount;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                bombCount = 0;
                if(board.get(i).get(j).getClass() != Bomb.class) {
                    for (int ii=-1; ii<2; ii++) {
                        for (int jj=-1; jj<2; jj++) {
                            if (!(ii==jj && ii==0)) {
                                try {
                                    if (board.get(i + ii).get(j + jj) instanceof Bomb) bombCount++;
                                } catch (Exception e){}
                            }
                        }
                    }
                    if (bombCount > 0) board.get(i).set(j, (new FieldNumber(bombCount)));
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
            if(p.x > 0 && p.x < 6 && p.y > 0 && p.y < 6 && board.get(p.x).get(p.y) instanceof BlankField && !board.get(x).get(y).isOpened)
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
        if (x >= 0 && x < height && y >= 0 && y < width && !getField(x,y).isOpened) {
            if (getField(x,y) instanceof BlankField) {
                boardView.open(x,y);
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
            if (getField(x,y) instanceof FieldNumber){
                boardView.open(x,y);
                getField(x,y).isOpened = true;
                wincond++;
            }
        }
    }

}
