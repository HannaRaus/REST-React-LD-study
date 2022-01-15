package com.ld.model.enums;

import com.ld.error_handling.exceptions.InvalidAccessTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccessTypeTest {

    @Test
    public void isAccessType() {
        assertTrue(AccessType.isAccessType("public"));
        assertTrue(AccessType.isAccessType("PUBLIC"));
        assertTrue(AccessType.isAccessType("PubLIC"));
        assertTrue(AccessType.isAccessType("  public  "));
        assertTrue(AccessType.isAccessType("private"));
        assertTrue(AccessType.isAccessType("PRIVATE"));
        assertTrue(AccessType.isAccessType("PriVate"));
        assertTrue(AccessType.isAccessType("  private  "));

        assertFalse(AccessType.isAccessType("piblic"));
        assertFalse(AccessType.isAccessType("pib  l ic"));
        assertFalse(AccessType.isAccessType("11Jjj"));
        assertFalse(AccessType.isAccessType("privit"));

        assertFalse(AccessType.isAccessType(""));
        assertFalse(AccessType.isAccessType(null));
    }

    @Test
    public void ofName() {
        assertEquals(AccessType.PUBLIC, AccessType.ofName("public"));
        assertEquals(AccessType.PUBLIC, AccessType.ofName("PUBLIC"));
        assertEquals(AccessType.PUBLIC, AccessType.ofName("PUbLIC"));
        assertEquals(AccessType.PUBLIC, AccessType.ofName("  PUbLIC  "));
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.ofName("public11"));

        assertEquals(AccessType.PRIVATE, AccessType.ofName("private"));
        assertEquals(AccessType.PRIVATE, AccessType.ofName("PRIVATE"));
        assertEquals(AccessType.PRIVATE, AccessType.ofName("PriVATe"));
        assertEquals(AccessType.PRIVATE, AccessType.ofName("  PRivatE  "));

        assertThrows(InvalidAccessTypeException.class, () -> AccessType.ofName("public11"));
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.ofName(""));
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.ofName(null));
    }

}