package org.tyaa.training.client.android.test.system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Универсальный класс для хранения значения
 * @param <T> тип значения
 * */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValueWrapper<T> {

    private T value;
}
