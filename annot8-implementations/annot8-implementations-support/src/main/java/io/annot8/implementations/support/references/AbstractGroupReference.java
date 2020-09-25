/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.references;

import io.annot8.api.references.GroupReference;
import java.util.Objects;

/**
 * Abstract implementation of GroupReference, providing correct implementations of equals, hashCode
 * and toString.
 *
 * <p>Two group references are taken to be equal if the following properties are all equal. The
 * actual implementation of the group reference is seen to be irrelevant and not checked.
 *
 * <ul>
 *   <li>groupId
 * </ul>
 */
public abstract class AbstractGroupReference implements GroupReference {

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (!GroupReference.class.isAssignableFrom(other.getClass())) {
      return false;
    }

    GroupReference that = (GroupReference) other;
    return Objects.equals(getGroupId(), that.getGroupId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getGroupId());
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [groupId=" + getGroupId() + "]";
  }
}
