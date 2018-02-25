package ua.estate.rialto.util.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.TimeZone;

/**
 * <p>
 * Handling Hibernate lazy-loading
 *
 * @link https://github.com/FasterXML/jackson
 * @link https://github.com/FasterXML/jackson-datatype-hibernate
 * @link http://wiki.fasterxml.com/JacksonHowToCustomSerializers
 */
public class JacksonObjectMapper extends ObjectMapper {

    private static final ObjectMapper MAPPER = new JacksonObjectMapper();
    private static final ObjectWriter PRETTY_MAPPER = MAPPER.writerWithDefaultPrettyPrinter();

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static ObjectWriter getPrettyWriter() {
        return PRETTY_MAPPER;
    }

    private JacksonObjectMapper() {
        super();

        registerModule(new Hibernate5Module());
        registerModule(new JavaTimeModule());

        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        setTimeZone(TimeZone.getDefault());
    }

    /**
     * Регистрирует модули в ObjectMapper
     * @param modules список регистрируемых модулей
     */
    public void setModuleList (List<Module> modules) {
        for (Module module : modules) {
            registerModule(module);
        }
    }
}
