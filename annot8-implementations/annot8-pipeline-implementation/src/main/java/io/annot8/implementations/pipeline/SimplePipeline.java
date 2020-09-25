/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import io.annot8.api.components.Processor;
import io.annot8.api.components.Resource;
import io.annot8.api.components.Source;
import io.annot8.api.components.annotations.SettingsClass;
import io.annot8.api.components.responses.ProcessorResponse;
import io.annot8.api.components.responses.SourceResponse;
import io.annot8.api.context.Context;
import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.pipelines.Pipeline;
import io.annot8.api.pipelines.PipelineDescriptor;
import io.annot8.api.settings.NoSettings;
import io.annot8.api.settings.Settings;
import io.annot8.common.components.logging.Logging;
import io.annot8.common.components.metering.Metering;
import io.annot8.implementations.support.context.SimpleContext;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePipeline implements Pipeline {
  private final String name;
  private final String description;

  private final Collection<Source> sources;
  private final Collection<Processor> processors;
  private final Context context;
  private final Logger logger;

  private SimplePipeline(
      Context context,
      String name,
      String description,
      Collection<Source> sources,
      Collection<Processor> processors) {
    this.name = name;
    this.description = description;
    this.sources = sources;
    this.processors = processors;
    this.context = context;

    this.logger =
        this.getContext()
            .getResource(Logging.class)
            .map(l -> l.getLogger(InMemoryPipelineRunner.class))
            .orElse(LoggerFactory.getLogger(InMemoryPipelineRunner.class));
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Context getContext() {
    return context;
  }

  public Collection<Source> getSources() {
    return sources;
  }

  public Collection<Processor> getProcessors() {
    return processors;
  }

  public SourceResponse read(ItemFactory itemFactory) {
    Optional<Source> optional = getSources().stream().findFirst();

    if (optional.isEmpty()) {
      return SourceResponse.done();
    }

    Source source = optional.get();

    logger.debug("[{}] Reading source {} for new items", getName(), source.toString());
    SourceResponse response = source.read(itemFactory);

    switch (response.getStatus()) {
      case DONE:
        logger.info("[{}] Finished reading all items from source {}", getName(), source.toString());
        remove(source);
        // Move onto the next source
        return read(itemFactory);
      case SOURCE_ERROR:
        logger.error(
            "[{}] Source {} returned a non-recoverable error and has been removed from the pipeline",
            getName(),
            source.toString());
        if (response.hasExceptions()) {
          for (Exception e : response.getExceptions()) {
            logger.error("The following exception was caught by the source", e);
          }
        }
        remove(source);
        return response;
    }

    return response;
  }

  public ProcessorResponse process(Item item) {
    logger.debug("[{}] Beginning processing of item {}", getName(), item.getId());

    List<Processor> erroring = new LinkedList<>();

    ProcessorResponse response = ProcessorResponse.ok();

    for (Processor processor : getProcessors()) {

      logger.debug(
          "[{}] Processing item {} using processor {}",
          getName(),
          item.getId(),
          processor.toString());
      response = processor.process(item);

      if (response.getStatus() == ProcessorResponse.Status.ITEM_ERROR) {
        logger.error(
            "[{}] Processor {} returned an error whilst processing the current item {}, and the item will not be processed by the remainder of the pipeline",
            getName(),
            processor.toString(),
            item.getId());
        if (response.hasExceptions()) {
          for (Exception e : response.getExceptions()) {
            logger.error("The following exception was caught by the processor", e);
          }
        }
        break;
      } else if (response.getStatus() == ProcessorResponse.Status.PROCESSOR_ERROR) {
        logger.error(
            "[{}] Processor {} returned a non-recoverable error whilst processing the current item {}, and the processor has been removed from the pipeline",
            getName(),
            processor.toString(),
            item.getId());
        if (response.hasExceptions()) {
          for (Exception e : response.getExceptions()) {
            logger.error("The following exception was caught by the processor", e);
          }
        }

        erroring.add(processor);
      }
    }

    // Actually remove all the processors which errored
    erroring.forEach(this::remove);

    return response;
  }

  protected void remove(Processor processor) {
    processors.remove(processor);
  }

  protected void remove(Source source) {
    sources.remove(source);
  }

  public void close() {
    sources.stream().forEach(Source::close);
    processors.stream().forEach(Processor::close);
    context.getResources().forEach(Resource::close);
  }

  public static class Builder implements Pipeline.Builder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePipeline.Builder.class);

    private String name;
    private String description;
    private List<Source> sources = new ArrayList<>();
    private List<Processor> processors = new ArrayList<>();
    private List<Resource> resources = new ArrayList<>();
    private PipelineDescriptor descriptor = null;
    private Context context;

    public Builder() {
      // Do nothing
    }

    @Override
    public Builder from(PipelineDescriptor pipelineDescriptor) {
      this.descriptor = pipelineDescriptor;
      return this;
    }

    @Override
    public Builder withResource(Resource resource) {
      resources.add(resource);
      return this;
    }

    @Override
    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    @Override
    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    @Override
    public Builder withSource(Source source) {
      sources.add(source);
      return this;
    }

    @Override
    public Builder withProcessor(Processor processor) {
      processors.add(processor);
      return this;
    }

    @Override
    public Builder withContext(Context context) {
      this.context = context;
      return this;
    }

    @Override
    public Pipeline build() throws IncompleteException {

      // Ensure we have a name

      if (descriptor != null && name == null) {
        name = descriptor.getName();
      }

      if (name == null || name.isEmpty()) {
        throw new IncompleteException("Pipeline must have a name");
      }

      // Pull resources from the provided context

      if (context != null) {
        context.getResources().forEach(resources::add);
      }

      // Add in Logging and Metering is none has been supplied

      if (!resources.stream().anyMatch(Logging.class::isInstance)) {
        resources.add(Logging.useLoggerFactory());
      }

      if (!resources.stream().anyMatch(Metering.class::isInstance)) {
        resources.add(Metering.useGlobalRegistry(name));
      }

      // New context

      Context pipelineContext = new SimpleContext(resources);

      // Build the pipeline from the descriptor (if present)

      if (descriptor != null) {

        // NOTE that the sources and processors will be added to the end of the pipeline here
        // (depending ordering implementation)

        descriptor.getSources().stream()
            .map(
                d -> {
                  // If settings haven't been provided, try to create some default settings
                  if (d.getSettings() == null) {
                    SettingsClass s = d.getClass().getAnnotation(SettingsClass.class);
                    if (s != null && s.value() != NoSettings.class) {
                      try {
                        Settings settings = s.value().getConstructor().newInstance();
                        d.setSettings(settings);
                      } catch (NoSuchMethodException nsme) {
                        LOGGER.warn(
                            "Could not create default settings - {} does not have a no-args constructor",
                            s.value().getName());
                      } catch (Exception e) {
                        LOGGER.warn(
                            "Could not instantiate default settings {}", s.value().getName(), e);
                      }
                    }
                  }

                  // Convert from SourceDescriptor to Source
                  return d.create(pipelineContext);
                })
            .map(Source.class::cast)
            .forEach(this::withSource);

        descriptor.getProcessors().stream()
            .map(
                d -> {
                  // If settings haven't been provided, try to create some default settings
                  if (d.getSettings() == null) {
                    SettingsClass s = d.getClass().getAnnotation(SettingsClass.class);
                    if (s != null && s.value() != NoSettings.class) {
                      try {
                        Settings settings = s.value().getConstructor().newInstance();
                        d.setSettings(settings);
                      } catch (NoSuchMethodException nsme) {
                        LOGGER.warn(
                            "Could not create default settings - {} does not have a no-args constructor",
                            s.value().getName());
                      } catch (Exception e) {
                        LOGGER.warn(
                            "Could not instantiate default settings {}", s.value().getName(), e);
                      }
                    }
                  }

                  // Convert from ProcessorDescriptor to Processor
                  return d.create(pipelineContext);
                })
            .map(Processor.class::cast)
            .forEach(this::withProcessor);

        if (name == null) {
          name = descriptor.getName();
        }

        if (description == null) {
          description = descriptor.getDescription();
        }
      }

      // Check that the pipeline is valid

      if (sources.isEmpty()) {
        throw new IncompleteException("Pipeline requires at least one source");
      }

      if (processors.isEmpty()) {
        throw new IncompleteException("Pipeline requires at least one processor");
      }

      return new SimplePipeline(pipelineContext, name, description, sources, processors);
    }
  }
}
