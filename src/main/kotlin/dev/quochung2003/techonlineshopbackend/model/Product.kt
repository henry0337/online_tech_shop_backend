package dev.quochung2003.techonlineshopbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("products")
data class Product(
    @Id val id: Int? = null,
    val name: String,
    val categoryId: Int,
    val type: List<String>,
    val price: Float,
    val rating: Float,
    val pictureUrl: String,
    val showAsRecommend: Boolean = true
)
