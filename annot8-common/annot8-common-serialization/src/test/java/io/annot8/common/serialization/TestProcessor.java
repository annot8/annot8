/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.serialization;

import io.annot8.api.components.Processor;
import io.annot8.api.components.responses.ProcessorResponse;
import io.annot8.api.data.Item;

public class TestProcessor implements Processor {

  public TestProcessor(TestSettings settings) {
    // E.g. connect to host and port
  }

  @Override
  public ProcessorResponse process(Item item) {
    return ProcessorResponse.ok();
  }
}
