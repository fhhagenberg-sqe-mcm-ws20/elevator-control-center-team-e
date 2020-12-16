package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.App
import com.google.inject.Injector

class InjectedApp(
    val app: App,
    val injector: Injector
)