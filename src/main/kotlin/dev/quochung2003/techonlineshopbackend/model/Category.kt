package dev.quochung2003.techonlineshopbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("categories")
data class Category(
    @Id var id: Int = 0,
    val title: String,
    val pictureUrl: String
)
