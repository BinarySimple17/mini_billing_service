package ru.binarysimple.billng.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.binarysimple.billng.model.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {
//    Optional<Account> findByUsername(String username) ;
}