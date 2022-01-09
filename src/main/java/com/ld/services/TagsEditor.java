package com.ld.services;

import com.ld.model.Tag;
import lombok.RequiredArgsConstructor;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class TagsEditor extends PropertyEditorSupport {

    private final TagService service;

    @Override
    public String getAsText() {
        if (isNull(getValue())) {
            return "";
        }
        Tag tag = (Tag) getValue();
        return tag.getLabel();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!isNull(text)) {
            Tag tag = service.findByLabel(text);
            this.setValue(tag);
        }
    }
}
