package javalab.pk.saper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javalab.pk.saper.Model.Pole;
import javalab.pk.saper.Model.PoleNumer;

import static org.junit.Assert.*;

/**
 * Created by remul on 18.05.2017.
 */
public class PoleNumerTest {
    private PoleNumer PoleNumer;


    @Test
    public void action7() throws Exception {
        PoleNumer = new PoleNumer(7);
        assertEquals(R.mipmap.number_7, PoleNumer.action());
    }
    @Test
    public void action13() throws Exception {
        PoleNumer = new PoleNumer(13);
        assertEquals(0, PoleNumer.action());
    }
    @Test
    public void actionminus() throws Exception {
        PoleNumer = new PoleNumer(-27);
        assertEquals(0, PoleNumer.action());
    }
}