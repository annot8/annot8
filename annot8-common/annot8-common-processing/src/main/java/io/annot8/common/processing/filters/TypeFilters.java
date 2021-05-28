/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import io.annot8.api.filters.Filter;
import io.annot8.api.helpers.WithType;

public class TypeFilters {
  private TypeFilters() {
    // Singleton
  }

  public static class TypeFilter<T extends WithType> implements Filter<T> {
    private final String type;

    public TypeFilter(String type) {
      this.type = type;
    }

    @Override
    public boolean test(T t) {
      return t.getType().equals(type);
    }
  }
}
