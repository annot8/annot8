/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.properties.MutableProperties;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestProperties implements MutableProperties, ImmutableProperties {

  private final Map<String, Object> properties = new HashMap<>();

  @Override
  public void set(String key, Object value) {
    properties.put(key, value);
  }

  @Override
  public Optional<Object> remove(String key) {
    return Optional.ofNullable(properties.remove(key));
  }

  @Override
  public Map<String, Object> getAll() {
    return Collections.unmodifiableMap(properties);
  }
}
