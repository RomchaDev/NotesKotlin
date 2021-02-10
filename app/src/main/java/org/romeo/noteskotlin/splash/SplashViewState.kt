package org.romeo.noteskotlin.splash

import org.romeo.noteskotlin.base.BaseViewState

class SplashViewState(isAuth: Boolean = false, error: Throwable? = null)
    : BaseViewState<Boolean>(isAuth, error)