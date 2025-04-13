package io.cdap.wrangler;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {
    @Test
    public void testParsing() {
        TimeDuration t1 = new TimeDuration("500ms");
        Assert.assertEquals(500, t1.getMilliseconds());

        TimeDuration t2 = new TimeDuration("2s");
        Assert.assertEquals(2000, t2.getMilliseconds());

        TimeDuration t3 = new TimeDuration("1min");
        Assert.assertEquals(60000, t3.getMilliseconds());

        TimeDuration t4 = new TimeDuration("1000"); // assume milliseconds
        Assert.assertEquals(1000, t4.getMilliseconds());
    }
}

