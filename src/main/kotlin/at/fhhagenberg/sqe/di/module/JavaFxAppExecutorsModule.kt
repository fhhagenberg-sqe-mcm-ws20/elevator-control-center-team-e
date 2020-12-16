package at.fhhagenberg.sqe.di.module

import at.fhhagenberg.sqe.AppExecutors
import at.fhhagenberg.sqe.util.JavaFxAppExecutors
import com.google.inject.AbstractModule
import com.google.inject.Singleton

class JavaFxAppExecutorsModule : AbstractModule() {
    override fun configure() {
        bind(AppExecutors::class.java).to(JavaFxAppExecutors::class.java).`in`(Singleton::class.java)
    }
}