package coduo.compilerserver.global.session.executionidgenerator;

import java.util.UUID;

public class UUIDExecutionIdGenerator implements ExecutionIdGenerator {

    @Override
    public String generateExecutionId() {
        return UUID.randomUUID().toString();
    }
}
