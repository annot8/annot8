/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.metering;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.lang.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.function.ToDoubleFunction;

public interface Metrics {

  /**
   * Tracks a monotonically increasing value.
   *
   * @param name The base metric name
   * @param tags Sequence of dimensions for breaking down the name.
   * @return A new or existing counter.
   */
  Counter counter(String name, Iterable<Tag> tags);

  /**
   * Tracks a monotonically increasing value.
   *
   * @param name The base metric name
   * @param tags MUST be an even number of arguments representing key/value pairs of tags.
   * @return A new or existing counter.
   */
  Counter counter(String name, String... tags);

  /**
   * Measures the distribution of samples.
   *
   * @param name The base metric name
   * @param tags Sequence of dimensions for breaking down the name.
   * @return A new or existing distribution summary.
   */
  DistributionSummary summary(String name, Iterable<Tag> tags);

  /**
   * Measures the distribution of samples.
   *
   * @param name The base metric name
   * @param tags MUST be an even number of arguments representing key/value pairs of tags.
   * @return A new or existing distribution summary.
   */
  DistributionSummary summary(String name, String... tags);

  /**
   * Measures the time taken for short tasks and the count of these tasks.
   *
   * @param name The base metric name
   * @param tags Sequence of dimensions for breaking down the name.
   * @return A new or existing timer.
   */
  Timer timer(String name, Iterable<Tag> tags);

  /**
   * Measures the time taken for short tasks and the count of these tasks.
   *
   * @param name The base metric name
   * @param tags MUST be an even number of arguments representing key/value pairs of tags.
   * @return A new or existing timer.
   */
  Timer timer(String name, String... tags);

  /**
   * Register a gauge that reports the value of the object after the function {@code f} is applied.
   * The registration will keep a weak reference to the object so it will not prevent garbage
   * collection. Applying {@code f} on the object should be thread safe.
   *
   * <p>If multiple gauges are registered with the same id, then the values will be aggregated and
   * the sum will be reported. For example, registering multiple gauges for active threads in a
   * thread pool with the same id would produce a value that is the overall number of active
   * threads. For other behaviors, manage it on the user side and avoid multiple registrations.
   *
   * @param name Name of the gauge being registered.
   * @param tags Sequence of dimensions for breaking down the name.
   * @param obj State object used to compute a value.
   * @param valueFunction Function that produces an instantaneous gauge value from the state object.
   * @param <T> The type of the state object from which the gauge value is extracted.
   * @return The number that was passed in so the registration can be done as part of an assignment
   *     statement.
   */
  @Nullable
  <T> T gauge(String name, Iterable<Tag> tags, @Nullable T obj, ToDoubleFunction<T> valueFunction);

  /**
   * Register a gauge that reports the value of the {@link Number}.
   *
   * @param name Name of the gauge being registered.
   * @param tags Sequence of dimensions for breaking down the name.
   * @param number Thread-safe implementation of {@link Number} used to access the value.
   * @param <T> The type of the number from which the gauge value is extracted.
   * @return The number that was passed in so the registration can be done as part of an assignment
   *     statement.
   */
  @Nullable
  <T extends Number> T gauge(String name, Iterable<Tag> tags, T number);

  /**
   * Register a gauge that reports the value of the {@link Number}.
   *
   * @param name Name of the gauge being registered.
   * @param number Thread-safe implementation of {@link Number} used to access the value.
   * @param <T> The type of the state object from which the gauge value is extracted.
   * @return The number that was passed in so the registration can be done as part of an assignment
   *     statement.
   */
  @Nullable
  <T extends Number> T gauge(String name, T number);

  /**
   * Register a gauge that reports the value of the object.
   *
   * @param name Name of the gauge being registered.
   * @param obj State object used to compute a value.
   * @param valueFunction Function that produces an instantaneous gauge value from the state object.
   * @param <T> The type of the state object from which the gauge value is extracted.
   * @return The number that was passed in so the registration can be done as part of an assignment
   *     statement.
   */
  @Nullable
  <T> T gauge(String name, T obj, ToDoubleFunction<T> valueFunction);

  /**
   * Register a gauge that reports the size of the {@link Collection}. The registration will keep a
   * weak reference to the collection so it will not prevent garbage collection. The collection
   * implementation used should be thread safe. Note that calling {@link Collection#size()} can be
   * expensive for some collection implementations and should be considered before registering.
   *
   * @param name Name of the gauge being registered.
   * @param tags Sequence of dimensions for breaking down the name.
   * @param collection Thread-safe implementation of {@link Collection} used to access the value.
   * @param <T> The type of the state object from which the gauge value is extracted.
   * @return The number that was passed in so the registration can be done as part of an assignment
   *     statement.
   */
  @Nullable
  <T extends Collection<?>> T gaugeCollectionSize(String name, Iterable<Tag> tags, T collection);

  /**
   * Register a gauge that reports the size of the {@link Map}. The registration will keep a weak
   * reference to the collection so it will not prevent garbage collection. The collection
   * implementation used should be thread safe. Note that calling {@link Map#size()} can be
   * expensive for some collection implementations and should be considered before registering.
   *
   * @param name Name of the gauge being registered.
   * @param tags Sequence of dimensions for breaking down the name.
   * @param map Thread-safe implementation of {@link Map} used to access the value.
   * @param <T> The type of the state object from which the gauge value is extracted.
   * @return The number that was passed in so the registration can be done as part of an assignment
   *     statement.
   */
  @Nullable
  <T extends Map<?, ?>> T gaugeMapSize(String name, Iterable<Tag> tags, T map);
}
