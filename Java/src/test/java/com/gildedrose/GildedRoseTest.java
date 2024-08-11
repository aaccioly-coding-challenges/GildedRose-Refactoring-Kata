package com.gildedrose;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    @DisplayName("normal item is not legendary nor aged brie")
    @Test
    void testIsNormalItem() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(GildedRose.isLegendary("Foo")).isFalse();
            softly.assertThat(GildedRose.isAgedBrie("Foo")).isFalse();
        });
    }

    @Nested
    class Sulfuras {

        private final Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", -1, 80);

        @DisplayName("category is legendary")
        @Test
        void testIsLegendary() {
            assertThat(GildedRose.isLegendary("Sulfuras..")).isTrue();
        }

        @DisplayName("quality stays at 80 and it never alters")
        @Test
        void testSulfurasQualityIsAlways80() {
            // given
            GildedRose app = createGildedRose(sulfuras);
            // when
            app.updateQuality();
            // then
            assertThat(sulfuras.quality).isEqualTo(80);
        }

        @DisplayName("sellIn value never alters")
        @Test
        void testSulfurasSellinNeverAlters() {
            GildedRose app = createGildedRose(sulfuras);
            app.updateQuality();
            assertThat(sulfuras.sellIn).isEqualTo(-1);
        }

    }

    @Nested
    class AgedBrie {

        private final Item agedBrie = new Item("Aged Brie", 2, 0);

        @DisplayName("category is aged brie")
        @Test
        void testIsAgedBrie() {
            assertThat(GildedRose.isAgedBrie("Aged Brie")).isTrue();
        }

        @DisplayName("quality increases by 1")
        @Test
        void testAgedBrieQualityIncreasesBy1() {
            GildedRose app = createGildedRose(agedBrie);
            app.updateQuality();
            assertThat(agedBrie.quality).isEqualTo(1);
        }

        @DisplayName("quality increases by 2 after sellIn date")
        @Test
        void testAgedBrieQualityIncreasesBy2AfterSellIn() {
            agedBrie.sellIn = 0;
            GildedRose app = createGildedRose(agedBrie);
            app.updateQuality();
            assertThat(agedBrie.quality).isEqualTo(2);
        }

        @DisplayName("quality never exceeds 50")
        @Test
        void testAgedBrieQualityNeverExceeds50() {
            agedBrie.quality = 50;
            GildedRose app = createGildedRose(agedBrie);
            app.updateQuality();
            assertThat(agedBrie.quality).isEqualTo(50);
        }
    }

    public GildedRose createGildedRose(Item item) {
        return new GildedRose(item);
    }

}
