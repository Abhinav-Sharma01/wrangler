package io.cdap.wrangler;

import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.Assert;
import org.junit.Test;

public class ByteSizeTest {
    @Test
    public void testParsing() {
        ByteSize size1 = new ByteSize("2MB");
        Assert.assertEquals(2 * 1024 * 1024, size1.getBytes());

        ByteSize size2 = new ByteSize("10kb");
        Assert.assertEquals(10 * 1024, size2.getBytes());

        ByteSize size3 = new ByteSize("1GB");
        Assert.assertEquals(1024L * 1024 * 1024, size3.getBytes());

        ByteSize size4 = new ByteSize("512"); // bytes
        Assert.assertEquals(512, size4.getBytes());
    }
}