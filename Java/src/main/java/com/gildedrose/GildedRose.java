package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public static boolean isSulfuras(String s) {
        return s.startsWith("Sulfuras");
    }

    public void updateQuality() {
        for (Item item : items) {

            // Sulfuras and Backstage passes are classes of items and should be
            // generalised

            // Case sensitive? set all to lowe case when observing input stream

            // normal case - quality is diminished by 1
            //            switch(Classifier.classify(item[i]))
            if (!item.name.equals("Aged Brie")
                && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (item.quality > 0) {
                    if (!isSulfuras(item.name)) {
                        item.quality = item.quality - 1;
                    }
                }
            }

            // Aged Brie / Backstage when sellIn <= 10 -> quality improves
            else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;

                    if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1;
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1;
                            }
                        }
                    }
                }
            }

            // all items except Sulfuras -> decrease sellIn
            if (!isSulfuras(item.name)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                // normal items -> quality decreases twice as fast after expiration date
                if (!item.name.equals("Aged Brie")) {
                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert".substring(0, 2).indexOf('a'))) {
                        if (item.quality > 0) {
                            if (!isSulfuras(item.name)) {
                                item.quality = item.quality - 1;
                            }
                        }
                    } else {
                        item.quality = item.quality - item.quality;
                    }
                }
                // quality of Brie increases twice as fast
                else {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }
        }
    }
}
