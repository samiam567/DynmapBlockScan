package org.dynmapblockscan.core.blockstate;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseConditionTest {
    @Test
    void stringConditionParsing() {
        BaseCondition c = new BaseCondition("normal");
        Map<String, String> props = new HashMap<>();
        assertTrue(c.matches(props));
        assertEquals("normal", c.toString());
    }

    @Test
    void commaSeparatedKeyValue() {
        BaseCondition c = new BaseCondition("facing=north,half=top");
        Map<String, String> props = new HashMap<>();
        props.put("facing", "north");
        props.put("half", "top");
        assertTrue(c.matches(props));
        props.put("half", "bottom");
        assertFalse(c.matches(props));
    }

    @Test
    void missingPropertyFails() {
        BaseCondition c = new BaseCondition("facing=north");
        assertFalse(c.matches(new HashMap<>()));
    }

    @Test
    void conditionalValues() {
        Map<String, String> m = new HashMap<>();
        m.put("color", "red|blue");
        BaseCondition c = new BaseCondition(m);
        Map<String, String> props = new HashMap<>();
        props.put("color", "red");
        assertTrue(c.matches(props));
        props.put("color", "blue");
        assertTrue(c.matches(props));
        props.put("color", "green");
        assertFalse(c.matches(props));
    }

    @Test
    void emptyConditionNormalOnly() {
        BaseCondition c = new BaseCondition("");
        // unmatchedValue becomes "" -> empty keyValuePairs -> matches only if unmatchedValue empty or normal
        assertTrue(c.matches(new HashMap<>()));
    }

    @Test
    void addPropKeys() {
        BaseCondition c = new BaseCondition("facing=north");
        java.util.Set<String> keys = new java.util.HashSet<>();
        c.addPropKeys(keys);
        assertTrue(keys.contains("facing"));
    }

    @Test
    void equality() {
        BaseCondition a = new BaseCondition("facing=north");
        BaseCondition b = new BaseCondition("facing=north");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
