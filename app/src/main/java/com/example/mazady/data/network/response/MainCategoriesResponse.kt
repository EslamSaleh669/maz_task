package com.example.mazady.data.network.response

import com.google.gson.annotations.SerializedName

data class MainCategoriesResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null
)

data class AdsBannersItem(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("media_type")
	val mediaType: String? = null
)

data class ChildrenItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("disable_shipping")
	val disableShipping: Int? = null,

	@field:SerializedName("children")
	val children: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("circle_icon")
	val circleIcon: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class CategoriesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("disable_shipping")
	val disableShipping: Int? = null,

	@field:SerializedName("children")
	val children: List<ChildrenItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("circle_icon")
	val circleIcon: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class Data(

	@field:SerializedName("ios_version")
	val iosVersion: String? = null,

	@field:SerializedName("google_version")
	val googleVersion: String? = null,

	@field:SerializedName("ios_latest_version")
	val iosLatestVersion: String? = null,

	@field:SerializedName("ads_banners")
	val adsBanners: List<AdsBannersItem?>? = null,

	@field:SerializedName("huawei_version")
	val huaweiVersion: String? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("statistics")
	val statistics: Statistics? = null
)

data class Statistics(

	@field:SerializedName("auctions")
	val auctions: Int? = null,

	@field:SerializedName("users")
	val users: Int? = null,

	@field:SerializedName("products")
	val products: Int? = null
)
