# 進捗

最終更新: 2026-10-03

## 現在地
- 実装中: なし
- 次にやること: 実装順 2「オークの原木の柵（剥ぎ機能なし）」

## 完了
| 日付 | 機能 | コミット | メモ |
|---|---|---|---|
| 2026-10-03 | プロジェクト作成（MDK 26.3 から） | 初回コミット | `./gradlew build` 成功。Gradle Wrapper の jar を 9.2.1 に更新し、公式チェックサムで検証済み |
| 2026-10-03 | 1. 共通基盤 | (このコミット) | DeferredRegister（ModBlocks/ModItems）、datagen 入口（GatherDataEvent.Client）、言語プロバイダー（en_us/ja_jp）。runData 成功、MOD 一覧に表示を確認 |

## 保留・課題
- `mcsrc/` に NeoForge 本体のソース（gradle キャッシュの `neoforge-*-sources.jar`）も展開済み。NeoForge 更新時は両方展開し直す
- NeoForge 26.3 はベータ版。正式版が出たら `neo_version` を上げて追従する

## 遊んでみた感想・調整したいこと
-
