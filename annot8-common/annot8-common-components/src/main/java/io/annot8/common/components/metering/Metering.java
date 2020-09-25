/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.metering;

import io.annot8.api.components.Annot8Component;
import io.annot8.api.components.Resource;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.Objects;

public final class Metering implements Resource {

  private final MeterRegistry meterRegistry;
  private final String prefix;

  protected Metering(String prefix) {
    this(io.micrometer.core.instrument.Metrics.globalRegistry, prefix);
  }

  protected Metering(MeterRegistry meterRegistry, String prefix) {
    // Ensure that we have at least something to create a registry with
    this.meterRegistry = Objects.requireNonNullElseGet(meterRegistry, SimpleMeterRegistry::new);
    this.prefix = prefix == null ? "" : prefix;
  }

  public static Metering useGlobalRegistry(String prefix) {
    return new Metering(prefix);
  }

  public static Metering useMeterRegistry(MeterRegistry meterRegistry, String prefix) {
    return new Metering(meterRegistry, prefix);
  }

  public static Metering notAvailable() {
    return new Metering(null, "noop");
  }

  public Metrics getMetrics(Class<? extends Annot8Component> clazz) {
    return getMetrics(clazz.getSimpleName());
  }

  public Metrics getMetrics(String name) {
    return new NamedMetrics(meterRegistry, prefix, name);
  }
}
