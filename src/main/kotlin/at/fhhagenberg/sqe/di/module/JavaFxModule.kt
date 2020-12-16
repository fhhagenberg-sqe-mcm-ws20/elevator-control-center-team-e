package at.fhhagenberg.sqe.di.module

import at.fhhagenberg.sqe.di.key.StringsFile
import at.fhhagenberg.sqe.util.ViewLoader
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Singleton
import java.text.NumberFormat
import java.util.*

class JavaFxModule : AbstractModule() {

    private val supportedLocales = listOf(
            Locale.ENGLISH
    )

    override fun configure() {
        val appLocale = getSupportedLocale()
        bind(ViewLoader::class.java).`in`(Singleton::class.java)
        bind(Locale::class.java).toInstance(appLocale)
        bind(NumberFormat::class.java).toInstance(NumberFormat.getInstance(appLocale).apply {
            maximumFractionDigits = 2
        })
        bind(Key.get(ResourceBundle::class.java, StringsFile::class.java)).toInstance(ResourceBundle.getBundle("strings", appLocale))
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