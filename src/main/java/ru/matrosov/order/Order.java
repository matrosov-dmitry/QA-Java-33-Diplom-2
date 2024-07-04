package ru.matrosov.order;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order
    {
        public final List<String> ingredients;
    }
