package com.hnu.wechatorder.util;

import com.hnu.wechatorder.view.ResultVO;

/**
 * @Classname ResultUtil
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/23 17:33
 */
public class ResultUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}