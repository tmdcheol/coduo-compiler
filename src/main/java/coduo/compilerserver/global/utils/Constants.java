package coduo.compilerserver.global.utils;

public final class Constants {

    // session
    public static final String EXECUTION_KEY = "EXECUTION_KEY";

    // command
    public static final String JAVASCRIPT_COMMAND = "node %s";
    public static final String CPP_COMPILE_COMMAND = "g++ %s -o %s";
    public static final String CPP_CHMOD_COMMAND = "chmod +x %s";
    public static final String CPP_RUN_COMMAND = "%s";
    public static final String CPP_EXECUTION_FILE_NAME = "output";


    // extension
    public static final String CPP_EXTENSION = "cpp";
    public static final String JAVASCRIPT_EXTENSION = "js";

    // execution
    public static final Integer SUCCESS_EXIT_CODE = 0;
    public static final Long PROCESS_TIME_OUT_SECOND = 10L;
    public static final String HOME_DIRECTORY_KEY = "user.home";
    public static final String WORKING_DIR_PREFIX_PATH = "project/code-file";
    public static final String FINAL_WORKING_DIR_NAME_PREFIX = "compile-project-";

    // general
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String PATH_SEPARATOR = "/";

}
