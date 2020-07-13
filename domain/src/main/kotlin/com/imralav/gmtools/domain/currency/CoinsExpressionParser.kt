package com.imralav.gmtools.domain.currency

class CoinsExpressionParser(val expression: String) {
    data class ParsingResult(val operands: List<CoinExpressions>, val operators: List<Char>)

    fun parse(): CoinExpressions {
        var trimmedExpression = expression.removeWhitespace()
        while(trimmedExpression.contains("""[()]""".toRegex())) {
            val allSubexpressions = """\((?<expression>[.\d\w+\-*/]+)\)""".toRegex().findAll(trimmedExpression)
            allSubexpressions.forEach {
                val subexpression = it.groups["expression"]?.value ?: ""
                val parsedSubexpression = parseSingleExpression(subexpression).evaluate()
                trimmedExpression = trimmedExpression.replaceFirst("($subexpression)", parsedSubexpression.toString()).removeWhitespace()
            }
        }
        return parseSingleExpression(trimmedExpression)
    }

    private fun parseSingleExpression(expression: String): CoinExpressions {
        val operands = expression.split("""[+\-*/]""".toRegex()).map(::getOperand).toList()
        val operators = expression.replace("""[^+\-*/]""".toRegex(), "").toList()
        return parseForOperator(parseForOperator(operands, operators, '*', '/'), '+', '-').operands.first()
    }

    private fun getOperand(value: String): CoinExpressions {
        val doubleValue = value.toDoubleOrNull()
        return if (doubleValue == null) {
            CoinExpressions.CoinsExpr(value.toCoins())
        } else {
            CoinExpressions.ConstExpr(doubleValue)
        }
    }

    private fun parseForOperator(operands: List<CoinExpressions>, operators: List<Char>, vararg selectedOperators: Char): ParsingResult {
        require(operands.size == operators.size + 1) { "There should be one more operand than there are operators" }
        val mutableOperands = operands.toMutableList()
        val mutableOperators = operators.toMutableList()
        var offset = 0
        var operatorsConsumed = 0
        while (offset < operators.size) {
            val currentOperator = mutableOperators[offset - operatorsConsumed]
            if (currentOperator in selectedOperators) {
                val leftOperand = mutableOperands[offset - operatorsConsumed]
                val rightOperand = mutableOperands[offset + 1 - operatorsConsumed]
                mutableOperands.removeAt(offset - operatorsConsumed)
                mutableOperators.removeAt(offset - operatorsConsumed)
                when (currentOperator) {
                    '+' -> mutableOperands[offset - operatorsConsumed] = leftOperand + rightOperand
                    '-' -> mutableOperands[offset - operatorsConsumed] = leftOperand - rightOperand
                    '*' -> mutableOperands[offset - operatorsConsumed] = leftOperand * rightOperand
                    '/' -> mutableOperands[offset - operatorsConsumed] = leftOperand / rightOperand
                }
                operatorsConsumed++
            }
            offset++
        }
        return ParsingResult(mutableOperands, mutableOperators)
    }

    private fun parseForOperator(previousResult: ParsingResult, vararg selectedOperators: Char): ParsingResult {
        return parseForOperator(previousResult.operands, previousResult.operators, *selectedOperators)
    }
}

fun String.removeWhitespace() = this.trim().replace("""\s""".toRegex(), "")