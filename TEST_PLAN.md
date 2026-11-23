# Test Plan — Universal Print Studio

- Unit tests: PaperDimensions conversions, PaperCatalog integrity, EscPosBuilder bytes.
- Instrumentation: Room DAO, navigation smoke.
- Device/hardware: USB/BT/network printer flows require real hardware; not verifiable in CI.
- CI: GitHub Actions ubuntu-latest builds debug APK.
