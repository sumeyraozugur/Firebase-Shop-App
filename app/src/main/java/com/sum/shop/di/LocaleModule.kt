package com.sum.shop.di

import android.content.Context
import androidx.room.Room
import com.sum.shop.room.BasketProductDao
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDao
import com.sum.shop.room.FavProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocaleModule {

    @Provides
    @Singleton
    fun provideBasketProductDatabase(@ApplicationContext context: Context): BasketProductDatabase =
        Room.databaseBuilder(context, BasketProductDatabase::class.java,"basket_database")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideBasketDao(basketProductDatabase: BasketProductDatabase):BasketProductDao = basketProductDatabase.basketDao()


    @Provides
    @Singleton
    fun provideFavProductDatabase(@ApplicationContext context: Context): FavProductDatabase =
        Room.databaseBuilder(context, FavProductDatabase::class.java,"fav_database")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideFavDao(favProductDatabase: FavProductDatabase): FavProductDao = favProductDatabase.favDao()
}



