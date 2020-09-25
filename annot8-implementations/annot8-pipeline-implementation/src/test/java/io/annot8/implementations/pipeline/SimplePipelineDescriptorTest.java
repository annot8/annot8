/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.annot8.api.components.ProcessorDescriptor;
import io.annot8.api.components.SourceDescriptor;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.pipelines.PipelineDescriptor;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SimplePipelineDescriptorTest {

  private final String PIPELINE_NAME = "Test pipeline";
  private final String PIPELINE_DESCRIPTION = "Simple test pipeline";

  @Mock private SourceDescriptor testSource;
  @Mock private ProcessorDescriptor testProcessor1;
  @Mock private ProcessorDescriptor testProcessor2;

  @Test
  public void test() {
    PipelineDescriptor p =
        new SimplePipelineDescriptor.Builder()
            .withName(PIPELINE_NAME)
            .withDescription(PIPELINE_DESCRIPTION)
            .withSource(testSource)
            .withProcessor(testProcessor1)
            .withProcessor(testProcessor2)
            .build();

    assertEquals(PIPELINE_NAME, p.getName());
    assertEquals(PIPELINE_DESCRIPTION, p.getDescription());
    assertEquals(1, p.getSources().size());
    assertEquals(2, p.getProcessors().size());
  }

  @Test
  public void testNoName() {
    PipelineDescriptor.Builder pb =
        new SimplePipelineDescriptor.Builder()
            .withDescription(PIPELINE_DESCRIPTION)
            .withSource(testSource)
            .withProcessor(testProcessor1);

    assertThrows(IncompleteException.class, pb::build);
  }

  @Test
  public void testNoSource() {
    PipelineDescriptor.Builder pb =
        new SimplePipelineDescriptor.Builder()
            .withName(PIPELINE_NAME)
            .withDescription(PIPELINE_DESCRIPTION)
            .withProcessor(testProcessor1);

    assertThrows(IncompleteException.class, pb::build);
  }

  @Test
  public void testNoProcessor() {
    PipelineDescriptor.Builder pb =
        new SimplePipelineDescriptor.Builder()
            .withName(PIPELINE_NAME)
            .withDescription(PIPELINE_DESCRIPTION)
            .withSource(testSource);

    assertThrows(IncompleteException.class, pb::build);
  }

  @Test
  public void testFrom() {
    PipelineDescriptor p =
        new SimplePipelineDescriptor.Builder()
            .withName(PIPELINE_NAME)
            .withDescription(PIPELINE_DESCRIPTION)
            .withSource(testSource)
            .withProcessor(testProcessor1)
            .build();

    PipelineDescriptor p2 =
        new SimplePipelineDescriptor.Builder().from(p).withProcessor(testProcessor2).build();

    assertEquals(PIPELINE_NAME, p2.getName());
    assertEquals(PIPELINE_DESCRIPTION, p2.getDescription());
    assertEquals(1, p2.getSources().size());
    assertEquals(2, p2.getProcessors().size());
  }

  @Test
  public void testProcessorsArgs() {
    PipelineDescriptor p =
        new SimplePipelineDescriptor.Builder()
            .withName(PIPELINE_NAME)
            .withDescription(PIPELINE_DESCRIPTION)
            .withSource(testSource)
            .withProcessors(testProcessor1, testProcessor2)
            .build();

    assertEquals(PIPELINE_NAME, p.getName());
    assertEquals(PIPELINE_DESCRIPTION, p.getDescription());
    assertEquals(1, p.getSources().size());
    assertEquals(2, p.getProcessors().size());
  }

  @Test
  public void testProcessorsCollection() {
    PipelineDescriptor p =
        new SimplePipelineDescriptor.Builder()
            .withName(PIPELINE_NAME)
            .withDescription(PIPELINE_DESCRIPTION)
            .withSource(testSource)
            .withProcessors(Arrays.asList(testProcessor1, testProcessor2))
            .build();

    assertEquals(PIPELINE_NAME, p.getName());
    assertEquals(PIPELINE_DESCRIPTION, p.getDescription());
    assertEquals(1, p.getSources().size());
    assertEquals(2, p.getProcessors().size());
  }
}
