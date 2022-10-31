package kg.o.internlabs.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

//    @Singleton
//    @Provides
//    fun provideSharedPrefs(@ApplicationContext context: Context) : SharedPreferences{
//         return context.getSharedPreferences("prefFileName",Context.MODE_PRIVATE)
//    }
}