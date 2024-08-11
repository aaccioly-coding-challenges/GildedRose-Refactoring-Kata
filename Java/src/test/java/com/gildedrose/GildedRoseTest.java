package com.gildedrose;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    @DisplayName("normal item is not legendary nor aged brie nor backstage passes")
    @Test
    void testIsNormalItem() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(GildedRose.isLegendary("Foo")).isFalse();
            softly.assertThat(GildedRose.isAgedBrie("Foo")).isFalse();
            softly.assertThat(GildedRose.isBackStagePass("Foo")).isFalse();
        });
    }

    @Nested
    class Sulfuras {

        private final Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", -1, 80);

        @DisplayName("category is legendary")
        @Test
        void testIsLegendary() {
            assertThat(GildedRose.isLegendary(sulfuras.name)).isTrue();
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
            assertThat(GildedRose.isAgedBrie(agedBrie.name)).isTrue();
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

    @Nested
    class BackstagePass {
        private final Item backStagePass = new Item(
            "Backstage passes to a TAFKAL80ETC concert",
            30,
            10);

        @DisplayName("is a Backstage pass")
        @Test
        void isBackstagePass() {
            assertThat(GildedRose.isBackStagePass(backStagePass.name)).isTrue();
        }

        @DisplayName("quality increases by 1 when sellIn is greater than 10")
        @Test
        void qualityIncreasesBy1WhenSellInIsGreaterThan10() {
            GildedRose app = createGildedRose(backStagePass);
            app.updateQuality();
            assertThat(backStagePass.quality).isEqualTo(11);
        }

        @DisplayName("quality increases by 2 when sellIn is between 10 and 6")
        @Test
        void qualityIncreasesBy2WhenSellInIsBetween10And6() {
            backStagePass.sellIn = 10;
            GildedRose app = createGildedRose(backStagePass);
            app.updateQuality();
            assertThat(backStagePass.quality).isEqualTo(12);
        }

        @DisplayName("quality increases by 3 when sellIn is between 5 and 0")
        @Test
        void qualityIncreasesBy3WhenSellInIsBetween5And0() {
            backStagePass.sellIn = 5;
            GildedRose app = createGildedRose(backStagePass);
            app.updateQuality();
            assertThat(backStagePass.quality).isEqualTo(13);
        }
    }

    public GildedRose createGildedRose(Item item) {
        return new GildedRose(item);
    }

}
