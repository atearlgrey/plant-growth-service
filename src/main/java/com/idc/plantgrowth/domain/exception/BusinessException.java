package com.idc.plantgrowth.domain.exception;

import com.idc.plantgrowth.domain.model.common.ErrorCode;

public class BusinessException extends BaseException {

    public BusinessException(ErrorCode code) {
        super(code);
    }

    public BusinessException(ErrorCode code, String message) {
        super(code, message);
    }
}
