# Universal Print Studio — Task Register

## Goal
Native Android universal printing app: paper selection (standard + custom), live canvas, preview, USB/BT/Network/System printing.

## Phase 1 (scaffold) — DONE
- [x] Gradle project (Kotlin + Compose + Material3 + Hilt + Room + DataStore)
- [x] Domain models: PaperSize, PaperDimensions, Document, Layer, Printer, PrintJob
- [x] PaperCatalog (ISO, NA, photo, label, receipt)
- [x] Room DB + DAOs + mappers + repository impls
- [x] Printer connectors: USB, Bluetooth, Network (API-level; hardware verification pending)
- [x] ESC/POS builder (basic)
- [x] Document renderer (bitmap)
- [x] Compose screens: Home, Papers, Canvas, Preview, Printers, Settings
- [x] Navigation
- [x] System print via PrintHelper
- [x] Unit test: PaperCatalog
- [x] GitHub Actions build workflow

## Phase 2 (pending)
- [ ] PDF export of rendered document
- [ ] Image layer loading (Coil) on canvas
- [ ] Drag/resize layers on canvas (touch gestures)
- [ ] Print job history persistence
- [ ] Printer capability detection + printable area
- [ ] ESC/POS raster image printing
- [ ] Bluetooth permission runtime flow
- [ ] USB permission request flow
- [ ] Integration tests
- [ ] Hardware verification on real printers

## Known limitations
- Printer connectors use Android APIs but are untested on real hardware.
- Canvas is view-only (no drag/resize yet).
- No PDF export yet.
- Network printer send requires prior connect() to cache address.
