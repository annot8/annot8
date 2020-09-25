/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.registries;

import io.annot8.api.components.*;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Annot8ComponentRegistry {

  private final Set<Class<? extends Annot8ComponentDescriptor>> classes;

  public Annot8ComponentRegistry(Set<Class<? extends Annot8ComponentDescriptor>> classes) {
    this.classes = Collections.unmodifiableSet(classes);
  }

  public Stream<Class<? extends SourceDescriptor>> getSources() {
    return classes.stream()
        .filter(SourceDescriptor.class::isAssignableFrom)
        .map(c -> c.asSubclass(SourceDescriptor.class));
  }

  public Stream<Class<? extends ProcessorDescriptor>> getProcessors() {
    return classes.stream()
        .filter(ProcessorDescriptor.class::isAssignableFrom)
        .map(c -> c.asSubclass(ProcessorDescriptor.class));
  }

  public Optional<Class<? extends SourceDescriptor>> getSource(String klass) {
    return getSources().filter(c -> c.getName().equals(klass)).findFirst();
  }

  public Optional<Class<? extends ProcessorDescriptor>> getProcessor(String klass) {
    return getProcessors().filter(c -> c.getName().equals(klass)).findFirst();
  }

  public <T extends Annot8ComponentDescriptor> Optional<Class<? extends T>> getComponent(
      String klass, Class<T> componentType) {
    return classes.stream()
        .filter(componentType::isAssignableFrom)
        .filter(c -> c.getName().equals(klass))
        .findFirst()
        .map(c -> c.asSubclass(componentType));
  }
}
