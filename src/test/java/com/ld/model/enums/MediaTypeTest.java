package com.ld.model.enums;

import com.ld.error_handling.exceptions.InvalidAccessTypeException;
import com.ld.error_handling.exceptions.InvalidMediaTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MediaTypeTest {

    @Test
    public void isAccessType() {
        assertTrue(MediaType.isMediaType("video"));
        assertTrue(MediaType.isMediaType("VIDEO"));
        assertTrue(MediaType.isMediaType("ViDEO"));
        assertTrue(MediaType.isMediaType("  viDeo  "));
        assertTrue(MediaType.isMediaType("image"));
        assertTrue(MediaType.isMediaType("IMAGE"));
        assertTrue(MediaType.isMediaType("IMagE"));
        assertTrue(MediaType.isMediaType("  imagE  "));

        assertFalse(MediaType.isMediaType("vidio"));
        assertFalse(MediaType.isMediaType("Im  age"));
        assertFalse(MediaType.isMediaType("11vid0"));
        assertFalse(MediaType.isMediaType("1mag3"));

        assertFalse(MediaType.isMediaType(""));
        assertFalse(MediaType.isMediaType(null));
    }

    @Test
    public void ofName() {
        assertEquals(MediaType.VIDEO, MediaType.ofName("video"));
        assertEquals(MediaType.VIDEO, MediaType.ofName("VIDEO"));
        assertEquals(MediaType.VIDEO, MediaType.ofName("VIdeO"));
        assertEquals(MediaType.VIDEO, MediaType.ofName("  viDEO  "));
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.ofName("pvideo11"));

        assertEquals(MediaType.IMAGE, MediaType.ofName("image"));
        assertEquals(MediaType.IMAGE, MediaType.ofName("IMAGE"));
        assertEquals(MediaType.IMAGE, MediaType.ofName("iMAGE"));
        assertEquals(MediaType.IMAGE, MediaType.ofName("  IMage  "));

        assertThrows(InvalidMediaTypeException.class, () -> MediaType.ofName("image11"));
        assertThrows(InvalidMediaTypeException.class, () -> MediaType.ofName(""));
        assertThrows(InvalidMediaTypeException.class, () -> MediaType.ofName(null));
    }

}