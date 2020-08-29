package tech.dbgsoftware.easyrest.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DoubleTypeAdapter extends TypeAdapter<Double> {
    @Override
    public Double read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return 0D;
        }
        String stringValue = reader.nextString();
        try{
            Double value = Double.valueOf(stringValue);
            return value;
        }catch(NumberFormatException e){
            return 0D;
        }
    }
    @Override
    public void write(JsonWriter writer, Double value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
