# Architecture Decision Records

This directory contains Architecture Decision Records (ADRs) for the Rabbits-and-Foxes project.

## What is an ADR?

An Architecture Decision Record (ADR) captures an important architectural decision made along with its context and consequences. ADRs help teams understand why certain decisions were made and provide a historical record of the project's evolution.

## Format

We use [MADR (Markdown Architecture Decision Records)](https://adr.github.io/madr/) version 4.0.0 for documenting decisions. MADR provides a lean template that balances simplicity with completeness.

## Naming Convention

ADR files follow the naming pattern: `NNNN-title-with-dashes.md`

- `NNNN` - Four-digit sequential number (e.g., 0001, 0002)
- `title-with-dashes` - Short descriptive title in kebab-case

Examples:
- `0001-use-madr-for-decisions.md`
- `0002-remove-source-from-jar.md`

## Creating a New ADR

1. Copy `adr-template.md` to a new file with the next sequential number
2. Fill in the template sections:
   - Write a clear title representing the problem and solution
   - Describe the context and problem
   - List decision drivers
   - Document considered options with pros and cons
   - Record the decision outcome and consequences
3. Commit the ADR along with the implementation (if applicable)

## Index

- [ADR-0001](0001-use-madr-for-decisions.md) - Use MADR for Architecture Decisions
- [ADR-0002](0002-remove-source-from-jar.md) - Remove Java Source Files from JAR Distribution
- [ADR-0003](0003-migrate-to-gradle-build-system.md) - Migrate from Maven to Gradle Build System

## References

- [MADR Homepage](https://adr.github.io/madr/)
- [MADR GitHub Repository](https://github.com/adr/madr)
