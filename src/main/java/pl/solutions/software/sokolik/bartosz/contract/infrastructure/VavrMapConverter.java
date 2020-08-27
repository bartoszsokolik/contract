package pl.solutions.software.sokolik.bartosz.contract.infrastructure;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class VavrMapConverter implements ModelConverter {

    private static Map<Class<?>, Class<? extends Map>> modelSubstitutes = Map.of(
            io.vavr.collection.Map.class, Map.class
    );

    @Override
    public Schema<?> resolve(AnnotatedType annotationType, ModelConverterContext context, Iterator<ModelConverter> chain) {
        JavaType type = Json.mapper().constructType(annotationType.getType());
        if (type != null) {
            Class<?> clazz = type.getRawClass();
            if (modelSubstitutes.containsKey(clazz)) {
                replaceModelType(annotationType, type, clazz);
            }
        }

        return chain.hasNext() ? chain.next().resolve(annotationType, context, chain) : null;
    }

    private void replaceModelType(AnnotatedType annotatedType, JavaType type, Class<?> clazz) {
        JavaType keyType = type.getBindings().getBoundType(0);
        JavaType valueType = type.getBindings().getBoundType(1);
        JavaType newModelType = Json.mapper().getTypeFactory().constructMapType(modelSubstitutes.get(clazz), keyType, valueType);
        annotatedType.setType(newModelType);
    }

}
