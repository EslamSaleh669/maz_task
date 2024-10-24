package com.example.mazady.data.network.response

import com.google.gson.annotations.SerializedName

data class PropertiesResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<PropertiesItem?>? = null
)

data class PropertiesItem(

	@field:SerializedName("parent")
	val parent: Any? = null,

	@field:SerializedName("other_value")
	val otherValue: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("options")
	val options: List<OptionsItem?>? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("list")
	val list: Boolean? = null,

	@field:SerializedName("type")
	val type: Any? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class OptionsItem(

	@field:SerializedName("parent")
	val parent: Int? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("child")
	var child: Boolean? = null
)
