package dev.quochung2003.techonlineshopbackend.repository

import dev.quochung2003.techonlineshopbackend.model.Category
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CategoryRepository : CoroutineCrudRepository<Category, Int>