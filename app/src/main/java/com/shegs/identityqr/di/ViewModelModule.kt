package com.shegs.identityqr.di

import com.shegs.identityqr.repositories.InformationRepository
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideInformationViewModel(informationRepository: InformationRepository): InformationViewModel {
        return InformationViewModel(informationRepository)
    }
}
