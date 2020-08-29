package tech.dbgsoftware.easyrest.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IntegerTypeAdapter extends TypeAdapter<Integer> {
    @Override
    public Integer read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return 0;
        }
        String stringValue = reader.nextString();
        try{
            Integer value = Integer.valueOf(stringValue);
            return value;
        }catch(NumberFormatException e){
            return 0;
        }
    }
    @Override
    public void write(JsonWriter writer, Integer value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
