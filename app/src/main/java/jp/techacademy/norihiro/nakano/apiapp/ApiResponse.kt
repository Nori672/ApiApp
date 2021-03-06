package jp.techacademy.norihiro.nakano.apiapp
import com.google.gson.annotations.SerializedName

//API通信によって取得されたJsonデータを格納したクラス（JSON To Kotlin Classというプラグインでkotlinクラスに変換）
data class ApiResponse(
    @SerializedName("results")
    val results: Results
)

data class Results(
    @SerializedName("shop")
    val shop: List<Shop>
)

data class Shop(
    @SerializedName("coupon_urls")
    val couponUrls: CouponUrls,

    @SerializedName("id")
    val id: String,

    @SerializedName("logo_image")
    val logoImage: String,

    @SerializedName("name")
    val name: String

)

data class CouponUrls(
    @SerializedName("pc")
    val pc: String,
    @SerializedName("sp")
    val sp: String
)

