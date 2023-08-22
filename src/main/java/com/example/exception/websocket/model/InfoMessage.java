package com.example.exception.websocket.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoMessage {

    private InfoMessageType type;
    private String comment;

}
