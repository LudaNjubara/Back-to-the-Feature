package exception;

import lombok.Getter;

@Getter
public class GitException extends RuntimeException {
    private final String title;
    private String message = "";

    public GitException(String title) {
        super(title);
        this.title = title;
    }

    public GitException(String title, String message) {
        super(title);
        this.title = title;
        this.message = message;
    }
}
