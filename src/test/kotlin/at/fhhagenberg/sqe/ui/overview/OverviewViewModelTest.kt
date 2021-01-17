package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OverviewViewModelTest {

    private lateinit var viewModel: OverviewViewModel

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        viewModel = injector.getInstance(OverviewViewModel::class.java)
        injector.getInstance(UpdateElevatorStoreTask::class.java).run()
    }

    @Test
    fun testElevatorNumbers() {
        val elevatorNumbers = viewModel.elevatorNumbers
        assertEquals(listOf(0, 1), elevatorNumbers)
    }
}