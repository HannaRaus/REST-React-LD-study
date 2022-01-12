package com.ld.services;

import com.ld.exceptions.EntityNotFoundException;
import com.ld.model.Tag;
import com.ld.repositories.TagRepository;
import com.ld.validation.ValidateTagRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService extends CrudService<Tag> {

    private final TagRepository repository;

    @Override
    protected JpaRepository<Tag, UUID> getRepository() {
        return repository;
    }

    public void save(ValidateTagRequest request) {
        log.info("TagService.save - Saving tag from request '{}", request);
        super.save(Tag.builder()
                .label(request.getLabel())
                .build());
    }

    public Tag findByLabel(String label) {
        log.info("TagService.findByLabel - Searching tag by label '{}", label);
        Optional<Tag> tag = repository.findByLabel(label);
        if (tag.isEmpty()) {
            log.error("TagService.findByLabel - Tag with label '{}' doesn't exist", label);
            throw new EntityNotFoundException(String.format("Tag with Label [%s] doesn't exist", label));
        }
        return tag.get();
    }

    public List<Tag> findSameTags(String label) {
        log.info("TagService.findSameTags - Searching same tags similar to label '{}", label);
        List<Tag> tags = repository.findByLabelContains(label);
        if (tags.isEmpty()) {
            log.error("TagService.findSameTags - There is no tags with same label '{}'", label);
            throw new EntityNotFoundException(String.format("Tag with Label [%s] doesn't exist", label));
        }
        return tags;
    }

    public boolean tagAlreadyExists(String label) {
        log.info("TagService.tagAlreadyExists - Checking if tag is already exist with label '{}", label);
        try {
            findByLabel(label);
        } catch (EntityNotFoundException ex) {
            return false;
        }
        return true;
    }

    public boolean isTagUnique(String label) {
        log.info("TagService.tagAlreadyExists - Checking if tag is unique with label '{}", label);
        List<Tag> tags;
        try {
            tags = findSameTags(label);
        } catch (EntityNotFoundException exception) {
            return true;
        }
        return isNull(tags) || tags.isEmpty();
    }
}
