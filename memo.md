TODO·疑問:
* Runtimeシステムの理解をもう少し深める
  - [ ] (OSスレッドに対する)fiberのスケジューリング
  - [x] `Runtime` objectがどこから生成されうか？
  - [x] スレッドプールの設定
  - [ ] userが書いたIOのチェーンを、どういう形でasync programにしているの？？
  - [ ] IOFiber#async の実装
    - [ ] IO.async { ... } で囲った処理って別のスレッドで実行されうるみたいなイメージあったけど、実際にそれってどこで行われてる？
* 過去issueを見て、それに対して行われた修正とかをみる
  * IOFiberの大規模リファクタ
    * https://github.com/typelevel/cats-effect/pull/1609
