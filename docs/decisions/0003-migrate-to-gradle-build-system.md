# Migrate from Maven to Gradle Build System

## Context and Problem Statement

The Rabbits-and-Foxes project currently uses Apache Maven as its build system. While Maven has served the project well, the team is looking to improve build performance, gain more flexibility in build configuration, and adopt more modern build tooling. The question is: should we continue with Maven or migrate to a different build system?

## Decision Drivers

- Build performance and incremental compilation capabilities
- Flexibility in build script customization
- Modern tooling and ecosystem support
- Developer experience and learning curve
- Compatibility with existing project structure and Java 25
- CI/CD integration and caching efficiency
- Community momentum and future-proofing
- Support for complex dependency management and multi-project builds

## Considered Options

- Continue with Apache Maven
- Migrate to Gradle with Groovy DSL
- Migrate to Gradle with Kotlin DSL
- Migrate to alternative build tools (Bazel, Mill, etc.)

## Decision Outcome

Chosen option: "Migrate to Gradle with Kotlin DSL", because it provides superior build performance through incremental compilation and build caching, offers more flexibility in build configuration, has strong IDE support, and uses Kotlin DSL for type-safe, modern build scripts that align with industry trends.

### Consequences

- Good, because Gradle's incremental compilation significantly reduces build times
- Good, because Gradle's build cache improves CI/CD performance
- Good, because Kotlin DSL provides type-safe, auto-completable build scripts
- Good, because Gradle has better support for complex build customization
- Good, because Gradle has strong community momentum and modern features
- Good, because Gradle wrapper ensures consistent build environment across developers
- Good, because IntelliJ IDEA and other IDEs have excellent Gradle integration
- Neutral, because team needs to learn Gradle concepts and Kotlin DSL syntax
- Neutral, because build file structure differs from Maven's pom.xml
- Bad, because requires migration effort and potential build script debugging
- Bad, because Dependabot PRs will need to update build.gradle.kts instead of pom.xml

### Confirmation

Compliance and success of this migration can be confirmed by:
- All 40 existing JUnit tests pass with Gradle
- Fat JAR (Rabbits-and-Foxes.jar) builds successfully with all dependencies
- Application runs correctly from the generated JAR
- JAR contains no source files (only compiled classes and resources)
- CI/CD pipeline passes with Gradle on all PRs
- Build performance is equal to or better than Maven
- All resources (images, sounds, levels, log4j2.xml) load correctly
- Lombok annotation processing works correctly

## Pros and Cons of the Options

### Continue with Apache Maven

Maintaining the current Maven-based build system.

- Good, because no migration effort required
- Good, because team is already familiar with Maven
- Good, because existing pom.xml is well-tested and working
- Good, because Dependabot already configured for Maven
- Neutral, because Maven is stable and widely used
- Bad, because Maven builds are generally slower than Gradle
- Bad, because Maven lacks incremental compilation
- Bad, because Maven XML configuration is verbose and less flexible
- Bad, because Maven has limited build caching capabilities
- Bad, because Maven's plugin ecosystem is less modern than Gradle's

### Migrate to Gradle with Groovy DSL

Using Gradle with traditional Groovy-based build scripts.

- Good, because Gradle provides fast incremental builds
- Good, because Gradle has superior build caching
- Good, because Gradle is more flexible than Maven
- Good, because Groovy DSL is widely documented
- Neutral, because Groovy is dynamically typed
- Bad, because Groovy DSL lacks IDE auto-completion and type safety
- Bad, because Groovy syntax can be error-prone without compile-time checks
- Bad, because Kotlin DSL is becoming the preferred standard

### Migrate to Gradle with Kotlin DSL

Using Gradle with Kotlin-based build scripts (build.gradle.kts).

- Good, because all benefits of Gradle (performance, caching, flexibility)
- Good, because Kotlin DSL provides type-safe, compile-checked build scripts
- Good, because excellent IDE support with auto-completion and refactoring
- Good, because Kotlin DSL is the future direction of Gradle
- Good, because Kotlin is already familiar to many Java developers
- Good, because better error messages and compile-time validation
- Good, because easier to maintain and refactor complex build logic
- Neutral, because slightly more verbose than Groovy DSL
- Neutral, because requires learning Kotlin DSL API
- Bad, because some older Gradle documentation uses Groovy examples

### Migrate to alternative build tools (Bazel, Mill, etc.)

Using other modern build tools like Bazel, Mill, or sbt.

- Good, because some tools offer extreme performance (Bazel)
- Good, because some tools offer innovative features
- Bad, because much steeper learning curve
- Bad, because smaller ecosystems and less community support
- Bad, because less tooling and IDE integration
- Bad, because may have compatibility issues with Java 25 or dependencies
- Bad, because excessive complexity for a project of this size
- Bad, because harder to onboard new developers

## More Information

### Migration Details

The migration from Maven to Gradle includes:

1. **Build Files**:
   - Replace `pom.xml` with `build.gradle.kts` (main build script)
   - Create `settings.gradle.kts` (project configuration)
   - Create `gradle.properties` (build properties)
   - Add Gradle Wrapper for version consistency

2. **Dependency Configuration**:
   - All Maven dependencies migrated to Gradle equivalents
   - Lombok configured with annotation processing
   - Test dependencies use `testImplementation` scope
   - Lombok uses `compileOnly` and `annotationProcessor` scopes

3. **Build Features**:
   - Shadow plugin for fat JAR creation (replaces maven-assembly-plugin)
   - JUnit 5 platform configuration (replaces maven-surefire-plugin)
   - Java 25 toolchain configuration
   - Resource copying from src/main/resources

4. **CI/CD**:
   - GitHub Actions workflow updated to use Gradle
   - Gradle cache configuration (replaces Maven cache)
   - Same test execution and build verification

5. **Documentation**:
   - CLAUDE.md updated with Gradle command equivalents
   - .gitignore updated for Gradle directories (.gradle/, build/)

### References

- [Gradle Official Documentation](https://docs.gradle.org/)
- [Gradle Kotlin DSL Primer](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Migrating from Maven to Gradle](https://docs.gradle.org/current/userguide/migrating_from_maven.html)
- [Shadow Plugin for Fat JARs](https://github.com/johnrengelman/shadow)
