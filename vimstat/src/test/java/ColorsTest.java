import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/*
 * Unit tests for Color enum
 *
 * @version 0.8.1
 * @since 28.01.2026
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

  @Test
  @DisplayName("apply method should wrap text with color code and reset")
  void testApplyMethod() {
    String redText = Colors.RED.apply("Error message");
    String expectedRed = "\u001B[31m" + "Error message" + "\u001B[0m";
    assertEquals(expectedRed, redText);

    String greenText = Colors.GREEN.apply("Success message");
    String expectedGreen = "\u001B[32m" + "Success message" + "\u001B[0m";
    assertEquals(expectedGreen, greenText);

    String yellowText = Colors.YELLOW.apply("Warning message");
    String expectedYellow = "\u001B[33m" + "Warning message" + "\u001B[0m";
    assertEquals(expectedYellow, yellowText);

    String resetText = Colors.RESET.apply("Normal text");
    String expectedReset = "\u001B[0m" + "Normal text" + "\u001B[0m";
    assertEquals(expectedReset, resetText);
  }

  @Test
  @DisplayName("apply method should handle empty strings")
  void testApplyWithEmptyString() {
    String redEmpty = Colors.RED.apply("");
    String expectedRedEmpty = "\u001B[31m" + "" + "\u001B[0m";
    assertEquals(expectedRedEmpty, redEmpty);
  }

  @Test
  @DisplayName("apply method should handle null")
  void testApplyWithNull() {
    String redNull = Colors.RED.apply(null);
    String expectedRedNull = "\u001B[31m" + "null" + "\u001B[0m";
    assertEquals(expectedRedNull, redNull);
  }

  @Test
  @DisplayName("apply method should preserve whitespace and special characters")
  void testApplyWithSpecialCharacters() {
    String textWithWhitespace = " Hello\nworld\t! ";
    String redText = Colors.RED.apply(textWithWhitespace);
    String expectedRed = "\u001B[31m" + textWithWhitespace + "\u001B[0m";
    assertEquals(expectedRed, redText);

    String textWithUnicode = "Hello 🌍 世界";
    String greenText = Colors.GREEN.apply(textWithUnicode);
    String expectedGreen = "\u001B[32m" + textWithUnicode + "\u001B[0m";
    assertEquals(expectedGreen, greenText);
  }

  @Test
  @DisplayName("apply method should handle multiple lines")
  void testApplyWithMultipleLines() {
    String textWithMultipleLines = "Line 1\nline 2\nline 3";
    String redText = Colors.RED.apply(textWithMultipleLines);
    String expectedRed = "\u001B[31m" + textWithMultipleLines + "\u001B[0m";
    assertEquals(expectedRed, redText);
  }

  @Test
  @DisplayName("valueOf should correctly parse enum names")
  void testValueOf() {
    assertEquals(Colors.RED, Colors.valueOf("RED"));
    assertEquals(Colors.GREEN, Colors.valueOf("GREEN"));
    assertEquals(Colors.YELLOW, Colors.valueOf("YELLOW"));
    assertEquals(Colors.RESET, Colors.valueOf("RESET"));
  }

  @Test
  @DisplayName("apply method should be chainable")
  void testApplyMethodChainable() {
    String coloredOnce = Colors.RED.apply("test");
    String coloredTwice = Colors.GREEN.apply(coloredOnce);
    String expected = "\u001B[32m" + "\u001B[31m" + "test" + "\u001B[0m" + "\u001B[0m";
    assertEquals(expected, coloredTwice);
  }

  @Test
  @DisplayName("apply method should not modify the original text")
  void testApplyDoesNotModifyOriginal() {
    String originalText = "Original text";
    String coloredText = Colors.RED.apply(originalText);
    assertNotEquals(originalText, coloredText);
    assertEquals("Original text", originalText);
    assertTrue(coloredText.contains(originalText));
  }
}
