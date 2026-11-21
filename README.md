# Laper Gods - Divine Contracts (Phase 1: fondasi)

Minecraft Java 26.3 - Fabric - Java 25. Dewa pertama: Thor.

## Status
Phase 1: build GitHub Actions sukses (dilaporkan user). Belum diuji di dalam game.
Build lewat GitHub Actions (tab Actions -> artifact `Artifacts` -> `build/libs/`).

## Isi Phase 1
- Toolchain resmi dari fabric-example-mod branch 26.3 (Loom 1.19-SNAPSHOT, Loader 0.20.1, Fabric API 0.166.2+26.3, Gradle 9.8.2).
- Patung Thor: blok + item terdaftar, TANPA crafting, tanpa tab kreatif, loot table kosong (tidak drop).
  Didapat hanya lewat: `/give @p lapergods:thor_statue` (pick-block kreatif juga diblokir lewat `getCloneItemStack`). Model/tekstur masih PLACEHOLDER (kubus) sampai Phase 2-3.
- `config/lapergods.json` dibuat otomatis (opsi kamera & aksesibilitas).
- Model data kamera sinematik (client, tanpa dependensi Minecraft): `CameraSequence`, `CameraKeyframe`, `CameraPath`, `CinematicEvent`, 8 sequence A-H dari analisis video.
- `tools/dev/CameraSelfTest.java`: tes mandiri data kamera, dijalankan CI sebelum Gradle.

## Belum ada
Controller kamera yang menggerakkan kamera game, UI, kontrak, senjata, Faith, skill, shader, model Blender, audio.

## Tanpa Blockbench
Seluruh model akan dibuat dengan Blender (Phase 2).
