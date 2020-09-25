/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components;

import io.annot8.api.components.Annot8Component;
import io.annot8.api.context.Context;
import io.annot8.common.components.logging.Logging;
import io.annot8.common.components.metering.Metering;
import io.annot8.common.components.metering.Metrics;
import io.annot8.common.components.metering.NoOpMetrics;
import io.micrometer.core.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractComponent implements Annot8Component {

  private Logger logger;

  private Metrics metrics;

  /** Create component with default logging and metrics */
  public AbstractComponent() {
    this(null, null);
  }

  /**
   * Create component with specific logging and metrics from context
   *
   * @param context the context
   */
  public AbstractComponent(Context context) {
    this(
        context.getResource(Logging.class).orElse(null),
        context.getResource(Metering.class).orElse(null));
  }

  /**
   * Create component with specific logging and metrics
   *
   * @param logging logging to use
   * @param metering metering to use
   */
  public AbstractComponent(Logging logging, Metering metering) {
    logger = logging != null ? logging.getLogger(this.getClass()) : null;
    metrics = metering != null ? metering.getMetrics(this.getClass()) : null;
  }

  /**
   * Get the (slf4j) logger for this component.
   *
   * <p>Ensure you have called configure (ie super.configure()) before using this. Otherwise you
   * will be given a no-op logger.
   *
   * @return non-null logger
   */
  protected Logger log() {
    // if configure has not been called we might not have a logger, so check and create is necessary
    if (logger == null) {
      createDefaultLogger();
    }

    return logger;
  }

  /**
   * Get the metrics for this component
   *
   * <p>Ensure you have called configure (ie super.configure()) before using this. Otherwise you
   * will be given a no-op logger.
   *
   * @return non-null metrics
   */
  protected Metrics metrics() {
    // if configure has not been called we might not have a metrics, so check and create is
    // necessary
    if (metrics == null) {
      createDefaultMetrics();
    }

    return metrics;
  }

  /**
   * Called by external manager (eg a pipeline) to set up logging
   *
   * @param logger logger to use, or null to change to no-op logging
   */
  public void setLogger(@Nullable Logger logger) {
    this.logger = logger;
  }

  /**
   * Called by external manager (eg a pipeline) to set up metrics
   *
   * @param metrics to use, or null to change to no-op metrics
   */
  public void setMetrics(Metrics metrics) {
    this.metrics = metrics;
  }

  protected void createDefaultLogger() {
    logger = LoggerFactory.getLogger(this.getClass());
  }

  protected void createDefaultMetrics() {
    metrics = NoOpMetrics.instance();
  }
}
