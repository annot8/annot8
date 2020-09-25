# Annot8

This is the main Annot8 repository, which contains an implementation of the [Annot8 API](https://github.com/annot8/annot8-api).

For components that can be used within the Annot8 framework, see the [Annot8 Components](https://github.com/annot8/annot8-components) repository.

## Versioning

The following approach is used to versioning within the Annot8 projects:

* The core Annot8 API is versioned as `major.minor` (e.g. `1.0`). 
  Any minor versions will be fully backwards compatible, but major versions may not be.
* Projects in this repository will be versioned as `major.project-version.patch` (e.g. `1.2.1`).
  The major version will match the API version used by the project, and will have their own project version followed by a patch level to indicate bug fixes.
  These projects may be developed and released at different rates, and therefore may have different project versions.

## Licence

Code in this repository is licenced under the [Apache Software Licence 2](https://www.apache.org/licenses/LICENSE-2.0).
See the NOTICE file for any additional restrictions.