package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class TimeDuration implements Token {
    private final String rawValue;
    private final long milliseconds;

    public TimeDuration(String value) {
        this.rawValue = value;
        this.milliseconds = parse(value);
    }

    private long parse(String value) {
        String v = value.trim().toLowerCase();
        if (v.endsWith("ms")) return Long.parseLong(v.replace("ms", ""));
        if (v.endsWith("s")) return Long.parseLong(v.replace("s", "")) * 1000;
        if (v.endsWith("min")) return Long.parseLong(v.replace("min", "")) * 60 * 1000;
        return Long.parseLong(v);
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    @Override
    public Object value() {
        return milliseconds;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(rawValue);
    }
}
