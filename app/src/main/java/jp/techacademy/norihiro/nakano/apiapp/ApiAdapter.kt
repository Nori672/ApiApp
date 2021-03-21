package jp.techacademy.norihiro.nakano.apiapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ApiAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 取得したJsonデータを解析し、Shop型オブジェクトとして生成したものを格納するリスト
    private val items = mutableListOf<Shop>()

    // 一覧画面から登録するときのコールバック（FavoriteFragmentへ通知するメソッド)
    var onClickAddFavorite: ((Shop) -> Unit)? = null
    // 一覧画面から削除するときのコールバック（ApiFragmentへ通知するメソッド)
    var onClickDeleteFavorite: ((Shop) -> Unit)? = null
    // Itemを押したときのメソッド
    var onClickItem: ((String) -> Unit)? = null


    fun refresh(list: List<Shop>) {
        update(list, false)
    }

    fun add(list: List<Shop>){
        update(list, true)
    }

    // ApiFragmentから表示リスト更新時に呼び出すメソッド
    fun update(list: List<Shop>, isAdd: Boolean){
        items.apply {
            if (!isAdd){
                clear() // items を 空にする
            }
            addAll(list) // itemsにlistを全て追加する
        }
        notifyDataSetChanged() // recyclerViewを再描画させる
    }

    //RecyclerViewで表示させる1行分のレイアウトを作成。（必要な行数分だけこのメソッドが使いまわされる）
    /*メソッドの戻り値の型がRecyclerView.ViewHolder型となっているので、
    RecyclerView.ViewHolder型（もしくは、RecyclerView.ViewHolderを継承したサブクラス型）オブジェクト生成が必要*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ApiItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_favorite, parent, false))
    }

    // ViewHolderを継承したApiItemViewHolderクラスの定義(idはrecycler_favorite.xmlのものを取得)
    class ApiItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        // レイアウトファイルからidがrootViewのConstraintLayoutオブジェクトを取得し、代入
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        // レイアウトファイルからidがnameTextViewのCTextViewオブジェクトを取得し、代入
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        // レイアウトファイルからidがimageViewのImageViewオブジェクトを取得し、代入
        val imageView: ImageView = view.findViewById(R.id.imageView)
        // レイアウトファイルからidがfavoriteImageViewのImageViewオブジェクトを取得し、代入
        val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)
    }

    override fun getItemCount(): Int {
        // itemsプロパティに格納されている要素数を返す
        return items.size
    }

    //RecycleViewで表示する1行分のデータを取得
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ApiItemViewHolder){
            // 生成されたViewHolderがApiItemViewHolderだったら。。。
            updateApiItemViewHolder(holder, position)
        }// {
        // ifの条件を増やせば別のViewHolderをバインドさせることが可能となる
        // }
    }

    private fun updateApiItemViewHolder(holder: ApiItemViewHolder, position: Int) {
        // 生成されたViewHolderの位置を指定し、オブジェクトを代入
        val data = items[position]

        // お気に入り状態を取得
        val isFavorite = FavoriteShop.findBy(data.id) != null

        holder.apply {
            rootView.apply {
                // 偶数番目と奇数番目で背景色を変更させる
                setBackgroundColor(ContextCompat.getColor(context,
                if (position % 2 == 0) android.R.color.white else android.R.color.darker_gray))
                setOnClickListener {
                    onClickItem?.invoke(if (data.couponUrls.sp.isNotEmpty()) data.couponUrls.sp else data.couponUrls.pc)
                }
            }
            // nameTextViewのtextプロパティに代入されたオブジェクトのnameプロパティを代入
            nameTextView.text = data.name
            // Picassoライブラリを使い、imageViewにdata.logoImageのurlの画像を読み込ませる
            Picasso.get().load(data.logoImage).into(imageView)
            // 白抜きの星マークの画像を指定
            favoriteImageView.apply {
                setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
                setOnClickListener {
                    if (isFavorite){
                        onClickDeleteFavorite?.invoke(data)
                    }else{
                        onClickAddFavorite?.invoke(data)
                    }
                    notifyItemChanged(position)
                }
            }
        }
    }
}