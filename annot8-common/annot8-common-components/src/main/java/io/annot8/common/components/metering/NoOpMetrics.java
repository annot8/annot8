/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.metering;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Metrics which can be used whatever is configured, but which don't do anything.
 *
 * <p>We use a real meter registry as otherwise we'd have to 'mock' out all the different meter
 * types.
 */
public final class NoOpMetrics {

  private static final MeterRegistry METER_REGISTRY = new SimpleMeterRegistry();

  private static final Metrics INSTANCE = new NamedMetrics(METER_REGISTRY, "noop");

  public static Metrics instance() {
    return INSTANCE;
  }

  private NoOpMetrics() {
    // Singleton
  }
}
