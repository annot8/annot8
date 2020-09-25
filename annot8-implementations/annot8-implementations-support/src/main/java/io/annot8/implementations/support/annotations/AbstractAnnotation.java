/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.annotations;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.properties.Properties;
import java.util.Objects;

/**
 * Abstract implementation of Annotation, providing correct implementations of equals, hashCode and
 * toString.
 *
 * <p>Two annotations are taken to be equal if the following properties are all equal. The actual
 * implementation of the annotation is seen to be irrelevant and not checked.
 *
 * <ul>
 *   <li>id
 *   <li>type
 *   <li>properties
 *   <li>bounds
 *   <li>contentName
 * </ul>
 */
public abstract class AbstractAnnotation implements Annotation {

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (!(other instanceof Annotation)) {
      return false;
    }

    Annotation a = (Annotation) other;

    return Objects.equals(getId(), a.getId())
        && Objects.equals(getType(), a.getType())
        && Properties.equals(getProperties(), a.getProperties())
        && Objects.equals(getBounds(), a.getBounds())
        && Objects.equals(getContentId(), a.getContentId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(), getType(), Properties.hashCode(getProperties()), getBounds(), getContentId());
  }

  @Override
  public String toString() {
    return this.getClass().getName()
        + " [id="
        + getId()
        + ", type="
        + getType()
        + ", contentId="
        + getContentId()
        + ", bounds="
        + getBounds()
        + ", properties="
        + getProperties()
        + "]";
  }
}
