package org.dynmapblockscan.core;

import java.util.HashMap;
import java.util.Map;
import org.dynmapblockscan.core.BlockStateOverrides.BlockStateOverride;
import org.dynmapblockscan.core.BlockStateOverrides.BlockTintOverride;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlockStateOverridesTest {
    @Test
    void getOverride() {
        BlockStateOverrides o = new BlockStateOverrides();
        o.overrides = new HashMap<>();
        Map<String, BlockStateOverride> m = new HashMap<>();
        BlockStateOverride bo = new BlockStateOverride();
        bo.blockStateName = "foo";
        m.put("bar", bo);
        o.overrides.put("mc", m);
        assertEquals("foo", o.getOverride("mc", "bar").blockStateName);
        assertNull(o.getOverride("mc", "missing"));
        assertNull(o.getOverride("other", "bar"));
    }

    @Test
    void getTinting() {
        BlockStateOverrides o = new BlockStateOverrides();
        o.tinting = new HashMap<>();
        BlockTintOverride t = new BlockTintOverride();
        t.colormap = new String[]{"a.png"};
        o.tinting.put("mc", new HashMap<>());
        o.tinting.get("mc").put("bar", new BlockTintOverride[]{t});
        Map<String, String> props = new HashMap<>();
        assertEquals(t, o.getTinting("mc", "bar", props));
        assertNull(o.getTinting("mc", "nope", props));
    }

    @Test
    void merge() {
        BlockStateOverrides a = new BlockStateOverrides();
        a.overrides = new HashMap<>();
        a.tinting = new HashMap<>();
        BlockStateOverrides b = new BlockStateOverrides();
        b.overrides = new HashMap<>();
        b.tinting = new HashMap<>();
        Map<String, BlockStateOverride> bm = new HashMap<>();
        BlockStateOverride bo = new BlockStateOverride();
        bo.nameSuffix = "x";
        bm.put("blk", bo);
        b.overrides.put("mod", bm);
        a.merge(b);
        assertEquals("x", a.getOverride("mod", "blk").nameSuffix);
    }
}
