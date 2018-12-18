package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class RoundRobin<Type> extends ArrayList<Type> {

    private Integer index = 0

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    @Override
    Iterator<Type> iterator() {
        return new Iterator<Type>() {

            @Override
            boolean hasNext() {
                return true
            }

            @Override
            Type next() {
                Type result = get(index)
                index = ((index + 1) % RoundRobin.this.size())
                return result
            }

            @Override
            void remove() {
                throw new Exception("Unable to remove from RoundRobin")
            }

        }
    }

}