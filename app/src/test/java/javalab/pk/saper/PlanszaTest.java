package javalab.pk.saper;

import android.app.Application;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.ServiceTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import javalab.pk.saper.Model.Plansza;

import static org.junit.Assert.*;

/**
 * Created by Kamil on 2017-05-11.
 */
public class PlanszaTest {
    private Plansza plansza;

    @Before
    public void setUp() throws Exception {
        plansza = Plansza.get(6, 6, 0);
    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(6, plansza.getWidth());
    }

    @Test
    public void getHeight() throws Exception {
        assertEquals(6, plansza.getHeight());
    }

    @Test
    public void getOpened() throws Exception {
        assertEquals(0, plansza.getOpened());
    }
}