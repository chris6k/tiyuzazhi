package com.tiyuzazhi.api;

import com.tiyuzazhi.beans.Examiner;
import com.tiyuzazhi.beans.StatsDashboard;
import com.tiyuzazhi.beans.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chris.xue
 */
public class UserApi {

    /**
     * 读取当前用户信息
     *
     * @return
     */
    public static User getUserInfo() {
        //TODO
        return null;
    }

    /**
     * 读取当前用户身份
     *
     * @return
     */
    public static int loginRole() {
        //TODO
        return 0;
    }

    /**
     * 获取用户dashboard信息
     *
     * @return
     */
    public static StatsDashboard getUserDashboard() {
        //TODO
        return null;
    }

    /**
     * 根据条件返回评审员信息
     *
     * @param type
     * @param offset
     * @param count
     * @return
     */
    public static List<Examiner> getExaminer(int type, int offset, int count) {
        //TODO
        return new ArrayList<Examiner>(0);
    }


}
