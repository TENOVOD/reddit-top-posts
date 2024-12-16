package com.alligator.reddittopposts.data.repository.impl

import com.alligator.reddittopposts.data.mappers.ItemMapper
import com.alligator.reddittopposts.data.remote.api.RedditApiService
import com.alligator.reddittopposts.data.repository.interfaces.DataRepository
import com.alligator.reddittopposts.domain.model.Post
import javax.inject.Inject

/**
 *  Get data by apiService and ->
 *  -> transform Dto objects (data layer) to Posts (domain layer)
 *
 */


class DataRepositoryImpl @Inject constructor(
    private val apiService: RedditApiService
) : DataRepository {

    private var afterIndex = ""
    private var beforeIndex = ""

    override suspend fun getFirstItems(): List<Post> {

        val posts = mutableListOf<Post>()

        val itemsRespDto = apiService.getFirstItems()
        afterIndex = itemsRespDto.data!!.after!!

        val items = itemsRespDto.data.children

        items!!.forEach { item ->
            val user = apiService.getAuthorIcon(item.data!!.author!!)
            posts.add(ItemMapper.mapToDomain(item.data, user.data))
        }
        return posts
    }

    override suspend fun getNextItems(): List<Post>? {
        try {
            val posts = mutableListOf<Post>()

            val itemsRespDto = apiService.getNextItems(afterIndex)
            afterIndex = itemsRespDto.data!!.after!!
            beforeIndex = itemsRespDto.data.before!!
            val items = itemsRespDto.data.children

            items!!.forEach { item ->
                val user = apiService.getAuthorIcon(item.data!!.author!!)
                posts.add(ItemMapper.mapToDomain(item.data, user.data))
            }
            return posts
        } catch (ex: Exception) {
            println(ex.message)
        }

        return null
    }

    override suspend fun getPreviousItems(): List<Post>? {
        try {
            val posts = mutableListOf<Post>()

            val itemsRespDto = apiService.getPreviousItems(beforeIndex)
            afterIndex = itemsRespDto.data!!.after!!
            beforeIndex = itemsRespDto.data.before ?: ""

            val items = itemsRespDto.data.children

            items!!.forEach { item ->
                val user = apiService.getAuthorIcon(item.data!!.author!!)
                posts.add(ItemMapper.mapToDomain(item.data, user.data))
            }
            return posts

        } catch (ex: Exception) {
            println(ex.message)
        }
        return null
    }
}