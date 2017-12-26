package org.example.calculator

import io.kotlintest.matchers.exactly
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.FunSpec

class CalculatorTest : FunSpec() {
    init {
        testAdd()
        testSubtract()
    }

    fun testAdd() = test("add returns the correct result") {
        val expected = 6.0

        add(2.0, 4.0) shouldBe exactly(expected)
    }

    fun testSubtract() = test("subtract returns the correct result") {
        val expected = 2.0

        subtract(4.0, 2.0) shouldBe exactly(expected)
    }
}
