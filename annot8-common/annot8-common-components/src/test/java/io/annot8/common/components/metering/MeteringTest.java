/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.metering;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.annot8.api.components.Annot8Component;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MeteringTest {

  @Test
  public void testMetering() {
    Metrics metrics = Metering.useGlobalRegistry("test").getMetrics(TestProcessor.class);
    assertNotNull(metrics);
  }

  @Test
  public void testUseMeterRegistry() {
    MeterRegistry registry = Mockito.mock(MeterRegistry.class);
    Metrics metrics = Metering.useMeterRegistry(registry, "test").getMetrics(TestProcessor.class);
    assertNotNull(metrics);
  }

  @Test
  public void testNotAvailable() {
    Metrics metrics = Metering.notAvailable().getMetrics(TestProcessor.class);
    assertNotNull(metrics);
  }

  private abstract class TestProcessor implements Annot8Component {}
}
