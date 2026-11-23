# Architecture — Universal Print Studio

Stack: Kotlin, Jetpack Compose, Material 3, Hilt, Room, DataStore, Coroutines/StateFlow.

Modules: single :app module (modularization deferred until needed).

Layers:
- domain/model: PaperSize, PaperDimensions, PaperCatalog, Printer, Document, DocumentLayer
- domain/repository: interfaces
- data/db: Room entities/DAOs/database
- data/repository: implementations
- data/mapper: entity<->domain
- data/di: Hilt modules
- ui/screens: Compose screens + ViewModels
- ui/navigation: NavHost
- ui/theme: Material3 theme
- printer/*: transport connectors + ESC/POS builder
- render/canvas: DocumentRenderer

Data flow: UI -> ViewModel -> Repository -> DAO -> Room. StateFlow for UI state.
