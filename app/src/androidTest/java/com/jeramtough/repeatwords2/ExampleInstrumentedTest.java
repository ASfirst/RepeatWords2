package com.jeramtough.repeatwords2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    // Context of the app under test.
    Context appContext;

    @Before
    public void pre() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void useAppContext() {


        assertEquals("com.jeramtough.repeatwords2", appContext.getPackageName());
    }

    @Test
    public void test2() {
    }
}
