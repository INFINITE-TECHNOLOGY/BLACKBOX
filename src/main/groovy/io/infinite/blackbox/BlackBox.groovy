package io.infinite.blackbox


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
     * BlackBox level
     * @return BlackBox level for transforming class/method/constructor.
     */
    BlackBoxLevel level()

    /**
     * When enabled, this will cause BlackBox to suppress exceptions
     * (not to rethrow them after logging)
     * @return
     */
    boolean suppressExceptions() default false

}