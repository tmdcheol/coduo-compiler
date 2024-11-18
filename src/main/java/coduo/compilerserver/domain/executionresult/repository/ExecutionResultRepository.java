package coduo.compilerserver.domain.executionresult.repository;

import coduo.compilerserver.domain.executionresult.ExecutionResult;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionResultRepository extends JpaRepository<ExecutionResult, Long> {

    Optional<ExecutionResult> findByExecutionId(String executionId);

}
