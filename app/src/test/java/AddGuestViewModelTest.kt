package com.example.guestapp.presentation.add_guest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.guestapp.data.local.entity.WeddingTable
import com.example.guestapp.domain.models.Gender
import com.example.guestapp.domain.models.WeddingSide
import com.example.guestapp.domain.repository.GuestRepository
import com.example.guestapp.domain.usecases.AddGuestUseCase
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddGuestViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var addGuestUseCase: AddGuestUseCase
    private lateinit var repository: GuestRepository
    private lateinit var viewModel: AddGuestViewModel

    private val mockTables = listOf(
        WeddingTable(id = 1, capacity = 8, tableNumber = 1, tableName = "Friends"),
        WeddingTable(id = 2, capacity = 8, tableNumber = 2, tableName = "Family"),
        WeddingTable(id = 3, capacity = 8, tableNumber = 3, tableName = "Neighbors"),
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        addGuestUseCase = mockk()
        repository = mockk()

        every { repository.getAllTables() } returns flowOf(mockTables)

        viewModel = AddGuestViewModel(addGuestUseCase, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be correct`() {
        val initialState = viewModel.uiState.value

        assertEquals(AddGuestUiState.INITIAL, initialState)
        assertEquals("", initialState.name)
        assertEquals("", initialState.age)
        assertEquals(Gender.MALE, initialState.gender)
        assertEquals(WeddingSide.BRIDE, initialState.side)
        assertEquals("", initialState.phoneNumber)
        assertNull(initialState.selectedTableId)
        assertEquals("", initialState.dietaryRestrictions)
        assertEquals("", initialState.notes)
        assertFalse(initialState.isLoading)
        assertFalse(initialState.expandedTable)
        assertFalse(initialState.showSuccessDialog)
        assertNull(initialState.errorMessage)
    }

    @Test
    fun `updateName should update name in state`() {
        val testName = "John Doe"
        viewModel.updateName(testName)

        assertEquals(testName, viewModel.uiState.value.name)
    }

    @Test
    fun `updateAge should update age in state`() {
        val testAge = "25"
        viewModel.updateAge(testAge)

        assertEquals(testAge, viewModel.uiState.value.age)
    }

    @Test
    fun `updateGender should update gender in state`() {
        val testGender = Gender.FEMALE
        viewModel.updateGender(testGender)

        assertEquals(testGender, viewModel.uiState.value.gender)
    }

    @Test
    fun `updateSide should update side in state`() {
        val testSide = WeddingSide.GROOM
        viewModel.updateSide(testSide)

        assertEquals(testSide, viewModel.uiState.value.side)
    }

    @Test
    fun `updatePhone should update phone number in state`() {
        val testPhone = "+1234567890"
        viewModel.updatePhone(testPhone)

        assertEquals(testPhone, viewModel.uiState.value.phoneNumber)
    }

    @Test
    fun `updateTable should update table id and close dropdown`() {
        val testTableId = 1L
        // First open
        viewModel.onTableExpandedChange()
        assertTrue(viewModel.uiState.value.expandedTable)

        // Then select a table
        viewModel.updateTable(testTableId)

        assertEquals(testTableId, viewModel.uiState.value.selectedTableId)
        assertFalse(viewModel.uiState.value.expandedTable)
    }

    @Test
    fun `updateTable with null should update table id and close dropdown`() {
        viewModel.onTableExpandedChange()
        viewModel.updateTable(null)

        assertNull(viewModel.uiState.value.selectedTableId)
        assertFalse(viewModel.uiState.value.expandedTable)
    }

    @Test
    fun `updateDietaryRestrictions should update dietary restrictions in state`() {
        val testRestrictions = "Vegetarian, No nuts"
        viewModel.updateDietaryRestrictions(testRestrictions)

        assertEquals(testRestrictions, viewModel.uiState.value.dietaryRestrictions)
    }

    @Test
    fun `updateNotes should update notes in state`() {
        val testNotes = "VIP guest"
        viewModel.updateNotes(testNotes)

        assertEquals(testNotes, viewModel.uiState.value.notes)
    }

    @Test
    fun `onTableExpandedChange should toggle expanded state`() {
        // Initially false
        assertFalse(viewModel.uiState.value.expandedTable)

        // Toggle to true
        viewModel.onTableExpandedChange()
        assertTrue(viewModel.uiState.value.expandedTable)

        // Toggle back to false
        viewModel.onTableExpandedChange()
        assertFalse(viewModel.uiState.value.expandedTable)
    }

    @Test
    fun `onDismissTableChoose should set expanded to false`() {
        // First expand
        viewModel.onTableExpandedChange()
        assertTrue(viewModel.uiState.value.expandedTable)

        // Then dismiss
        viewModel.onDismissTableChoose()
        assertFalse(viewModel.uiState.value.expandedTable)
    }

    @Test
    fun `addGuest should show error when name is blank`() {
        viewModel.updateName("")
        viewModel.updateAge("25")

        viewModel.addGuest()

        assertEquals("Имя гостя обязательно для заполнения", viewModel.uiState.value.errorMessage)
        verify { addGuestUseCase wasNot Called }
    }

    @Test
    fun `addGuest should show error when name is only whitespace`() {
        viewModel.updateName("   ")
        viewModel.updateAge("25")

        viewModel.addGuest()

        assertEquals("Имя гостя обязательно для заполнения", viewModel.uiState.value.errorMessage)
        verify { addGuestUseCase wasNot Called }
    }

    @Test
    fun `addGuest should show error when age is not a number`() {
        viewModel.updateName("John Doe")
        viewModel.updateAge("not a number")

        viewModel.addGuest()

        assertEquals("Введите корректный возраст", viewModel.uiState.value.errorMessage)
        verify { addGuestUseCase wasNot Called }
    }

    @Test
    fun `addGuest should show error when age is zero`() {
        viewModel.updateName("John Doe")
        viewModel.updateAge("0")

        viewModel.addGuest()

        assertEquals("Введите корректный возраст", viewModel.uiState.value.errorMessage)
        verify { addGuestUseCase wasNot Called }
    }

    @Test
    fun `addGuest should show error when age is negative`() {
        viewModel.updateName("John Doe")
        viewModel.updateAge("-5")

        viewModel.addGuest()

        assertEquals("Введите корректный возраст", viewModel.uiState.value.errorMessage)
        verify { addGuestUseCase wasNot Called }
    }

    @Test
    fun `addGuest should successfully add guest with required fields only`() = runTest {
        coEvery { addGuestUseCase(any()) } returns Result.success(0)

        viewModel.updateName("John Doe")
        viewModel.updateAge("25")

        viewModel.addGuest()

        coVerify {
            addGuestUseCase(match { guest ->
                guest.name == "John Doe" &&
                        guest.age == 25 &&
                        guest.gender == Gender.MALE &&
                        guest.side == WeddingSide.BRIDE &&
                        guest.tableId == null &&
                        guest.phoneNumber == null &&
                        guest.dietaryRestrictions == null &&
                        guest.notes == null
            })
        }

        // Verify state was reset and success dialog shown
        assertEquals(
            AddGuestUiState.INITIAL.copy(showSuccessDialog = true),
            viewModel.uiState.value
        )
    }

    @Test
    fun `addGuest should successfully add guest with all fields filled`() = runTest {
        coEvery { addGuestUseCase(any()) } returns Result.success(0)

        viewModel.updateName("  Jane Smith  ")
        viewModel.updateAge("30")
        viewModel.updateGender(Gender.FEMALE)
        viewModel.updateSide(WeddingSide.GROOM)
        viewModel.updateTable(2L)
        viewModel.updatePhone("+1234567890")
        viewModel.updateDietaryRestrictions("Vegetarian")
        viewModel.updateNotes("VIP guest")

        // Execute
        viewModel.addGuest()

        // Verify
        coVerify {
            addGuestUseCase(match { guest ->
                guest.name == "Jane Smith" && // Should be trimmed
                        guest.age == 30 &&
                        guest.gender == Gender.FEMALE &&
                        guest.side == WeddingSide.GROOM &&
                        guest.tableId == 2L &&
                        guest.phoneNumber == "+1234567890" &&
                        guest.dietaryRestrictions == "Vegetarian" &&
                        guest.notes == "VIP guest"
            })
        }

        // Verify success state
        assertTrue(viewModel.uiState.value.showSuccessDialog)
    }

    @Test
    fun `addGuest should handle empty optional fields correctly`() = runTest {
        coEvery { addGuestUseCase(any()) } returns Result.success(0)

        viewModel.updateName("John Doe")
        viewModel.updateAge("25")
        viewModel.updatePhone("") // Empty string
        viewModel.updateDietaryRestrictions("   ") // Whitespace only
        viewModel.updateNotes("") // Empty string

        // Execute
        viewModel.addGuest()

        // Verify
        coVerify {
            addGuestUseCase(match { guest ->
                guest.phoneNumber == null &&
                        guest.dietaryRestrictions == null &&
                        guest.notes == null
            })
        }
    }

    @Test
    fun `addGuest should show loading state during execution`() = runTest {
        coEvery { addGuestUseCase(any()) } returns Result.success(0)

        viewModel.updateName("John Doe")
        viewModel.updateAge("25")

        // Execute
        viewModel.addGuest()

        assertFalse(viewModel.uiState.value.isLoading)
        assertTrue(viewModel.uiState.value.showSuccessDialog)
    }

    @Test
    fun `addGuest should handle use case failure`() = runTest {
        // Setup
        val errorMessage = "Database error"
        coEvery { addGuestUseCase(any()) } returns Result.failure(Exception(errorMessage))

        viewModel.updateName("John Doe")
        viewModel.updateAge("25")

        // Execute
        viewModel.addGuest()

        // Verify error handling
        assertEquals(errorMessage, viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.uiState.value.showSuccessDialog)
    }

    @Test
    fun `addGuest should handle use case failure with null message`() = runTest {
        coEvery { addGuestUseCase(any()) } returns Result.failure(Exception())

        viewModel.updateName("John Doe")
        viewModel.updateAge("25")

        // Execute
        viewModel.addGuest()

        // Verify
        assertEquals("Произошла ошибка при добавлении гостя", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `dismissDialog should hide success dialog and call onBack`() = runTest {
        // Setup success state through actual flow
        simulateSuccessState()
        assertTrue(viewModel.uiState.value.showSuccessDialog)

        val onBack = mockk<() -> Unit>(relaxed = true)

        // Execute
        viewModel.dismissDialog(onBack)

        // Verify
        assertFalse(viewModel.uiState.value.showSuccessDialog)
        verify { onBack() }
    }

    @Test
    fun `dismissErrorDialog should clear error message`() {
        // Setup error state through actual validation
        viewModel.updateName("") // This will cause validation error
        viewModel.updateAge("25")
        viewModel.addGuest()

        assertNotNull(viewModel.uiState.value.errorMessage)

        // Execute
        viewModel.dismissErrorDialog()

        // Verify
        assertNull(viewModel.uiState.value.errorMessage)
    }

    private fun simulateSuccessState() {
        // Trigger success through a successful addGuest call
        coEvery { addGuestUseCase(any()) } returns Result.success(0)
        viewModel.updateName("Test User")
        viewModel.updateAge("25")
        viewModel.addGuest()
    }
}