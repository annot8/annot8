/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.annot8.api.properties.MutableProperties;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class MapMutablePropertiesTest {

  @Test
  public void testMutableProperties() {
    MutableProperties props = new MapMutableProperties();

    props.set("key1", "Hello World");
    props.set("key2", 17);
    props.set("key3", 17);
    props.set("key1", "Overwritten");

    assertTrue(props.has("key3"));
    Optional<Object> removed = props.remove("key3");
    assertTrue(removed.isPresent());
    assertEquals(17, removed.get());
    assertFalse(props.has("key3"));

    assertFalse(props.remove("key4").isPresent());

    Map<String, Object> map = props.getAll();
    testMap(map);

    MutableProperties props2 = new MapMutableProperties(props);
    testMap(props2.getAll());

    MutableProperties props3 = new MapMutableProperties(map);
    testMap(props3.getAll());
  }

  private void testMap(Map<String, Object> map) {
    assertEquals(2, map.size());
    assertEquals("Overwritten", map.get("key1"));
    assertEquals(17, map.get("key2"));
  }
}
