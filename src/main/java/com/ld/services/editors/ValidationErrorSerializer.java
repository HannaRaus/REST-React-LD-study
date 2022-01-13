package com.ld.services.editors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ld.validation.ValidationError;

import java.io.IOException;

public class ValidationErrorSerializer extends StdSerializer<ValidationError> {

    public ValidationErrorSerializer() {
        super(ValidationError.class);
    }

    public ValidationErrorSerializer(Class<ValidationError> clazz) {
        super(clazz);
    }

    public void serialize(
            ValidationError error, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeString(error.name());
        generator.writeFieldName("message");
        generator.writeString(error.getMessage());
        generator.writeEndObject();
    }
}
