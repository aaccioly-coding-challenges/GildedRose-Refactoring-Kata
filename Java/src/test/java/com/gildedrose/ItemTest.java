package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    private final Item normalItem = new Item("Foo", 10, 20);

    @DisplayName("toString prints name and quality")
    @Test
    void testToString() {
        assertThat(normalItem.toString()).isEqualTo("Foo, 10, 20");
    }
}
