package com.invictus.bloghub.models

import kotlinx.serialization.Serializable

@Serializable
data class blogitem(
    val title:String?=null,
    val description:String?=null,
    val category:String?=null
)