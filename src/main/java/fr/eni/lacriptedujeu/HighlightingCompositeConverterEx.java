package fr.eni.lacriptedujeu;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        if (message != null && message.contains("[SUCCESS]")) {
            return ANSIConstants.BOLD + ANSIConstants.GREEN_FG;
        }
        Level level = event.getLevel();
        return switch (level.toInt()) {
            case Level.ERROR_INT -> ANSIConstants.BOLD + ANSIConstants.RED_FG;
            case Level.WARN_INT -> ANSIConstants.BOLD + ANSIConstants.YELLOW_FG;
            case Level.INFO_INT -> ANSIConstants.BOLD + ANSIConstants.BLUE_FG;
            case Level.DEBUG_INT -> ANSIConstants.YELLOW_FG;
            case Level.TRACE_INT -> ANSIConstants.MAGENTA_FG;
            default -> ANSIConstants.DEFAULT_FG;
        };
    }
}
