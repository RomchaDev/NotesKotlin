package org.romeo.noteskotlin

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.romeo.noteskotlin.create_edit_note.CreateEditViewModel
import org.romeo.noteskotlin.main.MainViewModel
import org.romeo.noteskotlin.model.FirebaseDataProvider
import org.romeo.noteskotlin.model.Repository
import org.romeo.noteskotlin.splash.SplashViewModel

val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FirebaseDataProvider(get(), get()) }
    single { Repository(get()) }


    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { (intent: Intent) -> CreateEditViewModel(intent, get()) }
}