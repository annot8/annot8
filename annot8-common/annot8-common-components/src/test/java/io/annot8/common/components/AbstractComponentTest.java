/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.annot8.api.components.Processor;
import io.annot8.api.components.responses.ProcessorResponse;
import io.annot8.api.data.Item;
import org.junit.jupiter.api.Test;

/**
 * Unit tests to ensure the AbstractComponent implementation provides the correct resources to the
 * implementing class
 */
public class AbstractComponentTest {

  @Test
  public void testAbstractComponent() {
    TestComponent component = new TestComponent();
    component.process(null);
    component.close();
  }

  private class TestComponent extends AbstractComponent implements Processor {

    @Override
    public ProcessorResponse process(Item item) {
      assertNotNull(log());
      assertNotNull(metrics());
      return null;
    }
  }
}
