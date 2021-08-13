package com.bestzmr.buyi.system.exception;

import java.util.Map;

/**
 * @author Merlin
 */
public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(Map<String, Object> data) {
        super(ErrorCode.Role_NOT_FOUND, data);
    }
}
