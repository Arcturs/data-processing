package ru.itmo.pddp.asashina.eventgenerator.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventMessage {

    public String title;
    public Double reviewScore;
    public Long reviewTime;

}
