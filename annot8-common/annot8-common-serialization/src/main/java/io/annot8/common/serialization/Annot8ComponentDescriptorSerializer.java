/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.serialization;

import io.annot8.api.components.Annot8ComponentDescriptor;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/** Serialize Annot8ComponentDescriptor from JSON, using the JSON-B serializer interface */
public class Annot8ComponentDescriptorSerializer
    implements JsonbSerializer<Annot8ComponentDescriptor> {
  @Override
  public void serialize(
      Annot8ComponentDescriptor descriptor, JsonGenerator generator, SerializationContext ctx) {

    generator.writeStartObject();
    generator.writeStartObject(descriptor.getClass().getName());
    generator.write("name", descriptor.getName());
    ctx.serialize("settings", descriptor.getSettings(), generator);
    // TODO: Use reflection to loop through properties rather than hard code
    generator.writeEnd();
    generator.writeEnd();
  }
}
