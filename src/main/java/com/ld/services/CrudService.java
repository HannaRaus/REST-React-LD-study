package com.ld.services;

import com.ld.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public abstract class CrudService<T> {

    protected abstract JpaRepository<T, UUID> getRepository();

    public void save(T entity) {
        log.info("CrudService.save() - entity" + entity);
        getRepository().save(entity);
    }

    public T read(UUID id) {
        log.info("CrudService.read() - id" + id);
        return getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no object with such id -" + id));
    }

    public List<T> readAll() {
        log.info("CrudService.readAll()");
        return getRepository().findAll();
    }

    public T update(T entity) {
        log.info("CrudService.update() - entity" + entity);
        return getRepository().save(entity);
    }

    public void delete(UUID id) {
        log.info("CrudService.delete() - id" + id);
        getRepository().deleteById(id);
    }
}