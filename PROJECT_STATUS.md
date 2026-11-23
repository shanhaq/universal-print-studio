# Project Status — Universal Print Studio

## Current phase
Phase 01 (foundation) in progress. Scaffold created; missing UI screens added; build pending verification on GitHub Actions.

## Last updated
2026-11-23

## Completed
- Project scaffold (Kotlin, Compose, Hilt, Room, DataStore)
- Domain models: PaperSize, PaperCatalog, Printer, Document, DocumentLayer
- Data layer: Room entities/DAOs, repositories, mappers, DI modules
- Printer connectors: USB, Bluetooth, Network, ESC/POS builder (basic)
- DocumentRenderer (basic bitmap)
- ViewModels: Home, Papers, Canvas, Preview, Printers, Settings
- Screens: Home, Papers, Canvas, Preview, Printers, Settings
- Navigation wired

## Next
- Phase 02: expand paper standards database (ISO/JIS/ANSI/ARCH/US/regional/photo/envelope/label/card/thermal)
- Phase 03: Custom Paper Manager hardening
- Phase 04: WYSIWYG editor gestures
- Phase 05+: rendering, ESC/POS, profiles, queue, templates, QA, release

## Blockers
- Local Android build unavailable on Termux (aapt2 is x86_64). Verification via GitHub Actions.
