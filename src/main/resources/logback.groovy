import java.nio.charset.Charset

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')
        pattern =   '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{56} - %msg%n'
    }
}

logger("groovyx.net.http", TRACE, ['STDOUT'], false)
logger("com.demo", TRACE, ['STDOUT'], false)
logger("io.micronaut.http", TRACE, ['STDOUT'], false)

root(INFO, ['STDOUT'])

