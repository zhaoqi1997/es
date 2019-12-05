package com.zq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zhaoqi
 * @version 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class KeyWord {
    String name;
    String core;
}
