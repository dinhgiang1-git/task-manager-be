package dinhgiang.be.mapper.common;

import org.mapstruct.MappingTarget;

public interface UpdateMapper<E, U> {
    void updateEntity(U request, @MappingTarget E entity);
}
