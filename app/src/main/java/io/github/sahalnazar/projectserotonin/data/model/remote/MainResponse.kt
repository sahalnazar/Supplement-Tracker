package io.github.sahalnazar.projectserotonin.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainResponse(
    @SerialName("api")
    val api: Api? = null,
    @SerialName("appVersion")
    val appVersion: String? = null,
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("errors")
    val errors: List<String?>? = null,
    @SerialName("etag")
    val etag: String? = null,
    @SerialName("httpMethod")
    val httpMethod: String? = null,
    @SerialName("lastModified")
    val lastModified: String? = null,
    @SerialName("statusCode")
    val statusCode: Int? = null,
    @SerialName("userId")
    val userId: String? = null
) {
    @Serializable
    data class Api(
        @SerialName("name")
        val name: String? = null,
        @SerialName("version")
        val version: String? = null
    )

    @Serializable
    data class Data(
        @SerialName("config")
        val config: Config? = null,
        @SerialName("consumptionDisabledText")
        val consumptionDisabledText: String? = null,
        @SerialName("enableConsumption")
        val enableConsumption: Boolean? = null,
        @SerialName("itemsToConsume")
        val itemsToConsume: List<ItemsToConsume?>? = null,
        @SerialName("status")
        val status: String? = null,
        @SerialName("statusInfo")
        val statusInfo: String? = null
    ) {
        @Serializable
        data class Config(
            @SerialName("dock")
            val dock: Boolean? = null,
            @SerialName("showVideo")
            val showVideo: Boolean? = null,
            @SerialName("videoUrl")
            val videoUrl: String? = null
        )

        @Serializable
        data class ItemsToConsume(
            @SerialName("code")
            val code: String? = null,
            @SerialName("completed")
            val completed: Boolean? = null,
            @SerialName("items")
            val items: List<Item?>? = null,
            @SerialName("regular")
            val regular: Boolean? = null,
            @SerialName("title")
            val title: String? = null
        ) {
            @Serializable
            data class Item(
                @SerialName("canPause")
                val canPause: Boolean? = null,
                @SerialName("canReorder")
                val canReorder: Boolean? = null,
                @SerialName("consumptions")
                val consumptions: List<String?>? = null,
                @SerialName("dataNotAvailable")
                val dataNotAvailable: Boolean? = null,
                @SerialName("fillIcon")
                val fillIcon: String? = null,
                @SerialName("fillPct")
                val fillPct: Int? = null,
                @SerialName("infoText")
                val infoText: String? = null,
                @SerialName("interactions")
                val interactions: List<Interaction?>? = null,
                @SerialName("latestConsumption")
                val latestConsumption: LatestConsumption? = null,
                @SerialName("maxServingSize")
                val maxServingSize: Int? = null,
                @SerialName("minServingSize")
                val minServingSize: Int? = null,
                @SerialName("noDataResponse")
                val noDataResponse: NoDataResponse? = null,
                @SerialName("product")
                val product: Product? = null,
                @SerialName("shippingInDays")
                val shippingInDays: String? = null,
                @SerialName("showInfoIcon")
                val showInfoIcon: Boolean? = null,
                @SerialName("tags")
                val tags: List<String?>? = null,
                @SerialName("todayConsumption")
                val todayConsumption: String? = null
            ) {
                @Serializable
                data class Interaction(
                    @SerialName("count")
                    val count: Int? = null,
                    @SerialName("icon")
                    val icon: String? = null,
                    @SerialName("text")
                    val text: String? = null,
                    @SerialName("type")
                    val type: String? = null
                )

                @Serializable
                data class LatestConsumption(
                    @SerialName("date")
                    val date: String? = null,
                    @SerialName("dosage")
                    val dosage: Int? = null,
                    @SerialName("text")
                    val text: String? = null,
                    @SerialName("time")
                    val time: String? = null,
                    @SerialName("timestamp")
                    val timestamp: String? = null,
                    @SerialName("type")
                    val type: String? = null,
                    @SerialName("unit")
                    val unit: String? = null
                )

                @Serializable
                data class NoDataResponse(
                    @SerialName("description")
                    val description: String? = null,
                    @SerialName("eta")
                    val eta: String? = null,
                    @SerialName("title")
                    val title: String? = null
                )

                @Serializable
                data class Product(
                    @SerialName("active")
                    val active: Boolean? = null,
                    @SerialName("brand")
                    val brand: String? = null,
                    @SerialName("consumed")
                    val consumed: Boolean? = null,
                    @SerialName("foundation")
                    val foundation: Boolean? = null,
                    @SerialName("id")
                    val id: String? = null,
                    @SerialName("image")
                    val image: String? = null,
                    @SerialName("name")
                    val name: String? = null,
                    @SerialName("productId")
                    val productId: String? = null,
                    @SerialName("regimen")
                    val regimen: Regimen? = null,
                    @SerialName("remaining")
                    val remaining: Double? = null,
                    @SerialName("status")
                    val status: String? = null
                ) {
                    @Serializable
                    data class Regimen(
                        @SerialName("canEdit")
                        val canEdit: Boolean? = null,
                        @SerialName("description")
                        val description: String? = null,
                        @SerialName("fillPct")
                        val fillPct: Int? = null,
                        @SerialName("foodCode")
                        val foodCode: String? = null,
                        @SerialName("name")
                        val name: String? = null,
                        @SerialName("pausePct")
                        val pausePct: Int? = null,
                        @SerialName("required")
                        val required: Boolean? = null,
                        @SerialName("servingSize")
                        val servingSize: Int? = null,
                        @SerialName("servingUnit")
                        val servingUnit: String? = null,
                        @SerialName("split")
                        val split: Boolean? = null,
                        @SerialName("status")
                        val status: String? = null,
                        @SerialName("timeCode")
                        val timeCode: String? = null,
                        @SerialName("type")
                        val type: String? = null,
                        @SerialName("typeDesc")
                        val typeDesc: String? = null
                    )
                }
            }
        }
    }
}