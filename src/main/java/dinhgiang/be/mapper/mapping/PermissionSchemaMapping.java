package dinhgiang.be.mapper.mapping;

import dinhgiang.be.mapper.common.ToResponseMapper;
import dinhgiang.be.model.dto.response.project.PermissionSchemaResponse;
import dinhgiang.be.model.entity.project.PermissionSchema;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PermissionSchemaMapping extends ToResponseMapper<PermissionSchema, PermissionSchemaResponse> {

    @Override
    PermissionSchemaResponse toResponse(PermissionSchema entity);

    @Override
    List<PermissionSchemaResponse> toResponseList(Collection<PermissionSchema> entity);
}
