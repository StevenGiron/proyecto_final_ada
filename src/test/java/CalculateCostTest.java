import org.ada.commons.CalculateCost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        int totalCost = CalculateCost.calculateTotalCost(distance, canlenddar);
        assertEquals(8276, totalCost);

        int[][] distance2 = {
                {0, 184, 222, 177, 216, 231, 120, 60},
                {184, 0, 45, 123, 128, 200, 52, 100},
                {222, 45, 0, 129, 121, 203, 15, 300},
                {177, 123, 129, 0, 46, 83, 250, 15},
                {216, 128, 121, 46, 0, 83, 100, 7},
                {231, 200, 203, 83, 83, 0, 20, 10},
                {120, 52, 15, 250, 100, 20, 0, 441},
                {60, 100, 300, 15, 7, 10, 441, 54}
        };
        int[][] canlenddar2 = {
                {7, 5, 4, -3, -2, -8, -1, 6},
                {6, 3, -2, -8, -7, -1, 5, 4},
                {2, -1, -7, -6, -8, 4, 3, 5},
                {3, -8, -1, -7, -6, 5, 4, 2},
                {4, -6, -5, -1, 3, 2, 8, -7},
                {8, -7, -6, -5, 4, 3, 2, -1},
                {-5, -4, -8, 2, 1, 7, -6, 3},
                {-7, -5, -4, 3, 2, 8, 1, -6},
                {-6, -3, 2, 8, 7, 1, -5, -4},
                {-2, 1, 7, 6, 8, -4, -3, -5},
                {-3, 8, 1, 7, 6, -5, -4, -2},
                {-4, 6, 5, 1, -3, -2, -8, 7},
                {-8, 7, 6, 5, -4, -3, -2, 1},
                {5, 4, 8, -2, -1, -7, 6, -3}
        };
        int totalCost2 = CalculateCost.calculateTotalCost(distance2, canlenddar2);

        assertEquals(7658, totalCost2);

    }
}
