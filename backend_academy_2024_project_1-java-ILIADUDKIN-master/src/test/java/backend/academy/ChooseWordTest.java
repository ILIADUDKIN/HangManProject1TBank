package backend.academy;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChooseWordTest {

    private final ChooseWord chooseWord = new ChooseWord();

    @Test
    void testGetRandomWord_ValidCategoryAndHardLevel() {
        // Arrange
        ChooseWord.Category category = ChooseWord.Category.PROGRAMMING;
        int hardLevel = 3;

        // Act
        String[] result = chooseWord.getRandomWord(category, hardLevel);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.length); // Проверка, что массив содержит слово и подсказку
        assertFalse(result[0].isEmpty()); // Проверка, что слово не пустое
        assertFalse(result[1].isEmpty()); // Проверка, что подсказка не пустая
    }


    @Test
    void testGetRandomWord_NoWordsForHardLevel() {
        // Arrange
        ChooseWord.Category category = ChooseWord.Category.PROGRAMMING;
        ChooseWord chooseWord = new ChooseWord();
        int hardLevel = 4;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> chooseWord.getRandomWord(category, hardLevel));
    }

    @Test
    void testSetRandomCategory() {
        // Arrange

        // Act
        ChooseWord result = chooseWord.setRandomCategory();

        // Assert
        assertNotNull(result.selectedCategory()); // Проверка, что категория выбрана
        assertTrue(Arrays.asList(ChooseWord.Category.values()).contains(result.selectedCategory())); // Проверка, что выбранная категория - из допустимых
    }
}
