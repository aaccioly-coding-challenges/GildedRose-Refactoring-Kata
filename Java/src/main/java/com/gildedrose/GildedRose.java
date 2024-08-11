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
        } else if (isAgedBrie(item.name)) {
            item.sellIn--;
            if (item.quality < 50) {
                var qualityDelta = item.sellIn < 0 ? 2 : 1;
                item.quality += qualityDelta;
            }
            return;
        } else if (isBackStagePass(item.name)) {
            item.sellIn--;
            if (item.sellIn < 0) {
                item.quality = 0;
            } else {
                if (item.quality < 50) {
                    var qualityDelta = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
                    item.quality += qualityDelta;
                }
            }
            return;
        }

        // all items except Sulfuras -> decrease sellIn
        item.sellIn = item.sellIn - 1;

        if (item.quality > 0) {
            var qualityDelta = item.sellIn < 0 ? 2 : 1;
            item.quality = item.quality - qualityDelta;
        }
    }
}
