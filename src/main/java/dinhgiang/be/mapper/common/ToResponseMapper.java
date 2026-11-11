package dinhgiang.be.mapper.common;

import java.util.Collection;
import java.util.List;

public interface ToResponseMapper<E, R> {
    R toResponse(E entity);
    List<R> toResponseList(Collection<E> entity);
}
