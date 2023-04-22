package com.mpc.spellcaster.util

import groovy.json.JsonSlurper

class SpelCasterParser {

    String parseJsonToSpel(String jsonExpression) {
        def jsonSlurper = new JsonSlurper() as Object
        def parsedJson = jsonSlurper.parseText(jsonExpression)
        return buildSpelExpressionString(parsedJson.expression)
    }

    private String buildSpelExpressionString(def expressionNode) {
        switch (expressionNode.type) {
            case 'Literal':
                return expressionNode.value.toString()
            case 'Variable':
                return "#${expressionNode.value}"
            case 'MethodCall':
                def args = expressionNode.arguments.collect { buildSpelExpressionString(it) }.join(', ')
                return "${buildSpelExpressionString(expressionNode.target)}.${expressionNode.method}(${args})"
            case 'BeanReference':
                return "@${expressionNode.value}"
            case 'BinaryOperation':
                return "(${buildSpelExpressionString(expressionNode.leftOperand)} ${expressionNode.operator} ${buildSpelExpressionString(expressionNode.rightOperand)})"
            case 'UnaryOperation':
                return "${expressionNode.operator}${buildSpelExpressionString(expressionNode.operand)}"
            case 'TernaryOperation':
                return "(${buildSpelExpressionString(expressionNode.condition)} ? ${buildSpelExpressionString(expressionNode.trueExpression)} : ${buildSpelExpressionString(expressionNode.falseExpression)})"
            case 'PropertyAccess':
                def baseExpression = "${buildSpelExpressionString(expressionNode.target)}.${expressionNode.property}"
                return expressionNode.condition ? "${baseExpression}.?(${buildSpelExpressionString(expressionNode.condition)})" : baseExpression
            case 'IndexAccess':
                return "${buildSpelExpressionString(expressionNode.target)}[${buildSpelExpressionString(expressionNode.key)}]"
            default:
                throw new UnsupportedOperationException("Unsupported expression node type: ${expressionNode.type}")
        }
    }


}
