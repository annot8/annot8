/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.annotations;

import io.annot8.api.annotations.Group;
import io.annot8.api.properties.Properties;
import io.annot8.api.references.AnnotationReference;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Abstract implementation of Group, providing correct implementations of equals, hashCode and
 * toString.
 *
 * <p>Two groups are taken to be equal if the following properties are all equal. The actual
 * implementation of the group is seen to be irrelevant and not checked.
 *
 * <ul>
 *   <li>id
 *   <li>type
 *   <li>properties
 *   <li>annotationReferences
 * </ul>
 */
public abstract class AbstractGroup implements Group {

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (!(other instanceof Group)) {
      return false;
    }

    Group g = (Group) other;

    // First check "easy properties" so we can fail fast
    if (!(Objects.equals(getId(), g.getId())
        && Objects.equals(getType(), g.getType())
        && Properties.equals(this.getProperties(), g.getProperties()))) {
      return false;
    }

    // Now check references, which is expensive
    if (!getReferences().keySet().equals(g.getReferences().keySet())) {
      return false;
    }

    for (String key : getReferences().keySet()) {
      Iterator<AnnotationReference> ourIter =
          getReferences().getOrDefault(key, Stream.empty()).sorted().iterator();
      Iterator<AnnotationReference> otherIter =
          g.getReferences().getOrDefault(key, Stream.empty()).sorted().iterator();

      while (ourIter.hasNext()) {
        if (!otherIter.hasNext()) {
          return false;
        }

        if (!ourIter.next().equals(otherIter.next())) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getType(), Properties.hashCode(getProperties()), getReferences());
  }

  @Override
  public String toString() {
    return this.getClass().getName()
        + " [id="
        + getId()
        + ", type="
        + getType()
        + ", properties="
        + getProperties()
        + ", annotations="
        + getAnnotations()
        + "]";
  }
}
