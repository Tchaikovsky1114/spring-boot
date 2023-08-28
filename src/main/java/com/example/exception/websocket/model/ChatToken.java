package com.example.exception.websocket.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class ChatToken {
    private Set<String> tokenList;

    public void addToken(String token) {
        tokenList.add(token);
    }
    public void removeToken(String token) {
        tokenList.remove(token);
    }
    public String findToken(String token) {

        return tokenList.stream()
                .filter(it -> Objects.equals(it, token))
                .findFirst()
                .orElse(null);
    }
}
