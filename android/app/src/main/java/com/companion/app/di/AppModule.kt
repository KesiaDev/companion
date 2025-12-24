package com.companion.app.di

import com.companion.app.data.local.AvatarRepository
import com.companion.app.data.local.MemoryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Módulo de injeção de dependências
 * Centraliza criação de repositórios e serviços
 */
val appModule = module {
    // Repositórios
    single { AvatarRepository() }
    single { MemoryRepository() }
}

