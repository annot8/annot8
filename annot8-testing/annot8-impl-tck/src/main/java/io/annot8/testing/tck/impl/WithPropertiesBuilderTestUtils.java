/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.tck.impl;

import static org.junit.jupiter.api.Assertions.*;

import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.helpers.WithProperties;
import io.annot8.api.helpers.builders.WithPropertiesBuilder;
import io.annot8.api.helpers.builders.WithSave;
import io.annot8.testing.testimpl.TestConstants;
import io.annot8.testing.testimpl.TestProperties;

public class WithPropertiesBuilderTestUtils<
    T extends WithPropertiesBuilder<T> & WithSave<WithProperties>> {

  /**
   * Testing utility method to cover The save method of the provided WithPropertiesBuilder will be
   * called. Please ensure any other required fields are set.
   */
  public void testWithPropertiesBuilder(T propertiesBuilderTestable) {
    testWithProperty(propertiesBuilderTestable);
    testWithProperties(propertiesBuilderTestable);
    testWithoutProperty(propertiesBuilderTestable);
    testWithoutKeyValue(propertiesBuilderTestable);
  }

  private void testWithProperty(T propertiesBuilderTestable) {
    WithProperties properties = null;
    try {
      properties =
          propertiesBuilderTestable
              .withProperty(TestConstants.PROPERTY_KEY, TestConstants.PROPERTY_VALUE)
              .save();
    } catch (IncompleteException e) {
      fail("Test should not fail here");
    }
    assertTrue(properties.getProperties().has(TestConstants.PROPERTY_KEY));
    assertEquals(
        TestConstants.PROPERTY_VALUE,
        properties.getProperties().get(TestConstants.PROPERTY_KEY).get());
  }

  private void testWithProperties(T propertiesBuilderTestable) {
    TestProperties toAdd = new TestProperties();
    toAdd.set(TestConstants.PROPERTY_KEY, TestConstants.PROPERTY_VALUE);
    WithProperties properties = null;
    try {
      properties = propertiesBuilderTestable.withProperties(toAdd).save();
    } catch (IncompleteException e) {
      fail("Test should not fail here");
    }
    assertTrue(properties.getProperties().has(TestConstants.PROPERTY_KEY));
    assertEquals(
        TestConstants.PROPERTY_VALUE,
        properties.getProperties().get(TestConstants.PROPERTY_KEY).get());
  }

  private void testWithoutProperty(T propertiesBuilderTestable) {
    WithProperties properties = null;
    try {
      properties =
          propertiesBuilderTestable
              .withProperty(TestConstants.PROPERTY_KEY, TestConstants.PROPERTY_VALUE)
              .withoutProperty(TestConstants.PROPERTY_KEY)
              .save();
    } catch (IncompleteException e) {
      fail("Test should not fail here");
    }
    assertFalse(properties.getProperties().has(TestConstants.PROPERTY_KEY));
  }

  private void testWithoutKeyValue(T propertiesBuilderTestable) {
    TestProperties toAdd = new TestProperties();
    toAdd.set(TestConstants.PROPERTY_KEY, TestConstants.PROPERTY_VALUE);
    WithProperties properties = null;
    try {
      properties =
          propertiesBuilderTestable
              .withProperty(TestConstants.PROPERTY_KEY, TestConstants.PROPERTY_VALUE)
              .withoutProperty(TestConstants.PROPERTY_KEY, TestConstants.PROPERTY_VALUE)
              .save();
    } catch (IncompleteException e) {
      fail("Test should not fail here");
    }
    assertFalse(properties.getProperties().has(TestConstants.PROPERTY_KEY));
  }
}
