package io.infinite.blackbox

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Main @BlackBox annotation interface
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki#blackbox-annotation">BlackBox Blueprint - @BlackBox Annotation</a>
 *
 * Applies to methods and contstuctors.
 *
 */
@Target([ElementType.METHOD, ElementType.CONSTRUCTOR])
@Retention(RetentionPolicy.RUNTIME)
@GroovyASTTransformationClass("io.infinite.blackbox.BlackBoxTransformation")
@interface BlackBox {

    /**
     * BlackBox level, default BlackBoxLevel.METHOD (can be overriden by System property "blackbox.level.default")
     * @return BlackBox level for transforming method/constructor.
     */
    BlackBoxLevel blackBoxLevel() default BlackBoxLevel.METHOD

    /**
     * When enabled, this will cause BlackBox with levels METHOD_ERROR and above to suppress exceptions
     * (not to rethrow them after logging)
     * @return
     */
    boolean suppressExceptions() default false

    ErrorLoggingStrategy strategy() default ErrorLoggingStrategy.UNDEFINED

}