package com.leetcode.contest.task736;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

class Solution {

    public int evaluate(String expression) {
        Tokens tokens = getTokens(expression);

        Expression lispExpression = parseExpression(tokens);
        int value = lispExpression.evaluate(new EvaluationContext());

        return value;
    }

    private Tokens getTokens(String expression) {
        return new Lexer(expression).getTokens();
    }

    private Expression parseExpression(Tokens tokens) {
        return new RootParser().parseExpression(tokens);
    }

    private enum TokenType {
        OPEN_BRAСKET,
        CLOSE_BRACKET,
        LET,
        ADD,
        MULT,
        VAR,
        INT,
        SPACE
    }

    private static class Token {
        private final TokenType type;
        private final String value;

        private Token(TokenType type) {
            this(type, null);
        }

        private Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    private static class Tokens {
        private final List<Token> tokens;
        private int index;

        private Tokens(List<Token> tokens) {
            this.tokens = tokens;
        }

        public Token current() {
            return tokens.get(index);
        }

        public boolean next() {
            return ++index < tokens.size();
        }

        public boolean prev() {
            return --index >= 0;
        }

        public boolean isEnd() {
            return index == tokens.size();
        }

        public void skipSpace() {
            if (current().type == TokenType.SPACE) {
                next();
            }
        }

        public void skipCloseBracket() {
            if (current().type == TokenType.CLOSE_BRACKET) {
                next();
            }
        }
    }

    private static class Lexer {

        private static final Map<String, Token> keywords = new HashMap<>();

        static {
            keywords.put("add", new Token(TokenType.ADD));
            keywords.put("mult", new Token(TokenType.MULT));
            keywords.put("let", new Token(TokenType.LET));
        }

        private final String str;
        private int index;

        private Lexer(String str) {
            this.str = str;
        }

        public Tokens getTokens() {
            List<Token> tokens = new ArrayList<>();
            while (index < str.length()) {
                tokens.add(parseToken());
            }
            return new Tokens(tokens);
        }

        private Token parseToken() {
            Optional<Token> token;
            if ((token = parseOpenBracket()).isPresent()) {
                return token.get();
            } else if ((token = parseCloseBracket()).isPresent()) {
                return token.get();
            } else if ((token = parseSpace()).isPresent()) {
                return token.get();
            } else if ((token = parseInt()).isPresent()) {
                return token.get();
            } else if ((token = parseWord()).isPresent()) {
                return token.get();
            }
            throw new RuntimeException();
        }

        private char current() {
            return str.charAt(index);
        }

        private boolean next() {
            return ++index < str.length();
        }

        Optional<Token> parseOpenBracket() {
            if (current() == '(') {
                next();
                return Optional.of(new Token(TokenType.OPEN_BRAСKET));
            }
            return Optional.empty();
        }

        Optional<Token> parseCloseBracket() {
            if (current() == ')') {
                next();
                return Optional.of(new Token(TokenType.CLOSE_BRACKET));
            }
            return Optional.empty();
        }

        Optional<Token> parseSpace() {
            if (Character.isWhitespace(current())) {
                while (next() && Character.isWhitespace(current())) { }
                return Optional.of(new Token(TokenType.SPACE));
            }
            return Optional.empty();
        }

        Optional<Token> parseInt() {
            int startIndex = index;
            if (current() == '-' || Character.isDigit(current())) {
                while (next() && Character.isDigit(current())) {}
                String intValue = str.substring(startIndex, index);
                return Optional.of(new Token(TokenType.INT, intValue));
            }
            return Optional.empty();
        }

        Optional<Token> parseWord() {
            int startIndex = index;
            if (Character.isAlphabetic(current())) {
                while (next() && (Character.isAlphabetic(current()) || Character.isDigit(current()))) {}
                String word = str.substring(startIndex, index);
                if (keywords.containsKey(word)) {
                    return Optional.of(keywords.get(word));
                }
                return Optional.of(new Token(TokenType.VAR, word));
            }
            return Optional.empty();
        }
    }

    private class Scope {
        private final Map<String, Integer> varValues = new HashMap<>();

        public int getVarValue(String varName) {
            return varValues.get(varName);
        }

        public void setVarValue(String varName, int value) {
            varValues.put(varName, value);
        }

        public boolean hasVar(String varName) {
            return varValues.containsKey(varName);
        }
    }

    private class EvaluationContext {
        private final LinkedList<Scope> scopes = new LinkedList<>();

        public EvaluationContext() {
            scopes.addFirst(new Scope());
        }

        public int getVarValue(String varName) {
            for (Scope scope: scopes) {
                if (scope.hasVar(varName)) {
                    return scope.getVarValue(varName);
                }
            }
            throw new RuntimeException();
        }

        public void setVarValue(String varName, int value) {
            scopes.getFirst().setVarValue(varName, value);
        }

        public void pushScope() {
            scopes.addFirst(new Scope());
        }

        public void popScope() {
            scopes.removeFirst();
        }
    }

    private interface Expression {
        int evaluate(EvaluationContext context);
    }

    @FunctionalInterface
    private interface ExpressionParser {
        Optional<Expression> parse(RootParser parser, Tokens tokens);
    }

    private static class RootParser {
        private final List<ExpressionParser> parsers = new ArrayList<>();

        public RootParser() {
            parsers.add(IntExpression.parser());
            parsers.add(AddExpression.parser());
            parsers.add(MultExpression.parser());
            parsers.add(VarExpression.parser());
            parsers.add(LetExpression.parser());
        }

        public Expression parseExpression(Tokens tokens) {
            for (ExpressionParser parser: parsers) {
                Optional<Expression> parsedExpression = parser.parse(this, tokens);
                if (parsedExpression.isPresent()) {
                    return parsedExpression.get();
                }
            }
            throw new RuntimeException();
        }
    }

    private static class IntExpression implements Expression {
        private final int value;

        private IntExpression(int value) {
            this.value = value;
        }

        @Override
        public int evaluate(EvaluationContext context) {
            return value;
        }

        public static ExpressionParser parser() {
            return (parser, tokens) -> {
                if (tokens.current().type == TokenType.INT) {
                    IntExpression expression = new IntExpression(Integer.parseInt(tokens.current().value));
                    tokens.next();
                    return Optional.of(expression);
                }
                return Optional.empty();
            };
        }
    }

    private static class AddExpression implements Expression {
        private final Expression expr1;
        private final Expression expr2;

        private AddExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public int evaluate(EvaluationContext context) {
            return expr1.evaluate(context) + expr2.evaluate(context);
        }

        public static ExpressionParser parser() {
            return (parser, tokens) -> {
                if (tokens.current().type != TokenType.OPEN_BRAСKET) {
                    return Optional.empty();
                }

                tokens.next();

                if (tokens.current().type != TokenType.ADD) {
                    tokens.prev();
                    return Optional.empty();
                }

                tokens.next();

                tokens.skipSpace();
                Expression expr1 = parser.parseExpression(tokens);

                tokens.skipSpace();
                Expression expr2 = parser.parseExpression(tokens);

                tokens.skipCloseBracket();

                return Optional.of(new AddExpression(expr1, expr2));
            };
        }
    }

    private static class MultExpression implements Expression {
        private final Expression expr1;
        private final Expression expr2;

        private MultExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public int evaluate(EvaluationContext context) {
            return expr1.evaluate(context) * expr2.evaluate(context);
        }

        public static ExpressionParser parser() {
            return (parser, tokens) -> {
                if (tokens.current().type != TokenType.OPEN_BRAСKET) {
                    return Optional.empty();
                }

                tokens.next();

                if (tokens.current().type != TokenType.MULT) {
                    tokens.prev();
                    return Optional.empty();
                }

                tokens.next();

                tokens.skipSpace();
                Expression expr1 = parser.parseExpression(tokens);

                tokens.skipSpace();
                Expression expr2 = parser.parseExpression(tokens);

                tokens.skipCloseBracket();

                return Optional.of(new MultExpression(expr1, expr2));
            };
        }
    }

    private static class VarExpression implements Expression {
        private final String varName;

        private VarExpression(String varName) {
            this.varName = varName;
        }

        @Override
        public int evaluate(EvaluationContext context) {
            return context.getVarValue(varName);
        }

        public static ExpressionParser parser() {
            return (parser, tokens) -> {
                if (tokens.current().type == TokenType.VAR) {
                    VarExpression expression = new VarExpression(tokens.current().value);
                    tokens.next();
                    return Optional.of(expression);
                }
                return Optional.empty();
            };
        }
    }

    private static class LetExpression implements Expression {
        private final List<VarDefinition> varDefinitions;
        private final Expression result;

        private LetExpression(List<VarDefinition> varDefinitions, Expression result) {
            this.varDefinitions = varDefinitions;
            this.result = result;
        }

        @Override
        public int evaluate(EvaluationContext context) {
            context.pushScope();
            varDefinitions.forEach(varDefinition ->
                    context.setVarValue(varDefinition.varName, varDefinition.definition.evaluate(context))
            );

            int value = result.evaluate(context);

            context.popScope();
            return value;
        }

        private static class VarDefinition {
            private final String varName;
            private final Expression definition;

            private VarDefinition(String varName, Expression definition) {
                this.varName = varName;
                this.definition = definition;
            }
        }

        public static ExpressionParser parser() {
            return (parser, tokens) -> {
                if (tokens.current().type != TokenType.OPEN_BRAСKET) {
                    return Optional.empty();
                }

                tokens.next();

                if (tokens.current().type != TokenType.LET) {
                    tokens.prev();
                    return Optional.empty();
                }

                tokens.next();

                List<VarDefinition> varDefinitions = new ArrayList<>();
                while (true) {
                    tokens.skipSpace();
                    if (tokens.current().type != TokenType.VAR) {
                        break;
                    }

                    String varName = tokens.current().value;
                    tokens.next();

                    if (tokens.current().type == TokenType.CLOSE_BRACKET) {
                        tokens.skipCloseBracket();
                        return Optional.of(new LetExpression(varDefinitions, new VarExpression(varName)));
                    }

                    tokens.skipSpace();
                    Expression definition = parser.parseExpression(tokens);

                    varDefinitions.add(new VarDefinition(varName, definition));
                }

                Expression result = parser.parseExpression(tokens);

                tokens.skipCloseBracket();

                return Optional.of(new LetExpression(varDefinitions, result));
            };
        }
    }
}