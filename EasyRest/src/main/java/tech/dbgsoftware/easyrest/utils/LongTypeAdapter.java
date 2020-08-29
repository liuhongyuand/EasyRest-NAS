package tech.dbgsoftware.easyrest.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class LongTypeAdapter extends TypeAdapter<Long> {
    @Override
    public Long read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return 0L;
        }
        String stringValue = reader.nextString();
        try{
            Long value = Long.valueOf(stringValue);
            return value;
        }catch(NumberFormatException e){
            return 0L;
        }
    }
    @Override
    public void write(JsonWriter writer, Long value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
