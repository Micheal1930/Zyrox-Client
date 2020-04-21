package com.varrock.client.cache.maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Load oldschool maps & objects from the cache directly from this file.
 * It will grab the oldschool map files from the osrs maps cache index.
 * Depending on what regions are listed here, oldschool objects will be loaded (reg id) + 100,000
 * Region ids are used to load both the objects and maps
 * @author Jonny
 */
public class OldschoolMaps {

    private static final Map<Integer, Integer> OLDSCHOOL_REGIONS = new HashMap<>();

    public static int[] OLDSCHOOL_REGION_IDS = {
            5947, //south of blast mine
            8536, //demonic gorillas
            8280, //demonic gorillas
            8789,
            6457, // Great Kourand

            12867, //tob treasure

            10894, //monkey skeletons
            11150, //monkey skeletons

            12342,

            9619,
            14386, 14642,
            4919, 5022, 5023, 5279, 5280, 5535, 5945,
            5946, 6201, 5536, 4663, 6810, 9023, 9043, 11850, 11851, 12090, 12106, 12362, 12363, 12347, 12605, 12611,
            12701, 12702, 12703, 12861, 12889, 12957, 12958, 12959, 12961,
            /* Cerberus regions */
            4626, 4627, 46728, 4882, 4883, 4884, 5138, 5139, 5140,

            7987, 8243, 7986, 8242,

            12192, 7226,

            6461,

            11837, //wildy agility
            13972,

    };
    static {
        for(Integer region : OLDSCHOOL_REGION_IDS) {
            OLDSCHOOL_REGIONS.put(region, -1);
        }
    }

    public static boolean isOldschoolRegion(int regionId) {
        return OLDSCHOOL_REGIONS.get(regionId) != null;
    }
}
