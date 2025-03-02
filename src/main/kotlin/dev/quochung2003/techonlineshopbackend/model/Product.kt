package dev.quochung2003.techonlineshopbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("products")
data class Product(
    @Id var id: Int = 0,
    val name: String,
    val categoryId: Int,
    val type: List<String> = mutableListOf(),
    val price: Float,
    val rating: Float,
    val pictureUrl: String,
    val showAsRecommend: Boolean = true
)
