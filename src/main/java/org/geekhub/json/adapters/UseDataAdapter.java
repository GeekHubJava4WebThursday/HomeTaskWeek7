package org.geekhub.json.adapters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determines class of JsonDataAdapter that should be used to serialize annotated field value.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseDataAdapter {
    Class<? extends JsonDataAdapter> value();
}
