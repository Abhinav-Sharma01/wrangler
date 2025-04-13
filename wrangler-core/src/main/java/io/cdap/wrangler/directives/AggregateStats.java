package io.cdap.wrangler.directives;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.parser.*;
import java.util.*;

public class AggregateStats implements Directive {
    private String sizeColumn, timeColumn, outSizeCol, outTimeCol;
    private long totalBytes = 0, totalMillis = 0;
    private int count = 0;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder()
            .define("sizeColumn", TokenType.COLUMN_NAME)
            .define("timeColumn", TokenType.COLUMN_NAME)
            .define("outSizeCol", TokenType.COLUMN_NAME)
            .define("outTimeCol", TokenType.COLUMN_NAME)
            .build();
    }

    @Override
    public void initialize(Arguments args) {
        sizeColumn = args.value("sizeColumn");
        timeColumn = args.value("timeColumn");
        outSizeCol = args.value("outSizeCol");
        outTimeCol = args.value("outTimeCol");
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) {
        for (Row row : rows) {
            ByteSize bs = new ByteSize(row.getValue(sizeColumn).toString());
            TimeDuration td = new TimeDuration(row.getValue(timeColumn).toString());
            totalBytes += bs.getBytes();
            totalMillis += td.getMilliseconds();
            count++;
        }

        Row result = new Row();
        result.add(outSizeCol, totalBytes / (1024.0 * 1024)); // MB
        result.add(outTimeCol, totalMillis / 1000.0); // seconds
        return Collections.singletonList(result);
    }
}
