package jp.techacademy.norihiro.nakano.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity(),View.OnClickListener {

    private var favoriteShop: FavoriteShop = FavoriteShop()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString()) //webViewのloadUrlでwebページを読み込み

        favoriteShop = intent.getSerializableExtra(KEY_FAVORITE_SHOP) as? FavoriteShop ?: return{ finish() }()
        webView.loadUrl(favoriteShop.url)

        favoriteImageView.setOnClickListener(this)
    }

    override fun onClick(v: View) {

    }

    companion object{
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String){
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
        }

        private const val KEY_FAVORITE_SHOP = "key_favoriteShop"
        fun start2(activity: Activity, favorite: FavoriteShop){
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_FAVORITE_SHOP, favorite))
        }

        private const val VIEW_PAGER_POSITION_API = 0
        private const val VIEW_PAGER_POSITION_FAVORITE = 1
    }
}