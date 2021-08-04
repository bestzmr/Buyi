package com.bestzmr.buyi.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Merlin
 * @time: 2021/7/23 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractAuditBase {
    private Long id;
    private String name;
    private String description;
}
