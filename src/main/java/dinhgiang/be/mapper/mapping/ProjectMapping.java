package dinhgiang.be.mapper.mapping;

import dinhgiang.be.mapper.common.IgnoreRecordMapper;
import dinhgiang.be.mapper.common.UpdateMapper;
import dinhgiang.be.model.dto.request.project.ProjectUpdateRequest;
import dinhgiang.be.model.entity.project.Project;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProjectMapping extends
        UpdateMapper<Project, ProjectUpdateRequest>
{
    @Override
    @IgnoreRecordMapper
    void updateEntity(ProjectUpdateRequest request, @MappingTarget Project entity);
}
