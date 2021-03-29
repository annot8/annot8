/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

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
