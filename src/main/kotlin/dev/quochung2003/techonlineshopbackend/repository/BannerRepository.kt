package dev.quochung2003.techonlineshopbackend.repository

import dev.quochung2003.techonlineshopbackend.model.Banner
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BannerRepository : CoroutineCrudRepository<Banner, Int>