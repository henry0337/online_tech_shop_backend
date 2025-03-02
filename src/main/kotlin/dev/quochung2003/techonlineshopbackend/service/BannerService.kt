package dev.quochung2003.techonlineshopbackend.service

import dev.quochung2003.techonlineshopbackend.repository.BannerRepository
import org.springframework.stereotype.Service

@Service
class BannerService(
    private val repository: BannerRepository
) {
    fun getAllInstances() = repository.findAll()
}