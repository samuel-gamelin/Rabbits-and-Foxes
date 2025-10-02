# Remove Java Source Files from JAR Distribution

## Context and Problem Statement

The current Maven build configuration (pom.xml lines 101-106) includes all Java source files (`**/*.java`) as resources in the compiled classes directory, which means they are bundled into the final JAR distribution. This practice:
- Unnecessarily increases the JAR file size by approximately 1.2MB
- Exposes source code in the distribution artifact
- Deviates from standard Java application packaging practices
- Serves no functional purpose for the end user

Should we continue bundling source files in the JAR, or should we remove them from the distribution?

## Decision Drivers

- Minimize JAR file size for easier distribution
- Follow Java application packaging best practices
- Protect intellectual property by not exposing source in binaries
- Maintain clean separation between source and compiled artifacts
- Reduce resource usage during builds
- Ensure no functional impact on application behavior

## Considered Options

- Remove source file inclusion from pom.xml resources
- Keep source files in JAR
- Create separate source JAR using maven-source-plugin
- Include only select source files (e.g., documentation)

## Decision Outcome

Chosen option: "Remove source file inclusion from pom.xml resources", because it follows standard Java packaging practices, reduces JAR size without any negative impact on functionality, and maintains proper separation between source and distribution artifacts.

### Consequences

- Good, because JAR file size is reduced (source files ~1.2MB removed)
- Good, because follows standard Java application packaging conventions
- Good, because source code is not exposed in distribution artifacts
- Good, because build process is slightly faster (fewer resources to copy)
- Good, because aligns with how other Java applications are packaged
- Neutral, because source is still available in version control for developers
- Bad, because requires rebuilding existing JAR artifacts

### Confirmation

Implementation compliance confirmed by:
- JAR file no longer contains .java files: `jar tf target/Rabbits-and-Foxes.jar | grep -E "\.java$"` returns empty
- All 40 unit tests pass after the change
- Application runs correctly from the JAR
- Build logs show only 20 resources copied from src/main/resources (not source files)

## Pros and Cons of the Options

### Remove source file inclusion from pom.xml resources

Remove the resource configuration that includes `src/main/java/**/*.java`.

- Good, because reduces JAR size by ~1.2MB
- Good, because follows Java packaging best practices
- Good, because no source code in distribution
- Good, because simpler build configuration
- Good, because faster resource copying phase
- Neutral, because source remains available in Git
- Bad, because changes existing build output

### Keep source files in JAR

Continue including source files as resources in the JAR.

- Good, because no changes required
- Good, because maintains current behavior
- Bad, because wastes disk space in distribution
- Bad, because exposes source code unnecessarily
- Bad, because non-standard practice
- Bad, because larger downloads for users
- Bad, because confuses JAR contents (source + compiled classes)

### Create separate source JAR using maven-source-plugin

Use Maven's source plugin to create a separate `-sources.jar` artifact.

- Good, because provides source for developers who need it
- Good, because keeps distribution JAR clean
- Good, because follows Maven conventions
- Neutral, because creates additional artifact
- Bad, because more complex than needed for this use case
- Bad, because source JAR typically used for library publishing, not end-user applications

### Include only select source files

Include only certain source files (e.g., license headers, documentation classes).

- Good, because provides some documentation
- Bad, because arbitrary selection is non-standard
- Bad, because partial source code is confusing
- Bad, because still increases JAR size unnecessarily
- Bad, because more complex configuration

## More Information

- Modified file: `pom.xml` (removed lines 101-106)
- Previous configuration included: `<directory>src/main/java</directory>` with `<include>**/*.java</include>`
- New configuration: Only includes `src/main/resources` (images, sounds, levels, etc.)
- Build verification: JAR built successfully at 4.3MB (without source files)
- Test verification: All 40 tests pass
- Runtime verification: Application runs correctly from JAR
- Related to build optimization efforts for cleaner release artifacts
