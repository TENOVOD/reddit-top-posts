package com.alligator.reddittopposts.data.remote.api

import com.alligator.reddittopposts.di.module.HttpModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.jupiter.api.DisplayName

class RedditApiServiceTest {


    @Test
    @DisplayName("GET www.reddit.com/top.json?limit=10")
    fun shouldReturn10FetchingItems() = runBlocking {

        val client = HttpModule.provideHttpClient()
        val service = RedditApiService(client)

        val response = service.getFirstItems()

        val expectedCountOfFetchingItems = 10
        val actualCountOfFetchingItems = response.data?.dist
        assertEquals(expectedCountOfFetchingItems, actualCountOfFetchingItems)
    }

    /**
     * Test pagination
     */

    @Test
    @DisplayName("GET http://www.reddit.com/top.json?after=AFTER_INDEX&limit=10&count=10")
    fun shouldReturnNext10ItemsAndHaveBeforeIndex() = runBlocking {

        val client = HttpModule.provideHttpClient()
        val service = RedditApiService(client)
        var numberOfItemsAlreadySeen = 0

        //get first 10 items and after index
        val firstResponse = service.getFirstItems()
        numberOfItemsAlreadySeen += 10
        val afterIndex = firstResponse.data?.after

        //get next 10 items by after index
        val nextResponse =
            service.getNextItems(afterIndex = afterIndex!!)
        val expectedCountOfFetchingItems = 10
        val actualCountOfFetchingItems = nextResponse.data?.dist

        assertEquals(expectedCountOfFetchingItems, actualCountOfFetchingItems)

        assertNotNull(nextResponse.data?.before)
    }

    @Test
    @DisplayName("GET http://www.reddit.com/top.json?before=BEFORE_INDEX&limit=10&count=10")
    fun shouldReturnPrevious10ItemsAndHaveAfterIndex() = runBlocking {

        val client = HttpModule.provideHttpClient()
        val service = RedditApiService(client)
        var numberOfItemsAlreadySeen = 0

        //get first 10 items and after index
        val firstResponse = service.getFirstItems()
        numberOfItemsAlreadySeen += 10
        val afterIndex = firstResponse.data?.after

        //get next 10 items by after index
        val afterResponse =
            service.getNextItems(afterIndex = afterIndex!!)

        //get previous page (10 items) by before index
        numberOfItemsAlreadySeen-=10
        val beforeIndex = afterResponse.data?.before
        val beforeResponse = service.getPreviousItems(beforeIndex = beforeIndex!!)
        val expectedCountOfFetchingItems = 10
        val actualCountOfFetchingItems = beforeResponse.data?.dist


        assertEquals(expectedCountOfFetchingItems, actualCountOfFetchingItems)
        assertNull(beforeResponse.data?.before)
    }


    /**
     * Test request to get user data
     */

    @Test
    @DisplayName("https://www.reddit.com/user/AUTHOR_NICKNAME/about.json")
    fun shouldReturnAuthorData() = runBlocking {

        val client = HttpModule.provideHttpClient()
        val service = RedditApiService(client)
        var numberOfItemsAlreadySeen = 0

        //get first 10 items
        val firstResponse = service.getFirstItems()
        numberOfItemsAlreadySeen += 10

        //get next 10 items by after index
        val userResponse =
            service.getAuthorIcon(firstResponse.data?.children?.get(0)?.data!!.author!!)

        assertNotNull(userResponse.data)
    }

}