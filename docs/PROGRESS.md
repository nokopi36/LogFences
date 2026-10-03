# 進捗

最終更新: 2026-10-03

## 現在地
- 実装中: なし
- 次にやること: フェーズ 7（公開準備）。残り: アイコン（400×400、未定）、まっさらな環境での jar の起動確認、CurseForge へのアップロード

## 完了
| 日付 | 機能 | コミット | メモ |
|---|---|---|---|
| 2026-10-03 | プロジェクト作成（MDK 26.3 から） | 初回コミット | `./gradlew build` 成功。Gradle Wrapper の jar を 9.2.1 に更新し、公式チェックサムで検証済み |
| 2026-10-03 | 1. 共通基盤 | (このコミット) | DeferredRegister（ModBlocks/ModItems）、datagen 入口（GatherDataEvent.Client）、言語プロバイダー（en_us/ja_jp）。runData 成功、MOD 一覧に表示を確認 |
| 2026-10-03 | 2. オークの原木のフェンス（剥ぎなし） | (このコミット) | 見た目・繋がり・レシピ（12 個）・ドロップ・燃料・専用タブ + 建築ブロックタブをゲーム内で確認 |
| 2026-10-03 | 3. フェンスの部位ごとの樹皮剥ぎ | (このコミット) | useItemOn 上書き + クリック位置で部位判定。GameTest 2 件（部位判定・剥ぎ）成功。ゲーム内で 8 通り・再読み込みを確認 |
| 2026-10-03 | 4. オークの原木のフェンスゲート | (このコミット) | 内側の縦木は柱扱い、開いているときは斧でも開閉のみ。剥ぎ処理を BarkStripping に共通化。GameTest 計 4 件成功。ゲーム内で開閉・塀付き・4 方向を確認。**MVP 完了** |
| 2026-10-03 | 調整: ゲートの剥ぎ部位を 4 部位に | 内側の縦木を独立（GatePart / StrippablePart）。GameTest 5 件成功、ゲーム内で 16 通りを確認 |
| 2026-10-03 | 6. 全種類の木への展開（12 種） | (このコミット) | LogWood enum で登録・datagen・タブをループ化。ネザーの木は不燃・燃料なし・non_flammable_wood。GameTest 6 件成功、ゲーム内で見た目・音・名前・燃焼を確認 |
| 2026-10-03 | 7. 樹皮を剥いだ版のクラフト | (このコミット) | 別アイテム（StrippedLogFenceItem、ブロック共通）。全部剥がれていれば剥いだ版をドロップ・ピックブロック。両タブに追加。GameTest 7 件成功、ゲーム内で確認 |
| 2026-10-03 | 3.7 竹ブロックのフェンス | (このコミット) | LogWood に竹を追加（表皮・板材 2 枚）。レシピ個数を planksPerLog から計算（竹 6・2）。GameTest 8 件成功、ゲーム内で確認 |

## 保留・課題
- `mcsrc/` に NeoForge 本体のソース（gradle キャッシュの `neoforge-*-sources.jar`）も展開済み。NeoForge 更新時は両方展開し直す
- GameTest 用ファイル（`data/log_fences/structure/test_area.nbt`・`test_instance/`）が配布 jar に入る。フェーズ 7 で除外するか検討
- NeoForge 26.3 はベータ版。正式版が出たら `neo_version` を上げて追従する

## 公開準備（フェーズ 7）
- 1.0.0 を Beta で公開（NeoForge 26.3 がベータのため）。jar 名 `logfences-neoforge-26.3-1.0.0.jar`
- mods.toml: 説明文更新、displayURL / issueTrackerURL（GitHub）。logoFile はアイコン決定後
- GitHub リポジトリは公開準備が整うまで private（2026-10-04 に切り替え）。**CurseForge で公開する前に public に戻す**（mods.toml と説明文の GitHub リンクが 404 になるため）
- GameTest 用ファイルは jar に含めたまま（バニラと同じ形。/test を使わなければ影響なし）
- CurseForge の説明文: `docs/curseforge/description.md`（英語 → 日本語）、変更履歴: `CHANGELOG.md`
- CurseForge 規約（2026-10-04 確認）: AI はショーケース画像の誤認防止のみ規定、コードの規定なし。アイコン 400×400・単色不可・著作物不可・WebP 不可

## 他 MOD との互換性
- Diagonal Fences 26.3.0（+ Puzzles Lib 26.3.9）: 一緒に入れても動くが、Log Fences のフェンスは斜めにならない（状態の項目数が違うため対象外）。GameTest も成功。連携は SPEC 3.6「いつか」に記録

## 遊んでみた感想・調整したいこと
- テクスチャ（横向きの木目・柱上面の年輪）は好評
- ゲートはフェンスより細かく剥げてよい → 4 部位に変更済み
- 横木だけ剥ぐと「キ」の形が目立つ（フェンスの形そのもの。色の差で強調される）→ 形の変更案（上の横木を柱の上に載せる／柱の手前に打ち付ける）を検討し、バニラと同じ今の形を維持すると決定
