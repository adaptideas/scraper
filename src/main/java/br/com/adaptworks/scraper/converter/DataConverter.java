package br.com.adaptworks.scraper.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jonasabreu
 * 
 */
@SuppressWarnings("unchecked")
final public class DataConverter {

    private final List<Converter> converters;

    public DataConverter() {
        converters = new ArrayList<Converter>();
    }

    public DataConverter(final List<Converter> converters) {
        this.converters = converters;
    }

    public Object convert(final String value, final Class<?> type) {
        Converter converter = new NoOpConverter();
        for (Converter c : converters) {
            if (c.accept(type)) {
                converter = c;
            }
        }
        return converter.convert(value);
    }

}
