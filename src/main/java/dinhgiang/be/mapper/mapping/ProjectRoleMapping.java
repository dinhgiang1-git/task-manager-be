package dinhgiang.be.mapper.mapping;

import dinhgiang.be.mapper.common.UpdateMapper;
import dinhgiang.be.model.dto.request.project.ProjectRoleUpdateRequest;
import dinhgiang.be.model.entity.project.ProjectRole;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProjectRoleMapping extends
        UpdateMapper<ProjectRole, ProjectRoleUpdateRequest>
{
    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntity(ProjectRoleUpdateRequest request, @MappingTarget ProjectRole entity);
}
