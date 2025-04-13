package io.cdap.wrangler;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.directives.AggregateStats;
import io.cdap.wrangler.executor.ExecutorContext;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.parser.Text;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {
    @Test
    public void testAggregateStats() throws Exception {
        Row row1 = new Row("data_transfer_size", "2MB").add("response_time", "1s");
        Row row2 = new Row("data_transfer_size", "1MB").add("response_time", "500ms");

        List<Row> inputRows = Arrays.asList(row1, row2);

        AggregateStats directive = new AggregateStats();
        Arguments args = Arguments.builder()
            .add("sizeColumn", new Text("data_transfer_size"))
            .add("timeColumn", new Text("response_time"))
            .add("outSizeCol", new Text("total_size_mb"))
            .add("outTimeCol", new Text("total_time_sec"))
            .build();

        directive.initialize(args);
        List<Row> result = directive.execute(inputRows, new ExecutorContext());

        Assert.assertEquals(1, result.size());
        Row output = result.get(0);

        double expectedSizeMB = 3.0; // 2MB + 1MB
        double expectedTimeSec = 1.5; // 1s + 0.5s

        Assert.assertEquals(expectedSizeMB, (Double) output.getValue("total_size_mb"), 0.001);
        Assert.assertEquals(expectedTimeSec, (Double) output.getValue("total_time_sec"), 0.001);
    }
}
