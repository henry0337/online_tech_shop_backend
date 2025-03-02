package dev.quochung2003.techonlineshopbackend.repository

import dev.quochung2003.techonlineshopbackend.model.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductRepository : CoroutineCrudRepository<Product, Int>