package it.pierosilvestri.leaderboard_domain.di

import androidx.lifecycle.ViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import it.pierosilvestri.leaderboard_domain.use_case.CalculateLeaderboard

val leaderboardDomainModule: Module = module {
    single { CalculateLeaderboard() }
}