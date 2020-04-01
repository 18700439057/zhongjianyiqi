package com.sibo.business.biz;

import com.sibo.business.enums.Dashboard;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.mapper.VAssetsReceiveMainMapper;
import com.sibo.business.mapper.VAssetsReceiveMapper;
import com.sibo.business.mapper.VBusinessFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 首页信息查询
 */
@Service
public class DashboardBiz {

    @Autowired
    private VInstrumentLibraryBiz vInstrumentLibraryBiz;



    public Integer queryCount(String type,String userId){
        if(Dashboard.WAIT_CONFIRM_COUNT_LOAN.getCode().equals(type)){
            return vInstrumentLibraryBiz.queryWaitConfirmCount(userId);
        }else if(Dashboard.WAIT_CONFIRM_COUNT_RETURN.getCode().equals(type)){
            return vInstrumentLibraryBiz.queryGiveBackAffirmCount(userId);
        }else if(Dashboard.REJECT.getCode().equals(type)){
            return vInstrumentLibraryBiz.queryRejectData(userId);
        }
        return 0;
    }
}
