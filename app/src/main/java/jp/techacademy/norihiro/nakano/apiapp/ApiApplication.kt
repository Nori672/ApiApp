package jp.techacademy.norihiro.nakano.apiapp

import android.app.Application
import io.realm.Realm

//今回は、一覧画面とお気に入り画面など複数の画面（Fragment）を使用するため、共有のデータなどを扱うApplication クラスを継承したクラスを作成する
//お気に入りに追加した店舗のデータ保存にRealmを使うため、ApplicationクラスでRealmの初期化
class ApiApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}