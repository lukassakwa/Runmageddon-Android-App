package com.olivier.jasmedapp.presenter;

public abstract class BasePresenter<T> {

    protected T view;

    public void attach(T view){
        this.view = view;
    }

    public void detach(){
        this.view = null;
    }

    public boolean isAttached(){
        return this.view != null;
    }
}
