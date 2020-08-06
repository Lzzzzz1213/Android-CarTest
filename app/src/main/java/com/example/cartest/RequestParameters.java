package com.example.cartest;

/**
 * @author 李可
 * @date 2020年6月12日
 * @description 返回链接参数（参数设置规范以数据接口为准）
 */
public class RequestParameters {
    //驾照类型 0-5
    private final String[] model = {"a1", "a2", "b1", "b2", "c1", "c2"};
    //科目1，科目4 0-1
    private final String[] subject = {"1", "4"};
    //题库顺序 0-1
    private final String[] testType = {"rand", "order"};

    public String getModel(int i) {
        return model[i];
    }

    public String getSubject(int i) {
        return subject[i];
    }

}
