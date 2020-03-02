package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 */
public class VerifyCarrierPersonParams {
  private String  name;//	false	string	姓名
  private String  email;//	false	string	邮箱
  private String  idnumber;//	false	string	身份证

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
