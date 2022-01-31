/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import io.annot8.api.components.responses.ProcessorResponse;
import io.annot8.api.components.responses.SourceResponse;
import io.annot8.api.context.Context;
import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.pipelines.ErrorConfiguration;
import io.annot8.api.pipelines.Pipeline;
import io.annot8.api.pipelines.PipelineDescriptor;
import io.annot8.api.pipelines.PipelineRunner;
import io.annot8.common.components.logging.Logging;
import io.annot8.common.components.metering.Metering;
import io.annot8.common.components.metering.Metrics;
import io.annot8.common.components.metering.NoOpMetrics;
import io.annot8.implementations.support.factories.QueueItemFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryPipelineRunner implements PipelineRunner {

  private static final long DEFAULT_DELAY = 0;

  private final Pipeline pipeline;
  private final Logger logger;
  private final Metrics metrics;
  private final QueueItemFactory itemFactory;
  private final long delay;

  private AtomicBoolean running = new AtomicBoolean(true);
  private long pipelineFinished = -1;

  private InMemoryPipelineRunner(Pipeline pipeline, ItemFactory itemFactory) {
    this(pipeline, itemFactory, DEFAULT_DELAY);
  }

  private InMemoryPipelineRunner(Pipeline pipeline, ItemFactory itemFactory, long delay) {
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
    this.itemFactory.register(i -> metrics.counter("itemsCreated").increment());

    metrics.gauge("queueSize", this.itemFactory, QueueItemFactory::getQueueSize);

    this.delay = delay;
  }

  @Override
  public void run() {
    logger.info("Pipeline {} started", pipeline.getName());
    running.set(true);

    Long startTime =
        metrics.gauge(
            "runTime", // Convert from milliseconds to seconds
            System.currentTimeMillis(),
            t -> {
              if (running.get()) {
                return (System.currentTimeMillis() - t) / 1000.0;
              } else {
                // Once the pipeline has finished, don't continue to increment the runTime
                return (pipelineFinished - t) / 1000.0;
              }
            });
    logger.debug("Pipeline {} started at {}", pipeline.getName(), startTime);

    while (running.get()) {
      SourceResponse sr = pipeline.read(itemFactory);

      while (running.get() && !itemFactory.isEmpty()) {
        Optional<Item> optItem = itemFactory.next();

        if (optItem.isPresent()) {
          ProcessorResponse response =
              // Gives time in seconds
              metrics.timer("itemProcessingTime").record(() -> pipeline.process(optItem.get()));

          metrics.counter("itemsProcessed").increment();

          if (response.getStatus().equals(ProcessorResponse.Status.OK)) {
            metrics.counter("itemsProcessed.ok").increment();
          } else if (response.getStatus().equals(ProcessorResponse.Status.PROCESSOR_ERROR)) {
            metrics.counter("itemsProcessed.processorError").increment();
          } else if (response.getStatus().equals(ProcessorResponse.Status.ITEM_ERROR)) {
            metrics.counter("itemsProcessed.itemError").increment();
          }
        }
      }

      // If we are done, then we stop
      if (itemFactory.isEmpty() && sr.getStatus() == SourceResponse.Status.DONE) {
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

    try {
      logger.info("Pipeline {} closing", pipeline.getName());
      pipeline.close();
    } catch (IOException ce) {
      logger.debug("Close error - {}", ce.getMessage());
    }

    pipelineFinished = System.currentTimeMillis();
    logger.debug("Pipeline {} finished at {}", pipeline.getName(), pipelineFinished);
    logger.info(
        "Pipeline {} ran for {} seconds",
        pipeline.getName(),
        (pipelineFinished - startTime) / 1000.0);
  }

  public void stop() {
    logger.info("Stopping pipeline after current item/source");
    running.set(false);
  }

  public boolean isRunning() {
    return running.get();
  }

  public static class Builder {
    private long delay = DEFAULT_DELAY;
    private ErrorConfiguration errorConfiguration = new ErrorConfiguration();
    private Context context = null;

    private ItemFactory itemFactory;
    private PipelineDescriptor pipelineDescriptor;

    public Builder withDelay(long delay) {
      this.delay = delay;
      return this;
    }

    public Builder withErrorConfiguration(ErrorConfiguration errorConfiguration) {
      this.errorConfiguration = errorConfiguration;
      return this;
    }

    public Builder withContext(Context context) {
      this.context = context;
      return this;
    }

    public Builder withItemFactory(ItemFactory itemFactory) {
      this.itemFactory = itemFactory;
      return this;
    }

    public Builder withPipelineDescriptor(PipelineDescriptor descriptor) {
      this.pipelineDescriptor = descriptor;
      return this;
    }

    public InMemoryPipelineRunner build() {
      if (itemFactory == null) throw new IncompleteException("ItemFactory must be provided");

      if (pipelineDescriptor == null)
        throw new IncompleteException("PipelineDescriptor must be provided");

      return new InMemoryPipelineRunner(
          new SimplePipeline.Builder()
              .from(pipelineDescriptor)
              .withErrorConfiguration(errorConfiguration)
              .withContext(context)
              .build(),
          itemFactory,
          delay);
    }
  }
}
