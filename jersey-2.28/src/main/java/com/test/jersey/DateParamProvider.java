package com.test.jersey;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Provider
public class DateParamProvider implements ParamConverterProvider {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        public SimpleDateFormat initialValue() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            dateFormat.setLenient(false);
            return dateFormat;
        }
    };

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(Date.class)) {
            return (ParamConverter<T>) new DateConverter();
        } else {
            return null;
        }
    }

    private static class DateConverter implements ParamConverter<Date> {
        @Override
        public Date fromString(String s) throws IllegalArgumentException {
            try {
                return DATE_FORMAT.get().parse(s);
            } catch (ParseException e) {
                throw new BadRequestException(s + " is not a valid date format");
            }
        }

        @Override
        public String toString(Date date) throws IllegalArgumentException {
            return DATE_FORMAT.get().format(date);
        }
    }
}