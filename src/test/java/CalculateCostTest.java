import org.ada.commons.CalculateCost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculateCostTest {
    @Test
    @DisplayName("Test for calculating the total cost of a calendar")
    public void testCalculateCost(){

        int[][] distance = {
            {0, 745, 665, 929},
            {745, 0, 80, 337},
            {665, 80, 0, 380},
            {929, 337, 380, 0}
        };
        int[][] canlenddar = {
            {3, 4, -1, -2},
            {2, -1, 4, -3},
            {4, -3, 2, -1},
            {-3, -4, 1, 2},
            {-2, 1, -4, 3},
            {-4, 3, -2, 1}
        };

        Object totalCost = CalculateCost.calculateTotalCost(distance, canlenddar);
        assertEquals(8276, totalCost);
        assertTrue(totalCost instanceof Integer);
    }
}
