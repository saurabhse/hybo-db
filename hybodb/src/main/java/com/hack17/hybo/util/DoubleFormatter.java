package com.hack17.hybo.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DoubleFormatter extends JsonSerializer<Double>{
	@Override
	public void serialize(Double arg0, JsonGenerator arg1,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		String pattern = "###,###.#####";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String value = decimalFormat.format(arg0);
		arg1.writeString(value);
	}
}
