/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.capabilities;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.capabilities.AnnotationCapability;
import java.util.Objects;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class SimpleAnnotationCapability implements AnnotationCapability {

  private final String type;
  private final Class<? extends Bounds> bounds;

  @JsonbCreator
  public SimpleAnnotationCapability(
      @JsonbProperty("type") String type, @JsonbProperty("bounds") Class<? extends Bounds> bounds) {
    this.type = type;
    this.bounds = bounds;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public Class<? extends Bounds> getBounds() {
    return bounds;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof AnnotationCapability)) return false;
    AnnotationCapability other = (AnnotationCapability) o;

    return Objects.equals(type, other.getType()) && Objects.equals(bounds, other.getBounds());
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, bounds);
  }
}
