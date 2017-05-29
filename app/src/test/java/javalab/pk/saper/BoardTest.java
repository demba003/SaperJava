package javalab.pk.saper;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javalab.pk.saper.Model.Board;

import static org.junit.Assert.*;

/**
 * Created by Kamil on 2017-05-11.
 */

public class BoardTest {
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = Board.get(6, 6, 0);
    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(6, board.getWidth());
    }

    @Test
    public void getHeight() throws Exception {
        assertEquals(6, board.getHeight());
    }

    @Test
    public void getOpened() throws Exception {
        assertEquals(0, board.getOpened());
    }

    @After
    public void tearDown() throws Exception {
        Board.clear();
    }
}