package com.nikitakrapo

import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.nikitakrapo.trip_details.TripDetails
import com.nikitakrapo.trip_details.TripDetails.Event
import com.nikitakrapo.trip_details.TripDetailsComponent
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test


class TripDetailsTest {

    lateinit var component: TripDetails

    @Before
    fun init() {
        component = TripDetailsComponent(
            storeFactory = TimeTravelStoreFactory(),
            Dispatchers.Main,
            mockk(),
            "trip"
        )
    }

    @Test
    fun `dropdown menu opened on MoreClicked event`() = runBlocking {
        component.accept(Event.MoreClicked)

        assertTrue(component.models.first().isDropdownMenuOpened)
    }

    @Test
    fun `dropdown menu closed on OutsideOfDropdownClicked event`() = runBlocking {
        component.accept(Event.MoreClicked)
        component.accept(Event.OutsideOfDropdownClicked)

        assertFalse(component.models.first().isDropdownMenuOpened)
    }
}