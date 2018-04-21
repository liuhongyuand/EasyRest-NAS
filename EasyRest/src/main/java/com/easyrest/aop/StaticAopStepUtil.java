package com.easyrest.aop;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StaticAopStepUtil {

    private static List<AopPreCommitStep> AOP_PRE_COMMIT_STEP_LIST = new ArrayList<>();

    private static List<AopPostCommitStep> AOP_POST_COMMIT_STEP_LIST = new ArrayList<>();

    public void setAopPreCommitStepList(List<AopPreCommitStep> aopPreCommitStepList) {
        AOP_PRE_COMMIT_STEP_LIST = aopPreCommitStepList;
    }

    public void setAopPostCommitStepList(List<AopPostCommitStep> aopPostCommitStepList){
        AOP_POST_COMMIT_STEP_LIST = aopPostCommitStepList;
    }

    public static List<AopPreCommitStep> getAopPreCommitStepList() {
        return AOP_PRE_COMMIT_STEP_LIST;
    }

    public static List<AopPostCommitStep> getAopPostCommitStepList(){
        return AOP_POST_COMMIT_STEP_LIST;
    }

}
