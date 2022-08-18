package com.easy.pipeline;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.IntPredicate;

@Slf4j
class RemoveAlphabetsHandler implements Handler<String, String> {

    @Override
    public String process(String input) {
        var inputWithoutAlphabets = new StringBuilder();
        var isAlphabetic = (IntPredicate) Character::isAlphabetic;
        input.chars()
                .filter(isAlphabetic.negate())
                .mapToObj(x -> (char) x)
                .forEachOrdered(inputWithoutAlphabets::append);

        var inputWithoutAlphabetsStr = inputWithoutAlphabets.toString();
        log.info(
                String.format(
                        "Current handler: %s, input is %s of type %s, output is %s, of type %s",
                        RemoveAlphabetsHandler.class, input,
                        String.class, inputWithoutAlphabetsStr, String.class
                )
        );

        return inputWithoutAlphabetsStr;
    }
}
