package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SystemInfoTest {

    private int getMajorVersionNumber(String fullVersion) {
        // get major version number
        if (fullVersion.contains(".")) {
            String[] parts = fullVersion.split("\\.");
            return Integer.parseInt(parts[0]);
        }

        return Integer.parseInt(fullVersion);
    }

    @Test
    public void testJavaVersion() {
        String fullVersion = SystemInfo.javaVersion();
        int majorVersion = getMajorVersionNumber(fullVersion);

        assertTrue(majorVersion >= 13);
    }

    @Test
    public void testJavafxVersion() {
        String fullVersion = SystemInfo.javafxVersion();
        int majorVersion = getMajorVersionNumber(fullVersion);

        assertTrue(majorVersion >= 13);
    }
}