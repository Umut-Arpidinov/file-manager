package kg.o.internlabs.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context) : SharedPreferences{
         return context.getSharedPreferences("prefFileName",Context.MODE_PRIVATE)
    }
}