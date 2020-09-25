/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.metering;

import io.annot8.api.components.Annot8Component;
import io.micrometer.core.instrument.*;
import java.util.Collection;
import java.util.Map;
import java.util.function.ToDoubleFunction;

public class NamedMetrics implements Metrics {

  private final MeterRegistry meterRegistry;
  private final String prefix;

  public NamedMetrics(MeterRegistry meterRegistry, String prefix, String name) {
    this(meterRegistry, String.format("%s-%s", prefix, name));
  }

  public NamedMetrics(
      MeterRegistry meterRegistry, String prefix, Class<? extends Annot8Component> clazz) {
    this(meterRegistry, prefix, clazz.getName());
  }

  protected NamedMetrics(MeterRegistry meterRegistry, String prefix) {
    this.meterRegistry = meterRegistry;
    this.prefix = prefix;
  }

  private String getName(String name) {
    return String.format("%s-%s", prefix, name);
  }

  @Override
  public Counter counter(String name, Iterable<Tag> tags) {
    return meterRegistry.counter(getName(name), tags);
  }

  @Override
  public Counter counter(String name, String... tags) {
    return meterRegistry.counter(getName(name), tags);
  }

  @Override
  public DistributionSummary summary(String name, Iterable<Tag> tags) {
    return meterRegistry.summary(getName(name), tags);
  }

  @Override
  public DistributionSummary summary(String name, String... tags) {
    return meterRegistry.summary(getName(name), tags);
  }

  @Override
  public Timer timer(String name, Iterable<Tag> tags) {
    return meterRegistry.timer(getName(name), tags);
  }

  @Override
  public Timer timer(String name, String... tags) {
    return meterRegistry.timer(getName(name), tags);
  }

  @Override
  public <T> T gauge(String name, Iterable<Tag> tags, T obj, ToDoubleFunction<T> valueFunction) {
    return meterRegistry.gauge(getName(name), tags, obj, valueFunction);
  }

  @Override
  public <T extends Number> T gauge(String name, Iterable<Tag> tags, T number) {
    return meterRegistry.gauge(getName(name), tags, number);
  }

  @Override
  public <T extends Number> T gauge(String name, T number) {
    return meterRegistry.gauge(getName(name), number);
  }

  @Override
  public <T> T gauge(String name, T obj, ToDoubleFunction<T> valueFunction) {
    return meterRegistry.gauge(getName(name), obj, valueFunction);
  }

  @Override
  public <T extends Collection<?>> T gaugeCollectionSize(
      String name, Iterable<Tag> tags, T collection) {
    return meterRegistry.gaugeCollectionSize(getName(name), tags, collection);
  }

  @Override
  public <T extends Map<?, ?>> T gaugeMapSize(String name, Iterable<Tag> tags, T map) {
    return meterRegistry.gaugeMapSize(getName(name), tags, map);
  }
}
