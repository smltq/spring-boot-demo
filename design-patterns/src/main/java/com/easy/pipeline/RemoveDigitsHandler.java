package com.easy.pipeline;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.IntPredicate;

@Slf4j
class RemoveDigitsHandler implements Handler<String, String> {
    @Override
    public String process(String input) {
        var inputWithoutDigits = new StringBuilder();
        var isDigit = (IntPredicate) Character::isDigit;
        input.chars()
                .filter(isDigit.negate())
                .mapToObj(x -> (char) x)
                .forEachOrdered(inputWithoutDigits::append);

        var inputWithoutDigitsStr = inputWithoutDigits.toString();
        log.info(
                String.format(
                        "Current handler: %s, input is %s of type %s, output is %s, of type %s",
                        RemoveDigitsHandler.class, input, String.class, inputWithoutDigitsStr, String.class
                )
        );

        return inputWithoutDigitsStr;
    }
}
