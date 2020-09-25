/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.capabilities;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.capabilities.Capabilities;
import io.annot8.api.capabilities.Capability;
import io.annot8.api.data.Content;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class SimpleCapabilities implements Capabilities {

  private final Collection<Capability> creates;
  private final Collection<Capability> processes;
  private final Collection<Capability> deletes;

  @JsonbCreator
  public SimpleCapabilities(
      @JsonbProperty("creates") Collection<Capability> creates,
      @JsonbProperty("processes") Collection<Capability> processes,
      @JsonbProperty("deletes") Collection<Capability> deletes) {
    this.creates = creates;
    this.processes = processes;
    this.deletes = deletes;
  }

  @Override
  public Stream<Capability> creates() {
    return creates.stream();
  }

  @Override
  public Stream<Capability> processes() {
    return processes.stream();
  }

  @Override
  public Stream<Capability> deletes() {
    return deletes.stream();
  }

  public static class Builder {
    private List<Capability> creates = new ArrayList<>();
    private List<Capability> processes = new ArrayList<>();
    private List<Capability> deletes = new ArrayList<>();

    public Builder from(Capabilities capabilities) {
      capabilities.creates().forEach(creates::add);
      capabilities.processes().forEach(processes::add);
      capabilities.deletes().forEach(deletes::add);
      return this;
    }

    public Builder withCreates(Capability capability) {
      creates.add(capability);
      return this;
    }

    public Builder withoutCreates(Capability capability) {
      creates.remove(capability);
      return this;
    }

    public Builder withCreatesAnnotations(String type, Class<? extends Bounds> bounds) {
      return withCreates(new SimpleAnnotationCapability(type, bounds));
    }

    public Builder withoutCreatesAnnotations(String type, Class<? extends Bounds> bounds) {
      return withoutCreates(new SimpleAnnotationCapability(type, bounds));
    }

    public Builder withCreatesContent(Class<? extends Content> type) {
      return withCreates(new SimpleContentCapability(type));
    }

    public Builder withoutCreatesContent(Class<? extends Content> type) {
      return withoutCreates(new SimpleContentCapability(type));
    }

    public Builder withCreatesGroups(String type) {
      return withCreates(new SimpleGroupCapability(type));
    }

    public Builder withoutCreatesGroups(String type) {
      return withoutCreates(new SimpleGroupCapability(type));
    }

    public Builder withProcesses(Capability capability) {
      processes.add(capability);
      return this;
    }

    public Builder withoutProcesses(Capability capability) {
      processes.remove(capability);
      return this;
    }

    public Builder withProcessesAnnotations(String type, Class<? extends Bounds> bounds) {
      return withProcesses(new SimpleAnnotationCapability(type, bounds));
    }

    public Builder withoutProcessesAnnotations(String type, Class<? extends Bounds> bounds) {
      return withoutProcesses(new SimpleAnnotationCapability(type, bounds));
    }

    public Builder withProcessesContent(Class<? extends Content> type) {
      return withProcesses(new SimpleContentCapability(type));
    }

    public Builder withoutProcessesContent(Class<? extends Content> type) {
      return withoutProcesses(new SimpleContentCapability(type));
    }

    public Builder withProcessesGroups(String type) {
      return withProcesses(new SimpleGroupCapability(type));
    }

    public Builder withoutProcessesGroups(String type) {
      return withoutProcesses(new SimpleGroupCapability(type));
    }

    public Builder withDeletes(Capability capability) {
      deletes.add(capability);
      return this;
    }

    public Builder withoutDeletes(Capability capability) {
      deletes.remove(capability);
      return this;
    }

    public Builder withDeletesAnnotations(String type, Class<? extends Bounds> bounds) {
      return withDeletes(new SimpleAnnotationCapability(type, bounds));
    }

    public Builder withoutDeletesAnnotations(String type, Class<? extends Bounds> bounds) {
      return withoutDeletes(new SimpleAnnotationCapability(type, bounds));
    }

    public Builder withDeletesContent(Class<? extends Content> type) {
      return withDeletes(new SimpleContentCapability(type));
    }

    public Builder withoutDeletesContent(Class<? extends Content> type) {
      return withoutDeletes(new SimpleContentCapability(type));
    }

    public Builder withDeletesGroups(String type) {
      return withDeletes(new SimpleGroupCapability(type));
    }

    public Builder withoutDeletesGroups(String type) {
      return withoutDeletes(new SimpleGroupCapability(type));
    }

    public SimpleCapabilities build() {
      return new SimpleCapabilities(creates, processes, deletes);
    }
  }
}
