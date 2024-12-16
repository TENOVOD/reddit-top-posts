package com.alligator.reddittopposts.domain.usecase

import com.alligator.reddittopposts.data.repository.interfaces.DataRepository
import com.alligator.reddittopposts.domain.model.Post
import javax.inject.Inject

/**
 * Provide previous list of posts
 */

class GetPreviousPosts @Inject constructor(
    private val dataRepo: DataRepository
) {
    suspend operator fun invoke():List<Post>?{
        return dataRepo.getPreviousItems()
    }
}