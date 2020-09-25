/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import io.annot8.api.components.responses.SourceResponse;
import io.annot8.api.context.Context;
import io.annot8.api.data.ItemFactory;
import io.annot8.api.pipelines.Pipeline;
import io.annot8.api.pipelines.PipelineDescriptor;
import io.annot8.api.pipelines.PipelineRunner;
import io.annot8.common.components.logging.Logging;
import io.annot8.common.components.metering.Metering;
import io.annot8.common.components.metering.Metrics;
import io.annot8.common.components.metering.NoOpMetrics;
import io.annot8.implementations.support.stores.QueueItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryPipelineRunner implements PipelineRunner {

  private static final long DEFAULT_DELAY = 1000;

  private final Pipeline pipeline;
  private final Logger logger;
  private final Metrics metrics;
  private final QueueItemFactory itemFactory;
  private final long delay;

  private boolean running = true;

  public InMemoryPipelineRunner(Pipeline pipeline, ItemFactory itemFactory) {
    this(pipeline, itemFactory, DEFAULT_DELAY);
  }

  public InMemoryPipelineRunner(Pipeline pipeline, ItemFactory itemFactory, long delay) {
    this.pipeline = pipeline;
    this.logger =
        pipeline
            .getContext()
            .getResource(Logging.class)
            .map(l -> l.getLogger(InMemoryPipelineRunner.class))
            .orElse(LoggerFactory.getLogger(InMemoryPipelineRunner.class));

    this.metrics =
        pipeline
            .getContext()
            .getResource(Metering.class)
            .map(value -> value.getMetrics("pipeline"))
            .orElseGet(NoOpMetrics::instance);

    this.itemFactory = new QueueItemFactory(itemFactory);
    this.itemFactory.register(i -> logger.debug("Item {} added to queue", i.getId()));

    this.delay = delay;
  }

  public InMemoryPipelineRunner(PipelineDescriptor pipelineDescriptor, ItemFactory itemFactory) {
    this(new SimplePipeline.Builder().from(pipelineDescriptor).build(), itemFactory, DEFAULT_DELAY);
  }

  public InMemoryPipelineRunner(
      PipelineDescriptor pipelineDescriptor, ItemFactory itemFactory, long delay) {
    this(new SimplePipeline.Builder().from(pipelineDescriptor).build(), itemFactory, delay);
  }

  public InMemoryPipelineRunner(
      PipelineDescriptor pipelineDescriptor, ItemFactory itemFactory, Context context) {
    this(
        new SimplePipeline.Builder().from(pipelineDescriptor).withContext(context).build(),
        itemFactory,
        DEFAULT_DELAY);
  }

  public InMemoryPipelineRunner(
      PipelineDescriptor pipelineDescriptor, ItemFactory itemFactory, Context context, long delay) {
    this(
        new SimplePipeline.Builder().from(pipelineDescriptor).withContext(context).build(),
        itemFactory,
        delay);
  }

  @Override
  public void run() {
    logger.info("Pipeline {} started", pipeline.getName());
    running = true;

    Long startTime =
        metrics.gauge("runTime", System.currentTimeMillis(), t -> System.currentTimeMillis() - t);
    logger.debug("Pipeline {} started at {}", pipeline.getName(), startTime);

    while (running) {
      SourceResponse sr = pipeline.read(itemFactory);

      while (running && !itemFactory.isEmpty()) {
        metrics
            .timer("itemProcessingTime")
            .record(() -> itemFactory.next().ifPresent(pipeline::process));
        metrics.counter("itemsProcessed").increment();
      }

      // If we are done, then we stop
      if (sr.getStatus() == SourceResponse.Status.DONE) {
        stop();
      }

      if (delay > 0) {
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          logger.debug("Sleep interrupted - {}", e.getMessage());
        }
      }
    }

    logger.debug("Pipeline {} finished at {}", pipeline.getName(), startTime);
    logger.info(
        "Pipeline {} ran for {} seconds",
        pipeline.getName(),
        (System.currentTimeMillis() - startTime) / 1000.0);
  }

  public void stop() {
    logger.info("Stopping pipeline after current item/source");
    running = false;
  }
}
