/**
 * This module contains common functionality for the Annot8 framework, including helper and utility
 * functions, abstract classes, factory objects, and common implementations of some components
 * (notably Bounds).
 *
 * <p>It does not contain default implementations of the majority of components, which are held in
 * the default-impl module.
 *
 * <p>The abstract classes in this module are there to provide correct implementations of functions
 * such as equals, hashCode and toString. They do not provide any logic beyond this, and should
 * generally be used by any implementations of the interfaces they are abstracting.
 */
open module io.annot8.implementations.support {
  requires transitive io.annot8.api;

  exports io.annot8.implementations.support.annotations;
  exports io.annot8.implementations.support.factories;
  exports io.annot8.implementations.support.references;
  exports io.annot8.implementations.support.registries;
  exports io.annot8.implementations.support.stores;
  exports io.annot8.implementations.support.properties;
  exports io.annot8.implementations.support.content;
  exports io.annot8.implementations.support.delegates;
  exports io.annot8.implementations.support.listeners;
  exports io.annot8.implementations.support.context;
}
