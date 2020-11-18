package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.util.ViewLoader
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Singleton
import java.util.*

class JavaFxModule : AbstractModule() {

    private val supportedLocales = listOf(
            Locale.ENGLISH
    )

    override fun configure() {
        bind(ViewLoader::class.java).`in`(Singleton::class.java)
        bind(Key.get(ResourceBundle::class.java, StringsFile::class.java)).toInstance(ResourceBundle.getBundle("strings", getSupportedLocale()))
    }

    private fun getSupportedLocale(): Locale {
        val systemLocale = Locale.getDefault()

        return if (supportedLocales.any { locale -> locale.language == systemLocale.language } ) {
            systemLocale
        } else {
            supportedLocales.first()
        }
    }
}