package org.dynmapblockscan.core.blockstate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelRotationTest {
    @Test
    void basic() {
        ModelRotation r = ModelRotation.getModelRotation(0, 90, 0);
        assertFalse(r.isDefault());
        assertEquals(90, r.rotY);
        assertTrue(ModelRotation.getModelRotation(0,0,0).isDefault());
        assertNotNull(r.toString());
    }
}
