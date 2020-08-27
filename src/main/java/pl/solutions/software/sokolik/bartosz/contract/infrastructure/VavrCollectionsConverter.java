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
public class VavrCollectionsConverter implements ModelConverter {
    private static Map<Class, Class> modelSubstitutes = Map.of(
            io.vavr.collection.List.class, java.util.List.class,
            io.vavr.collection.Set.class, java.util.Set.class
    );

    @Override
    public Schema resolve(AnnotatedType annotatedType, ModelConverterContext context, Iterator<ModelConverter> chain) {
        JavaType type = Json.mapper().constructType(annotatedType.getType());
        if (type != null) {
            Class<?> clazz = type.getRawClass();
            if (modelSubstitutes.containsKey(clazz)) {
                replaceModelType(annotatedType, type, clazz);
            }
        }

        return chain.hasNext() ? chain.next().resolve(annotatedType, context, chain) : null;
    }

    private void replaceModelType(AnnotatedType annotatedType, JavaType type, Class<?> clazz) {
        JavaType itemType = type.getBindings().getBoundType(0);
        JavaType newModelType = Json.mapper().getTypeFactory().constructCollectionType(modelSubstitutes.get(clazz), itemType);
        annotatedType.setType(newModelType);
    }

}
