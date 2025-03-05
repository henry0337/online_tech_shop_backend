package dev.quochung2003.techonlineshopbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("categories")
data class Category(
    @Id val id: Int? = null,
    val title: String,
    val pictureUrl: String
)
