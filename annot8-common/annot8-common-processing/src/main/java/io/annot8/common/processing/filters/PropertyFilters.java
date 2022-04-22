/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import io.annot8.api.filters.Filter;
import io.annot8.api.helpers.WithProperties;
import java.util.Optional;

public class PropertyFilters {
  private PropertyFilters() {
    // Singleton
  }

  public static <T extends WithProperties> Filter<T> byProperty(String key) {
    return new HasPropertyFilter<>(key, null, null);
  }

  public static <T extends WithProperties> Filter<T> byProperty(String key, Class<?> clazz) {
    return new HasPropertyFilter<>(key, clazz, null);
  }

  public static <T extends WithProperties> Filter<T> byProperty(String key, Object value) {
    return new HasPropertyFilter<>(key, null, value);
  }

  public static class HasPropertyFilter<T extends WithProperties> implements Filter<T> {
    private final String key;
    private final Class<?> valueClass;
    private final Object value;

    public HasPropertyFilter(String key, Class<?> valueClass, Object value) {
      this.key = key;
      this.valueClass = valueClass;
      this.value = value;
    }

    @Override
    public boolean test(T t) {
      Optional<Object> o = t.getProperties().get(key);
      if (o.isEmpty()) {
        return false;
      } else if (value != null) {
        return o.get().equals(value);
      } else if (valueClass != null) {
        return valueClass.isInstance(o.get());
      } else {
        return true;
      }
    }
  }
}
