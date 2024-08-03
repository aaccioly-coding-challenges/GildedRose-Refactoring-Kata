package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
        assertThat(app.items[0].name).contains("foo");
    }


    @DisplayName("Test if item is named Sulfuras")
    @Test
    void testIsSulfuras() {
        assertThat(GildedRose.isSulfuras("Sulfuras..")).isTrue();
    }

    @DisplayName("Test if the  isSulfuras method returns false for items not named Sulfuras")
    @Test
    void testIsNormalItem() {
        assertThat(GildedRose.isSulfuras("Foo..")).isFalse();
    }

}
