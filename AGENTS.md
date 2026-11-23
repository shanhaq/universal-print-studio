# Universal Print Studio — AGENTS.md

Native Android printing/paper-layout app. Package com.zyvo.universalprint. Kotlin + Jetpack Compose + Hilt + Room + DataStore.

## Build
- CI: GitHub Actions (ubuntu-latest) runs `gradle testDebugUnitTest` then `gradle assembleDebug`. Local build impossible on Termux (aapt2 x86_64).
- Repo: https://github.com/shanhaq/universal-print-studio (token in ~/.git-credentials)
- Workflow: .github/workflows/build.yml

## Key files
- Domain: domain/model/PaperSize.kt, PaperCatalog.kt, Printer.kt, Document.kt
- Data: data/db/, data/repository/, data/mapper/, data/di/
- UI: ui/screens/*, ui/navigation/Screen.kt, ui/theme/Theme.kt
- Printer: printer/usb, bluetooth, network, escpos/, core/
- Render: render/canvas/DocumentRenderer.kt
- Engine: engine/layout/LayoutEngine.kt

## Conventions
- Paper dimensions always in mm (PaperDimensions). Physical size != printable area.
- ESC/POS capabilities are profile-based, never assumed.
- Print status distinguishes sent/acknowledged/completed/failed/unknown.

## Phases done
01 (foundation), 02 (paper standards), 03 partial (validation), 05 (layout engine), 08 partial (ESC/POS), 18 partial (tests). See PROJECT_STATUS.md / FEATURE_MATRIX.md.
EOF
echo done