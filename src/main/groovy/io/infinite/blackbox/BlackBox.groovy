package io.infinite.blackbox

import io.infinite.carburetor.CarburetorLevel
import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 *
 * Applies to methods and contstuctors.
 *
 */
@Target([ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE])
@Retention(RetentionPolicy.RUNTIME)
@GroovyASTTransformationClass("io.infinite.blackbox.BlackBoxTransformation")
@interface BlackBox {

    /**
     * BlackBox level, default BlackBoxLevel.ERROR (can be overridden using Carburetor.json in working directory)
     * @return BlackBox level for transforming method/constructor.
     */
    CarburetorLevel level() default CarburetorLevel.METHOD

    /**
     * When enabled, this will cause BlackBox with levels METHOD_ERROR and above to suppress exceptions
     * (not to rethrow them after logging)
     * @return
     */
    boolean suppressExceptions() default false

}