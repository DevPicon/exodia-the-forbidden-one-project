# Project Governance

## Product definition

**Jetpack Compose Custom Drawing Samples** is an incremental Android sample gallery for experimenting with, preserving, and reusing custom drawing, animation, gesture, and visualization techniques built with Jetpack Compose.

The project is a technical playground and reference application. It is not an end-user drawing product.

## Product boundaries

### In scope

- Independent Jetpack Compose drawing and visualization samples.
- Canvas, DrawScope, graphics layers, gestures, animation, and custom layout experiments.
- Small interactive controls that help demonstrate a technique.
- A navigable catalog that makes every supported sample discoverable.
- Reusable composables with explicit inputs and content slots where appropriate.
- English and Spanish user-facing copy.
- Focused previews, tests, and technical notes for each sample.

### Out of scope by default

- User accounts or authentication.
- Cloud synchronization or collaborative editing.
- Persistence unless a sample specifically demonstrates persistence.
- A general-purpose drawing editor.
- Production business rules, monetization, or prize generation.
- Architecture added only in anticipation of hypothetical product growth.

An out-of-scope capability requires an explicit story in the project ledger before implementation.

## Source of truth

[`docs/PROJECT_PLAN.md`](docs/PROJECT_PLAN.md) is the single source of truth for stories, tasks, priorities, status, acceptance criteria, test cases, and completion evidence.

Do not maintain parallel task lists in the README, issues, comments, or additional planning documents. Those surfaces may link to the project plan but must not redefine status.

## Sample contract

Every new sample must provide:

1. A stable identifier in the project ledger.
2. A discoverable entry from the app catalog.
3. An independent demonstration screen.
4. A reusable composable with clear parameters.
5. Controls when they materially help explore the technique.
6. At least one deterministic preview.
7. English and Spanish user-facing strings.
8. Focused tests for the sample's primary behavior.
9. A short explanation of the technique and important rendering constraints.
10. No inert buttons, unreachable implementations, or undocumented behavior.

## Definition of done

A story that changes code is done only when all of the following are true:

- Its acceptance criteria are satisfied.
- Relevant automated checks pass.
- Device or emulator behavior is verified when the story depends on gestures, rendering, lifecycle, or platform behavior.
- Documentation matches the delivered behavior.
- The implementation has been committed to Git.
- The project ledger records `COMPLETED` and includes the implementation commit hash as evidence.

Passing checks or having code in the working tree is not completion.

## Status workflow

Allowed ledger statuses are:

- `BACKLOG`: defined but not yet selected.
- `READY`: scoped and ready to implement.
- `IN_PROGRESS`: implementation or verification is underway, including uncommitted code.
- `BLOCKED`: progress requires an external decision or unavailable dependency.
- `COMPLETED`: acceptance is satisfied and committed evidence exists.

Status transition rules:

1. Move a selected story to `IN_PROGRESS` before changing production code.
2. Keep it `IN_PROGRESS` while code is uncommitted, even if checks pass.
3. Commit the implementation and verification changes.
4. Update the ledger to `COMPLETED` with the implementation commit hash.
5. Commit the ledger update.

If a regression invalidates acceptance criteria, reopen the story as `READY` or create a corrective story linked to it.

## Engineering principles

- Keep each sample isolated and understandable without reading unrelated samples.
- Prefer simple architecture appropriate for a sample gallery.
- Keep state ownership explicit and lifecycle-safe when state affects behavior.
- Let callers control composable placement through `Modifier`.
- Use density-independent units for user-facing dimensions.
- Isolate destructive blend modes and advanced rendering operations to the intended layer.
- Validate inputs for reusable components or document their preconditions.
- Avoid unrelated refactors inside a sample story.

## UX and accessibility

- Samples must remain usable on compact screens, landscape, dark mode, and increased font scale when applicable.
- Canvas content must expose meaningful semantics when it communicates information or accepts interaction.
- Visible actions must work; placeholder actions must not ship as enabled controls.
- Hardcoded user-facing strings are not allowed.

## Verification policy

The default verification set is:

```shell
./gradlew testDebugUnitTest lintDebug assembleDebug
```

Also compile Android instrumented tests when they exist:

```shell
./gradlew compileDebugAndroidTestKotlin
```

Run `connectedDebugAndroidTest` when an authorized device or emulator is available and the story has Android UI tests. Record limitations when device execution is unavailable.

## Commit policy

- Each commit should represent one coherent story or a focused documentation/status transition.
- Commit messages describe the product or engineering change directly.
- Commit messages must not include implementation-tool attribution.
- Do not mix unrelated corrections or cleanup into a feature commit.
- A ledger completion update must reference the implementation commit it closes.

## Changing governance

Governance changes must be intentional, reviewed against the product definition, and committed separately from unrelated feature work. When governance and the project ledger disagree, governance defines the process and the ledger defines delivery status.
