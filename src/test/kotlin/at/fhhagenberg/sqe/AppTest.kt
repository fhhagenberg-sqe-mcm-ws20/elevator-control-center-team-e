package at.fhhagenberg.sqe

import javafx.stage.Stage
import org.testfx.api.FxRobot

//@ExtendWith(ApplicationExtension.class)
class AppTest {
    //private Button button;
    /**
     * Will be called with `@Before` semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    //@Start
    fun start(stage: Stage?) {
        // var app = new App();
        // app.start(stage);
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    //@Test
    fun testButtonWithText(robot: FxRobot?) {
        // FxAssert.verifyThat(".button", LabeledMatchers.hasText("Click me!"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    //@Test
    fun testButtonClick(robot: FxRobot?) {
        // when:
        // robot.clickOn(".button");

        // or (lookup by css class):
        // FxAssert.verifyThat(".button", LabeledMatchers.hasText("Clicked!"));
    }
}