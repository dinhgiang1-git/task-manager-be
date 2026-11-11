package dinhgiang.be.mapper.common;

public interface ToEntityMapper <E, C> {
    E toEntity(C request);
}
