package dinhgiang.be.mapper.mapping;

import dinhgiang.be.mapper.common.UpdateMapper;
import dinhgiang.be.model.dto.request.project.ProjectPermissionUpdateRequest;
import dinhgiang.be.model.entity.project.ProjectPermission;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProjectPermissionMapping extends
        UpdateMapper<ProjectPermission, ProjectPermissionUpdateRequest>
{
    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntity(ProjectPermissionUpdateRequest request, @MappingTarget ProjectPermission entity);
}
