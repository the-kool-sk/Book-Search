package com.ptb.booksearch.libraraysearch.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Not all fields in the response are deserialized here
@Serializable
data class SearchResults(
    @SerialName("num_found")
    val numFound: Int,
    @SerialName("docs")
    val documents: List<Document>
)

//Not all fields in the response are deserialized here
@Serializable
data class Document(
    @SerialName("cover_i")
    val coverId: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("author_name")
    val author: List<String>? = null,
    @SerialName("key")
    val key: String
)