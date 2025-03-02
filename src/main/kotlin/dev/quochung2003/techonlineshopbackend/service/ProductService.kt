package dev.quochung2003.techonlineshopbackend.service

import dev.quochung2003.techonlineshopbackend.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val repository: ProductRepository
) {
    fun getAllInstances() = repository.findAll()
}