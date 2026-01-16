package ru.binarysimple.billng.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.binarysimple.billng.dto.OperationDto;
import ru.binarysimple.billng.dto.OperationRequest;
import ru.binarysimple.billng.model.Operation;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OperationMapper {
    Operation toEntity(OperationDto operationDto);

    OperationDto toOperationDto(Operation operation);

    Operation toEntity(OperationRequest operationRequest);

    OperationRequest toOperationRequest(Operation operation);
}