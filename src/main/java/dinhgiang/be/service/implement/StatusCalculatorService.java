package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.model.entity.workitem.WorkItem;
import dinhgiang.be.model.entity.workitem.WorkItemAssignment;
import dinhgiang.be.model.entity.workitem.WorkItemStatus;
import dinhgiang.be.repository.WorkItemAssignmentRepository;
import dinhgiang.be.repository.WorkItemRepository;
import dinhgiang.be.repository.WorkItemStatusRepository;
import dinhgiang.be.service.interfaces.IStatusCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusCalculatorService implements IStatusCalculatorService {

    private final WorkItemRepository workItemRepository;
    private final WorkItemAssignmentRepository workItemAssignmentRepository;
    private final WorkItemStatusRepository workItemStatusRepository;

    @Override
    @Transactional
    public void recalculateStatus(Long workItemId) {
        if (workItemId == null) {
            return;
        }

        List<WorkItemStatus> statuses = workItemStatusRepository.findAll();
        Map<Long, String> statusIdToCode = statuses.stream()
                .collect(Collectors.toMap(WorkItemStatus::getId, WorkItemStatus::getCode));
        Map<String, Long> codeToStatusId = statuses.stream()
                .collect(Collectors.toMap(WorkItemStatus::getCode, WorkItemStatus::getId));

        recalculateInternal(workItemId, statusIdToCode, codeToStatusId, new HashSet<>());
    }

    private void recalculateInternal(
            Long workItemId,
            Map<Long, String> statusIdToCode,
            Map<String, Long> codeToStatusId,
            Set<Long> visitedIds) {

        // Chặn vòng lặp vô hạn nếu dữ liệu cây có cycle
        if (workItemId == null || !visitedIds.add(workItemId)) {
            return;
        }

        WorkItem item = workItemRepository.findById(workItemId).orElse(null);
        if (item == null || item.isClosed() || item.getArchivedAt() != null) {
            return;
        }

        List<WorkItem> activeChildren = workItemRepository
                .findAllByParentIdAndClosedFalseAndArchivedAtIsNull(workItemId);

        List<String> codes;
        if (!activeChildren.isEmpty()) {
            codes = activeChildren.stream()
                    .map(child -> statusIdToCode.get(child.getWorkItemStatusId()))
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            List<WorkItemAssignment> assignments = workItemAssignmentRepository.findByWorkItemIdOrderByIdAsc(workItemId);
            codes = assignments.stream()
                    .map(a -> statusIdToCode.get(a.getWorkItemStatusId()))
                    .filter(Objects::nonNull)
                    .toList();
        }

        String targetCode = aggregateStatusCodes(codes);
        Long newStatusId = codeToStatusId.get(targetCode);
        if (newStatusId == null) {
            throw new BusinessException("Không tìm thấy trạng thái " + targetCode);
        }

        if (!Objects.equals(item.getWorkItemStatusId(), newStatusId)) {
            String oldCode = statusIdToCode.get(item.getWorkItemStatusId());
            log.info("Cập nhật trạng thái việc {}: {} -> {}", item.getWorkItemCode(), oldCode, targetCode);

            item.setWorkItemStatusId(newStatusId);
            item.setEndedAt("DONE".equals(targetCode) ? Instant.now() : null);
            workItemRepository.save(item);

            if (item.getParentId() != null) {
                recalculateInternal(item.getParentId(), statusIdToCode, codeToStatusId, visitedIds);
            }
        }
    }

    private String aggregateStatusCodes(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return "NOT_STARTED";
        }
        if (codes.stream().allMatch("DONE"::equals)) {
            return "DONE";
        }
        if (codes.stream().allMatch("NOT_STARTED"::equals)) {
            return "NOT_STARTED";
        }

        boolean allUnfinishedPaused = codes.stream()
                .filter(c -> !"DONE".equals(c))
                .allMatch("PAUSED"::equals);
        if (allUnfinishedPaused) {
            return "PAUSED";
        }

        return "IN_PROGRESS";
    }
}
