package coduo.compilerserver.domain;

import static coduo.compilerserver.global.utils.Constants.PATH_SEPARATOR;

import java.util.List;

public record Project(
    LanguageVersion languageVersion,
    List<SourceFile> files,
    String entryPoint
) {

    public record SourceFile(
        String path,
        String content
    ) {

        public String getFileName() {
            return path.substring(path.lastIndexOf(PATH_SEPARATOR) + 1);

        }

    }
}
