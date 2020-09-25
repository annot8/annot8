/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Group;
import io.annot8.api.filters.AndFilter;
import io.annot8.api.filters.Filter;
import io.annot8.api.filters.NotFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupFilters {

  private GroupFilters() {
    // Singleton
  }

  public static Filter<Group> byType(String type) {
    return new TypeFilter(type);
  }

  public static Filter<Group> byProperty(String key) {
    return new HasPropertyFilter(key, null, null);
  }

  public static Filter<Group> byProperty(String key, Class<?> clazz) {
    return new HasPropertyFilter(key, clazz, null);
  }

  public static Filter<Group> byProperty(String key, Object value) {
    return new HasPropertyFilter(key, null, value);
  }

  public static Filter<Group> hasRoles(String... roles) {
    return new HasRolesFilter(roles);
  }

  public static Filter<Group> includesAnnotation(Filter<Annotation> filter) {
    return new IncludesAnnotationFilter(filter);
  }

  public static Filter<Group> not(Filter<Group> filter) {
    return new NotFilter<>(filter);
  }

  public static Filter<Group> and(Filter<Group>... filters) {
    return new AndFilter<>(filters);
  }

  public static class TypeFilter implements Filter<Group> {
    private final String type;

    public TypeFilter(String type) {
      this.type = type;
    }

    @Override
    public boolean test(Group group) {
      return group.getType().equals(type);
    }
  }

  // TODO: Same as ANnotation (WithProperties?)
  public static class HasPropertyFilter implements Filter<Group> {

    private final String key;
    private final Class<?> valueClass;
    private final Object value;

    public HasPropertyFilter(String key, Class<?> valueClass, Object value) {
      this.key = key;
      this.valueClass = valueClass;
      this.value = value;
    }

    @Override
    public boolean test(Group group) {
      Optional<Object> o = group.getProperties().get(key);
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

  public static class HasRolesFilter implements Filter<Group> {
    private final List<String> roles;

    public HasRolesFilter(String... roles) {
      this.roles = Arrays.asList(roles);
    }

    @Override
    public boolean test(Group group) {
      Set<String> groupRoles = group.getRoles().collect(Collectors.toSet());
      return groupRoles.containsAll(roles);
    }
  }

  public static class IncludesAnnotationFilter implements Filter<Group> {
    private final Filter<Annotation> filter;

    public IncludesAnnotationFilter(Filter<Annotation> filter) {
      this.filter = filter;
    }

    @Override
    public boolean test(Group group) {
      return group.getAnnotations().values().stream().flatMap(s -> s).anyMatch(filter::test);
    }
  }
}
