package config;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import io.sentry.Sentry;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SentryInitializer implements ProjectActivity {

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        System.out.println("SentryInitializer initialized");
        Sentry.init(options -> {
            options.setDsn("https://c031595d3081e05d0ea5c28856cdb642@o4508217709035520.ingest.de.sentry.io/4508217713885264");
            options.setTracesSampleRate(1.0);
            options.setDebug(true);
        });
        return null;
    }
}