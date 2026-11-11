package dinhgiang.be.mapper.common;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Target({METHOD, ANNOTATION_TYPE})
@Retention(CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "updateAt", ignore = true)
@Mapping(target = "updateBy", ignore = true)
public @interface IgnoreRecordMapper {
}
