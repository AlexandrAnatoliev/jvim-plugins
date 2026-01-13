import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Color enum
 *
 * @version 0.7.6
 * @since 12.01.2026
 * @author AlexandrAnatoliev
 */
public class ColorsTest {
    @Test
    @DisplayName("Colors enum should have correct ANSI codes")
    void testColorCodes() {
        assertEquals("\u001B[31m", Colors.RED.toString());
        assertEquals("\u001B[31m", Colors.RED.code);
        assertEquals("\u001B[32m", Colors.GREEN.toString());
        assertEquals("\u001B[32m", Colors.GREEN.code);
        assertEquals("\u001B[33m", Colors.YELLOW.toString());
        assertEquals("\u001B[33m", Colors.YELLOW.code);
        assertEquals("\u001B[0m", Colors.RESET.toString());
        assertEquals("\u001B[0m", Colors.RESET.code);
    }
}

