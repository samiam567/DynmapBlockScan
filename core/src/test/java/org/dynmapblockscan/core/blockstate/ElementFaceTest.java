package org.dynmapblockscan.core.blockstate;

import org.dynmap.modsupport.BlockSide;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ElementFaceTest {
    @Test
    void byFaceAndSide() {
        assertEquals(ElementFace.UP, ElementFace.byFace("up"));
        assertNull(ElementFace.byFace("nope"));
        assertEquals(ElementFace.UP, ElementFace.bySide(BlockSide.TOP));
        assertNull(ElementFace.bySide(null));
    }

    @Test
    void valuesCount() {
        assertEquals(6, ElementFace.values().length);
    }
}
