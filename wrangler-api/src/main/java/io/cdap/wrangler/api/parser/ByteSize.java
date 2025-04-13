package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ByteSize implements Token {
    private final String rawValue;
    private final long bytes;

    public ByteSize(String value) {
        this.rawValue = value;
        this.bytes = parse(value);
    }

    private long parse(String value) {
        String v = value.trim().toLowerCase();
        if (v.endsWith("kb")) return Long.parseLong(v.replace("kb", "")) * 1024;
        if (v.endsWith("mb")) return Long.parseLong(v.replace("mb", "")) * 1024 * 1024;
        if (v.endsWith("gb")) return Long.parseLong(v.replace("gb", "")) * 1024L * 1024L * 1024L;
        return Long.parseLong(v);
    }

    public long getBytes() {
        return bytes;
    }

    @Override
    public Object value() {
        return bytes;
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(rawValue);
    }
}
