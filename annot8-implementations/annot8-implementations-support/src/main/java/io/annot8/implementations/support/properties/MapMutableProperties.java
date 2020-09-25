/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.properties;

import io.annot8.api.properties.MutableProperties;
import io.annot8.api.properties.Properties;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of MutableProperties interface using an in-memory HashMap to store the properties.
 */
public class MapMutableProperties implements MutableProperties {

  private final Map<String, Object> properties = new HashMap<>();

  /** Create a new instance with no key-values */
  public MapMutableProperties() {
    // Do nothing
  }

  /**
   * Create a new instance with key-values from an existing Properties object *
   *
   * @param properties properties to copy
   */
  public MapMutableProperties(Properties properties) {
    properties.getAll().forEach(this.properties::put);
  }

  /**
   * Create a new instance with key-values from an existing Map
   *
   * @param properties properties to copy
   */
  public MapMutableProperties(Map<String, Object> properties) {
    properties.forEach(this.properties::put);
  }

  @Override
  public Map<String, Object> getAll() {
    return properties;
  }

  @Override
  public void set(String key, Object value) {
    properties.put(key, value);
  }

  @Override
  public Optional<Object> remove(String key) {
    if (properties.containsKey(key)) {
      return Optional.of(properties.remove(key));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public String toString() {
    return this.getClass().getName()
        + " ["
        + properties.entrySet().stream()
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining(", "))
        + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(properties);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Properties)) {
      return false;
    }

    Properties p = (Properties) o;
    return Objects.equals(properties, p.getAll());
  }
}
