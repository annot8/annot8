/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import io.annot8.api.components.ProcessorDescriptor;
import io.annot8.api.components.SourceDescriptor;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.pipelines.NoOpOrderer;
import io.annot8.api.pipelines.PipelineDescriptor;
import io.annot8.api.pipelines.PipelineOrderer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimplePipelineDescriptor implements PipelineDescriptor {
  private final String name;
  private final String description;

  private final Collection<SourceDescriptor> sources;
  private final Collection<ProcessorDescriptor> processors;

  private SimplePipelineDescriptor(
      String name,
      String description,
      Collection<SourceDescriptor> sources,
      Collection<ProcessorDescriptor> processors) {
    this.name = name;
    this.description = description;
    this.sources = sources;
    this.processors = processors;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Collection<SourceDescriptor> getSources() {
    return sources;
  }

  @Override
  public Collection<ProcessorDescriptor> getProcessors() {
    return processors;
  }

  public static class Builder implements PipelineDescriptor.Builder {

    private String name;
    private String description;
    private List<SourceDescriptor> sources = new ArrayList<>();
    private List<ProcessorDescriptor> processors = new ArrayList<>();
    private PipelineOrderer orderer = NoOpOrderer.getInstance();

    @Override
    public Builder from(PipelineDescriptor pipelineDescriptor) {
      name = pipelineDescriptor.getName();
      description = pipelineDescriptor.getDescription();
      sources.addAll(pipelineDescriptor.getSources());
      processors.addAll(pipelineDescriptor.getProcessors());

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
    public PipelineDescriptor.Builder withOrderer(PipelineOrderer orderer) {
      this.orderer = orderer;
      return this;
    }

    @Override
    public Builder withSource(SourceDescriptor source) {
      sources.add(source);
      return this;
    }

    @Override
    public Builder withProcessor(ProcessorDescriptor processor) {
      processors.add(processor);
      return this;
    }

    @Override
    public SimplePipelineDescriptor build() throws IncompleteException {
      if (name == null || name.isEmpty()) {
        throw new IncompleteException("Pipeline must have a name");
      }

      if (sources.isEmpty()) {
        throw new IncompleteException("Pipeline requires at least one source");
      }

      if (processors.isEmpty()) {
        throw new IncompleteException("Pipeline requires at least one processor");
      }

      if (orderer == null) {
        throw new IncompleteException("Pipeline requires a PipelineOrderer");
      }

      Collection<SourceDescriptor> orderedSources = orderer.orderSources(sources);
      Collection<ProcessorDescriptor> orderedProcessors = orderer.orderProcessors(processors);

      return new SimplePipelineDescriptor(name, description, orderedSources, orderedProcessors);
    }
  }
}
