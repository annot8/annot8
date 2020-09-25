/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import java.util.Objects;

public class ColumnMetadata {

  private final String name;
  private final long size;

  public ColumnMetadata(String name, long size) {
    this.name = name;
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public long getSize() {
    return size;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (o == null) return false;
    if (!(o instanceof ColumnMetadata)) return false;
    ColumnMetadata other = (ColumnMetadata) o;
    return Objects.equals(name, other.getName()) && Objects.equals(size, other.getSize());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, size);
  }
}
