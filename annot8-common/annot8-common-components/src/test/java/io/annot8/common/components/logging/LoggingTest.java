/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class LoggingTest {

  @Test
  public void testUseLoggerFactory() {
    Logger logger = Logging.useLoggerFactory().getLogger("test");
    assertNotNull(logger);
  }

  @Test
  public void testUseILoggerFactory() {
    ILoggerFactory factory = Mockito.mock(ILoggerFactory.class);
    Logger logger = Mockito.mock(Logger.class);
    when(factory.getLogger(Mockito.anyString())).thenReturn(logger);
    Logger createdLogger = Logging.useILoggerFactory(factory).getLogger("test");
    assertNotNull(logger);
    assertEquals(logger, createdLogger);
    Mockito.verify(factory, times(1)).getLogger(Mockito.anyString());
  }

  @Test
  public void testNotAvailable() {
    Logger logger = Logging.useILoggerFactory(null).getLogger("test");
    assertNotNull(logger);
  }

  @Test
  public void testGetLogger() {
    Logger logger = Logging.useLoggerFactory().getLogger(LoggingTest.class);
    assertNotNull(logger);
  }
}
