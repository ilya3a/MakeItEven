package com.yoyo.makeiteven;

public class StageInfo {

    public int getNum1() {
        return num1;
    }

    public StageInfo(int num1, int num2, int num3, int num4, int target, String hint) {
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.target = target;
        this.hint = hint;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    public void setNum4(int num4) {
        this.num4 = num4;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum3() {
        return num3;
    }

    public int getNum4() {
        return num4;
    }

    public int getTarget() {
        return target;
    }

    public String getHint() {
        return hint;
    }

    private int num1,num2,num3,num4,target;
    private String hint;
}
