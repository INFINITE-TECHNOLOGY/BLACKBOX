package io.infinite.blackbox

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Main @BlackBox annotation interface
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#blackbox-annotation">BlackBox Blueprint - @BlackBox Annotation</a>
 *
 * Applies to methods and contstuctors.
 *
 */
@Target([ElementType.METHOD, ElementType.CONSTRUCTOR])
@Retention(RetentionPolicy.RUNTIME)
@GroovyASTTransformationClass("io.infinite.blackbox.BlackBoxTransformation")
@interface BlackBox {

    /**
     * BlackBox level, default BlackBoxLevel.METHOD
     * @return BlackBox level for transforming method/constructor.
     */
    BlackBoxLevel blackBoxLevel() default BlackBoxLevel.METHOD

}