package coduo.compilerserver.domain.executionresult;

import static coduo.compilerserver.global.utils.Constants.SUCCESS_EXIT_CODE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class ExecutionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String executionId;
    private Integer exitCode;

    @Lob
    private String output;
    @Lob
    private String errorMessage;

    @Builder
    private ExecutionResult(
        String executionId,
        Integer exitCode,
        String output,
        String errorMessage
    ) {
        this.executionId = executionId;
        this.exitCode = exitCode;
        this.output = output;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return exitCode.equals(SUCCESS_EXIT_CODE);
    }
}
