/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.logging;

import io.annot8.api.components.Resource;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Logging */
public final class Logging implements Resource {

  private final ILoggerFactory factory;

  protected Logging() {
    this(null);
  }

  protected Logging(ILoggerFactory iLoggerFactory) {
    this.factory = iLoggerFactory;
  }

  public static Logging useLoggerFactory() {
    return new Logging();
  }

  public static Logging useILoggerFactory(ILoggerFactory iLoggerFactory) {
    return new Logging(iLoggerFactory);
  }

  public Logger getLogger(Class<?> clazz) {
    return getLogger(clazz.getName());
  }

  public Logger getLogger(String name) {
    if (factory == null) {
      return LoggerFactory.getLogger(name);
    } else {
      return factory.getLogger(name);
    }
  }
}
