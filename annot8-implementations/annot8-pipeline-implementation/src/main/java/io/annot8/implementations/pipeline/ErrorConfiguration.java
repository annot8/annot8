/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import java.util.Objects;

public class ErrorConfiguration {
  private OnSourceError onSourceError = OnSourceError.REMOVE_SOURCE;
  private OnProcessingError onItemError = OnProcessingError.DISCARD_ITEM;
  private OnProcessingError onProcessorError = OnProcessingError.REMOVE_PROCESSOR;

  public OnSourceError getOnSourceError() {
    return onSourceError;
  }

  public void setOnSourceError(OnSourceError onSourceError) {
    this.onSourceError = onSourceError;
  }

  public OnProcessingError getOnItemError() {
    return onItemError;
  }

  public void setOnItemError(OnProcessingError onItemError) {
    this.onItemError = onItemError;
  }

  public OnProcessingError getOnProcessorError() {
    return onProcessorError;
  }

  public void setOnProcessorError(OnProcessingError onProcessorError) {
    this.onProcessorError = onProcessorError;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorConfiguration that = (ErrorConfiguration) o;
    return onSourceError == that.onSourceError
        && onItemError == that.onItemError
        && onProcessorError == that.onProcessorError;
  }

  @Override
  public int hashCode() {
    return Objects.hash(onSourceError, onItemError, onProcessorError);
  }

  @Override
  public String toString() {
    return "ErrorConfiguration{"
        + "onSourceError="
        + onSourceError
        + ", onItemError="
        + onItemError
        + ", onProcessorError="
        + onProcessorError
        + '}';
  }

  public enum OnSourceError {
    REMOVE_SOURCE,
    IGNORE
  }

  public enum OnProcessingError {
    DISCARD_ITEM,
    REMOVE_PROCESSOR,
    IGNORE
  }
}
