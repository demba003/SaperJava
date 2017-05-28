package javalab.pk.saper;

import org.junit.Test;

import javalab.pk.saper.Model.FieldNumber;

import static org.junit.Assert.*;

/**
 * Created by remul on 18.05.2017.
 */
public class FieldNumberTest {
    private FieldNumber PoleNumer;


    @Test
    public void action7() throws Exception {
        PoleNumer = new FieldNumber(7);
        assertEquals(R.mipmap.number_7, PoleNumer.action());
    }
    @Test
    public void action13() throws Exception {
        PoleNumer = new FieldNumber(13);
        assertEquals(0, PoleNumer.action());
    }
    @Test
    public void actionminus() throws Exception {
        PoleNumer = new FieldNumber(-27);
        assertEquals(0, PoleNumer.action());
    }
}