package dev.quochung2003.techonlineshopbackend.service

import dev.quochung2003.techonlineshopbackend.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val repository: CategoryRepository
) {
    fun getAllInstances() = repository.findAll()
}