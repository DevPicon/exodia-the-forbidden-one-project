# User Stories

## DRAW-005 — Scratch card overlay sample

### Definition

As a developer learning Jetpack Compose custom drawing, I want to open a scratch-card sample where a touch gesture removes an opaque overlay so that I can understand how to reveal another composable underneath it.

### Acceptance criteria

- The home screen exposes a dedicated scratch-card sample.
- The sample places arbitrary composable content below an opaque Canvas overlay.
- Dragging a finger across the overlay clears the touched path and reveals the content below.
- Cleared paths use round caps and joins so consecutive pointer movements appear continuous.
- Clearing the overlay does not clear the composable below it.
- The sample can be restarted without leaving the screen.
- The screen title and user-facing copy are available in English and Spanish.

### Test cases

1. Open the sample from Home and verify that the scratch-card screen is displayed.
2. Drag across the overlay and verify that a scratch stroke is recorded.
3. Verify that the hidden prize content is part of the scratch-card content slot.
4. Reset the sample and verify that a fresh opaque overlay is created.
5. Build the debug APK and run unit tests and Android lint.

### Scope

Included:

- A reusable `ScratchCard` composable with a content slot.
- A demonstration screen with prize content and reset action.
- Home navigation, localized labels, preview, and focused Compose UI tests.

Excluded:

- Prize generation or business rules.
- Persistence across process death.
- Percentage-based automatic reveal.
- Changes to the existing drawing, chart, timer, or comparison samples.
