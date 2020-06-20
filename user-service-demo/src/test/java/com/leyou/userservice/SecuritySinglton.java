package com.leyou.userservice;

public class SecuritySinglton {

    private static volatile SecuritySinglton instance;
    public static SecuritySinglton getInstance(){
        if(instance == null) {
            synchronized (SecuritySinglton.class) {
                if (instance == null) {
                    instance = new SecuritySinglton();
                }
            }
        }
        Integer.compare(3,5);
        return instance;
    }

    @Override
    protected final CloneNotSupportedException clone() {
        return new CloneNotSupportedException();
    }

    private  SecuritySinglton(){
    }

}
