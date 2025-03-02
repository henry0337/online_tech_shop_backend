package dev.quochung2003.techonlineshopbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("banners")
data class Banner(
    @Id var id: Int = 0,
    val url: String
)
