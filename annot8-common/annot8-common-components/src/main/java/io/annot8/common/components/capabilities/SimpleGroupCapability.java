/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.capabilities;

import io.annot8.api.capabilities.GroupCapability;
import java.util.Objects;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class SimpleGroupCapability implements GroupCapability {
  private final String type;

  @JsonbCreator
  public SimpleGroupCapability(@JsonbProperty("type") String type) {
    this.type = type;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof GroupCapability)) return false;
    GroupCapability other = (GroupCapability) o;

    return Objects.equals(type, other.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
