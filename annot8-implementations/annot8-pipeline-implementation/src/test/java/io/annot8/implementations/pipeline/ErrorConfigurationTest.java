/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ErrorConfigurationTest {
  @Test
  public void test() {
    ErrorConfiguration ec = new ErrorConfiguration();

    assertNotNull(ec.getOnItemError());
    assertNotNull(ec.getOnProcessorError());
    assertNotNull(ec.getOnSourceError());

    ec.setOnItemError(ErrorConfiguration.OnProcessingError.DISCARD_ITEM);
    assertEquals(ErrorConfiguration.OnProcessingError.DISCARD_ITEM, ec.getOnItemError());

    ec.setOnProcessorError(ErrorConfiguration.OnProcessingError.REMOVE_PROCESSOR);
    assertEquals(ErrorConfiguration.OnProcessingError.REMOVE_PROCESSOR, ec.getOnProcessorError());

    ec.setOnSourceError(ErrorConfiguration.OnSourceError.IGNORE);
    assertEquals(ErrorConfiguration.OnSourceError.IGNORE, ec.getOnSourceError());

    assertNotNull(ec.toString());
  }

  @Test
  public void testEquals() {
    ErrorConfiguration ec1 = new ErrorConfiguration();
    ErrorConfiguration ec2 = new ErrorConfiguration();

    assertEquals(ec1, ec2);
    assertEquals(ec1.hashCode(), ec2.hashCode());

    ec1.setOnSourceError(ErrorConfiguration.OnSourceError.REMOVE_SOURCE);
    ec2.setOnSourceError(ErrorConfiguration.OnSourceError.IGNORE);

    assertNotEquals(ec1, ec2);
    assertNotEquals(ec1.hashCode(), ec2.hashCode());
  }
}
