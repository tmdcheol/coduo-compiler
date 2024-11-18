package coduo.compilerserver.domain.executor.process;

import static coduo.compilerserver.global.utils.Constants.*;

import coduo.compilerserver.domain.LanguageVersion;
import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.Project.SourceFile;
import coduo.compilerserver.domain.executor.ExecutionResult;
import coduo.compilerserver.domain.executor.LanguageExecutor;
import coduo.compilerserver.global.response.exception.GeneralException;
import coduo.compilerserver.global.response.status.ErrorStatus;
import coduo.compilerserver.global.utils.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractProcessLanguageExecutor implements LanguageExecutor {

    protected Path createDirectory(LanguageVersion languageVersion) {
        try {
            Path workingDirectoryPath = getWorkingDirectoryPath(languageVersion);
            return Files.createTempDirectory(workingDirectoryPath, FINAL_WORKING_DIR_NAME_PREFIX);
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus.CREATE_DIRECTORY_ERROR, e);
        }
    }

    protected void saveFiles(Project project, Path workingDirectory) {
        try {
            for (SourceFile sourceFile : project.files()) {
                saveOne(sourceFile, workingDirectory);
            }
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus.SAVE_FILE_ERROR, e);
        }
    }

    protected ExecutionResult runProcess(
            String command,
            Path workingDirectory,
            String executionId
    ) {
        StringBuilder outputBuilder = new StringBuilder();
        StringBuilder errorBuilder = new StringBuilder();

        try {
            Integer exitCode = run(
                    command, workingDirectory,
                    outputBuilder, errorBuilder
            );

            return createExecutionResult(
                    exitCode,
                    executionId,
                    outputBuilder,
                    errorBuilder
            );

        } catch (InterruptedException | IOException e) {
            throw new GeneralException(ErrorStatus.PROCESS_EXECUTION_ERROR, e);
        }
    }

    private Integer run(
            String command,
            Path workingDirectory,
            StringBuilder outputBuilder,
            StringBuilder errorBuilder
    ) throws IOException, InterruptedException {

        String[] commandArray = command.split(SPACE);
        ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
        processBuilder.directory(workingDirectory.toFile());
        Process process = processBuilder.start();
        log.debug("process id : {}", process.pid());

        /**
         * 스트림을 별개의 스레드로 두어서 메인 스레드가 blocking 되지 않도록
         */
        Thread outputHandleThread = new Thread(
                () -> readStream(process.getInputStream(), outputBuilder)
        );
        outputHandleThread.start();

        Thread errorHandleThread = new Thread(
                () -> readStream(process.getErrorStream(), errorBuilder)
        );
        errorHandleThread.start();

        try {
            return waitProcessWithTimeout(
                    process,
                    Constants.PROCESS_TIME_OUT_SECOND,
                    TimeUnit.SECONDS
            );
        } finally {
            outputHandleThread.join();
            errorHandleThread.join();
        }
    }

    private void readStream(InputStream inputStream, StringBuilder builder) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(NEW_LINE);
            }
        } catch (IOException e) {
            log.error("Error reading stream", e);
        }
    }

    private int waitProcessWithTimeout(Process process, long timeout, TimeUnit timeUnit)
            throws InterruptedException {
        boolean isFinished = process.waitFor(timeout, timeUnit);
        if (!isFinished) {
            process.destroyForcibly();
            log.info("프로세스 타임아웃이 발생했으며, 강제종료를 수행합니다.");
            throw new GeneralException(ErrorStatus.PROCESS_TIMEOUT_ERROR);
        }
        return process.exitValue();
    }


    private Path getWorkingDirectoryPath(LanguageVersion languageVersion) {
        String language = languageVersion.getLanguage();
        String homeDirectory = System.getProperty(HOME_DIRECTORY_KEY);
        return Path.of(homeDirectory, WORKING_DIR_PREFIX_PATH, language);
    }

    private void saveOne(SourceFile sourceFile, Path workingDirectory) throws IOException {
        Path savePath = workingDirectory.resolve(sourceFile.path());
        Files.createDirectories(savePath.getParent());
        Files.writeString(savePath, sourceFile.content());
        log.debug("saved file path: {}", savePath);
    }

    private ExecutionResult createExecutionResult(
            Integer exitCode,
            String executionId,
            StringBuilder outputBuilder,
            StringBuilder errorBuilder
    ) {
        if (exitCode.equals(SUCCESS_EXIT_CODE)) {
            return ExecutionResult.builder()
                    .executionId(executionId)
                    .exitCode(exitCode)
                    .output(outputBuilder.toString())
                    .build();
        }

        return ExecutionResult.builder()
                .executionId(executionId)
                .exitCode(exitCode)
                .errorMessage(errorBuilder.toString())
                .build();
    }


    @Override
    public abstract ExecutionResult execute(Project project, String executor);

    @Override
    public abstract boolean supports(LanguageVersion languageVersion);

}
