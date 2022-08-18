package com.easy.pipeline;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.Arrays;

@Slf4j
class ConvertToCharArrayHandler implements Handler<String, char[]> {
    @Override
    public char[] process(String input) {
        var characters = input.toCharArray();
        var string = Arrays.toString(characters);
        log.info(
                String.format("Current handler: %s, input is %s of type %s, output is %s, of type %s",
                        ConvertToCharArrayHandler.class, input, String.class, string, Character[].class)
        );

        return characters;
    }
}
