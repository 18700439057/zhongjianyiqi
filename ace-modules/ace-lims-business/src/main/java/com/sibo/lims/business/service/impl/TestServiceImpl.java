package com.sibo.lims.business.service.impl;

import com.sibo.lims.business.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String test() {
        return "hello lims";
    }
}
