package com.gildedrose;

class GildedRose {
    private final Item[] items;

    public GildedRose(Item... items) {
        this.items = items;
    }

    public static boolean isLegendary(String itemName) {
        return itemName.startsWith("Sulfuras");
    }

    public static boolean isAgedBrie(String itemName) {
        return itemName.equals("Aged Brie");
    }

    public static boolean isBackStagePass(String itemName) {
        return itemName.startsWith("Backstage passes");
    }

    public void updateQuality() {
        for (Item item : items) {

            // Sulfuras and Backstage passes are classes of items and should be
            // generalised

            // Case sensitive? set all to lowe case when observing input stream

            // normal case - quality is diminished by 1
            //            switch(Classifier.classify(item[i]))
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        if (isLegendary(item.name)) {
            return;
        }

        item.sellIn--;

        if (isAgedBrie(item.name)) {
            var qualityDelta = item.sellIn < 0 ? 2 : 1;
            item.quality = Math.min(50, item.quality + qualityDelta);
        } else if (isBackStagePass(item.name)) {
            if (item.sellIn < 0) {
                item.quality = 0;
            } else {
                var qualityDelta = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
                item.quality = Math.min(50, item.quality + qualityDelta);
            }
        } else { // regular item
            var qualityDelta = item.sellIn < 0 ? 2 : 1;
            item.quality = Math.max(0, item.quality - qualityDelta);
        }
    }
}
